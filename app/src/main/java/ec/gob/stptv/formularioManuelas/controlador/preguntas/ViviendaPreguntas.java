package ec.gob.stptv.formularioManuelas.controlador.preguntas;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;


public class ViviendaPreguntas {

    /**
     * tipo de levantamiento de ocupacion
     */
    public enum TipoLevantamiento {
        BARRIDO(1),
        DEMANDA(1);

        private int valor;

        TipoLevantamiento(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getTipoLevantamientoAdapter(Context context) {

        ArrayList<Values> tipoLevantamiento = new ArrayList<Values>();

        tipoLevantamiento.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        tipoLevantamiento.add(new Values(TipoLevantamiento.BARRIDO.getValor(), context.getString(R.string.barrido)));
        tipoLevantamiento.add(new Values(TipoLevantamiento.DEMANDA.getValor(), context.getString(R.string.demanda)));

        ArrayAdapter<Values> tipoLevantamientoAdapter = new ArrayAdapter<Values>(context,
                android.R.layout.simple_spinner_item, tipoLevantamiento);

        tipoLevantamientoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tipoLevantamientoAdapter;
    }

    /**
     * AREA
     */
    public enum Area {
        URBANA(1),
        RURAL(2);

        private int valor;

        Area(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getAreaAdapter(Context context) {

        ArrayList<Values> area = new ArrayList<Values>();

        area.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        area.add(new Values(Area.URBANA.getValor(), context.getString(R.string.barrido)));
        area.add(new Values(Area.RURAL.getValor(), context.getString(R.string.demanda)));

        ArrayAdapter<Values> areaAdapter = new ArrayAdapter<Values>(context,
                android.R.layout.simple_spinner_item, area);

        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return areaAdapter;
    }


    /**
     * condicion de ocupacion
     */
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

        condicionOcupacion.add(new Values(CondicionOcupacion.OCUPADA.getValor(), context.getString(R.string.condicionOcupacionOpcion1)));

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

    /**
     *
     * pregunta 2 Vivienda Via Acceso Principal
     */
    public enum ViviendaViaAccesoPrincipal {

        CARRETERA(1),
        EMPEDRADO(2),
        LASTRADO(3),
        SENDERO(4),
        RIO(5),
        OTROS(6);

        private int valor;

        ViviendaViaAccesoPrincipal(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getViviendaViaAccesoPrincipalAdapter(Context context) {

        ArrayList<Values> viviendaViaAccesoPrincipal = new ArrayList<Values>();

        viviendaViaAccesoPrincipal.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        viviendaViaAccesoPrincipal.add(new Values(ViviendaViaAccesoPrincipal.CARRETERA.getValor(),context.getString(R.string.viaAccesoPrincipalOpcion1)));
        viviendaViaAccesoPrincipal.add(new Values(ViviendaViaAccesoPrincipal.EMPEDRADO.getValor(),context.getString(R.string.viaAccesoPrincipalOpcion2)));
        viviendaViaAccesoPrincipal.add(new Values(ViviendaViaAccesoPrincipal.LASTRADO.getValor(),context.getString(R.string.viaAccesoPrincipalOpcion3)));
        viviendaViaAccesoPrincipal.add(new Values(ViviendaViaAccesoPrincipal.SENDERO.getValor(),context.getString(R.string.viaAccesoPrincipalOpcion4)));
        viviendaViaAccesoPrincipal.add(new Values(ViviendaViaAccesoPrincipal.RIO.getValor(),context.getString(R.string.viaAccesoPrincipalOpcion5)));
        viviendaViaAccesoPrincipal.add(new Values(ViviendaViaAccesoPrincipal.OTROS.getValor(),context.getString(R.string.viaAccesoPrincipalOpcion6)));

        ArrayAdapter<Values> viviendaViaAccesoPrincipalAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, viviendaViaAccesoPrincipal);
        viviendaViaAccesoPrincipalAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return viviendaViaAccesoPrincipalAdapter;
    }

    /**
     *
     * pregunta 3 Vivienda Material Techo
     */
    public enum ViviendaMaterialTecho {

        HORMIGON(1),
        ASBESTO(2),
        ZINC(3),
        TEJA(4),
        PALMA(5),
        OTRO_MATERIAL(6);

        private int valor;

        ViviendaMaterialTecho(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getViviendaMaterialTechoAdapter(Context context) {

        ArrayList<Values> viviendaMaterialTecho = new ArrayList<Values>();

        viviendaMaterialTecho.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        viviendaMaterialTecho.add(new Values(ViviendaMaterialTecho.HORMIGON.getValor(),context.getString(R.string.materialTechoOpcion1)));
        viviendaMaterialTecho.add(new Values(ViviendaMaterialTecho.ASBESTO.getValor(),context.getString(R.string.materialTechoOpcion2)));
        viviendaMaterialTecho.add(new Values(ViviendaMaterialTecho.ZINC.getValor(),context.getString(R.string.materialTechoOpcion3)));
        viviendaMaterialTecho.add(new Values(ViviendaMaterialTecho.TEJA.getValor(),context.getString(R.string.materialTechoOpcion4)));
        viviendaMaterialTecho.add(new Values(ViviendaMaterialTecho.PALMA.getValor(),context.getString(R.string.materialTechoOpcion5)));
        viviendaMaterialTecho.add(new Values(ViviendaMaterialTecho.OTRO_MATERIAL.getValor(),context.getString(R.string.materialTechoOpcion6)));


        ArrayAdapter<Values> viviendaMaterialTechoAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, viviendaMaterialTecho);
        viviendaMaterialTechoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return viviendaMaterialTechoAdapter;
    }

    /**
     * pregunta 4 seccion3 Vivienda Material Piso
     */
    public enum ViviendaMaterialPiso {

        DUELA(1),
        BALDOSA(2),
        MARMOL(3),
        CEMENTO(4),
        TABLA(5),
        CANIA(6),
        TIERRA(7),
        OTRO_MATERIAL(8);

        private int valor;

        ViviendaMaterialPiso(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getViviendaMaterialPisoAdapter(Context context) {

        ArrayList<Values> viviendaMaterialPiso = new ArrayList<Values>();

        viviendaMaterialPiso.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.DUELA.getValor(),context.getString(R.string.materialPisoOpcion1)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.BALDOSA.getValor(),context.getString(R.string.materialPisoOpcion2)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.MARMOL.getValor(),context.getString(R.string.materialPisoOpcion3)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.CEMENTO.getValor(),context.getString(R.string.materialPisoOpcion4)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.TABLA.getValor(),context.getString(R.string.materialPisoOpcion5)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.CANIA.getValor(),context.getString(R.string.materialPisoOpcion6)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.TIERRA.getValor(),context.getString(R.string.materialPisoOpcion7)));
        viviendaMaterialPiso.add(new Values(ViviendaMaterialPiso.OTRO_MATERIAL.getValor(),context.getString(R.string.materialPisoOpcion8)));

        ArrayAdapter<Values> viviendaMaterialPisoAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, viviendaMaterialPiso);
        viviendaMaterialPisoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return viviendaMaterialPisoAdapter;
    }

    /**
     *
     * pregunta 5 seccion3 ViviendaMaterialParedes
     */
    public enum ViviendaMaterialParedes {

        HORMIGON(1),
        ASBESTO(2),
        ADOBE(3),
        MADERA(4),
        BAHAREQUE(5),
        CANIA(6),
        OTRO_MATERIAL(7);

        private int valor;

        ViviendaMaterialParedes(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getViviendaMaterialParedesAdapter(Context context) {

        ArrayList<Values> viviendaMaterialParedes = new ArrayList<Values>();

        viviendaMaterialParedes.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.HORMIGON.getValor(),context.getString(R.string.materialParedesOpcion1)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.ASBESTO.getValor(),context.getString(R.string.materialParedesOpcion2)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.ADOBE.getValor(),context.getString(R.string.materialParedesOpcion3)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.MADERA.getValor(),context.getString(R.string.materialParedesOpcion4)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.BAHAREQUE.getValor(),context.getString(R.string.materialParedesOpcion5)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.CANIA.getValor(),context.getString(R.string.materialParedesOpcion6)));
        viviendaMaterialParedes.add(new Values(ViviendaMaterialParedes.OTRO_MATERIAL.getValor(),context.getString(R.string.materialParedesOpcion7)));

        ArrayAdapter<Values> viviendaMaterialParedesAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, viviendaMaterialParedes);
        viviendaMaterialParedesAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return viviendaMaterialParedesAdapter;
    }

    /**
     *
     * pregunta 6 7 8 seccion3EstadoTechoPisoPared
     */
    public enum EstadoTechoPisoPared {

        BUENO(1),
        REGULAR(2),
        MALO(3);

        private int valor;

        EstadoTechoPisoPared(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getEstadoTechoPisoParedAdapter(Context context) {

        ArrayList<Values> estadoTechoPisoPared = new ArrayList<Values>();

        estadoTechoPisoPared.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        estadoTechoPisoPared.add(new Values(EstadoTechoPisoPared.BUENO.getValor(),context.getString(R.string.techoPisoParedOpcion1)));
        estadoTechoPisoPared.add(new Values(EstadoTechoPisoPared.REGULAR.getValor(),context.getString(R.string.techoPisoParedOpcion2)));
        estadoTechoPisoPared.add(new Values(EstadoTechoPisoPared.MALO.getValor(),context.getString(R.string.techoPisoParedOpcion3)));

        ArrayAdapter<Values> estadoTechoPisoParedAdapter = new ArrayAdapter<Values>(
                context, android.R.layout.simple_spinner_item, estadoTechoPisoPared);
        estadoTechoPisoParedAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return estadoTechoPisoParedAdapter;
    }

}
