package ec.gob.stptv.formularioManuelas.controlador.preguntas;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;


public class HogarPreguntas {

    /**
     * Tenencia Hogar
     */
    public enum TenenciaHogar {

        PROPIA_PAGANDO(1),
        PROPIA_PAGADA(2),
        ARRIENDO(3),
        ANTICRESIS(4),
        CEDIDA(5),
        RECIBIDA(6),
        OTRO(7);

        private int valor;
        TenenciaHogar(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Documento Hogar
     */
    public enum DocumentoHogar {
        
        ESCRITURA_TITULO(1),
        ESCRITURA_TRAMITE(2),
        PROMESA_COMPRA(3),
        POSESION(4),
        OTRO(5),
        NINGUNO(6);

        private int valor;
        DocumentoHogar(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getTenenciaHogarAdapter(Context context) {

        ArrayList<Values> tenenciaHogar = new ArrayList<Values>();

        tenenciaHogar.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        tenenciaHogar.add(new Values(TenenciaHogar.PROPIA_PAGANDO.getValor(),context.getString(R.string.tenenciaHogarOpcion1)));
        tenenciaHogar.add(new Values(TenenciaHogar.PROPIA_PAGANDO.getValor(),context.getString(R.string.tenenciaHogarOpcion2)));
        tenenciaHogar.add(new Values(TenenciaHogar.ARRIENDO.getValor(),context.getString(R.string.tenenciaHogarOpcion3)));
        tenenciaHogar.add(new Values(TenenciaHogar.ANTICRESIS.getValor(),context.getString(R.string.tenenciaHogarOpcion4)));
        tenenciaHogar.add(new Values(TenenciaHogar.CEDIDA.getValor(),context.getString(R.string.tenenciaHogarOpcion5)));
        tenenciaHogar.add(new Values(TenenciaHogar.RECIBIDA.getValor(),context.getString(R.string.tenenciaHogarOpcion6)));
        tenenciaHogar.add(new Values(TenenciaHogar.OTRO.getValor(),context.getString(R.string.tenenciaHogarOpcion7)));

        ArrayAdapter<Values> tenenciaHogarAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, tenenciaHogar);
        tenenciaHogarAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tenenciaHogarAdapter;
    }

    public static ArrayAdapter<Values> getDocumentoHogarAdapter(Context context) {

        ArrayList<Values> listaDocumentoHogar = new ArrayList<Values>();

        listaDocumentoHogar.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.ESCRITURA_TITULO.getValor(),context.getString(R.string.documentoHogarOpcion1)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.ESCRITURA_TRAMITE.getValor(),context.getString(R.string.documentoHogarOpcion2)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.PROMESA_COMPRA.getValor(),context.getString(R.string.documentoHogarOpcion3)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.POSESION.getValor(),context.getString(R.string.documentoHogarOpcion4)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.OTRO.getValor(),context.getString(R.string.documentoHogarOpcion5)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.NINGUNO.getValor(),context.getString(R.string.documentoHogarOpcion6)));

        ArrayAdapter<Values> DocumentoHogarAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, listaDocumentoHogar);
        DocumentoHogarAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return DocumentoHogarAdapter;
    }

    


}
