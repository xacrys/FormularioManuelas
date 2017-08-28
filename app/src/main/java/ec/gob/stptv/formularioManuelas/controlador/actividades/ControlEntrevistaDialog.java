package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ControlPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

public class ControlEntrevistaDialog extends DialogFragment{
	private Button guardarControlEntrevistaButton;
	private Button cerrarControlEntrevistaButton;
	private Vivienda vivienda;
	private ContentResolver contentResolver;
	private RadioGroup controlEntrevistaRadioGroup;

	public static final String PRODUCT_MODE = "PRODUCT_MODE";
	private LinearLayout linlaHeaderProgress;
	public ControlEntrevistaDialog() {
	}

	static ControlEntrevistaDialog newInstance(Vivienda _vivienda) {
		ControlEntrevistaDialog f = new ControlEntrevistaDialog();
		
		Bundle args = new Bundle();
		args.putSerializable("vivienda", _vivienda);
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		vivienda = (Vivienda) getArguments().getSerializable("vivienda");
		contentResolver = getActivity().getContentResolver();

		View view = inflater.inflate(
				R.layout.activity_main_fragment_control_entrevista, container);

		controlEntrevistaRadioGroup = view
		.findViewById(R.id.controlEntrevistaRadioGroup);
		

		guardarControlEntrevistaButton = view
				.findViewById(R.id.guardarObservacionButton);
		cerrarControlEntrevistaButton = view
				.findViewById(R.id.cerrarObservacionButton);
		
		getDialog().setTitle(getString(R.string.controlEntrevistas));
		getDialog().setCanceledOnTouchOutside(false);
		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setCancelable(false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
			
		linlaHeaderProgress = getActivity().findViewById(R.id.linlaHeaderProgress);
		
			if (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.RECHAZO.getValor()) {
				controlEntrevistaRadioGroup.check(R.id.controlEntrevistaRechazadoRadioButton);
			}
			else
			{
				if (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.NADIE_EN_CASA.getValor()) {
					controlEntrevistaRadioGroup	.check(R.id.controlEntrevistaNadieEnCasaRadioButton);
				}
				else
				{
					if (vivienda.getIdcontrolentrevista() == ControlPreguntas.ControlEntrevista.INFORMANTE_NO_CALIFICADO.getValor()) {
						controlEntrevistaRadioGroup	.check(R.id.controlEntrevistaInformanteNoCalificadoRadioButton);
					}
				}
				
			}
	
		guardarControlEntrevistaButton.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				guardar();
				
			}
		});
	
		cerrarControlEntrevistaButton.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	
		
	}
	
	/**
	 * Metodo para indicar alertas
	 * 
	 * @param title
	 * @param message
	 */
	private void getAlert(String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

	/**
	 * Método para guardar el estado de control entrevista
	 */
	protected void guardar() {
		
		if (controlEntrevistaRadioGroup.getCheckedRadioButtonId() != -1) {
			if (controlEntrevistaRadioGroup.getCheckedRadioButtonId() == R.id.controlEntrevistaRechazadoRadioButton) {
				vivienda.setIdcontrolentrevista(ControlPreguntas.ControlEntrevista.RECHAZO
						.getValor());
			} else {
				if (controlEntrevistaRadioGroup.getCheckedRadioButtonId() == R.id.controlEntrevistaNadieEnCasaRadioButton) {
					vivienda.setIdcontrolentrevista(ControlPreguntas.ControlEntrevista.NADIE_EN_CASA
							.getValor());
				} else {
					if (controlEntrevistaRadioGroup.getCheckedRadioButtonId() == R.id.controlEntrevistaInformanteNoCalificadoRadioButton) {
						vivienda.setIdcontrolentrevista(ControlPreguntas.ControlEntrevista.INFORMANTE_NO_CALIFICADO
								.getValor());
					}
				}
			}
		} else {
			getAlert(getString(R.string.validacion_aviso),
					getString(R.string.mv_control_entrevista));
			return;
		}
		vivienda.setNumerovisitas(vivienda.getNumerovisitas() + 1);
		vivienda.setEstadosincronizacion(Global.SINCRONIZACION_INCOMPLETA);
		ViviendaDao.update(contentResolver, vivienda);
		getActivity().finish();
		dismiss();
	}
	
	
	
}
