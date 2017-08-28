package ec.gob.stptv.formularioManuelas.controlador.actividades;


import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;
import ec.gob.stptv.formularioManuelas.modelo.dao.ViviendaDao;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ObservacionDialog extends DialogFragment {

	private EditText observacionEditText;
	private Vivienda vivienda;
	private ContentResolver contentResolver;

	public ObservacionDialog() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog alert = new AlertDialog.Builder(getActivity())
				.setTitle("Observaciones")
				.setIcon(android.R.drawable.ic_menu_info_details)
				.setView(getContentView())
				.setPositiveButton(R.string.menu_guardar,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								guardarObservacion();
								dismiss();
								Utilitarios.hideKeyboard(observacionEditText,
										getActivity());
								// MainActivity activity = (MainActivity)
								// getActivity();
								// activity.onFinishEditDialog(starDate,
								// endDate);
							}
						})
				.setNegativeButton("Cerrar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
								Utilitarios.hideKeyboard(observacionEditText,
										getActivity());
							}
						}).create();
		alert.setCanceledOnTouchOutside(false);
		alert.setCancelable(false);
		return alert;
	}

	@SuppressLint("InflateParams")
	private View getContentView() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(
				R.layout.activity_main_fragment_observacion, null);

		vivienda = (Vivienda) getArguments().getSerializable("vivienda");
		contentResolver = getActivity().getContentResolver();

		observacionEditText = view
				.findViewById(R.id.observacionEditText);

		if ((vivienda.getObservacion()!= (Global.CADENAS_VACIAS))) {
			observacionEditText.setText(vivienda.getObservacion());
		}

		return view;
	}

	protected void guardarObservacion() {
		if (!TextUtils.isEmpty(observacionEditText.getText().toString())) {

			vivienda.setObservacion(observacionEditText.getText().toString());
		} else {
			vivienda.setObservacion(Global.CADENAS_VACIAS);
		}
		ViviendaDao.update(contentResolver, vivienda);
	}
}
