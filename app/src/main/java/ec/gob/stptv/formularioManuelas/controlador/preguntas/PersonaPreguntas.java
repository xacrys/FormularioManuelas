package ec.gob.stptv.formularioManuelas.controlador.preguntas;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ec.gob.stptv.formularioManuelas.R;
import ec.gob.stptv.formularioManuelas.controlador.util.Global;
import ec.gob.stptv.formularioManuelas.controlador.util.Values;


public class PersonaPreguntas {

    /**
     * Enum para Tipo de Residente
     */
    private enum TipoResidente {

        HABITUALES_PRESENTES(1),
        HABITALES_AUSENTES(2),
        TEMPORALES_PRESENTES(3);
        private int valor;
        TipoResidente(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para documentacion
     */
    private enum ControlTrabajoDocumento {

        CEDULA(1),
        PARTIDA_NACIMIENTO(2),
        NO_SABE(3),
        NUNCA_TUVO(4),
        PARTIDA_SIN_CEDULA(5),
        NO_ESTA_INSCRITO(6),
        PASAPORTE(7);

        private int valor;

        ControlTrabajoDocumento(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para Sexo
     */
    private enum SexoPersona {
        MASCULINO(1),
        FEMENINO(2);
        private int valor;
        SexoPersona(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para parentesco
     */
    private enum ControlTrabajoParentesco {

        JEFE(1),
        ESPOSO(2),
        HIJO(3),
        HIJASTRO(4),
        PADRE(5),
        SUEGRO(6),
        YERNO(7),
        NIETO(8),
        HERMANO(9),
        CUNIADO(10),
        OTRO_FAMILIAR(11),
        NO_FAMILIAR(12),
        SERVICIO_DOMESTICO(14);


        private int valor;

        ControlTrabajoParentesco(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para ESTADO CIVIL
     */
    private enum ControlTrabajoEstadoCivil {

        UNION_LIBRE(1),
        CASADO(2),
        VIUDO(3),
        SEPARADO(4),
        DIVORCIADO(5),
        SOLTERO(6);

        private int valor;

        ControlTrabajoEstadoCivil(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para NACIONALIDAD
     */
    private enum Nacionalidad {

        ECUATORIANO(1),
        COLOMBIANO(2),
        ESTADOUNIDENSE(3),
        PERUANO(4),
        ESPAÑOL(5),
        CUBANO(6),
        VENEZOLANO(7),
        OTRA(8);

        private int valor;

        Nacionalidad(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para AUTODEFINICION ETNICA
     */
    private enum AutoDefinicionEtnica {

        INDIGENA(1),
        AFROECUATORIANO(2),
        NEGRO(3),
        MULATO(4),
        MONTUBIO(5),
        MESTIZO(6),
        BLANCO(7),
        OTRO(8);

        private int valor;

        AutoDefinicionEtnica(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para APORTE AL SEGURO
     */
    private enum AporteSeguro {

        IESS_GENERAL(1),
        IESS_VOLUNTARIO(2),
        IESS_CAMPESINO(3),
        IESS_NO_REMUNERADO(4),
        ISSFA_ISSPOL(5),
        NO_APORTA(6),
        NO_SABE(7);

        private int valor;

        AporteSeguro(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para SEGURO DE SALUD
     */
    private enum SeguroSalud {

        IESS_GENERAL(1),
        IESS_VOLUNTARIO(2),
        IESS_CAMPESINO(3),
        ISSFA_ISSPOL(4),
        PRIVADO_CON_HOSPITALIZACION(5),
        PRIVADO_SIN_HOSPITALIZACION(6),
        SERVICIOS_MUNICIPALES(7),
        MSP(8),
        OTROS(9),
        NINGUNO(10);

        private int valor;

        SeguroSalud(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para ACUDE A ESTABLECIMIENTO DE EDUCACION
     */
    private enum AsistenciaEstablecimiento {

        ORDINARIA(1),
        ESPECIALIZADA(2),
        SUPERIOR(3),
        NO_ASISTE(4);

        private int valor;

        AsistenciaEstablecimiento(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para RECIBIO ATENCION DE SERVICIO DE PROTECCION ESPECIALIZADA
     */
    private enum ServicioProteccion {

        CIBV(1),
        CNH(2),
        CENTRO_REFERENCIA(3),
        CENTROS_DIURNOS(4),
        ATENCION_HOGAR(5),
        NO_RECIBIO(6);

        private int valor;

        ServicioProteccion(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para INSTITUCIONES DIERON AYUDAS TECNICAS
     */
    private enum InstitucionAyudasTecnicas {
        GOBIERNO_CENTRAL(1),
        GOBIERNO_AUTONOMOS(2),
        ORGANIZACIONES_PRIVADAS(3),
        NINGUNA(4);
        private int valor;

        InstitucionAyudasTecnicas(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    /**
     * Enum para ENFERMEDADES
     */
    private enum SufreEnfermedades {
        ENFERMEDAD_CATASTROFICA(1),
        ENFERMEDAD_RARA(2),
        VIH(3),
        NINGUNA(4);
        private int valor;

        SufreEnfermedades(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }


    /**
     * Enum para ENFERMEDADES CATASTROFICAS
     */
    private enum EnfermedadCatastrofica {
        MALFORMACIONES_CONGENITAS(1),
        CANCER(2),
        TUMOR_CEREBRAL(3),
        INSUFECIENCIA_RENAL(4),
        TRANSPLANTE(5),
        QUEMADURAS(6),
        MALFORMACIONES_ARTERIALES(7),
        KLIPPEL(8),
        ANEURISMA(9);

        private int valor;

        EnfermedadCatastrofica(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }
    }

    public static ArrayAdapter<Values> getTipoResidenteAdapter(Context context) {

        ArrayList<Values> listaTipoResidente = new ArrayList<>();

        listaTipoResidente.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaTipoResidente.add(new Values(TipoResidente.HABITUALES_PRESENTES.getValor(), context.getString(R.string.tipoResidenteOpcion1)));
        listaTipoResidente.add(new Values(TipoResidente.HABITALES_AUSENTES.getValor(), context.getString(R.string.tipoResidenteOpcion2)));
        listaTipoResidente.add(new Values(TipoResidente.TEMPORALES_PRESENTES.getValor(), context.getString(R.string.tipoResidenteOpcion3)));
        ArrayAdapter<Values> tipoResidenteAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaTipoResidente);
        tipoResidenteAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tipoResidenteAdapter;
    }

    public static ArrayAdapter<Values> getControlTrabajoDocumentoAdapter(Context context) {

        ArrayList<Values> listaControlTrabajoDocumento = new ArrayList<>();

        listaControlTrabajoDocumento.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.CEDULA.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion1)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.PARTIDA_NACIMIENTO.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion2)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.NO_SABE.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion3)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.NUNCA_TUVO.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion4)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.PARTIDA_SIN_CEDULA.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion5)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.NO_ESTA_INSCRITO.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion6)));
        listaControlTrabajoDocumento.add(new Values(ControlTrabajoDocumento.PASAPORTE.getValor(), context.getString(R.string.controlTrabajoDocumentoOpcion7)));

        ArrayAdapter<Values> controlTrabajoDocumentoAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaControlTrabajoDocumento);
        controlTrabajoDocumentoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return controlTrabajoDocumentoAdapter;
    }

    public static ArrayAdapter<Values> getSexoPersonaAdapter(Context context) {

        ArrayList<Values> listaSexoPersona = new ArrayList<>();

        listaSexoPersona.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaSexoPersona.add(new Values(SexoPersona.MASCULINO.getValor(), context.getString(R.string.sexoOpcion1)));
        listaSexoPersona.add(new Values(SexoPersona.FEMENINO.getValor(), context.getString(R.string.sexoOpcion2)));
        ArrayAdapter<Values> sexoPersonaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaSexoPersona);
        sexoPersonaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return sexoPersonaAdapter;
    }

    public static ArrayAdapter<Values> getControlTrabajoParentescoAdapter(Context context) {

        ArrayList<Values> listaControlTrabajoParentesco = new ArrayList<>();

        listaControlTrabajoParentesco.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.JEFE.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion1)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.ESPOSO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion2)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.HIJO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion3)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.HIJASTRO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion4)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.PADRE.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion5)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.SUEGRO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion6)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.YERNO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion7)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.NIETO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion8)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.HERMANO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion9)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.CUNIADO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion10)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.OTRO_FAMILIAR.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion11)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.NO_FAMILIAR.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion12)));
        listaControlTrabajoParentesco.add(new Values(ControlTrabajoParentesco.SERVICIO_DOMESTICO.getValor(), context.getString(R.string.controlTrabajoParentescoJefeHogarOpcion13)));

