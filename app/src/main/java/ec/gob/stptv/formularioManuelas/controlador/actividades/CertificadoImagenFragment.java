package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Hogar;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Imagen;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Usuario;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Vivienda;

public class CertificadoImagenFragment extends Fragment {

	private ContentResolver contentResolver;

	private TableLayout formulariosTableLayout;

	private static TableLayout personasNucleosTableLayout;

	Vivienda vivienda;

	Persona persona;

	private String id = "0";

	int contadorNucleos;

	private RelativeLayout hogarRelativeLayout;

	private Button capturarFotoViviendaButton;

	private Button finalizarEntrevistaButton;

	protected String fileNameFormulario = "";

	protected String fileNameVivienda = "";

	private static Usuario usuario;

	private static final int REQUEST_PICTURE_CERTIFICADO = 2;
	private static final int REQUEST_CODE_SCANNER_BARCODE = 3;
	private static final int REQUEST_STICKER_CODE_SCANNER_BARCODE = 4;
	private static final int REQUEST_CODE_SCANNER_BARCODE_VERIFICAR = 5;

	private Bitmap imagenCertificadoBitmap;

	private ImageView fotoViviendaImageView;

	private int banderaFormulario = 0;

	private int banderaVivienda = 0;

	private Imagen imagen = new Imagen();

	private Button actualizarCertificadoButton;

	private EditText certificadoEditText;

	private Button limpiarButton;

	private Button actualizarCertificadoVerificarButton;

	private EditText certificadoVerificarEditText;

	private Button limpiarCertificadoVerificarButton;



	private LinearLayout pantallaNucleosLinearLayout;

	private Hogar hogar;

	private LinearLayout linlaHeaderProgress;

	protected Uri mImageCertificadoUri;

	private TextView avisoEncuestaCompletaTextView;

	private Animation myFadeInAnimation;


	public static final String PRODUCT_MODE = "PRODUCT_MODE";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View item = inflater.inflate(
				R.layout.activity_main_fragment_certificado_imagen, container,
				false);

		contentResolver = getActivity().getContentResolver();
		this.obtenerVistas(item);

		return item;
	}

	/**
	 * Método para obtener las controles de la vista
	 */
	private void obtenerVistas(View item) {

		pantallaNucleosLinearLayout = item.findViewById(R.id.pantallaNucleosLinearLayout);
		actualizarCertificadoButton = item.findViewById(R.id.actualizarCertificadoButton);
		capturarFotoViviendaButton = item.findViewById(R.id.capturarFotoViviendaButton);
		finalizarEntrevistaButton = item.findViewById(R.id.finalizarEntrevistaButton);
		limpiarButton = item.findViewById(R.id.limpiarButton);
		certificadoEditText = item.findViewById(R.id.certificadoEditText);
		avisoEncuestaCompletaTextView = item.findViewById(R.id.avisoEncuestaCompletaTextView);
		actualizarCertificadoVerificarButton = item.findViewById(R.id.actualizarCertificadoVerificarButton);
		certificadoVerificarEditText = item.findViewById(R.id.certificadoVerificarEditText);
		limpiarCertificadoVerificarButton = item.findViewById(R.id.limpiarCertificadoVerificarButton);
		myFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);


	}

	@Override
	public void onResume() {
		avisoEncuestaCompletaTextView.startAnimation(myFadeInAnimation);
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		linlaHeaderProgress = getActivity().findViewById(R.id.linlaHeaderProgress);
		vivienda = ViviendaFragment.getVivienda();


	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {



		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}


}
