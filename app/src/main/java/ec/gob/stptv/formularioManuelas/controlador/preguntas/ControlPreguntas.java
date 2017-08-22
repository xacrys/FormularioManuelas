package ec.gob.stptv.formularioManuelas.controlador.preguntas;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;

/**
 * Created by lmorales on 22/08/17.
 */

public class ControlPreguntas {

    public enum ControlEntrevista {

        SIN_ESTADO(-1), INCOMPLETA(0), COMPLETA(1),
        RECHAZO(2), NADIE_EN_CASA(3),INFORMANTE_NO_CALIFICADO(4), ELIMINADO(5),
        TODOS(10);


        private int valor;

        ControlEntrevista(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }

    }

    public static ArrayAdapter<Values> getControlEntrevistaAdapter(Context context) {

        ArrayList<Values> controlEntrevista = new ArrayList<Values>();

        controlEntrevista.add(new Values(ControlEntrevista.TODOS.getValor(), context.getString(R.string.estadoTodos)));

        controlEntrevista.add(new Values(ControlEntrevista.INCOMPLETA.getValor(), context.getString(R.string.estadoImcompleto)));
        controlEntrevista.add(new Values(ControlEntrevista.COMPLETA.getValor(), context.getString(R.string.controlEntrevistasOpcion1)));
        controlEntrevista.add(new Values(ControlEntrevista.RECHAZO.getValor(), context.getString(R.string.controlEntrevistasOpcion2)));
        controlEntrevista.add(new Values(ControlEntrevista.NADIE_EN_CASA.getValor(), context.getString(R.string.controlEntrevistasOpcion3)));
        controlEntrevista.add(new Values(ControlEntrevista.INFORMANTE_NO_CALIFICADO.getValor(),	context.getString(R.string.controlEntrevistasOpcion4)));

        ArrayAdapter<Values> controlEntrevistaAdapter = new ArrayAdapter<Values>(context,
                android.R.layout.simple_spinner_item, controlEntrevista);

        controlEntrevistaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return controlEntrevistaAdapter;
    }

}
