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
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

public class MainActivity extends Activity {

    private ContentResolver cr;

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

        cr = getContentResolver();

        this.getView();
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


                /*vivienda.setNumerovisitas(vivienda.getNumerovisitas() + 1);
                int filasAfectadas = ViviendaDao.update(cr, vivienda);

                if(vivienda.getNumerovisitas() < Global.NUMERO_VISITAS_MAXIMO)
                {
                    getEditVisitasObservacion(vivienda);

                    //finish();
                }


                if(vivienda.getVisitas() == SeccionIPreguntas.NUMERO_VISITAS_MAXIMO)
                {
                    if(vivienda.getCondicionOcupacion() == SeccionIIPreguntas.CondicionOcupacion.OCUPADA.getValor()
                            && vivienda.getControlEntrevista() == SeccionVIIPreguntas.ControlEntrevista.INCOMPLETA.getValor()
                            )
                    {

                        vivienda.setVisitas(2);
                        filasAfectadas = ViviendaDao.update(cr, vivienda);
                        getEditControlEntrevista(vivienda);
                    }
                }*/

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        return dialog;
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

    public void getView() {
        Resources res = getResources();

        TabHost tabs = findViewById(android.R.id.tabhost);
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


        tabs.setCurrentTab(0);


    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //menu.findItem(R.id.menu_observacion).setEnabled(ViviendaFragment.isEnabledObervaciones());

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
