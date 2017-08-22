package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.HogarPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.PersonaPreguntas;
import ec.gob.stptv.formularioManuelas.modelo.entidades.Persona;

/***
 *  Autor:Christian Tintin
 */

public class MiembrosHogarTodasPersonasFragment extends Fragment {

    private Button nuevoButton;
    private Button atrasButton;
    private Button guardarPersonaButton;
    private Spinner parentescoSpinner;
    private Spinner estadoCivilSpinner;
    private Spinner nacionalidadSpinner;
    private Spinner etniaSpinner;
    private Spinner seguroSocial1Spinner;
    private Spinner seguroSocial2Spinner;
    private Spinner seguroSalud1Spinner;
    private Spinner seguroSalud2Spinner;
    private Spinner asistenciaEstablecimientoSpinner;
    private Spinner proteccionSocialpinner;
    private Spinner sufreEnfermedadesSpinner;
    private Spinner enfermedadCatastroficaSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_miembros_hogar_todas_personas,
                container, false);


        Bundle extra = getActivity().getIntent().getExtras();
        this.obtenerVistas(item);
        this.cargarPreguntas();
        this.realizarAcciones();
        return item;
    }

    /**
     * Método para obtener las controles de la vista
     *
     */
    private void obtenerVistas(View item) {

        nuevoButton = item.findViewById(R.id.nuevoButton);
        atrasButton = item.findViewById(R.id.atrasButton);
        parentescoSpinner=item.findViewById(R.id.parentescoSpinner);
        estadoCivilSpinner = item.findViewById(R.id.estadoCivilSpinner);
        nacionalidadSpinner = item.findViewById(R.id.nacionalidadSpinner);
        etniaSpinner = item.findViewById(R.id.etniaSpinner);
        seguroSocial1Spinner = item.findViewById(R.id.seguroSocial1Spinner);
        seguroSocial2Spinner=item.findViewById(R.id.seguroSocial2Spinner);
        seguroSalud1Spinner = item.findViewById(R.id.seguroSalud1Spinner);
        seguroSalud2Spinner = item.findViewById(R.id.seguroSalud2Spinner);
        asistenciaEstablecimientoSpinner = item.findViewById(R.id.asistenciaEstablecimientoSpinner);
        proteccionSocialpinner=item.findViewById(R.id.proteccionSocialpinner);
        sufreEnfermedadesSpinner=item.findViewById(R.id.sufreEnfermedadesSpinner);
        enfermedadCatastroficaSpinner=item.findViewById(R.id.enfermedadCatastroficaSpinner);
    }



    /**
     * Método que llena los controles con datos de la base
     */
    private void llenarCamposVivienda() {

    }

    /**
     * Método que carga las preguntas de los controles de la aplicacion por ejemplo los spinner
     */
    private void cargarPreguntas() {
        parentescoSpinner.setAdapter(PersonaPreguntas.getControlTrabajoParentescoAdapter(getActivity()));
        estadoCivilSpinner.setAdapter(PersonaPreguntas.getControlTrabajoEstadoCivilAdapter(getActivity()));
        nacionalidadSpinner.setAdapter(PersonaPreguntas.getNacionalidadAdapter(getActivity()));
        etniaSpinner.setAdapter(PersonaPreguntas.getAutoDefinicionEtnicaAdapter(getActivity()));
        seguroSocial1Spinner.setAdapter(PersonaPreguntas.getAporteSeguroAdapter(getActivity()));
        seguroSocial2Spinner.setAdapter(PersonaPreguntas.getAporteSeguroAdapter(getActivity()));
        seguroSalud1Spinner.setAdapter(PersonaPreguntas.getSeguroSaludAdapter(getActivity()));
        seguroSalud2Spinner.setAdapter(PersonaPreguntas.getSeguroSaludAdapter(getActivity()));
        proteccionSocialpinner.setAdapter(PersonaPreguntas.getServicioProteccionAdapter(getActivity()));
        asistenciaEstablecimientoSpinner.setAdapter(PersonaPreguntas.getAsistenciaEstablecimientoAdapter(getActivity()));
        sufreEnfermedadesSpinner.setAdapter(PersonaPreguntas.getSufreEnfermedadesAdapter(getActivity()));
        enfermedadCatastroficaSpinner.setAdapter(PersonaPreguntas.getEnfermedadCatastroficaAdapter(getActivity()));


    }

    /**
     * Método para habilitar o desabilitar los controles de la vista
     */
    public void habilitarDeshabilitar() {

    }

    /**
     * Método que guarda la vivienda en la base de datos
     */
    private void guardar() {

    }

    /**
     * Método que permite hacer los saltos de la pregunta
     */
    private void mallasValidacion() {


    }

    /**
     * valida campos obligatorios, numeros de telefonos etc.
     *
     */
    protected boolean validarCampos() {

        return false;
    }

    /**
     * mètodo para realizar las acciones
     */
    private void realizarAcciones() {

        atrasButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cerrarVentana();

            }
        });
    }


    /**
     * Metodo para cerrar la ventana
     */
    private void cerrarVentana() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

}
