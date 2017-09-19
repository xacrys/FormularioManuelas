package ec.gob.stptv.formularioManuelas.controlador.actividades;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.servicio.LocalizacionService;
import ec.gob.stptv.formularioManuelas.controlador.sincronizacion.SincronizacionVivienda;
import ec.gob.stptv.formularioManuelas.controlador.sincronizacion.WebService;
import ec.gob.stptv.formularioManuelas.controlador.util.ClaveEncriptada;
import ec.gob.stptv.formularioManuelas.controlador.util.DatePickerFragment;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Pageable;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;
import ec.gob.stptv.formularioManuelas.modelo.dao.FaseDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.UsuarioDao;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FormulariosActivity extends Activity {

	private PackageInfo infoApp;
	private LayoutInflater inflaterLayout;
	private Spinner faseSpinner;
	private Spinner estadoSpinner;
	private Button fechaInicioButton;
	private Button fechaFinButton;
	private Button buscarFormulariosButton;
	private TableLayout formulariosTableLayout;
	private LinearLayout paginationLinearLayout;
	private TextView paginationInfo;
	private ProgressDialog mProgressDialog;
	private Usuario usuario;
	
	AlertDialog.Builder builder ;
	//private Spinner faseSpinner;
	private Fase faseActual;
	private Gson gson;
	private ProgressDialog mProgressDialogSector;
	private ProgressDialog mProgressDialogFase;
	private Spinner filtroSpinner;
	private EditText valorEditText;
	private CheckBox consultaIndividualCheckBox;
	private LinearLayout panelConsultaIndividual;
	private int totalViviendasASincronizar = 0;
	private int contadorSincronizacion = 0;
	//private SincronizacionVivienda sincronizacionVivenda;
	private ProgressDialog mProgressDialogFormularios;
	ContentResolver contentResolver;
	private SincronizacionVivienda sincronizacionVivenda;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formularios);
		this.gson = new GsonBuilder().serializeNulls().create();
		this.contentResolver = getContentResolver();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Bundle extra = this.getIntent().getExtras();
		
		try {
			infoApp = getPackageManager().getPackageInfo(getPackageName(), 0);
			setTitle(getTitle() + " V. " + infoApp.versionName + " "
					+ Global.ENVIRONMENT + " - VC. " + infoApp.versionCode);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		sincronizacionVivenda = new SincronizacionVivienda(this);
		
		this.usuario = (Usuario) extra.getSerializable("usuario");
		this.faseActual = FaseDao.getFase(contentResolver, Fase.whereFaseEnabled, null);
		this.obtenerVistas();
		this.cargarPreguntas();
		this.realizarAcciones();
	}

	/**
	 * Método para realizar las acciones
	 */
	private void realizarAcciones() {

		this.fechaInicioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker(1);

			}
		});
		this.fechaFinButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker(2);
			}
		});

		this.buscarFormulariosButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utilitarios.hideSoftKeyboard(FormulariosActivity.this);
					buscarFormularios();
			}
		});

	}

	public void buscarFormularios() {

		paginationLinearLayout.removeAllViews();
		formulariosTableLayout.removeAllViews();
		paginationInfo.setText("" );

		String estado = ((Values) estadoSpinner.getSelectedItem()).getKey();
		int idFase = ((Fase) faseSpinner.getSelectedItem()).getId();
		//String isSincronizado = sincronizadoSwitch.isChecked() ? "1": "0";

		String where = null;
		String parametros[] = null;

		if( estadoSpinner.getSelectedItemPosition() <= 5 && (estado.equals(String.valueOf(ControlPreguntas.ControlEntrevista.COMPLETA.getValor()))
				|| estado.equals(String.valueOf(ControlPreguntas.ControlEntrevista.INCOMPLETA.getValor()))
				|| estado.equals(String.valueOf(ControlPreguntas.ControlEntrevista.RECHAZO.getValor()))
				|| estado.equals(String.valueOf(ControlPreguntas.ControlEntrevista.NADIE_EN_CASA.getValor()))
				|| estado.equals(String.valueOf(ControlPreguntas.ControlEntrevista.INFORMANTE_NO_CALIFICADO.getValor()))
		))
		{

			where = Vivienda.whereByFechasControlEntrevistaFormularios;
			parametros = new String[]{String.valueOf(idFase), fechaInicioButton.getText().toString(),
					fechaFinButton.getText().toString(), String.valueOf(estado)};

		}else{
			if (estado.equals(String.valueOf(ControlPreguntas.ControlEntrevista.TODOS.getValor()))) {

				where = Vivienda.whereByFechasFormularios;
				parametros = new String[]{String.valueOf(idFase), fechaInicioButton.getText().toString(),
						fechaFinButton.getText().toString(), String.valueOf(ControlPreguntas.ControlEntrevista.ELIMINADO.getValor())};
			}
		}

		final List<Vivienda> viviendas = ViviendaDao.getViviendas(contentResolver, where, parametros, Vivienda.COLUMNA_ID );


		final Pageable<Vivienda> pagination = new Pageable<Vivienda>(viviendas);
		pagination.setPageSize(20);
		Log.e("", "viviendas.size(): " + viviendas.size());
		Log.e("", "pagination.getMaxPages(): " + pagination.getMaxPages());


		if(pagination.getMaxPages() > 0)
		{
			paginationInfo.setText("Total: " + viviendas.size() + " registros - Página "+ pagination.getMaxPages()+ " de " + pagination.getMaxPages());
			pagination.setPage(pagination.getMaxPages());
			cargarFormularios(pagination.getListForPage());
		}

		if(pagination.getMaxPages() > 1)
		{
			for (int i = 1; i <= pagination.getMaxPages(); i++) {

				final int page = i;
				Button button = new Button(this);
				button.setText("" + page);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						v.setActivated(true);
						pagination.setPage(page);
						paginationInfo.setText("Total: " + viviendas.size() + " registros - Página " + page + " de " + pagination.getMaxPages());
						cargarFormularios(pagination.getListForPage());
					}
				});
				paginationLinearLayout.addView(button);

				pagination.setPage(i);
				Log.e("", "pagination.setPage: " + pagination.getPage());
				for (Vivienda vivienda : pagination.getListForPage()) {
					Log.e("", "viviendas.getCodigo(): " + vivienda.getId());
				}

			}
		}
	}


	/**
	 *
	 * @param viviendas
	 */
	public void cargarFormularios(List<Vivienda> viviendas) {

		formulariosTableLayout.removeAllViews();

		for (final Vivienda vivienda : viviendas) {

			final View row = inflaterLayout.inflate(R.layout.reusable_table_row_formulario,
					null);


			if (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INCOMPLETA
					.getValor()
					&& vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_COMPLETA) {
				row.setBackgroundResource(R.drawable.table_row_selector_incompletas);
			} else {
				if (vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_COMPLETA) {
					row.setBackgroundResource(R.drawable.table_row_selector_validation);
				} else {
					if (vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_INCOMPLETA) {
						row.setBackgroundResource(R.drawable.table_row_selector);
					}
					else
					{
						if (vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_CERTIFICADO_REPETIDO) {
							row.setBackgroundResource(R.drawable.table_row_selector);
						}
					}

				}

			}

			row.setTag(vivienda);

			((TextView) row.findViewById(R.id.columnaCodigoVivTextView)).setText(vivienda.getVivcodigo());


			((TextView) row.findViewById(R.id.columnaFechaRegistroTextView))
					.setText(vivienda.getFechaencuesta());

			if(!vivienda.getFormulario().equals(String.valueOf(Global.ENTEROS_VACIOS)))
			{
				((TextView) row.findViewById(R.id.columnaCertificadoTextView)).setText("C: " + vivienda.getFormulario());
			}
			else
			{
					((TextView) row.findViewById(R.id.columnaCertificadoTextView)).setText(Global.SIN_REGISTRO);

			}

			((TextView) row.findViewById(R.id.columnaZonaTextView))
					.setText(vivienda.getZona());

			((TextView) row.findViewById(R.id.columnaSectorTextView))
					.setText(vivienda.getSector());

			if(!vivienda.getManzana().equals(String.valueOf(Global.ENTEROS_VACIOS)))
			{
				((TextView) row.findViewById(R.id.columnaManzanaTextView))
						.setText(String.valueOf(vivienda.getManzana()));
			}else{
				((TextView) row.findViewById(R.id.columnaManzanaTextView))
						.setText("");
			}

			if(!vivienda.getEdificio().equals(Global.ENTEROS_VACIOS_CATALOGOS))
			{
				((TextView) row.findViewById(R.id.columnaEdificioTextView))
						.setText(String.valueOf(vivienda.getEdificio()));
			}else{
				((TextView) row.findViewById(R.id.columnaEdificioTextView))
						.setText("");
			}

			if (!vivienda.getVivienda().equals(Global.ENTEROS_VACIOS_CATALOGOS)) {
				((TextView) row.findViewById(R.id.columnaViviendaTextView))
						.setText(String.valueOf(vivienda.getVivienda()));
			}
			else
			{
				((TextView) row.findViewById(R.id.columnaViviendaTextView))
						.setText("");
			}

			if (!vivienda.getHogar1().equals(Global.ENTEROS_VACIOS_CATALOGOS)) {
				((TextView) row.findViewById(R.id.columnaHogarInicialFinalTextView))
						.setText(String.valueOf(vivienda.getHogar1())+ "-"+ String.valueOf(vivienda.getHogart()));
			} else {
				((TextView) row.findViewById(R.id.columnaHogarInicialFinalTextView))
						.setText("");
			}

			if (!vivienda.getNumerovisitas().equals(Global.ENTEROS_VACIOS_CATALOGOS)) {
				((TextView) row.findViewById(R.id.columnaNumeroVisitasTextView))
						.setText(String.valueOf(vivienda.getNumerovisitas()));
			} else {
				((TextView) row.findViewById(R.id.columnaNumeroVisitasTextView))
						.setText("");
			}

			//falta estado
			String estadoSincronizacion = "";
			if(vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_COMPLETA)
			{
				estadoSincronizacion = "Sincronizado";
			}
			else
			{
				if(vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_INCOMPLETA)
				{
					estadoSincronizacion = "No Sincronizado";
				}
				else
				{
					if(vivienda.getEstadosincronizacion() == Global.SINCRONIZACION_CERTIFICADO_REPETIDO)
					{
						estadoSincronizacion = "Certificado Duplicado";
					}
				}
			}
			int posicionCondicion = Utilitarios.getPosicionByKey(ViviendaPreguntas.getCondicionOcupacionAdapter(this), String.valueOf(vivienda.getIdocupada()));
			if(vivienda.getIdcontrolentrevista() != ControlPreguntas.ControlEntrevista.SIN_ESTADO.getValor())
			{
				int posicion = Utilitarios.getPosicionByKey(ControlPreguntas.getControlEntrevistaAdapter(this), String.valueOf(vivienda.getIdcontrolentrevista()));
				String cE = "";
				if(posicion > -1)
				{
					cE = ControlPreguntas.getControlEntrevistaAdapter(this).getItem(posicion).getValue();
				}
				((TextView) row.findViewById(R.id.columnaEstadoTextView))
						.setText("Sinc: "
								+ estadoSincronizacion
								+ "\nCE: "
								+ cE
								+ "\nCO: "
								+  ViviendaPreguntas.getCondicionOcupacionAdapter(this).getItem(posicionCondicion).getValue());
			}
			else
			{

				((TextView) row.findViewById(R.id.columnaEstadoTextView))
						.setText("Sinc: "
								+ estadoSincronizacion
								+ "\n CE: "
								+ "SIN ESTADO"
								+ "\nCO: "
								+ ViviendaPreguntas.getCondicionOcupacionAdapter(this).getItem(posicionCondicion).getValue());
			}
			if (!(vivienda.getObservacion().equals(Global.CADENAS_VACIAS))) {

				((TextView) row.findViewById(R.id.columnaObservacionTextView))
						.setText(vivienda.getObservacion());
			}
			else{
				((TextView) row.findViewById(R.id.columnaObservacionTextView))
						.setText("");
			}


			if (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.COMPLETA
					.getValor()){

				row.setOnClickListener(new OnClickListener() {
					public void onClick(final View v) {
						getAlertActions("Información", vivienda);

					}
				});

			}else{
				if (vivienda.getIdcontrolentrevista() != ControlPreguntas.ControlEntrevista.ELIMINADO.getValor()){
					row.setOnClickListener(new OnClickListener() {
						public void onClick(final View v) {

							builder.setMessage(
									getString(R.string.mv_numero_visitas))
									.setTitle("Visitar");
							builder.setIcon(getResources().getDrawable(
									android.R.drawable.ic_dialog_alert));
							builder.setPositiveButton(
									getString(R.string.opcion_si),
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int id) {

											String where;
											String parametros[];
											where = Fase.whereById;
											parametros = new String[] {String.valueOf(((Vivienda) v.getTag()).getIdfase())};
											Fase fase = FaseDao.getFase(contentResolver, where, parametros);
											Intent intent = new Intent(FormulariosActivity.this,
													MainActivity.class);
											intent.putExtra("vivienda", (Vivienda) v.getTag());
											intent.putExtra("usuario", usuario);
											intent.putExtra("fase", fase);
											startActivity(intent);
										}
									});

							builder.setNegativeButton(
									getString(R.string.opcion_no),
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int id) {
											dialog.cancel();
										}
									});

							AlertDialog dialogo = builder.create();
							dialogo.show();


						}
					});
				}
			}


			formulariosTableLayout.addView(row);

		}

	}

	/**
	 * Metdo que realiza las acciones en los formularios por ejemplo completar la informacion, validar con el supervisor
	 * @param title
	 * @param _vivienda
	 */
	private void getAlertActions(String title, final Vivienda _vivienda) {

		final String[] acciones = {getString(R.string.mensajeBotonVisitar)};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(getResources().getDrawable(
				android.R.drawable.ic_dialog_info));
		builder.setTitle(title);
		builder.setSingleChoiceItems(acciones, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						String where;
						String parametros[];
						where = Fase.whereById;
						parametros = new String[] {String.valueOf(_vivienda.getIdfase())};
						Fase fase = FaseDao.getFase(contentResolver,where, parametros);

						switch (which) {
							/**
							 * Completar información de la vivienda
							 */
							case 0:
								Intent intent = new Intent(FormulariosActivity.this,
										MainActivity.class);
								intent.putExtra("vivienda", _vivienda);
								intent.putExtra("usuario", usuario);
								intent.putExtra("fase", fase);
								startActivity(intent);
								dialog.dismiss();
								break;

						}
					}
				});

		builder.setCancelable(true);
		builder.setPositiveButton("Cerrar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();

	}


	@Override
	protected void onResume() {
		this.buscarFormularios();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_formularios, menu);
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.menu_nuevo:

				Date fechaInicio  = null;
				Date fechaFin  = null;
				Date fechaActual  = null;
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				faseActual = FaseDao.getFase(contentResolver, Fase.whereFaseEnabled, null);
				Utilitarios.printObject(faseActual);
				try {
					fechaInicio  = formatoFecha.parse(faseActual.getFechainicio());
					fechaFin = formatoFecha.parse(faseActual.getFechafin());
					fechaActual = formatoFecha.parse(Utilitarios.getCurrentDate());
					Utilitarios.logInfo(getPackageName().getClass().getName(),
							"faseActual.getFechaInicio(): " + faseActual.getFechainicio() +
									" faseActual.getFechaFin(): " + faseActual.getFechafin() +
									" Fecha Actual: " + Utilitarios.getCurrentDate());
					if(fechaActual.compareTo(fechaInicio) >= 0 && fechaActual.compareTo(fechaFin) <= 0)
					{

						Intent intent = new Intent(FormulariosActivity.this,MainActivity.class);
						intent.putExtra("usuario", usuario);
						intent.putExtra("fase", faseActual);
						startActivity(intent);
					}else
					{

						getFaseAlert("Validacion", "La fase ya se termino");
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;

			case R.id.menu_sincronizar:
				boolean isConectedInternet = Utilitarios.verificarConexion(this);
				if (isConectedInternet) {

					this.sincronizarFormularios();

				} else
				{
					Toast.makeText(getApplicationContext(),	"No existe coneccion a internet", Toast.LENGTH_LONG).show();
				}
				break;


			case R.id.menu_salir:

				getExitAlert();
				break;

			case R.id.menu_sincronizar_imagenes:

				Intent intentImagenes = new Intent(FormulariosActivity.this,
						ImagenesActivity.class);
				intentImagenes.putExtra("usuario", usuario);
				startActivity(intentImagenes);
				break;

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void openProgressBar() {

		mProgressDialog.setMessage(getString(R.string.mensaje_sincronizacion_formularios));
		mProgressDialog.setIcon(android.R.drawable.stat_sys_upload);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setTitle("Sincronizacion");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	/**
	 * mensajes de alerta de la fase terminada
	 * @param title
	 * @param message
	 */
	private void getFaseAlert(String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message)
				.setTitle(title);

		builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		builder.setPositiveButton("Actualizar La fase", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				openProgressBarFase();

				new Thread(new Runnable() {

					FaseTask faseTask = new FaseTask();
					DefaultHttpClient httpClient = new DefaultHttpClient();

					public void run() {
						try {

							faseTask.execute(httpClient).get(Global.ASYNCTASK_TIMEROUT, TimeUnit.MILLISECONDS);

						} catch (InterruptedException e1) {
							e1.printStackTrace();
						} catch (ExecutionException e1) {
							e1.printStackTrace();
						} catch (TimeoutException e1) {
							Log.e("", "Termino el tiempo");
							httpClient.getConnectionManager().shutdown();
							faseTask.cancel(true);
							e1.printStackTrace();
						}
					}
				}).start();



			}
		});


		AlertDialog dialog = builder.create();

		dialog.show();
	}

	private void openProgressBarFase() {

		mProgressDialogFase.setMessage("Actualizando la fase esto puede tardar hasta 2 minutos,  por favor no cerrar o minimizar la ventana y espere... si no actualiza intentelo mas tarde");
		mProgressDialogFase.setIcon(android.R.drawable.stat_sys_download);
		mProgressDialogFase.setIndeterminate(false);
		mProgressDialogFase.setTitle("Sincronización");
		mProgressDialogFase.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialogFase.setCancelable(false);
		mProgressDialogFase.show();
	}

	private void getExitAlert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage("Esta seguro que desea salir?").setTitle(
				"Confirmación");

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
	}

	private void sincronizarFormularios() {
		String where = Vivienda.whereByEstadoSincronizacionControlEntrevista;
		String parametros[] = new String[] {String
				.valueOf(Global.SINCRONIZACION_INCOMPLETA) ,
				String.valueOf(ControlPreguntas.ControlEntrevista.ELIMINADO.getValor())};

		ArrayList<Vivienda> viviendas = ViviendaDao.getViviendas(contentResolver, where, parametros, Vivienda.COLUMNA_VIVCODIGO);

		totalViviendasASincronizar = viviendas.size();

		if(totalViviendasASincronizar > 0)
		{
			this.openProgressBar();
		}

		ExecutorService exec = Executors.newSingleThreadExecutor();

		for (final Vivienda vivienda : viviendas) {
			exec.execute(new Runnable() {
				public void run() {
					sincronizacionVivenda.sincronizarAll(vivienda, FormulariosActivity.this);
				}
			});
		}
		exec.shutdown();

	}


	/**
	 * Método para obtener las controles de la vista
	 */
	private void obtenerVistas() {

		inflaterLayout = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		builder = new AlertDialog.Builder(this);
		this.faseSpinner = findViewById(R.id.faseSpinner);
		this.estadoSpinner = findViewById(R.id.estadoSpinner);
		this.fechaInicioButton = findViewById(R.id.fechaInicioButton);
		this.fechaFinButton = findViewById(R.id.fechaFinButton);
		this.buscarFormulariosButton = findViewById(R.id.buscarFormulariosButton);
		this.formulariosTableLayout = findViewById(R.id.formulariosTableLayout);
		paginationLinearLayout = findViewById(R.id.paginationLinearLayout);
		paginationInfo = findViewById(R.id.paginationInfo);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialogFase = new ProgressDialog(this);


	}


	/**
	 * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
	 */
	private void cargarPreguntas() {
		estadoSpinner.setAdapter(ControlPreguntas.getControlEntrevistaAdapter(this));
		faseSpinner.setAdapter(ViviendaPreguntas.getAreaAdapter(this));

		ArrayList<Fase> fases= FaseDao.getFases(contentResolver, null, null, Fase.COLUMNA_ESTADO + " desc");
		ArrayAdapter<Fase> fasesAdapter = new ArrayAdapter<Fase>(this, android.R.layout.simple_spinner_item,fases);
		fasesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		faseSpinner.setAdapter(fasesAdapter);
		fechaInicioButton.setText(Utilitarios.getCurrentDate());
		fechaFinButton.setText(Utilitarios.getCurrentDate());


	}

	private void showDatePicker(int tipoFecha) {

		DatePickerFragment date = new DatePickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		if (tipoFecha == 1) {
			date.setCallBack(ondateInicio);
		}

		if (tipoFecha == 2) {
			date.setCallBack(ondateFin);
		}

		date.show(getFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondateInicio = new OnDateSetListener() {

		@SuppressLint("SimpleDateFormat")
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			fechaInicioButton.setText(Utilitarios.getDate(year, monthOfYear,
					dayOfMonth));

		}
	};
	OnDateSetListener ondateFin = new OnDateSetListener() {

		@SuppressLint("SimpleDateFormat")
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			fechaFinButton.setText(Utilitarios.getDate(year, monthOfYear,
					dayOfMonth));

		}
	};
	

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
	
	private void getAlertRoussunMaps(String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message).setTitle(title);

		builder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.roussun.applications.android.maps")));
						dialog.cancel();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();

	}


	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getExitAlert();
		
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		Intent msgIntent = new Intent(this, LocalizacionService.class);
		stopService(msgIntent);
		super.onDestroy();
	}

	/**
	 * Cierra el progreso
	 */
	public void closeProgressBar() {

		contadorSincronizacion = contadorSincronizacion + 1;
		if (contadorSincronizacion == totalViviendasASincronizar) {
			mProgressDialog.dismiss();
			contadorSincronizacion = 0;
			totalViviendasASincronizar = 0;
		}

	}

	/**
	 * Representa una tarea asincrona para traer la fase
	 */
	@SuppressLint("SimpleDateFormat")
	public class FaseTask extends AsyncTask<Object, Void, Object> {

		DefaultHttpClient httpClient;

		@Override
		protected Object doInBackground(Object... params) {

			Object respuesta = null;
			httpClient = (DefaultHttpClient)params[0];

			JSONObject values = new JSONObject();
			try {


				String user = usuario.getUsuario();
				String password = usuario.getPassword();
				String imei = Utilitarios.getImeiDispositivo(getApplicationContext());

				if (TextUtils.isEmpty(imei)) {

					String[] parametros = new String[] {user, password};
					String where= Usuario.whereByUsuarioYPassword;
					Usuario usuarioBD = UsuarioDao.getUsuario(contentResolver, where, parametros);
					if(usuarioBD != null)
					{
						imei = usuarioBD.getImei();
					}
				}

				values.put("login", user);
				values.put("password", password);
				values.put("imei", imei);
				values.put("version", infoApp.versionName);

				Utilitarios.logInfo(FormulariosActivity.class.getName(), "Peticion de nueva fase valores"+ values.toString());

				String _respuesta = WebService.getJsonData(Global.URL_WEB_SERVICE_USUARIOS,	values, httpClient);

				Utilitarios.logInfo(FormulariosActivity.class.getName(), "respuesta"+_respuesta);
				if (!_respuesta.equals("")) {
					try {
						respuesta = new JSONObject(_respuesta);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}


			return respuesta;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(final Object _respuesta) {

			if (_respuesta != null) {
				try {

					JSONObject respuesta = (JSONObject) _respuesta;
					Integer codigo = respuesta.getInt("codigo");
					switch (codigo) {

						case 0:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.error_servidor));
							break;

						case 1:
							String idFase = respuesta.getString("idFase");
							String[] parametros = new String[] {idFase};
							Fase _fase = FaseDao.getFase(contentResolver,Fase.whereById, parametros);
							if(_fase.getId() == 0)
							{
								_fase.setId(Integer.parseInt(idFase));
								_fase.setFechainicio(respuesta.getString("fechaInicio"));
								_fase.setFechafin(respuesta.getString("fechaFin"));
								_fase.setNombrefase(respuesta.getString("fase"));
								//_fase.setNombreOperativo(respuesta.getString("nombreOperativo"));
								_fase.setEstado(1);
								Uri uri  = FaseDao.save(contentResolver, _fase);
								FaseDao.updateEstadoFases(contentResolver,_fase);

								if(uri != null)
								{
									((ArrayAdapter<Fase>)faseSpinner.getAdapter()).add(_fase);
									((ArrayAdapter<Fase>)faseSpinner.getAdapter()).notifyDataSetChanged();

									((ArrayAdapter<Fase>)faseSpinner.getAdapter()).sort(new Comparator<Fase>() {

										@Override
										public int compare(Fase lhs, Fase rhs) {

											int compareId = rhs.getId();
											return compareId - lhs.getId();
										}
									});
								}
							}
							else
							{
								SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

								_fase.setFechainicio(respuesta.getString("fechaInicio"));
								_fase.setFechafin(respuesta.getString("fechaFin"));
								_fase.setNombrefase(respuesta.getString("fase"));
								//_fase.setNombreOperativo(respuesta.getString("nombreOperativo"));

								try
								{
									Date fechaInicio = formatoFecha.parse(_fase.getFechainicio());
									Date fechaFin = formatoFecha.parse(_fase.getFechafin());
									Date fechaActual = formatoFecha.parse(Utilitarios.getCurrentDate());

									if(fechaActual.compareTo(fechaInicio) >= 0 && fechaActual.compareTo(fechaFin) <= 0)
									{
										_fase.setEstado(1);
										FaseDao.update(contentResolver,_fase);
										FaseDao.updateEstadoFases(contentResolver,_fase);

									}
									else
									{
										getAlert(getString(R.string.validacion_aviso), "No existe una nueva fase");
									}
								} catch (ParseException e) {

									e.printStackTrace();
								}
							}

							break;
						case -1:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.error_incorrect_password));
							break;

						case -2:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.error_asignacion_tablet));
							break;

						case -3:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.error_asignacion_fase));
							break;

						case -4:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.error_version));
							break;

						case -5:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDispositivoFase));
							break;

						case -6:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDispositivoVersion));
							break;

						case -7:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.errorFaseVersion));
							break;

						case -8:
							getAlert(getString(R.string.validacion_aviso), getString(R.string.errorDispositivoFaseVersion));
							break;

						default:
							break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else
			{
				Toast toast = Toast.makeText(getApplicationContext(),
						getString(R.string.error_conectar_servidor),
						Toast.LENGTH_LONG);
				toast.show();
			}
			mProgressDialogFase.dismiss();
		}

		@Override
		protected void onCancelled() {

			mProgressDialogFase.dismiss();
		}
	}

}
