package es.jtestme.executors.impl;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import es.jtestme.domain.JTestMeResult;

public class JNDIExecutor extends JTestMeDefaultExecutor {

    /*
     * "CD_NOMBRE" "DE_VALOR" "DE_COMENTARIO"
     * "numero.maximo.contraidos" "500" "número máximo de envío a contraído a INTECO de forma masiva"
     * "codigo.aplicacion" "SNC" "Identificador del nombre de la aplicación en Gestión de Usuarios"
     * "extension" ".htm" "Extensión por defecto de las URL gestionados por Struts"
     * "formato.hora" "HH:mm:ss" "Formato de la hora"
     * "formato.fecha" "dd/MM/yyyy" "Formato de la fecha"
     * "departamento.sancionador" "7"
     * "Identificador del tipo de ámbito de los Departamentos Sancionadores utilizado en Gestión de Usuarios"
     * "ambitos.padre" "3, 4, 5"
     * "Identificadores de los tipos de ámbito de las Delegacion, Subdelegación y Dirección Insular utilizado en Gestión de Usuarios"
     * "mail.smtp.port" "25" "Puerto del servidor SMTP"
     * "mail.smtp.host" "10.1.29.213" "Host del servidor SMTP"
     * "listados.paginacion.exportar" "1000"
     * "Tamaño máximo del número de elementos que se permiten exportar en los listados"
     * "listados.paginacion" "10" "Tamaño por defecto del número de elementos mostrados en los listados"
     * "mail.from" "aplicacion.sanciones@map.es" "Remitente de los emails enviados desde la aplicación"
     * "mail.smtp.password" "" "Password del servidor SMTP"
     * "mail.smtp.username" "" "Usuario del servidor SMTP"
     * "usuarios.mail.responsable" "carlos.alonso1@map.es" "Mails de los responsables de la aplicación Sanciones"
     * "usuarios.mail.asunto" "[Incidencia] Aplicación Administración Usuarios caida"
     * "Asunto de los mails enviados desde la aplicación Sanciones"
     * "usuarios.nombreejb" "FachadaUsuarios" "Nombre de la conexión al EJB de Gestión de Usuarios"
     * "usuarios.factory" "org.jnp.interfaces.NamingContextFactory"
     * "Factoría de la conexión al EJB de Gestión de Usuarios"
     * "usuarios.prefijo" "" "Prefijo de la conexión al EJB de Gestión de Usuarios"
     * "usuarios.url" "jnp://mazingerz.map.es:1099" "URL de la conexión al EJB de Gestión de Usuarios"
     * "festivos.sabados.festivos" "false"
     * "Indica si los sábados se consideran días festivos o no, para el calculo con festivos"
     * "festivos.prefijo" "" "Prefijo de la conexión al EJB de Festivos"
     * "festivos.factory" "org.jnp.interfaces.NamingContextFactory" "Factoría de la conexión al EJB de Festivos"
     * "festivos.nombreejb" "GestorFestivosBean" "Nombre de la conexión al EJB de Festivos"
     * "festivos.url" "jnp://mazingerz.map.es:1099" "URL de la conexión al EJB de Festivos"
     * "inteco.webservice.token" "https://webpub2.igae.meh.es/webtokenserpyf/PublicTokenCert.asmx"
     * "URL de la conexión al Servicio Web de INTECO Token"
     * "javax.net.ssl.trustStorePassword" "changeit"
     * "Password del almacen de certificados digitales necesario para las distintas conexiones"
     * "javax.net.ssl.trustStore" ""
     * "Ruta del almacen de certificados digitales necesario para las distintas conexiones"
     * "inteco.ssl.keystoretype" "PKCS12"
     * "Tipo de certificado digital necesario para la conexión al Servicio Web de INTECO"
     * "inteco.ssl.keystore" "" "Ruta del certificado digital necesario para la conexión al Servicio Web de INTECO"
     * "inteco.ssl.keystorepassword" "20100215"
     * "Password del certificado digital necesario para la conexión al Servicio Web de INTECO"
     * "inteco.proxy.port" "8080" "Proxy Port de la conexión al Servicio Web de INTECO"
     * "inteco.proxy.host" "10.1.86.226" "Proxy Host de la conexión al Servicio Web de INTECO"
     * "intecomasivo.respuesta.numero.intentos" "3"
     * "Número de intentos de consulta de la conexión al Servicio Web de INTECO Masiva"
     * "intecomasivo.respuesta.tiempo.espera" "300000"
     * "Tiempo máxima de espera para realizar la consulta de la conexión al Servicio Web de INTECO Masiva"
     * "intecomasivo.accion.consulta" "consulta" "Acción CONSULTA de la conexión al Servicio Web de INTECO Masiva"
     * "intecomasivo.accion.marcado" "marcado" "Acción MARCADO de la conexión al Servicio Web de INTECO Masiva"
     * "intecomasivo.accion.alta" "alta" "Acción ALTA de la conexión al Servicio Web de INTECO Masiva"
     * "intecomasivo.usuario.procesos" "proceso.automatico"
     * "Usuario de proceso de la conexión al Servicio Web de INTECO Masiva"
     * "inteco.sancion.menor" "6.01"
     * "Cuantía mínima de la sanción IGAE para poder enviar el apunte al Servicio Web de INTECO"
     * "inteco.formato.fecha" "yyyyMMdd" "Formato de la fecha de la conexión al Servicio Web de INTECO"
     * "inteco.nifautogenerado" "true"
     * "Indica si la aplicación genera automáticamene el NIF para realizar apuntes al Servicio Web de INTECO"
     * "inteco.modelo" "069" "Modelo de Impreso de la conexión al Servicio Web de INTECO"
     * "inteco.sistema.informacion" "INTECO" "Información del servicio de la conexión al Servicio Web de INTECO"
     * "inteco.sistema.clave" "INTECO" "Clave del servicio de la conexión al Servicio Web de INTECO"
     * "inteco.servicio.ip" "" "IP del servicio de la conexión al Servicio Web de INTECO"
     * "inteco.servicio.password" "" "Password del servicio de la conexión al Servicio Web de INTECO"
     * "inteco.servicio.usuario" "WSINTECOMAP5" "Usuario del servicio de la conexión al Servicio Web de INTECO"
     * "inteco.centrogestor.prefijo" "22050" "Código de Centro Gestor de la conexión al Servicio Web de INTECO"
     * "inteco.usuario" "ENWS0001" "Usuario de la conexión al Servicio Web de INTECO"
     * "inteco.url.impreso" "https://webpub4.igae.meh.es/intecoTEST/files/GetPdf?file=:archivo&token=:token"
     * "URL para obtener el impreso 069 del servicio de INTECO"
     * "inteco.webservice.masivo" "https://webpub4.igae.meh.es/intecoTEST/EnviosMasivos"
     * "URL de la conexión al Servicio Web de INTECO Masivo"
     * "inteco.webservice.simple" "https://webpub4.igae.meh.es/intecoTEST/IntecoIndividualesWS"
     * "URL de la conexión al Servicio Web de INTECO"
     * "pago.descripcion.inteco"
     * "Información de pago ingresado actualizada de manera automática, número de justificante"
     * "Descripción del pago generado automáticamente al realizarse el Ingreso desde el Servicio Web de INTECO"
     * "numero.maximo.impresionanexos" "1000"
     * "Número máximo de registros mostrados en el listado de Impresión de Anexos"
     * "numero.maximo.impresionescritos" "500"
     * "Número máximo de registros mostrados en el listado de Impresión Escritos"
     * "estadofase.descripcion.defecto.resolucion"
     * "Resolución del Expediente. Se ha dado de alta la fase de Resolución."
     * "Descripción del de estado fase creado por defecto al iniciarse la fase de Resolución de un Expediente"
     * "estadofase.descripcion.defecto.acuerdoiniciacion"
     * "Apertura del Expediente. Se ha dado de alta la fase de Acuerdo de Iniciación."
     * "Descripción del de estado fase creado por defecto al iniciarse un Expediente (Acuerdo de Iniciación)"
     * "estadofase.apertura.expediente.defecto" "Abierto"
     * "Tipo de estado fase por defecto si un Expediente no se ha iniciado (ninguna Fase)"
     * "estadofase.maxima.paralizacion" "1"
     * "Número de meses máximo que puede estar paralizado un Expediente hasta que se vuelve a activar el tiempo de prescripción"
     * "expediente.alegacion.copia" "10"
     * "Número de días que incrementa el tiempo para alegaciones si se solicita una copia del Expediente."
     * "expediente.caducidad.simplificado" "1"
     * "Número de meses máximo hasta que se caduca un Expediente con procedimiento simplificado"
     * "expediente.caducidad.normal" "6"
     * "Número de meses máximo hasta que se caduca un Expediente con procedimiento normal"
     * "aplicacion.extranjeria" "USUEXT001" "Valor del codigo de la aplicacion de extranjeria"
     * "aplicacion.drogas" "USUDROG001" "Valor del codigo de la aplicacion de dogas"
     * "aplicacion.mir" "USUMIR001" "Valor del codigo de la aplicaciones del ministerio"
     * "extranjeria.webservice.url" "http://afroditaa.map.es/extSanciones/" "Url de los servicios de extranjería"
     * "extranjeria.webservice.app" "USUSAN001"
     * "Identificador de la aplicación de sanciones para las llamadas a los SW de extranjería"
     * "extranjeria.webservice.clave" "sanciones"
     * "Clave de la aplicación de sanciones para acceder a los sw de extranjería"
     * "extranjeria.usuario.proceso" "procesoautomatico" "Proceso Automático de actualización de oficinas"
     * "estadisticas.inteco.dinamico.url" "http://10.1.31.18/SNC_INFORMES/IDinamico.aspx?informe=APU"
     * "URL de las estadísticas dinámicas de los Apuntes en INTECO"
     * "numero.maximo.edictos" "1000" "Número de registros máximos a mostrar al buscar los edictos"
     * "estadisticas.exp.url"
     * "http://10.1.31.18/ReportServersnc?%2fInformes+Sanciones%2fEstadisticas+SNC+Expedientes&rs:Command=Render&Ambito=:ambito"
     * "URL de las estadísticas estáticas de los Expedientes"
     * "inteco.concepto1" "100399" "Código Concepto1 de la conexión al Servicio Web de INTECO - IGAE INTECO"
     * "inteco.concepto2" "400079" "Código Concepto2 de la conexión al Servicio Web de INTECO - Deuda SSGG"
     * "codigobarras.urlws" "http://mazingerz.map.es:9053/Etiquetas_Correos/services/etiquetas"
     * "URL Servicio Web Etiquetas"
     * "fusion.webservice" "http://10.1.29.164/Fusion/ServicioWordWriter.asmx"
     * "URL de la conexión al Servicio Web de Fusión"
     * "fusion.ftp.host" "10.1.29.164" "FTP Host del servidor de Fusión de Documentos"
     * "fusion.ftp.username" "admin" "FTP Password del servidor de Fusión de Documentos"
     * "fusion.ftp.password" "admin" "FTP Username del servidor de Fusión de Documentos"
     * "web.ciudadanos" "https://sede.mpt.gob.es/" "URL de la Web de Sanciones Ciudadanos"
     * "departamento.mail.contacto.prohibidos"
     * "aplicacion.sanciones,gestion.sanciones,usuarios.sanciones,evamaria.ruiz,maria.riera,noemi.civicos,carlos.alonso1,javier.abad,rafael.delarosa,daniel.gomez-hidalgo,aplicaciones.nivel1"
     * "Lista de correos electrónicos no permitidos para utilizar como contacto de los ciudadanos."
     * "estadisticas.di.url"
     * "http://10.1.31.18/ReportServersnc?%2fInformes+Sanciones%2fEstadisticas+SNC+Documentos&rs:Command=Render&Ambito=:ambito"
     * "URL de las estadísticas estáticas de los Documentos Iniciales"
     * "estadisticas.exp.dinamico.url" "http://10.1.31.18/SNC_INFORMES/IDinamico.aspx?informe=EXP"
     * "URL de las estadísticas dinámicas de los Expedientes"
     * "estadisticas.di.dinamico.url" "http://10.1.31.18/SNC_INFORMES/IDinamico.aspx?informe=DOC"
     * "URL de las estadísitcas dinámicas de los Documentos Iniciales"
     * "fusion.webservice.aplicacion" "SNC" "Aplicación de la conexión al Servicio Web de Fusión"
     * "registro.webservice" "http://afroditaa.map.es:19067/regtelematicomptap/services/"
     * "URL de la dirección del Servicio Web de Registro"
     * "registro.login" "appSanciones" "Login del usuario de conexión al Servicio Web de Registro"
     * "registro.password" "Tl11UZ0Lk8xRubrc3WrSSQ==" "Password del usuario de conexión al Servicio Web de Registro"
     * "registro.maximo.peticiones" "99" "Número máximo de peticiones soportadas por el Servicio Web de Registro"
     * "consultaidentidades.url" "http://afroditaa.map.es:19080/SCSPWS320/ws/" "URL del  WS de Consulta de Identidad"
     * "consultaidentidades.solicitante.codigoCertificado" "CDISFWS01"
     * "Codigo del certificado solicitante necesario para la llamada al WS de Consulta de Identidades"
     * "consultaidentidades.solicitante.nifMinisterio" "S2833002E"
     * "CIF del Ministerio solicitante necesario para la llamada al WS de Consulta de Identidades"
     * "consultaidentidades.solicitante.nombreEmisor" "MHAP"
     * "Nombre del Ministerio solicitante necesario para la llamada al WS de Consulta de Identidades"
     * "consultaidentidades.solicitante.unidadTramitadora" "DIVISIÓN DE SISTEMAS DE INFORMACIÓN Y COMUNICACIONES"
     * "Unidad tramitadora solicitante  solicitante necesaria para la llamada al WS de Consulta de Identidades"
     * "consultaidentidades.solicitante.finalidad" "RECUPERACIÓN DE DATOS PARA APLICACIÓN DE SANCIONES"
     * "Finalidad necesaria para la llamada al WS de Consulta de Identidades"
     * "notificacionesTelematicas.webService.aplicacion" "AppSanciones"
     * "Constante del identificador de la aplicacion de sanciones"
     * "portafirmas.url" "http://prejappgal01.mpt.es:9624/portafirma/ " "url de conexión del WS de portafirmas"
     * "portafirmas.usuario" "SANCIONES" "usuario del WS de portafirmas"
     * "portafirmas.password" "Sanciones10" "contraseña del WS de portafirmas"
     * "numero.maximo.impresionmasiva" "100" "Numero Maximo de impresiones masiva"
     * "csv.ws.url" "http://eeutil.dev.mpt.es/eeutil/ws/EeUtilService" ""
     * "csv.ws.usuario" "extranjeria" ""
     * "csv.ws.password" "juanito" ""
     * "estadisticas.es.documentosExtr.url"
     * "http://10.1.31.18/ReportServerSNC/Pages/ReportViewer.aspx?%2fInformes+Sanciones%2fEstadisticas+SNC+Documentos+Extranjeria&rs%3aCommand=Render&Ambito=14"
     * ""
     * "estadisticas.es.expedientesExtr.url"
     * "http://10.1.31.18/ReportServerSNC/Pages/ReportViewer.aspx?%2fInformes+Sanciones%2fEstadisticas+SNC+Expedientes+Extranjeria&rs:Command=Render&Ambito=14"
     * ""
     * "terex.ssl.certalias" "certmptap" "Alias del certificado"
     * "terex.ssl.certpassword" "1111" "Password del certificado"
     * "terex.respuesta.tiempo.espera" "300000"
     * "Tiempo máxima de espera para realizar la consulta de la conexión al Servicio Web de TEREX"
     * "terex.respuesta.numero.intentos" "3" "Número de intentos de consulta de la conexión al Servicio Web de TEREX"
     * "terex.ssl.keystore" "" "Ruta del certificado digital necesario para la conexión al Servicio Web de TEREX"
     * "terex.ssl.keystorepassword" "1111"
     * "Password del certificado digital necesario para la conexión al Servicio Web deTEREX"
     * "terex.ssl.keystoretype" "pkcs12"
     * "Tipo de certificado digital necesario para la conexión al Servicio Web de TEREX"
     */

