package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;

import org.json.JSONObject;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

public class MainActivity extends Activity {

    private ContentResolver contentResolver;
    TabHost tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PackageInfo infoApp;
        try {
            infoApp = getPackageManager().getPackageInfo(getPackageName(), 0);
            setTitle(getTitle() + " V. " + infoApp.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        contentResolver = getContentResolver();

        this.obtenerVistas();
        //this.realizarAcciones();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Vivienda vivienda = ViviendaFragment.getVivienda();

            if(vivienda.getId() != 0)
            {
                if (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA
                        .getValor()) {
                    finish();
                }
                else
                {
                    if(vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                            && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INCOMPLETA.getValor())
                    {
                        getVisitastAlert(vivienda).show();
                    }
                    else
                    {
                        getExitAlert().show();
                    }
                }
            }
            else
            {
                getExitAlert().show();
            }
        }
        return false;
    }

    /**
     *
     * @param vivienda
     * @return
     */
    private AlertDialog getVisitastAlert(final Vivienda vivienda) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Esta seguro que desea salir?")
                .setTitle(R.string.confirmacion_aviso);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                vivienda.setNumerovisitas(vivienda.getNumerovisitas() + 1);
                int filasAfectadas = ViviendaDao.update(contentResolver, vivienda);

                if(vivienda.getNumerovisitas() < Global.NUMERO_VISITAS_MAXIMO)
                {
                    getEditVisitasObservacion(vivienda);

                    //finish();
                }


                if(vivienda.getNumerovisitas() == Global.NUMERO_VISITAS_MAXIMO)
                {
                    if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()
                            && vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INCOMPLETA.getValor()
                            ) {
                        vivienda.setNumerovisitas(2);
                        filasAfectadas = ViviendaDao.update(contentResolver, vivienda);
                        getEditControlEntrevista(vivienda);
                    }
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();

    }

    /**
     * Métdo para llamar al dialogo de control de entrevsita cuando ya es tercera visita
     * @param vivienda
     */
    private void getEditControlEntrevista(Vivienda vivienda) {

        if (vivienda.getIdocupada() == ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor()) {
            FragmentManager ft = getFragmentManager();
            ControlEntrevistaDialog controlEntrevistaDialog = ControlEntrevistaDialog
                    .newInstance(ViviendaFragment.getVivienda());

            controlEntrevistaDialog
                    .show(ft, "fragment_edit_control_entrevista");
        } else {
            finish();
        }

    }

    /**
     * Métdo para llamar al dialogo de visitas
     * @param vivienda
     */
    private void getEditVisitasObservacion(Vivienda vivienda) {
        FragmentManager fm = getFragmentManager();
        ObservacionVisitasDialog observacionVisitasDialog = new ObservacionVisitasDialog();
        Bundle parametros = new Bundle();
        parametros.putSerializable("vivienda", ViviendaFragment.getVivienda());
        observacionVisitasDialog.setArguments(parametros);
        observacionVisitasDialog.show(fm, "fragment_observaciones");
    }


    /**
     * Presenta mensaje de salida
     * @return
     */
    private AlertDialog getExitAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Esta seguro que desea salir?")
                .setTitle(R.string.confirmacion_aviso);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;

    }



    @Override
    protected void onStart() {

        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * Obtiene los tabs de la actividad
     */
    public void obtenerVistas() {
        Resources res = getResources();

        tabs = findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");

        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.seccionVivienda),
                res.getDrawable(android.R.drawable.ic_menu_save));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.seccionHogar),
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getString(R.string.seccionMiembrosHogar),
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab4");
        spec.setContent(R.id.tab4);
        spec.setIndicator(getString(R.string.seccionCertificadoImagen),
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }

    /**
     * Método para realizar las acciones
     */
    private void realizarAcciones() {
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                int currentTab = tabs.getCurrentTab();

                switch (currentTab) {
                    case 1:
                        Vivienda vivienda = ViviendaFragment.getVivienda();
                        if (vivienda.getId() ==0){
                                getAlert("Aviso", "Debe llenar los campos de la sección vivienda");
                                tabs.setCurrentTab(0);
                        }
                        break;

                    case 2:
                        Hogar hogar = HogarFragment.getHogar();
                        if (hogar.getId() == 0) {
                            getAlert("Aviso", "Debe llenar los campos de la sección hogar");
                            tabs.setCurrentTab(1);
                        }
                        break;

                    case 3:

                        if (MiembrosHogarFragment.getCountTablaPersonas() > 0) {

                            if (!MiembrosHogarFragment
                                    .validarInformacionCompletaPersona()) {
                                tabs.setCurrentTab(2);
                                getAlert("Aviso",
                                        "Informacion incompleta de personas");
                                return;
                            }

                        } else {
                            tabs.setCurrentTab(2);
                            getAlert("Aviso", "Debe existir al menos un miembro del hogar");
                            return;
                        }

                        break;
                }
            }
        });
    }

    /**
     * MUestra mensjaes de alerta
     * @param title
     * @param message
     */
    private void getAlert(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message).setTitle(title);

        builder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_observacion).setEnabled(ViviendaFragment.isEnabledObervaciones());
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_observacion:
                FragmentManager fm = getFragmentManager();
                ObservacionDialog editarObservacionesDialog = new ObservacionDialog();
                Bundle parametros = new Bundle();
                parametros.putSerializable("vivienda", ViviendaFragment.getVivienda());
                editarObservacionesDialog.setArguments(parametros);
                editarObservacionesDialog.show(fm, "fragment_observaciones");
                break;

            case R.id.menu_control_entrevista:
                /*FragmentManager controlEntevista = getFragmentManager();
                ControlEntrevistaDialog controlEntrevistaDialog = new ControlEntrevistaDialog();
                parametros = new Bundle();
                parametros.putSerializable("vivienda", ViviendaFragment.getVivienda());
                controlEntrevistaDialog.setArguments(parametros);
                controlEntrevistaDialog.show(controlEntevista, "fragment_control_entrevista");*/
                break;

            case R.id.menu_exit:
                getExitAlert();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
