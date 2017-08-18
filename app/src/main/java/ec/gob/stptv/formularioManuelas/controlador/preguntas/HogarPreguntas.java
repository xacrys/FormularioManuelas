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
    private enum TenenciaHogar {

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
    private enum DocumentoHogar {
        
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

    /**
     * Fuente Agua
     */
    private enum FuenteAgua {

        RED_PUBLICA(1),
        PILETA(2),
        OTRA_FUENTE(3),
        CARRO_REPARTIDOR(4),
        POZO(5),
        RIO(6),
        AGUA_LLUVIA(7),
        OTRO(8);

        private int valor;
        FuenteAgua(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Fuente Agua
     */
    private enum UbicacionAgua {
        TUBERIA_DENTRO(1),
        TUBERIA_FUERA(2),
        TUBERIA_EXTERNA(3),
        NO_RECIBE(4);
        private int valor;
        UbicacionAgua(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Tratamiento Agua
     */
    private enum TratamientoAgua {
        
        HIERVEN(1),
        CLORO(2),
        FILTRAN(3),
        COMPRAN(4),
        NINGUNO(5);
        private int valor;
        TratamientoAgua(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * ServicioSanitario
     */
    private enum ServicioSanitario {
        ESCUSADO_ALCANTARILLADO(1),
        ESCUSADO_SEPTICO(2),
        ESCUSADO_CIEGO(3),
        DESCARGA_MAR(4),
        LETRINA(5),
        NO_TIENE(6);
        private int valor;
        ServicioSanitario(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * UbicacionSanitario
     */
    private enum UbicacionSanitario {
        DENTRO(1),
        FUERA(2),
        EXTERNA(3);
        private int valor;
        UbicacionSanitario(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * SERVICIO DUCHA
     */
    private enum ServicioDucha{
        EXCLUSIVO(1),
        COMPARTIDO(2),
        NO_TIENE(3);
        private int valor;
        ServicioDucha(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * COMO SE ELIMINA LA BASURA
     */
    private enum EliminaBasura{
        MUNICIPAL(1),
        CALLE(2),
        QUEMAN(3),
        ENTIERRAN(4),
        RECICLAN(5),
        CONTRATAN_SERVICIO(6),
        OTRO(7);
        private int valor;
        EliminaBasura(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * COMO OBTIENEN ENERGIA ELECTRICA
     */
    private enum TipoAlumbrado{
        EMPRESA_ELECTRICA(1),
        PLANTA_ELECTRICA(2),
        PANEL_SOLAR(3),
        VELA_CANDIL(4),
        NINGUNO(5);
        private int valor;
        TipoAlumbrado(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * COMO SE COCINA
     */
    private enum EnergeticoCocina{
        GAS(1),
        LENIA(2),
        ELECTRICIDAD(3),
        OTRO(4),
        NO_COCINA(5);
        private int valor;
        EnergeticoCocina(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getTenenciaHogarAdapter(Context context) {

        ArrayList<Values> tenenciaHogar = new ArrayList<>();

        tenenciaHogar.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        tenenciaHogar.add(new Values(TenenciaHogar.PROPIA_PAGANDO.getValor(),context.getString(R.string.tenenciaHogarOpcion1)));
        tenenciaHogar.add(new Values(TenenciaHogar.PROPIA_PAGANDO.getValor(),context.getString(R.string.tenenciaHogarOpcion2)));
        tenenciaHogar.add(new Values(TenenciaHogar.ARRIENDO.getValor(),context.getString(R.string.tenenciaHogarOpcion3)));
        tenenciaHogar.add(new Values(TenenciaHogar.ANTICRESIS.getValor(),context.getString(R.string.tenenciaHogarOpcion4)));
        tenenciaHogar.add(new Values(TenenciaHogar.CEDIDA.getValor(),context.getString(R.string.tenenciaHogarOpcion5)));
        tenenciaHogar.add(new Values(TenenciaHogar.RECIBIDA.getValor(),context.getString(R.string.tenenciaHogarOpcion6)));
        tenenciaHogar.add(new Values(TenenciaHogar.OTRO.getValor(),context.getString(R.string.tenenciaHogarOpcion7)));

        ArrayAdapter<Values> tenenciaHogarAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, tenenciaHogar);
        tenenciaHogarAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tenenciaHogarAdapter;
    }

    public static ArrayAdapter<Values> getDocumentoHogarAdapter(Context context) {

        ArrayList<Values> listaDocumentoHogar = new ArrayList<>();

        listaDocumentoHogar.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.ESCRITURA_TITULO.getValor(),context.getString(R.string.documentoHogarOpcion1)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.ESCRITURA_TRAMITE.getValor(),context.getString(R.string.documentoHogarOpcion2)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.PROMESA_COMPRA.getValor(),context.getString(R.string.documentoHogarOpcion3)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.POSESION.getValor(),context.getString(R.string.documentoHogarOpcion4)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.OTRO.getValor(),context.getString(R.string.documentoHogarOpcion5)));
        listaDocumentoHogar.add(new Values(DocumentoHogar.NINGUNO.getValor(),context.getString(R.string.documentoHogarOpcion6)));

        ArrayAdapter<Values> DocumentoHogarAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaDocumentoHogar);
        DocumentoHogarAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return DocumentoHogarAdapter;
    }

    public static ArrayAdapter<Values> getFuenteAguaAdapter(Context context) {

        ArrayList<Values> listaFuenteAgua = new ArrayList<>();

        listaFuenteAgua.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaFuenteAgua.add(new Values(FuenteAgua.RED_PUBLICA.getValor(),context.getString(R.string.fuenteAguaOpcion1)));
        listaFuenteAgua.add(new Values(FuenteAgua.PILETA.getValor(),context.getString(R.string.fuenteAguaOpcion2)));
        listaFuenteAgua.add(new Values(FuenteAgua.OTRA_FUENTE.getValor(),context.getString(R.string.fuenteAguaOpcion3)));
        listaFuenteAgua.add(new Values(FuenteAgua.CARRO_REPARTIDOR.getValor(),context.getString(R.string.fuenteAguaOpcion4)));
        listaFuenteAgua.add(new Values(FuenteAgua.POZO.getValor(),context.getString(R.string.fuenteAguaOpcion5)));
        listaFuenteAgua.add(new Values(FuenteAgua.RIO.getValor(),context.getString(R.string.fuenteAguaOpcion6)));
        listaFuenteAgua.add(new Values(FuenteAgua.AGUA_LLUVIA.getValor(),context.getString(R.string.fuenteAguaOpcion7)));
        listaFuenteAgua.add(new Values(FuenteAgua.OTRO.getValor(),context.getString(R.string.fuenteAguaOpcion8)));

        ArrayAdapter<Values> fuenteAguaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaFuenteAgua);
        fuenteAguaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return fuenteAguaAdapter;
    }

    public static ArrayAdapter<Values> getUbicacionAguaAdapter(Context context) {

        ArrayList<Values> listaUbicacionAgua = new ArrayList<>();

        listaUbicacionAgua.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaUbicacionAgua.add(new Values(UbicacionAgua.TUBERIA_DENTRO.getValor(),context.getString(R.string.ubicacionAguaOpcion1)));
        listaUbicacionAgua.add(new Values(UbicacionAgua.TUBERIA_FUERA.getValor(),context.getString(R.string.ubicacionAguaOpcion2)));
        listaUbicacionAgua.add(new Values(UbicacionAgua.TUBERIA_EXTERNA.getValor(),context.getString(R.string.ubicacionAguaOpcion3)));
        listaUbicacionAgua.add(new Values(UbicacionAgua.NO_RECIBE.getValor(),context.getString(R.string.ubicacionAguaOpcion4)));

        ArrayAdapter<Values> ubicacionAguaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaUbicacionAgua);
        ubicacionAguaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return ubicacionAguaAdapter;
    }

    public static ArrayAdapter<Values> getTratamientoAguaAdapter(Context context) {

        ArrayList<Values> listaTratamientoAgua = new ArrayList<>();

        listaTratamientoAgua.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaTratamientoAgua.add(new Values(TratamientoAgua.HIERVEN.getValor(),context.getString(R.string.tratamientoAguaOpcion1)));
        listaTratamientoAgua.add(new Values(TratamientoAgua.CLORO.getValor(),context.getString(R.string.tratamientoAguaOpcion2)));
        listaTratamientoAgua.add(new Values(TratamientoAgua.FILTRAN.getValor(),context.getString(R.string.tratamientoAguaOpcion3)));
        listaTratamientoAgua.add(new Values(TratamientoAgua.COMPRAN.getValor(),context.getString(R.string.tratamientoAguaOpcion4)));
        listaTratamientoAgua.add(new Values(TratamientoAgua.NINGUNO.getValor(),context.getString(R.string.tratamientoAguaOpcion5)));

        ArrayAdapter<Values> tratamientoAguaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaTratamientoAgua);
        tratamientoAguaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tratamientoAguaAdapter;
    }

    public static ArrayAdapter<Values> getServicioSanitarioAdapter(Context context) {

        ArrayList<Values> listaServicioSanitario = new ArrayList<>();

        listaServicioSanitario.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaServicioSanitario.add(new Values(ServicioSanitario.ESCUSADO_ALCANTARILLADO.getValor(),context.getString(R.string.servicioSanitarioOpcion1)));
        listaServicioSanitario.add(new Values(ServicioSanitario.ESCUSADO_SEPTICO.getValor(),context.getString(R.string.servicioSanitarioOpcion2)));
        listaServicioSanitario.add(new Values(ServicioSanitario.ESCUSADO_CIEGO.getValor(),context.getString(R.string.servicioSanitarioOpcion3)));
        listaServicioSanitario.add(new Values(ServicioSanitario.DESCARGA_MAR.getValor(),context.getString(R.string.servicioSanitarioOpcion4)));
        listaServicioSanitario.add(new Values(ServicioSanitario.LETRINA.getValor(),context.getString(R.string.servicioSanitarioOpcion5)));
        listaServicioSanitario.add(new Values(ServicioSanitario.NO_TIENE.getValor(),context.getString(R.string.servicioSanitarioOpcion6)));

        ArrayAdapter<Values> servicioSanitarioAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaServicioSanitario);
        servicioSanitarioAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return servicioSanitarioAdapter;
    }

    public static ArrayAdapter<Values> getUbicacionSanitarioAdapter(Context context) {

        ArrayList<Values> listaUbicacionSanitario = new ArrayList<>();

        listaUbicacionSanitario.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaUbicacionSanitario.add(new Values(UbicacionSanitario.DENTRO.getValor(),context.getString(R.string.ubicacionSanitarioOpcion1)));
        listaUbicacionSanitario.add(new Values(UbicacionSanitario.FUERA.getValor(),context.getString(R.string.ubicacionSanitarioOpcion2)));
        listaUbicacionSanitario.add(new Values(UbicacionSanitario.EXTERNA.getValor(),context.getString(R.string.ubicacionSanitarioOpcion3)));
        ArrayAdapter<Values> ubicacionSanitarioAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaUbicacionSanitario);
        ubicacionSanitarioAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return ubicacionSanitarioAdapter;
    }

    public static ArrayAdapter<Values> getServicioDuchaAdapter(Context context) {

        ArrayList<Values> listaServicioDucha = new ArrayList<>();

        listaServicioDucha.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaServicioDucha.add(new Values(ServicioDucha.EXCLUSIVO.getValor(),context.getString(R.string.servicioDuchaOpcion1)));
        listaServicioDucha.add(new Values(ServicioDucha.COMPARTIDO.getValor(),context.getString(R.string.servicioDuchaOpcion2)));
        listaServicioDucha.add(new Values(ServicioDucha.NO_TIENE.getValor(),context.getString(R.string.servicioDuchaOpcion3)));
        ArrayAdapter<Values> servicioDuchaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaServicioDucha);
        servicioDuchaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return servicioDuchaAdapter;
    }

    public static ArrayAdapter<Values> getEliminaBasuraAdapter(Context context) {

        ArrayList<Values> listaEliminaBasura = new ArrayList<>();

        listaEliminaBasura.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaEliminaBasura.add(new Values(EliminaBasura.MUNICIPAL.getValor(),context.getString(R.string.eliminaBasuraOpcion1)));
        listaEliminaBasura.add(new Values(EliminaBasura.CALLE.getValor(),context.getString(R.string.eliminaBasuraOpcion2)));
        listaEliminaBasura.add(new Values(EliminaBasura.QUEMAN.getValor(),context.getString(R.string.eliminaBasuraOpcion3)));
        listaEliminaBasura.add(new Values(EliminaBasura.ENTIERRAN.getValor(),context.getString(R.string.eliminaBasuraOpcion4)));
        listaEliminaBasura.add(new Values(EliminaBasura.RECICLAN.getValor(),context.getString(R.string.eliminaBasuraOpcion5)));
        listaEliminaBasura.add(new Values(EliminaBasura.CONTRATAN_SERVICIO.getValor(),context.getString(R.string.eliminaBasuraOpcion6)));
        listaEliminaBasura.add(new Values(EliminaBasura.OTRO.getValor(),context.getString(R.string.eliminaBasuraOpcion7)));
        ArrayAdapter<Values> eliminaBasuraAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaEliminaBasura);
        eliminaBasuraAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return eliminaBasuraAdapter;
    }

    public static ArrayAdapter<Values> getTipoAlumbradoAdapter(Context context) {

        ArrayList<Values> listaTipoAlumbrado = new ArrayList<>();

        listaTipoAlumbrado.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaTipoAlumbrado.add(new Values(TipoAlumbrado.EMPRESA_ELECTRICA.getValor(),context.getString(R.string.tipoAlumbradoOpcion1)));
        listaTipoAlumbrado.add(new Values(TipoAlumbrado.PLANTA_ELECTRICA.getValor(),context.getString(R.string.tipoAlumbradoOpcion2)));
        listaTipoAlumbrado.add(new Values(TipoAlumbrado.PANEL_SOLAR.getValor(),context.getString(R.string.tipoAlumbradoOpcion3)));
        listaTipoAlumbrado.add(new Values(TipoAlumbrado.VELA_CANDIL.getValor(),context.getString(R.string.tipoAlumbradoOpcion4)));
        listaTipoAlumbrado.add(new Values(TipoAlumbrado.NINGUNO.getValor(),context.getString(R.string.tipoAlumbradoOpcion5)));
        ArrayAdapter<Values> tipoAlumbradoAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaTipoAlumbrado);
        tipoAlumbradoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tipoAlumbradoAdapter;
    }
    

    public static ArrayAdapter<Values> getEnergeticoCocinaAdapter(Context context) {

        ArrayList<Values> listaEnergeticoCocina = new ArrayList<>();

        listaEnergeticoCocina.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaEnergeticoCocina.add(new Values(EnergeticoCocina.GAS.getValor(),context.getString(R.string.energeticoCocinaOpcion1)));
        listaEnergeticoCocina.add(new Values(EnergeticoCocina.LENIA.getValor(),context.getString(R.string.energeticoCocinaOpcion2)));
        listaEnergeticoCocina.add(new Values(EnergeticoCocina.ELECTRICIDAD.getValor(),context.getString(R.string.energeticoCocinaOpcion3)));
        listaEnergeticoCocina.add(new Values(EnergeticoCocina.OTRO.getValor(),context.getString(R.string.energeticoCocinaOpcion4)));
        listaEnergeticoCocina.add(new Values(EnergeticoCocina.NO_COCINA.getValor(),context.getString(R.string.energeticoCocinaOpcion5)));
        ArrayAdapter<Values> energeticoCocinaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaEnergeticoCocina);
        energeticoCocinaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return energeticoCocinaAdapter;
    }
    
    
    


}