        ArrayAdapter<Values> controlTrabajoParentescoAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaControlTrabajoParentesco);
        controlTrabajoParentescoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return controlTrabajoParentescoAdapter;
    }

    public static ArrayAdapter<Values> getControlTrabajoEstadoCivilAdapter(Context context) {

        ArrayList<Values> listaControlTrabajoEstadoCivil = new ArrayList<>();

        listaControlTrabajoEstadoCivil.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaControlTrabajoEstadoCivil.add(new Values(ControlTrabajoEstadoCivil.UNION_LIBRE.getValor(), context.getString(R.string.controlTrabajoEstadoCivilOpcion1)));
        listaControlTrabajoEstadoCivil.add(new Values(ControlTrabajoEstadoCivil.CASADO.getValor(), context.getString(R.string.controlTrabajoEstadoCivilOpcion2)));
        listaControlTrabajoEstadoCivil.add(new Values(ControlTrabajoEstadoCivil.VIUDO.getValor(), context.getString(R.string.controlTrabajoEstadoCivilOpcion3)));
        listaControlTrabajoEstadoCivil.add(new Values(ControlTrabajoEstadoCivil.SEPARADO.getValor(), context.getString(R.string.controlTrabajoEstadoCivilOpcion4)));
        listaControlTrabajoEstadoCivil.add(new Values(ControlTrabajoEstadoCivil.DIVORCIADO.getValor(), context.getString(R.string.controlTrabajoEstadoCivilOpcion5)));
        listaControlTrabajoEstadoCivil.add(new Values(ControlTrabajoEstadoCivil.SOLTERO.getValor(), context.getString(R.string.controlTrabajoEstadoCivilOpcion6)));
        ArrayAdapter<Values> controlTrabajoEstadoCivilAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaControlTrabajoEstadoCivil);
        controlTrabajoEstadoCivilAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return controlTrabajoEstadoCivilAdapter;
    }

    public static ArrayAdapter<Values> getNacionalidadAdapter(Context context) {

        ArrayList<Values> listaNacionalidad = new ArrayList<>();

        listaNacionalidad.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaNacionalidad.add(new Values(Nacionalidad.ECUATORIANO.getValor(), context.getString(R.string.nacionalidadOpcion1)));
        listaNacionalidad.add(new Values(Nacionalidad.COLOMBIANO.getValor(), context.getString(R.string.nacionalidadOpcion2)));
        listaNacionalidad.add(new Values(Nacionalidad.ESTADOUNIDENSE.getValor(), context.getString(R.string.nacionalidadOpcion3)));
        listaNacionalidad.add(new Values(Nacionalidad.PERUANO.getValor(), context.getString(R.string.nacionalidadOpcion4)));
        listaNacionalidad.add(new Values(Nacionalidad.ESPAÑOL.getValor(), context.getString(R.string.nacionalidadOpcion5)));
        listaNacionalidad.add(new Values(Nacionalidad.CUBANO.getValor(), context.getString(R.string.nacionalidadOpcion6)));
        listaNacionalidad.add(new Values(Nacionalidad.VENEZOLANO.getValor(), context.getString(R.string.nacionalidadOpcion7)));
        listaNacionalidad.add(new Values(Nacionalidad.OTRA.getValor(), context.getString(R.string.nacionalidadOpcion8)));
        ArrayAdapter<Values> nacionalidadAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaNacionalidad);
        nacionalidadAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return nacionalidadAdapter;
    }

    public static ArrayAdapter<Values> getAutoDefinicionEtnicaAdapter(Context context) {

        ArrayList<Values> listaAutoDefinicionEtnica = new ArrayList<>();

        listaAutoDefinicionEtnica.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.INDIGENA.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion1)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.AFROECUATORIANO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion2)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.NEGRO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion3)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.MULATO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion4)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.MONTUBIO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion5)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.MESTIZO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion6)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.BLANCO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion7)));
        listaAutoDefinicionEtnica.add(new Values(AutoDefinicionEtnica.OTRO.getValor(), context.getString(R.string.autodefinicionEtnicaOpcion8)));
        ArrayAdapter<Values> autodefinicionEtnicaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaAutoDefinicionEtnica);
        autodefinicionEtnicaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return autodefinicionEtnicaAdapter;
    }

    public static ArrayAdapter<Values> getAporteSeguroAdapter(Context context) {

        ArrayList<Values> listaAporteSeguro = new ArrayList<>();

        listaAporteSeguro.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaAporteSeguro.add(new Values(AporteSeguro.IESS_GENERAL.getValor(), context.getString(R.string.aporteSeguroOpcion1)));
        listaAporteSeguro.add(new Values(AporteSeguro.IESS_VOLUNTARIO.getValor(), context.getString(R.string.aporteSeguroOpcion2)));
        listaAporteSeguro.add(new Values(AporteSeguro.IESS_CAMPESINO.getValor(), context.getString(R.string.aporteSeguroOpcion3)));
        listaAporteSeguro.add(new Values(AporteSeguro.IESS_NO_REMUNERADO.getValor(), context.getString(R.string.aporteSeguroOpcion4)));
        listaAporteSeguro.add(new Values(AporteSeguro.ISSFA_ISSPOL.getValor(), context.getString(R.string.aporteSeguroOpcion5)));
        listaAporteSeguro.add(new Values(AporteSeguro.NO_APORTA.getValor(), context.getString(R.string.aporteSeguroOpcion6)));
        listaAporteSeguro.add(new Values(AporteSeguro.NO_SABE.getValor(), context.getString(R.string.aporteSeguroOpcion7)));
        ArrayAdapter<Values> aporteSeguroAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaAporteSeguro);
        aporteSeguroAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return aporteSeguroAdapter;
    }

    public static ArrayAdapter<Values> getSeguroSaludAdapter(Context context) {

        ArrayList<Values> listaSeguroSalud = new ArrayList<>();

        listaSeguroSalud.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaSeguroSalud.add(new Values(SeguroSalud.IESS_GENERAL.getValor(), context.getString(R.string.seguroSaludOpcion1)));
        listaSeguroSalud.add(new Values(SeguroSalud.IESS_VOLUNTARIO.getValor(), context.getString(R.string.seguroSaludOpcion2)));
        listaSeguroSalud.add(new Values(SeguroSalud.IESS_CAMPESINO.getValor(), context.getString(R.string.seguroSaludOpcion3)));
        listaSeguroSalud.add(new Values(SeguroSalud.ISSFA_ISSPOL.getValor(), context.getString(R.string.seguroSaludOpcion4)));
        listaSeguroSalud.add(new Values(SeguroSalud.PRIVADO_CON_HOSPITALIZACION.getValor(), context.getString(R.string.seguroSaludOpcion5)));
        listaSeguroSalud.add(new Values(SeguroSalud.PRIVADO_SIN_HOSPITALIZACION.getValor(), context.getString(R.string.seguroSaludOpcion6)));
        listaSeguroSalud.add(new Values(SeguroSalud.SERVICIOS_MUNICIPALES.getValor(), context.getString(R.string.seguroSaludOpcion7)));
        listaSeguroSalud.add(new Values(SeguroSalud.MSP.getValor(), context.getString(R.string.seguroSaludOpcion8)));
        listaSeguroSalud.add(new Values(SeguroSalud.OTROS.getValor(), context.getString(R.string.seguroSaludOpcion9)));
        listaSeguroSalud.add(new Values(SeguroSalud.NINGUNO.getValor(), context.getString(R.string.seguroSaludOpcion10)));
        ArrayAdapter<Values> seguroSaludAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaSeguroSalud);
        seguroSaludAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return seguroSaludAdapter;
    }

    public static ArrayAdapter<Values> getAsistenciaEstablecimientoAdapter(Context context) {

        ArrayList<Values> listaAsistenciaEstablecimiento = new ArrayList<>();

        listaAsistenciaEstablecimiento.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaAsistenciaEstablecimiento.add(new Values(AsistenciaEstablecimiento.ORDINARIA.getValor(), context.getString(R.string.asistenciaEstablecimientoOpcion1)));
        listaAsistenciaEstablecimiento.add(new Values(AsistenciaEstablecimiento.ESPECIALIZADA.getValor(), context.getString(R.string.asistenciaEstablecimientoOpcion2)));
        listaAsistenciaEstablecimiento.add(new Values(AsistenciaEstablecimiento.SUPERIOR.getValor(), context.getString(R.string.asistenciaEstablecimientoOpcion3)));
        listaAsistenciaEstablecimiento.add(new Values(AsistenciaEstablecimiento.NO_ASISTE.getValor(), context.getString(R.string.asistenciaEstablecimientoOpcion4)));
        ArrayAdapter<Values> asistenciaEstablecimientoAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaAsistenciaEstablecimiento);
        asistenciaEstablecimientoAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return asistenciaEstablecimientoAdapter;
    }

    public static ArrayAdapter<Values> getServicioProteccionAdapter(Context context) {

        ArrayList<Values> listaServicioProteccion = new ArrayList<>();

        listaServicioProteccion.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaServicioProteccion.add(new Values(ServicioProteccion.CIBV.getValor(), context.getString(R.string.servicioProteccionOpcion1)));
        listaServicioProteccion.add(new Values(ServicioProteccion.CNH.getValor(), context.getString(R.string.servicioProteccionOpcion2)));
        listaServicioProteccion.add(new Values(ServicioProteccion.CENTRO_REFERENCIA.getValor(), context.getString(R.string.servicioProteccionOpcion3)));
        listaServicioProteccion.add(new Values(ServicioProteccion.CENTROS_DIURNOS.getValor(), context.getString(R.string.servicioProteccionOpcion4)));
        listaServicioProteccion.add(new Values(ServicioProteccion.ATENCION_HOGAR.getValor(), context.getString(R.string.servicioProteccionOpcion5)));
        listaServicioProteccion.add(new Values(ServicioProteccion.NO_RECIBIO.getValor(), context.getString(R.string.servicioProteccionOpcion6)));
        ArrayAdapter<Values> servicioProteccionAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaServicioProteccion);
        servicioProteccionAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return servicioProteccionAdapter;
    }

    public static ArrayAdapter<Values> getInstitucionAyudasTecnicasAdapter(Context context) {

        ArrayList<Values> listaInstitucionAyudasTecnicas = new ArrayList<>();

        listaInstitucionAyudasTecnicas.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaInstitucionAyudasTecnicas.add(new Values(InstitucionAyudasTecnicas.GOBIERNO_CENTRAL.getValor(), context.getString(R.string.institucionAyudasTecnicasOpcionA)));
        listaInstitucionAyudasTecnicas.add(new Values(InstitucionAyudasTecnicas.GOBIERNO_AUTONOMOS.getValor(), context.getString(R.string.institucionAyudasTecnicasOpcionB)));
        listaInstitucionAyudasTecnicas.add(new Values(InstitucionAyudasTecnicas.ORGANIZACIONES_PRIVADAS.getValor(), context.getString(R.string.institucionAyudasTecnicasOpcionC)));
        listaInstitucionAyudasTecnicas.add(new Values(InstitucionAyudasTecnicas.NINGUNA.getValor(), context.getString(R.string.institucionAyudasTecnicasOpcionD)));

        ArrayAdapter<Values> institucionAyudasTecnicasAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaInstitucionAyudasTecnicas);
        institucionAyudasTecnicasAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return institucionAyudasTecnicasAdapter;
    }

    public static ArrayAdapter<Values> getSufreEnfermedadesAdapter(Context context) {

        ArrayList<Values> listaSufreEnfermedades = new ArrayList<>();

        listaSufreEnfermedades.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaSufreEnfermedades.add(new Values(SufreEnfermedades.ENFERMEDAD_CATASTROFICA.getValor(), context.getString(R.string.sufreEnfermedadesOpcion1)));
        listaSufreEnfermedades.add(new Values(SufreEnfermedades.ENFERMEDAD_RARA.getValor(), context.getString(R.string.sufreEnfermedadesOpcion2)));
        listaSufreEnfermedades.add(new Values(SufreEnfermedades.VIH.getValor(), context.getString(R.string.sufreEnfermedadesOpcion3)));
        listaSufreEnfermedades.add(new Values(SufreEnfermedades.NINGUNA.getValor(), context.getString(R.string.sufreEnfermedadesOpcion4)));

        ArrayAdapter<Values> sufreEnfermedadesAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaSufreEnfermedades);
        sufreEnfermedadesAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return sufreEnfermedadesAdapter;
    }


    public static ArrayAdapter<Values> getEnfermedadCatastroficaAdapter(Context context) {

        ArrayList<Values> listaEnfermedadCatastrofica = new ArrayList<>();

        listaEnfermedadCatastrofica.add(new Values(Global.VALOR_SELECCIONE, context.getString(R.string.seleccionRespuesta)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.MALFORMACIONES_CONGENITAS.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion1)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.CANCER.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion2)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.TUMOR_CEREBRAL.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion3)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.INSUFECIENCIA_RENAL.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion4)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.TRANSPLANTE.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion5)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.QUEMADURAS.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion6)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.MALFORMACIONES_ARTERIALES.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion7)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.KLIPPEL.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion8)));
        listaEnfermedadCatastrofica.add(new Values(EnfermedadCatastrofica.ANEURISMA.getValor(), context.getString(R.string.enfermedadCatastroficaOpcion9)));

        ArrayAdapter<Values> enfermedadCatastroficaAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, listaEnfermedadCatastrofica);
        enfermedadCatastroficaAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return enfermedadCatastroficaAdapter;
    }
}
