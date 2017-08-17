package ec.gob.stptv.formularioManuelas.controlador.preguntas;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;


public class ViviendaPreguntas {

    public enum CondicionOcupacion {
        OCUPADA(1);

        private int valor;

        CondicionOcupacion(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getCondicionOcupacionAdapter(Context context) {

        ArrayList<Values> condicionOcupacion = new ArrayList<Values>();

        condicionOcupacion.add(new Values(ViviendaPreguntas.CondicionOcupacion.OCUPADA.getValor(), context.getString(R.string.condicionOcupacionOpcion1)));

        ArrayAdapter<Values> condicionOcupacionAdapter = new ArrayAdapter<Values>(context,
                android.R.layout.simple_spinner_item, condicionOcupacion);

        condicionOcupacionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return condicionOcupacionAdapter;
    }

    /**
     * tipo de vivienda
     */
    public enum TipoVivienda {

        CASA_VILLA(1),
        DEPARTAMENTO(2),
        CUARTOS(3),
        MEDIAGUA(4),
        RANCHO(5),
        CHOZA(6),
        OTRO(7);

        private int valor;

        TipoVivienda(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getTipoViviendaAdapter(Context context) {

        ArrayList<Values> tipoVivienda = new ArrayList<Values>();

        tipoVivienda.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        tipoVivienda.add(new Values(TipoVivienda.CASA_VILLA.getValor(),context.getString(R.string.tipoViviendaOpcion1)));
        tipoVivienda.add(new Values(TipoVivienda.DEPARTAMENTO.getValor(),context.getString(R.string.tipoViviendaOpcion2)));
        tipoVivienda.add(new Values(TipoVivienda.CUARTOS.getValor(),context.getString(R.string.tipoViviendaOpcion3)));
        tipoVivienda.add(new Values(TipoVivienda.MEDIAGUA.getValor(),context.getString(R.string.tipoViviendaOpcion4)));
        tipoVivienda.add(new Values(TipoVivienda.RANCHO.getValor(),context.getString(R.string.tipoViviendaOpcion5)));
        tipoVivienda.add(new Values(TipoVivienda.CHOZA.getValor(),context.getString(R.string.tipoViviendaOpcion6)));
        tipoVivienda.add(new Values(TipoVivienda.OTRO.getValor(),context.getString(R.string.tipoViviendaOpcion7)));

        ArrayAdapter<Values> tipoViviendaAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, tipoVivienda);
        tipoViviendaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tipoViviendaAdapter;
    }
}