    private static final String PARAM_FACTORY = "factory";
    private static final String PARAM_URL = "url";
    private static final String PARAM_PKGS = "pkgs";
    private static final String PARAM_LOOKUP = "lookup";

    private final String factory;
    private final String url;
    private final String pkgs;
    private final String lookup;

    public JNDIExecutor(final Map<String, String> params) {
        super(params);
        factory = getParamString(PARAM_FACTORY);
        url = getParamString(PARAM_URL);
        pkgs = getParamString(PARAM_PKGS);
        lookup = getParamString(PARAM_LOOKUP);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        Context context = null;
        try {
            final Properties env = new Properties();
            if (factory != null && factory.trim().length() > 0) {
                env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
            }
            if (url != null && url.trim().length() > 0) {
                env.put(Context.PROVIDER_URL, url);
            }
            if (pkgs != null && pkgs.trim().length() > 0) {
                env.put(Context.URL_PKG_PREFIXES, pkgs);
            }
            context = new InitialContext(env);
            final Object jndiRef = context.lookup(lookup);
            if (jndiRef != null) {
                result.setSuscess(true);
            } else {
                result.setMessage("JNDI Reference not found");
            }
        } catch (final NamingException e) {
            result.setMessage(e.toString());
        } catch (final Throwable e) {
            result.setMessage(e.toString());
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (final NamingException e) {
                }
            }
        }
        return result;
    }
}
