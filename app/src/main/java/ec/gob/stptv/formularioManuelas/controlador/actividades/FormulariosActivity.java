package ec.gob.stptv.formularioManuelas.controlador.actividades;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Fase;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;

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
	private ContentResolver cr;
	private LayoutInflater inflaterLayout;
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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formularios);
		this.gson = new GsonBuilder().serializeNulls().create();
		this.cr = getContentResolver();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Bundle extra = this.getIntent().getExtras();
		
		try {
			infoApp = getPackageManager().getPackageInfo(getPackageName(), 0);
			setTitle(getTitle() + " V. " + infoApp.versionName + " "
					+ Global.ENVIRONMENT + " - VC. " + infoApp.versionCode);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		//sincronizacionVivenda = new SincronizacionVivienda(this);
		
		//this.usuario = (Usuario) extra.getSerializable("usuario");
		//this.faseActual = FaseDao.getFase(cr, Fase.whereFaseEnabled, null, null);
		
		this.getViews();
		this.loadPreguntas();
		this.getActions();
		this.mallasValidacion();
	}

	private void getActions() {

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
				if(consultaIndividualCheckBox.isChecked())
				{
					buscarFormulario();
				}
				else
				{
					buscarFormularios();
				}
				

			}
		});

	}

	protected void buscarFormulario() {

	}
	
	public void mallasValidacion() {

		
	}
	
	private boolean validacionCampos() {



		boolean cancel = false;


		return cancel;
	}
	

	@Override
	protected void onResume() {

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

				Intent intent = new Intent(FormulariosActivity.this,MainActivity.class);
				//intent.putExtra("usuario", usuario);
				//intent.putExtra("fase", faseActual);
				startActivity(intent);

				break;


			case R.id.menu_salir:

				getExitAlert();

				break;

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
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

	}



	private void getViews() {

		inflaterLayout = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		builder = new AlertDialog.Builder(this);
		
		this.estadoSpinner = findViewById(R.id.estadoSpinner);
		this.fechaInicioButton = findViewById(R.id.fechaInicioButton);
		this.fechaFinButton = findViewById(R.id.fechaFinButton);
		this.buscarFormulariosButton = findViewById(R.id.buscarFormulariosButton);
		this.formulariosTableLayout = findViewById(R.id.formulariosTableLayout);



	}

	private void loadPreguntas() {



	}

	private void showDatePicker(int tipoFecha) {

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
	

	public void buscarFormularios() {

		
	}

	
	
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

		super.onDestroy();
	}
}
