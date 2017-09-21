package ec.gob.stptv.formularioManuelas.controlador.actividades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.sincronizacion.SincronizacionImagenes;
import ec.gob.stptv.formularioManuelas.controlador.util.DatePickerFragment;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Pageable;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.dao.ImagenDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Imagen;

public class ImagenesActivity extends Activity {

	private TableLayout imagenesTableLayout;
	private ContentResolver cr;
	private LayoutInflater inflaterLayout;
	private int totalImagenesASincronizar;
	private int contadorSincronizacion;
	private ProgressDialog mProgressDialog;
	private SincronizacionImagenes sincronizacionImagenes;
	private Button fechaInicioButton;
	private Button fechaFinButton;
	private Button buscarFormulariosButton;
	private LinearLayout paginationLinearLayout;
	private TextView paginationInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagenes);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.cr = getContentResolver();
		this.inflaterLayout = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.sincronizacionImagenes = new SincronizacionImagenes(this);
	
		this.getViews();
		this.getAcctions();
		 
	}
	
	private void getViews() {
		fechaInicioButton = findViewById(R.id.fechaInicioButton);
		fechaFinButton = findViewById(R.id.fechaFinButton);
		buscarFormulariosButton = findViewById(R.id.buscarFormulariosButton);
		imagenesTableLayout = findViewById(R.id.imagenesTableLayout);
		paginationLinearLayout = findViewById(R.id.paginationLinearLayout);
		paginationInfo = findViewById(R.id.paginationInfo);
		mProgressDialog = new ProgressDialog(this);
	}

	private void getAcctions() {
		fechaInicioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker(1);

			}
		});
		fechaFinButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePicker(2);
			}
		});

		buscarFormulariosButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buscarImagenes();
			}
		});
		
		fechaInicioButton.setText(Utilitarios.getCurrentDate());
		fechaFinButton.setText(Utilitarios.getCurrentDate());
		
	}

	@Override
	protected void onResume() {
		buscarImagenes();
		super.onResume();
	}
	
	private void showDatePicker(int tipoFecha) {
		DatePickerFragment date = new DatePickerFragment();
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_imagenes, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {

		case R.id.menu_sincronizar_imagenes:

			boolean isConectedInternet = Utilitarios.verificarConexion(this);
			
 			if (isConectedInternet) {
				
				String where = Imagen.whereByFechasEstadoSincronizacion;
				String parametros[] = new String[] {fechaInicioButton.getText().toString(),  fechaFinButton.getText().toString(), String.valueOf(Global.SINCRONIZACION_INCOMPLETA)};
				
				ArrayList<Imagen> imagenes = ImagenDao.getImagenes(cr,where , parametros, Imagen.COLUMNA_VIVCODIGO + ","+ Imagen.COLUMNA_TIPO);
				
				totalImagenesASincronizar = imagenes.size();
				if(totalImagenesASincronizar > 0)
				{
					this.openProgressBar();
				}
				
				ExecutorService exec = Executors.newSingleThreadExecutor();

				for (final Imagen imagen : imagenes) {
					exec.execute(new Runnable() {
						public void run() {
							sincronizacionImagenes.sincronizarAll(imagen, ImagenesActivity.this);
						}
					});
				}
				exec.shutdown();
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No existe conección a internet con WI-FI", Toast.LENGTH_LONG).show();
			}
			break;	
			
		case R.id.menu_exit:
			getExitAlert();
			break;
			
		/*case R.id.menu_cambiar_estado:
			new CambiarEstadoFormulariosTask().execute();
			break;*/

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	public void buscarImagenes() {

		paginationLinearLayout.removeAllViews();
		imagenesTableLayout.removeAllViews();
		paginationInfo.setText("" );
	
		String where = Imagen.whereByFechasEstadoSincronizacion;
		String parametros[] = new String[] {fechaInicioButton.getText().toString(),  fechaFinButton.getText().toString(), String.valueOf(Global.SINCRONIZACION_INCOMPLETA)};
		
		final ArrayList<Imagen> imagenes = ImagenDao.getImagenes(cr,where , parametros, Imagen.COLUMNA_VIVCODIGO + ","+ Imagen.COLUMNA_TIPO);
	
		final Pageable<Imagen> pagination = new Pageable<Imagen>(imagenes);
		pagination.setPageSize(20);
		Log.e("", "imagenes.size(): " + imagenes.size());
		Log.e("", "pagination.getMaxPages(): " + pagination.getMaxPages());
		
		
		if(pagination.getMaxPages() > 0)
		{
			paginationInfo.setText("Total: " + imagenes.size() + " registros - Página "+ pagination.getMaxPages()+ " de " + pagination.getMaxPages());
			pagination.setPage(pagination.getMaxPages());
			loadImagenes(pagination.getListForPage());
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
					paginationInfo.setText("Total: " + imagenes.size() + " registros - Página " + page + " de " + pagination.getMaxPages());
					loadImagenes(pagination.getListForPage());
				}
			});
			paginationLinearLayout.addView(button);
			
			pagination.setPage(i);
			Log.e("", "pagination.setPage: " + pagination.getPage());
			for (Imagen imagen : pagination.getListForPage()) {
				Log.e("", "viviendas.getCodigo(): " + imagen.getId());
			}
			
		}
		}
		
	}
	
	@SuppressLint("InflateParams")
	private void loadImagenes(List<Imagen>  imagenes) {
		
		imagenesTableLayout.removeAllViews();
		
		for (Imagen imagen : imagenes) {
			
			final View row = inflaterLayout.inflate(R.layout.reusable_table_row_imagen,	null);
			row.setTag(imagen);

			
			((TextView) row.findViewById(R.id.columnaIdTextView))
			.setText("" + imagen.getId());
			
			((TextView) row.findViewById(R.id.columnaCodigoVivTextView))
					.setText(imagen.getVivcodigo());
			
			((TextView) row.findViewById(R.id.columnaTipoTextView))
			.setText("" + imagen.getTipo());
			
			((TextView) row.findViewById(R.id.columnaFechaTextView))
			.setText("" + imagen.getFecha());
			
			String estadoSincronizacion = "";
			
			if(imagen.getEstadosincronizacion() == Global.SINCRONIZACION_COMPLETA)
			{
				estadoSincronizacion = "Sincronizado";
			}
			else
			{
				if(imagen.getEstadosincronizacion() == Global.SINCRONIZACION_INCOMPLETA)
				{
					estadoSincronizacion = "No Sincronizado";
				}
			}
			((TextView) row.findViewById(R.id.columnaEstadoTextView))
			.setText("Estado sinc: "+ estadoSincronizacion);
					
			imagenesTableLayout.addView(row);

		}
	}
	
	private void getExitAlert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage("Esta seguro que desea salir?")
				.setTitle("Confirmación");

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

	public void closeProgressBar() {
		
		contadorSincronizacion = contadorSincronizacion +1;
		Log.e("", "contadorSincronizacion:" + contadorSincronizacion + " totalImagenesASincronizar: " + totalImagenesASincronizar);
		if(contadorSincronizacion == totalImagenesASincronizar)
		{
			mProgressDialog.dismiss();
			contadorSincronizacion = 0;
			totalImagenesASincronizar = 0;
		}
	}
	
	private void openProgressBar() {
		
		mProgressDialog.setMessage("Sincronización de imagenes esto puede tardar hasta 7 minutos por imagen,  por favor no cerrar o minimizar la ventana y espere... si no sincroniza intentelo mas tarde");
		mProgressDialog.setIcon(android.R.drawable.stat_sys_upload);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setTitle("Sincronización");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	public void changeRowSincronizada(Imagen imagen) {
		
		for (int i = 0; i < imagenesTableLayout.getChildCount(); i++) {

			TableRow row = (TableRow) imagenesTableLayout.getChildAt(i);
			Imagen _imagen = (Imagen) row.getTag();
			if (_imagen.getId().equals(imagen.getId())) {
				imagenesTableLayout.removeViewAt(i);
			}

		}
	}
}
