package ec.gob.stptv.formularioManuelas.controlador.actividades;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.preguntas.ViviendaPreguntas;
import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;

/***
 * Autor:Christian Tintin
 */
public class ViviendaFragment extends Fragment {

    private Spinner tipoLevantamientoSpinner;
    private Spinner areaSpinner;
    private Spinner provinciaSpinner;
    private Spinner cantoneSpinner;
    private Spinner parroquiaSpinner;


    private Spinner condicionOcupacionSpinner;
    private Spinner tipoViviendaSpinner;
    private Spinner viaAccesoPrincipalSpinner;
    private Spinner materialTechoTechoSpinner;
    private Spinner materialPisoSpinner;
    private Spinner materialParedesSpinner;
    private Spinner estadoTechoSpinner;
    private Spinner estadoPisoSpinner;
    private Spinner estadoParedSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.activity_main_fragment_vivienda,
                container, false);

        Bundle extra = getActivity().getIntent().getExtras();
        this.obtenerVistas(item);
        this.cargarPreguntas();

        return item;
    }

    /**
     * Método para obtener las controles de la vista
     */
    private void obtenerVistas(View item) {

        tipoLevantamientoSpinner = item.findViewById(R.id.tipoLevantamientoSpinner);
        areaSpinner = item.findViewById(R.id.areaSpinner);
        condicionOcupacionSpinner = item.findViewById(R.id.condicionOcupacionSpinner);

        tipoViviendaSpinner = item.findViewById(R.id.tipoViviendaSpinner);
        viaAccesoPrincipalSpinner = item.findViewById(R.id.viaAccesoPrincipalSpinner);
        materialTechoTechoSpinner = item.findViewById(R.id.materialTechoTechoSpinner);
        materialPisoSpinner = item.findViewById(R.id.materialPisoSpinner);
        materialParedesSpinner = item.findViewById(R.id.materialParedesSpinner);
        estadoTechoSpinner = item.findViewById(R.id.estadoTechoSpinner);
        estadoPisoSpinner = item.findViewById(R.id.estadoPisoSpinner);
        estadoParedSpinner = item.findViewById(R.id.estadoParedSpinner);

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

        tipoLevantamientoSpinner.setAdapter(ViviendaPreguntas.getTipoLevantamientoAdapter(getActivity()));
        areaSpinner.setAdapter(ViviendaPreguntas.getAreaAdapter(getActivity()));
        condicionOcupacionSpinner.setAdapter(ViviendaPreguntas.getCondicionOcupacionAdapter(getActivity()));

        tipoViviendaSpinner.setAdapter(ViviendaPreguntas.getTipoViviendaAdapter(getActivity()));
        viaAccesoPrincipalSpinner.setAdapter(ViviendaPreguntas.getViviendaViaAccesoPrincipalAdapter(getActivity()));
        materialTechoTechoSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialTechoAdapter(getActivity()));
        materialPisoSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialPisoAdapter(getActivity()));
        materialParedesSpinner.setAdapter(ViviendaPreguntas.getViviendaMaterialParedesAdapter(getActivity()));
        estadoTechoSpinner.setAdapter(ViviendaPreguntas.getEstadoTechoPisoParedAdapter(getActivity()));
        estadoPisoSpinner.setAdapter(ViviendaPreguntas.getEstadoTechoPisoParedAdapter(getActivity()));
        estadoParedSpinner.setAdapter(ViviendaPreguntas.getEstadoTechoPisoParedAdapter(getActivity()));

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
     */
    protected boolean validarCampos() {

        return false;
    }
}
