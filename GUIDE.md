# JTestMe: Guía del Usuario #

## Índice ##

 1. [Introducción] (#introduccion)
 2. Configuración Básica
  * Tipos Verificators
  * Properties
  * Viewers
  * Java SE
  * Java EE
  * Dependencias
 3. Configuración Avanzada
  * Log
  * Parámetros JTestMeFilter
  * Verificators Personalizados
  * Viewers Personalizados
  * Verificators Programados
  * Seguridad
 4. Desarrollo


### <a name="introduccion"></a> 1. Introducción

El objetivo de JTestMe es facilitar la comprobación de los servicios y/o recursos necesarios de una aplicación para su correcta ejecución, de esta forma será posible tener monitorizado en todo momento las dependencias externas de una aplicación.

JTestMe es una librería Java, que es compatible tanto con aplicaciones Java SE, como aplicaciones Java EE, disponiendo incluso de un `javax.servlet.Filter` para poder visualizar de forma sencilla el estado de los recursos de una aplicación.

### <a name="configuracion"></a> 2. Configuración Básica

JTestMe se puede configurar tanto de forma programática, como mediante archivo properties de configuración externo.


#### <a name="tipos-verificators"></a> 2.1. Tipos Verificators

Lo primero que hemos de conocer son los tipos de verificadores que dispone de manera interna JTestMe para poder utilizarlos:
 * **connection**, verifica conexiones HTTP y HTTPS.
 * **datasource**, verifica conexiones JNDI de java.sql.DataSource.
 * **file**, verifica si se tiene permisos de escritura/lectura sobre un directorio/archivo.
 * **ftp**, verifica conexión FTP.
 * **graphics**, permite comprobar si el sistema dispone de librerías X para imprimir reportes y otras librerías que hacen uso de este servicio.
 * **jce**, verifica disponibilidad Java Cryptography Extension (JCE). 
 * **jndi**, verifica conexiones con recursos JNDI (EJB, Resources,...).
 * **jdbc**, verifica conexiones JDBC.
 * **ldap**, verifica conexiones LDAP.
 * **memory**, verifica tamaño mínimo memoria definido.
 * **openoffice**, verifica conexiones con el servicio OpenOffice o LibreOffice.
 * **property**, verifica existencia de una propiedad.
 * **smtp**, verifica la conexión con el servidor SMTP.
 * **webservice**, verifica las conexiones con servicios web.
 * **custom**, permite implementar una solución propia para poder verificar un recurso de la aplicación. Más adelante se mostrará como implementar este tipo de verificación en el punto [3.3. Verificators Personalizados] (#verificators-personalizados).


-------------

<h2>1. Introducción</h2>
El objetivo de JTestMe es facilitar la comprobación de los servicios y/o recursos necesarios de una aplicación para su correcta ejecución, de esta forma será posible tener monitorizado en todo momento las dependencias externas de una aplicación.<br>
<br>
JTestMe es una librería Java, que es compatible tanto con aplicaciones Java SE, como aplicaciones Java EE, disponiendo incluso de un <code>javax.servlet.Filter</code> para poder visualizar de forma sencilla el estado de los recursos de una aplicación.<br>
<br>
<h2>2. Configuración Básica</h2>
JTestMe se puede configurar tanto de forma programática, como mediante archivo properties de configuración externo.<br>
<br>
<h3>2.1. Tipos Verificators</h3>
Lo primero que hemos de conocer son los tipos de verificadores que dispone de manera interna JTestMe para poder utilizarlos:<br>
<br>
<ul><li><b>connection</b>, permite verificar conexiones HTTP y HTTPS.<br>
</li><li><b>datasource</b>, permite verificar conexiones JNDI de <code>java.sql.DataSource</code>.<br>
</li><li><b>ftp</b>, permite verificar conexión FTP.<br>
</li><li><b>graphics</b>, permite comprobar si el sistema dispone de librerías X para imprimir reportes y otras librerías que hacen uso de este servicio.<br>
</li><li><b>jndi</b>, permite verificar conexiones con recursos JNDI (EJB, Resources,...).<br>
</li><li><b>jdbc</b>, permite verificar conexiones JDBC.<br>
</li><li><b>ldap</b>, permite verificar conexiones LDAP.<br>
</li><li><b>openoffice</b>, permite verificar conexiones con el servicio OpenOffice o LibreOffice.<br>
</li><li><b>smtp</b>, permite verificar la conexión con el servidor SMTP.<br>
</li><li><b>webservice</b>, permite verificar las conexiones con servicios web.<br>
</li><li><b>custom</b>, permite implementar una solución propia para poder verificar un recurso de la aplicación. Más adelante se mostrará como implementar este tipo de verificación en el punto <a href='#3.3._Verificators_Personalizados.md'>3.3. Verificators Personalizados</a>.</li></ul>

<h3>2.2. Properties</h3>
Para cada uno de los tipos de test será necesario definir una serie de parámetros para que JTestMe ejecuta de forma correcta la verificación.<br>
<br>
Los parámetros de configuración comunes para todos los tipos de test son:<br>
<ul><li><b>type</b>, indica el tipo de test que se trata, este parámetro es obligatorio.<br>
</li><li><b>name</b>, representa el nombre del verificador, este parámetro es obligatorio.<br>
</li><li><b>description</b>, almacena la descripción del verificador, campo opcional.<br>
</li><li><b>resolution</b>, almacena el comentario de qué hacer en caso de que falle la verificación, campo opcional.<br>
</li><li><b>optional</b>, indica si la verificación es opcional o no, campo booleano opcional cuyo valor por defecto es false.</li></ul>

Para cada tipo de test existen toda una serie de parámetros propios de configuración:<br>
<ul><li><b>connection</b>:<br>
<ul><li><b>url</b>, representa la URL sobre la que se efectuará la verificación, campo obligatorio.<br>
</li><li>timeout, indica el tiempo máximo de espera antes de cancelar al verificación de la conexión, por defecto 5''.<br>
</li><li>proxyhost, host del proxy de la red, campo opcional.<br>
</li><li>proxyport, puerto del proxy de la red, campo opcional.<br>
</li><li>truststore, almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.<br>
</li><li>truststorepassword, password del almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.</li></ul></li></ul>

<ul><li><b>datasource</b>:<br>
<ul><li><b>datasource</b>, nombre del contexto del DataSource, campo obligatorio.<br>
</li><li>testquery, consulta SQL para verificar el correcto funcionamiento de la conexión a la fuente de datos, campo opcional.</li></ul></li></ul>

<ul><li><b>ftp</b>:<br>
<ul><li><b>host</b>, host del servidor FTP para conectarse, campo obligatorio.<br>
</li><li>port, puerto del servidor FTP a conectarse, campo opcional su valor por defecto es 21.<br>
</li><li>username, usuario de conexión al servidor FTP, campo opcional.<br>
</li><li>password, password de conexión al servidor FTP, campo opcional.</li></ul></li></ul>

<ul><li><b>graphics</b>: No tiene ningún parámetro de configuración exclusivo.</li></ul>

<ul><li><b>jdbc</b>:<br>
<ul><li><b>driver</b>, nombre de la clase con el driver JDBC para cargarlo, campo obligatorio.<br>
</li><li><b>url</b>, cadena de conexión a la base de datos, campo obligatorio.<br>
</li><li>username, usuario de conexión a la base de datos, campo opcional.<br>
</li><li>password, password de conexión a la base de datos, campo opcional.<br>
</li><li>testquery, consulta SQL para verificar el correcto funcionamiento de la conexión a la fuente de datos, campo opcional.</li></ul></li></ul>

<ul><li><b>jndi</b>:<br>
<ul><li><b>lookup</b>, nombre del recurso JNDI para buscarlo en el contexto, campo obligatorio.<br>
</li><li>factory, parámetro que permite modificar el parámetro <code>Context.INITIAL_CONTEXT_FACTORY</code>, campo opcional.<br>
</li><li>url, parámetro que permite modificar el parámetro <code>Context.PROVIDER_URL</code>, campo opcional.<br>
</li><li>pkgs, parámetro que permite modificar el parámetro <code>Context.URL_PKG_PREFIXES</code>, campo opcional.</li></ul></li></ul>

<ul><li><b>ldap</b>:<br>
<ul><li><b>url</b>, cadena de conexión al servidor LDAP, campo obligatorio.<br>
</li><li>principal, usuario de conexión al servidor LDAP, campo opcional.<br>
</li><li>credentials, password de conexión al servidor LDAP, campo opcional.</li></ul></li></ul>

<ul><li><b>openoffice</b>:<br>
<ul><li>host, host del servidor donde se encuentra disponible el servicio OpenOffice, campo opcional su valor por defecto es localhost.<br>
</li><li>port, puerto del servicio OpenOffice, campo opcional por defecto es 8700.</li></ul></li></ul>

<ul><li><b>smtp</b>:<br>
<ul><li><b>host</b>, host de conexión al servidor SMTP, campo obligatorio.<br>
</li><li>port, puerto de escucha del servidor SMTP, campo opcional por defecto su valor es 25.<br>
</li><li>auth, valor booleano que nos indica si es necesaria autorización para conectarnos al servidor SMTP, por defecto su valor es "false".<br>
</li><li>username, usuario de conexión al servidor SMTP, campo opcional.<br>
</li><li>password, password de conexión al servidor SMTP, campo opcional.<br>
</li><li>starttls<br>
</li><li>startssl</li></ul></li></ul>

<ul><li><b>webservice</b>:<br>
<ul><li><b>protocol</b>, protocolo de conexión al web service, campo obligatorio cuyo valores pueden ser: "rpc" o "soap" o "rest".<br>
</li><li><b>endpoint</b>, URL con la ubicación del endpoint web service, campo obligatorio.<br>
</li><li>namespaceuri, namespace del web service, campo opcional.<br>
</li><li>localpart, nombre del servicio del web service que se utilizará, campo opcional.<br>
</li><li>truststore, almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.<br>
</li><li>truststorepassword, password del almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.</li></ul></li></ul>

<ul><li><b>custom</b>:<br>
<ul><li><b>class</b>, classpath completo de la clase propia que implementa un verificador, esta clase debe implementar el interface <code>Verificator</code>, campo obligatorio.</li></ul></li></ul>

<br>
Para los parámetros es posible introducir claves de variables alojadas en las propiedades del sistema (<code>System.getProperty</code>) o recursos del classpath (<code>classpath:</code>).  Ejemplo:<br>
<pre><code>....param.truststore=${pathTrustStore}/trustore<br>
....param.truststore=${trustStorePassword}<br>
....param.truststore=classpath:resource<br>
</code></pre>

<br>
Ejemplo de archivo de configuración con verificadores JTestMe:<br>
<pre><code># Test Connection<br>
jtestme.connection.type=connection<br>
jtestme.connection.name=Connection Test<br>
jtestme.connection.description=Connection Test<br>
jtestme.connection.resolution=Qué hacer si falla el test<br>
jtestme.connection.optional=true<br>
jtestme.connection.param.url=<br>
jtestme.connection.param.timeout=<br>
jtestme.connection.param.proxyhost=<br>
jtestme.connection.param.proxyport=<br>
jtestme.connection.param.truststore=<br>
jtestme.connection.param.truststorepassword=<br>
<br>
# Test Datasource<br>
jtestme.datasource.type=datasource<br>
jtestme.datasource.name=Datasource Test<br>
jtestme.datasource.description=Datasource Test<br>
jtestme.datasource.resolution=Qué hacer si falla el test<br>
jtestme.datasource.param.datasource=<br>
jtestme.datasource.param.testquery=#optional<br>
jtestme.datasource.optional=true<br>
<br>
# Test FTP<br>
jtestme.ftp.type=ftp<br>
jtestme.ftp.name=FTP Test<br>
jtestme.ftp.description=FTP Test<br>
jtestme.ftp.resolution=Qué hacer si falla el test<br>
jtestme.ftp.param.host=<br>
jtestme.ftp.param.port=#default 21<br>
jtestme.ftp.param.username=#default anonymous<br>
jtestme.ftp.param.password=#default anonymous<br>
<br>
# Test Graphics<br>
jtestme.graphics.type=graphics<br>
jtestme.graphics.name=Graphics Test<br>
jtestme.graphics.description=Graphics Test<br>
jtestme.graphics.resolution=Qué hacer si falla el test<br>
<br>
# Test JDBC<br>
jtestme.jdbc.type=jdbc<br>
jtestme.jdbc.name=JDBC Database Test<br>
jtestme.jdbc.description=JDBC Database Test<br>
jtestme.jdbc.resolution=Qué hacer si falla el test<br>
jtestme.jdbc.param.driver=<br>
jtestme.jdbc.param.url=<br>
jtestme.jdbc.param.username=<br>
jtestme.jdbc.param.password=<br>
jtestme.jdbc.param.testquery=#optional<br>
jtestme.jdbc.param.testquery=#optional<br>
<br>
# Test JNDI<br>
jtestme.jndi.type=jndi<br>
jtestme.jndi.name=JNDI Test<br>
jtestme.jndi.description=JNDI Test<br>
jtestme.jndi.resolution=Qué hacer si falla el test<br>
jtestme.jndi.param.factory=#Context.INITIAL_CONTEXT_FACTORY<br>
jtestme.jndi.param.url=#Context.PROVIDER_URL<br>
jtestme.jndi.param.pkgs=#Context.URL_PKG_PREFIXES<br>
jtestme.jndi.param.lookup=#lookup name<br>
<br>
# Test LDAP<br>
jtestme.ldap.type=ldap<br>
jtestme.ldap.name=LDAP Test<br>
jtestme.ldap.description=LDAP Test<br>
jtestme.ldap.resolution=Qué hacer si falla el test<br>
jtestme.ldap.param.url=<br>
jtestme.ldap.param.principal=<br>
jtestme.ldap.param.credentials=<br>
<br>
# Test OpenOffice<br>
jtestme.openoffice.type=openoffice<br>
jtestme.openoffice.name=OpenOffice Test<br>
jtestme.openoffice.description=OpenOffice Test<br>
jtestme.openoffice.resolution=Qué hacer si falla el test<br>
jtestme.openoffice.param.host=#default localhost<br>
jtestme.openoffice.param.port=#default 8700<br>
<br>
# Test SMTP<br>
jtestme.smtp.type=smtp<br>
jtestme.smtp.name=SMTP Test<br>
jtestme.smtp.description=SMTP Test<br>
jtestme.smtp.resolution=Qué hacer si falla el test<br>
jtestme.smtp.param.host=<br>
jtestme.smtp.param.port=#default 25<br>
jtestme.smtp.param.auth=#default false<br>
jtestme.smtp.param.username=<br>
jtestme.smtp.param.password=<br>
jtestme.smtp.param.starttls=<br>
jtestme.smtp.param.startssl=<br>
<br>
# Test WebService : rpc / soap /rest<br>
jtestme.webservice.type=webservice<br>
jtestme.webservice.name=WebService Test<br>
jtestme.webservice.description=WebService Test<br>
jtestme.webservice.resolution=Qué hacer si falla el test<br>
jtestme.webservice.param.protocol=#rpc|soap|rest<br>
jtestme.webservice.param.endpoint=<br>
jtestme.webservice.param.namespaceuri=<br>
jtestme.webservice.param.localpart=<br>
jtestme.webservice.param.truststore=<br>
jtestme.webservice.param.truststorepassword= <br>
<br>
# Test Custom<br>
jtestme.custom.type=custom<br>
jtestme.custom.name=Custom Test<br>
jtestme.custom.description=Custom Test<br>
jtestme.custom.resolution=Qué hacer si falla el test<br>
jtestme.custom.param.class=#className implements es.jtestme.verificators.Verificator<br>
</code></pre>

<h3>2.3. Viewers</h3>
JTestMe tiene definido una serie de visores, <code>es.jtestme.viewers.Viewer</code>, por defecto, lo que permite de forma sencilla formatear los resultados obtenidos de la ejecución de los verificators (<code>es.jtestme.domain.VerificatorResult</code>).<br>
<br>
Los tipos de visores predefinidos están definidos en el enum <code>es.jtestme.viewers.ViewerType</code> y son:<br>
<ul><li><b>HTML</b>, resultado HTML, implementado en la clase <code>es.jtestme.viewers.impl.HTMLViewer</code>.<br>
</li><li><b>TXT</b>, resultado en texto plano formateado, implementado en la clase <code>es.jtestme.viewers.impl.PlainTextViewer</code>.<br>
</li><li><b>JSON</b>, resultado anotación JSon, implementado en la clase <code>es.jtestme.viewers.impl.JSONViewer</code>.<br>
</li><li><b>XML</b>, resultado en formato XML, implementado en la clase <code>es.jtestme.viewers.impl.XMLViewer</code>.</li></ul>

Todas las clases constructoras de vistas, siguen el patrón Singleton, sólo existirá una instancia en ejecución.<br>
<br>
Para facilitar la invocación del viewer adecuado JTestMe dispone de la factoría <code>es.jtestme.viewers.ViewerFactory</code>:<br>
<pre><code>// json viewer <br>
es.jtestme.viewers.Viewer jsonViewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.JSON);<br>
<br>
// recuperar content-type<br>
String contentType = jsonViewer.getContentType();<br>
<br>
// construcción de vista<br>
String content = jsonViewer.getContentViewer(List&lt;es.jtestme.domain.VerificatorResult&gt; verificatorsResults);	<br>
</code></pre>

<h3>2.4. Java SE</h3>
Para configurar JTestMe en una aplicación Java SE, es necesario invocar al constructor de JTestMe, <code>es.jtestme.JTestMeBuilder</code>, el cual permite tanto configurar los verifcators (añadiendolos directamente, o bien mediante archivo de propiedades), y ejecutar los verificators configurados.<br>
<br>
El constructor <code>es.jtestme.JTestMeBuilder</code>, sigue el patrón Singleton, por lo que se puede invocar desde distintos sitios de la aplicación y siempre se tratará de la misma referencia, solo hay una única instancia.<br>
<br>
Ejemplo:<br>
<pre><code>// invocamos al constructor de JTestMe<br>
es.jtestme.JTestMeBuilder builder = es.jtestme.JTestMeBuilder.getInstance();<br>
<br>
// cargar verificators de un archivo properties<br>
builder.loadVerificators(configLocation);<br>
<br>
// cargar verificator de forma programada<br>
builder.addVerificator(new es.jtestme.verificators.Verificator());<br>
builder.addVerificator(new es.jtestme.verificators.impl.SMTPVerificator(params));<br>
<br>
// permite ejecutar un verificator de forma individual (uid), devolviendo su resultado <br>
es.jtestme.domain.VerificatorResult verificatorResult = builder.executeVerificator(verificatorUid);<br>
<br>
// permite ejecutar todos los verificators, devolviendo sus resultados<br>
List&lt;es.jtestme.domain.VerificatorResult&gt; verificatorsResults = builder.executeVerificators();<br>
<br>
// dispone de un método para limpiar la lista de verificators configurados<br>
builder.destroy();<br>
</code></pre>

JTestMe permite además de utilizar su motor de ejecución de verificatos, también permite construir facilmente una vista gracias a los visores (<a href='#2.3._Viewers.md'>Viewers</a>):<br>
<pre><code>// ejecutamos los verificators obteniendo sus resultados<br>
List&lt;es.jtestme.domain.VerificatorResult&gt; verificatorsResults = ...;<br>
<br>
// cargamos el viewer deseado<br>
es.jtestme.viewers.Viewer viewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.TXT);<br>
<br>
// delegamos los resultados de los verificators al viewer para construya la vista <br>
String txt = viewer.getContentViewer(verificatorsResults);<br>
</code></pre>

<h3>2.5. Java EE</h3>
Para configurar JTestMe en una aplicación Web, se puede hacer de distintas formas:<br>
<ul><li><b>automática</b>, definir en el web.xml el <code>javax.servlet.Filter</code> de JTestMe, <code>es.jtestme.filter.JTestMeFilter</code> y definir en un archivo properties los verificators.<br>
</li><li><b>manual</b>, invocar al constructor de JTestMe, <code>es.jtestme.JTestMeBuilder</code>, al igual que se hace en una aplicación Java SE. Además será necesario configurar los filtros o servlet necesario para mapear la ruta donde se desea mostrar visualmente los resultados de los verificators.</li></ul>

En este caso se muestra como efectuar la configuración de forma automática, la más sencilla y rápida; configurando el <code>javax.servlet.Filter</code> y definiendo el archivo de properties con los verificators definidos.<br>
<br>
1.Configurar el <code>javax.servlet.Filter</code> en el <b>web.xml</b>, añadiendo el filtro el primero:<br>
<pre><code>&lt;filter&gt;<br>
	&lt;filter-name&gt;jtestmefilter&lt;/filter-name&gt;<br>
	&lt;filter-class&gt;es.jtestme.filter.JTestMeFilter&lt;/filter-class&gt;<br>
	&lt;!--<br>
	...<br>
	&lt;init-param&gt;<br>
		&lt;param-name&gt;config-location&lt;/param-name&gt;<br>
		&lt;param-value&gt;classpath:jtestme.properties&lt;/param-value&gt;<br>
	&lt;/init-param&gt;<br>
	&lt;init-param&gt;<br>
		&lt;param-name&gt;log&lt;/param-name&gt;<br>
		&lt;param-value&gt;true&lt;/param-value&gt;<br>
	&lt;/init-param&gt;<br>
	...<br>
	--&gt;<br>
&lt;/filter&gt;<br>
&lt;filter-mapping&gt;<br>
	&lt;filter-name&gt;jtestmefilter&lt;/filter-name&gt;<br>
	&lt;url-pattern&gt;/jtestme/*&lt;/url-pattern&gt;<br>
&lt;/filter-mapping&gt;<br>
</code></pre>

<blockquote>Existen diferentes parámetros configuraciones para el <code>javax.servlet.Filter</code> de JTestMe, todos ellos son opcionales. Se puede ver todos los parámetros diponibles para la configuración de la librería en el punto <a href='#3.2._Par%C3%A1metros_JTestMeFilter.md'>Parámetros JTestMeFilter</a>.</blockquote>

<br>
2. Configurar el archivo properties, <b>jtestme.properties</b>, en la ruta indicada en los parámetros del filtro:<br>
<pre><code>...<br>
# Test Datasource<br>
jtestme.datasource.type=datasource<br>
jtestme.datasource.name=Datasource Test<br>
jtestme.datasource.param.datasource=<br>
...<br>
</code></pre>

<blockquote><i>NOTA: también es posible cargar en tiempo de ejecución verificadores, al igual que se muestra para aplicaciones Java SE. Igualmente se podría arrancar JTestMe sin definir ningún archivo de configuración externo.</i></blockquote>

<h3>2.6. Dependencias</h3>
La librería JTestMe esta implementado con la versión <b>1.5</b> de Java.<br>
<br>
Además existirá una serie de dependencias adiciones en función de los verificadores que se utilicen:<br>
<ul><li><b>smpt</b>, <code>javax.mail</code>
<pre><code>&lt;dependency&gt;<br>
	&lt;groupId&gt;javax.mail&lt;/groupId&gt;<br>
	&lt;artifactId&gt;mail&lt;/artifactId&gt;<br>
	&lt;version&gt;1.4.6&lt;/version&gt;<br>
	&lt;optional&gt;true&lt;/optional&gt;<br>
&lt;/dependency&gt;<br>
</code></pre></li></ul>

<ul><li><b>openoffice</b>, <code>jodconverter</code>
<pre><code>&lt;dependency&gt;<br>
	&lt;groupId&gt;com.artofsolving&lt;/groupId&gt;<br>
	&lt;artifactId&gt;jodconverter&lt;/artifactId&gt;<br>
	&lt;version&gt;2.2.1&lt;/version&gt;<br>
	&lt;optional&gt;true&lt;/optional&gt;<br>
&lt;/dependency&gt;<br>
</code></pre></li></ul>

<ul><li><b>webservice rpc</b> <code>jaxrpc-impl</code>
<pre><code> &lt;dependency&gt;<br>
	&lt;groupId&gt;com.sun.xml.rpc&lt;/groupId&gt;<br>
	&lt;artifactId&gt;jaxrpc-impl&lt;/artifactId&gt;<br>
	&lt;version&gt;1.1.3_01&lt;/version&gt;<br>
	&lt;optional&gt;true&lt;/optional&gt;<br>
&lt;/dependency&gt;<br>
</code></pre></li></ul>

<h2>3. Configuración Avanzada</h2>
A continuación se muestran conceptos avanzados de la librería JTestMe que se pueden aplicar.<br>
<br>
<h3>3.1. Log</h3>
JTestMe cuenta con un Logger propio, <code>es.jtestme.logger.JTestMeLogger</code>. Esta clase no instanciable dispone de las funciones más comunes para lanzar trazas de log utilizando las librerías Java de Logger más utilizadas:<br>
<ul><li><b>SLF4J</b>, <code>org.slf4j.Logger</code>.<br>
</li><li><b>LOG4J</b>, <code>org.apache.log4j.Logger</code>.<br>
</li><li><b>LOGGER</b>, <code>java.util.logging.Logger</code>.</li></ul>

La clase se encarga de detectar cual de las librerías está disponible para mostrar los las trazas log. No es necesario que en el classpath se encuentre la librería, slf4j o log4j, pero si se encuentra su uso tiene preferencia frente al logger por defecto de java (<code>java.util.logging.Logger</code>).<br>
<br>
La clase de Logger de JTestMe, permite también desactivar todos las trazas de forma programática:<br>
<pre><code>// método para activar/desactivar todas las trazas log de JTestMe<br>
es.jtestme.logger.JTestMeLogger.setLoggerEnabled(false);<br>
<br>
// comprobamos si están activadas/desactivadas las trazas log<br>
boolean jtestmeLoggerEnabled = es.jtestme.logger.JTestMeLogger.isLoggerEnabled();<br>
</code></pre>

Para una aplicación web, el filtro de JTestMe, dispone de un parámetro para definir su comportamiento:<br>
<pre><code>&lt;filter&gt;<br>
	&lt;filter-name&gt;jtestmefilter&lt;/filter-name&gt;<br>
	&lt;filter-class&gt;es.jtestme.filter.JTestMeFilter&lt;/filter-class&gt;<br>
	...<br>
	&lt;init-param&gt;<br>
		&lt;param-name&gt;log&lt;/param-name&gt;<br>
		&lt;param-value&gt;false&lt;/param-value&gt;<br>
	&lt;/init-param&gt;<br>
	...<br>
&lt;/filter&gt;<br>
</code></pre>

<h3>3.2. Parámetros JTestMeFilter</h3>
El filtro web de JTestMe, <code>es.jtestme.filter.JTestMeFilter</code>, dispone de una serie de parámetros que permiten modificar su configuración por defecto.<br>
<br>
Para definir los parámetros de configuración del filtro sólo es necesario definirlos mediante un <code>&lt;init-param&gt;</code> en al configuración del <code>javax.servlet.Filter</code>.<br>
<pre><code>&lt;filter&gt;<br>
	&lt;filter-name&gt;jtestmefilter&lt;/filter-name&gt;<br>
	&lt;filter-class&gt;es.jtestme.filter.JTestMeFilter&lt;/filter-class&gt;<br>
	...<br>
	&lt;init-param&gt;<br>
		&lt;param-name&gt;...&lt;/param-name&gt;<br>
		&lt;param-value&gt;...&lt;/param-value&gt;<br>
	&lt;/init-param&gt;<br>
	...<br>
&lt;/filter&gt;<br>
</code></pre>

Los parámetros de configuración de <code>es.jtestme.filter.JTestMeFilter</code> son:<br>
<table><thead><th>  PROPIEDAD  </th><th>  VALOR POR DEFECTO  </th><th>  DESCRIPCIÓN  </th></thead><tbody>
<tr><td>  <b>config-location</b>  </td><td>  <code>classpath:jtestme.properties</code>  </td><td>Ubicación del archivo de configuración de JTestMe donde se definen los verificators </td></tr>
<tr><td>  <b>encoding</b>  </td><td>  <code>UTF-8</code>  </td><td>Códificación por defecto de las respuestas HTTP a las peticiones el filtro de JTestMe </td></tr>
<tr><td>  <b>log</b>  </td><td> <code>true</code>   </td><td>Activa/Desactiva las trazas log de la librería JTestMe </td></tr>
<tr><td>  <b>param-type-format</b>  </td><td>  <code>format</code>  </td><td>Nombre del parámetro request que debe recibir el filtro de JTestMe para cambiar el tipo de respuesta. </td></tr>
<tr><td>  <b>default-viewer</b>  </td><td>  <code>html</code>  </td><td>Viewer que se mostrará por defecto si no especifíca el formato. </td></tr>
<tr><td>  <b>html-viewer-class</b>  </td><td>  <code>es.jtestme.viewers.impl.HTMLViewer</code>  </td><td>Implementación por defecto del viewer HTML. </td></tr>
<tr><td>  <b>json-viewer-class</b>  </td><td>  <code>es.jtestme.viewers.impl.JSONViewer</code>  </td><td>Implementación por defecto del viewer JSON. </td></tr>
<tr><td>  <b>txt-viewer-class</b>  </td><td>  <code>es.jtestme.viewers.impl.PlainTextViewer</code>  </td><td>Implementación por defecto del viewer TXT. </td></tr>
<tr><td>  <b>xml-viewer-class</b>  </td><td>  <code>es.jtestme.viewers.impl.XMLViewer</code>  </td><td>Implementación por defecto del viewer XML. </td></tr>
<tr><td>  <b>custom-viewer-class</b>  </td><td>  <code>es.jtestme.viewers.impl.CustomViewer</code>  </td><td>Implementación por defecto del viewer CUSTOM. </td></tr>
<tr><td>  <b>schedule</b>  </td><td>  <code>false</code>  </td><td>Activa/Desactiva la ejecución programada de los verificators. </td></tr>
<tr><td>  <b>schedule-period</b>  </td><td>  <code>10</code>    </td><td>Periodo en minutos para la ejecución de todos los verificators configurados. </td></tr>
<tr><td>  <b>schedule-viewer</b>  </td><td>  <code>TXT</code>   </td><td>Tipo de Viewer utilizado por defecto para mostrar en el Log el resultado de la ejecución de los verificators programados, Los tipos de viewers disponibles son: HTML, JSON, XML, TXT y CUSTOM. </td></tr></tbody></table>


El parámetro <b>param-type-format</b>, permite definir el nombre de la variable request que permitirá cambiar el tipo de respuesta que se devuelve: HTML, TXT, JSON y XML. El valor del tipo de respuesta es "case insensitive" y debe coincidir con uno de los tipos definidos en la clase <code>es.jtestme.viewers.ViewerType</code>.<br>
Ejemplo:<br>
<ul><li><code>http://.../jtestme</code> ==> resultado HTML<br>
</li><li><code>http://.../jtestme?format=txt</code> ==> resultado TXT<br>
</li><li><code>http://.../jtestme?format=json</code> ==> resultado JSON<br>
</li><li><code>http://.../jtestme?format=XML</code> ==> resultado XML</li></ul>

<h3>3.3. Verificators Personalizados</h3>
Uno de los tipos de verificators de JTestMe es <b>custom</b>, el cual permite definir un tipo de verificator personalizado, siendo necesario programar su comportamiento.<br>
<br>
Para definir la configuración del verificator de un tipo <b>custom</b> es necesario definir la ruta de la clase que implementa el interface <code>es.jtestme.verificators.Verificator</code> en el parámetro <code>class</code>. Adicionalmente se pueden pasar parámetros a la clase personalizada que implementa el verificator.<br>
<pre><code>....<br>
jtestme.custom.type=custom<br>
jtestme.custom.name=Custom Test<br>
jtestme.custom.description=Custom Test Description<br>
jtestme.custom.resolution=How to resolve the problema<br>
jtestme.custom.param.class=#class implements es.jtestme.verificators.Verificator<br>
jtestme.custom.param.paramRecivedCustomVerificatorClass=...<br>
jtestme.custom.param.myCustomParameter=100<br>
....<br>
</code></pre>

Para implementar un verificator personalizado este debe implementar el interface <code>es.jtestme.verificators.Verificator</code>, hay dos posibles soluciones:<br>
<ul><li>clase pública que implementa directamente interface <code>es.jtestme.verificators.Verificator</code>
<pre><code>public class MyCustomVerificator implements es.jtestme.verificators.Verificator {<br>
<br>
	private String uid = "myCustomVerificator";<br>
	private Map&lt;String, String&gt; parameters;<br>
	<br>
	private String paramRecivedCustomVerificatorClass;<br>
	private Integer myCustomParameter;<br>
	<br>
	// constructor opcional sin parámetros<br>
	public MyCustomVerificator() {<br>
	}<br>
	<br>
	// constructor opcional que recibe los parámetros de la configuración<br>
	public MyCustomVerificator(Map&lt;String, String&gt; parameters) {<br>
		this("myCustomVerificator", parameters);<br>
	}<br>
	<br>
	// constructor opcional que recibe el identificador del verificator (uid) y los parámetros de la configuración<br>
	public MyCustomVerificator(String uid, Map&lt;String, String&gt; parameters) {<br>
		this.uid = uid;<br>
		this.parameters = parameters;<br>
		this.paramRecivedCustomVerificatorClass = parameters.get("paramRecivedCustomVerificatorClass");<br>
		this.myCustomParameter = Integer.parseInt(parameters.get("myCustomParameter")); <br>
	}<br>
	<br>
	// devuelve el identificador único del verificator para poder identificarlo<br>
	public String getUid() {<br>
		return uid;<br>
	}<br>
	<br>
	// ejecuta la lógica del verificator<br>
	public VerificatorResult execute() {<br>
		es.jtestme.domain.VerificatorResult result = new es.jtestme.domain.VerificatorResult();<br>
		result.setType(es.jtestme.verificators.VerificatorType.CUSTOM.toString());<br>
		result.setName("my custom verificator");<br>
		result.setDescription("my custom verificator description");<br>
		result.setResolution("my custom verificator resolution");<br>
		result.setOptional(true|false);<br>
		<br>
		// implementación del custom verificator<br>
		<br>
		return result; <br>
	}<br>
}<br>
</code></pre></li></ul>


<ul><li>clase pública que extiende de la clase <code>es.jtestme.verificators.impl.AbstractVerificator</code> la cual implementa el interface Verificator y además cuenta que varios métodos útiles para recuperar los valores de los parámetros de la configuración.<br>
<pre><code>public class MyCustomVerificator extends es.jtestme.verificators.impl.AbstractVerificator {<br>
<br>
	// no es necesario definir los atributos "uid" y "parametes" ya están definidos en el padre<br>
<br>
	private String paramRecivedCustomVerificatorClass;<br>
	private Integer myCustomParameter;<br>
	<br>
	// uno de los siguientes constructores es obligatorio implementarlo<br>
	<br>
	// constructor recibe los parámetros de la configuración<br>
	public MyCustomVerificator(Map&lt;String, String&gt; parameters) {<br>
		super(parameters);<br>
		this.paramRecivedCustomVerificatorClass = super.getParamString("paramRecivedCustomVerificatorClass");<br>
		this.myCustomParameter = super.getParamInteger("myCustomParameter");<br>
	}<br>
	<br>
	// constructor recibe el identificador del verificator (uid) y los parámetros de la configuración<br>
	public MyCustomVerificator(String uid, Map&lt;String, String&gt; parameters) {<br>
		super(uid, parameters);<br>
		this.paramRecivedCustomVerificatorClass = super.getParamString("paramRecivedCustomVerificatorClass");<br>
		this.myCustomParameter = super.getParamInteger("myCustomParameter"); <br>
	}<br>
	<br>
	// el método "getUid()" es implementado por el padre, no es necesario<br>
	<br>
	// ejecuta la lógica del verificator<br>
	public VerificatorResult execute() {<br>
		// clase padre facilita el VerificatorResult con la información por defecto: type, name, description,...<br>
		es.jtestme.domain.VerificatorResult result = super.getResult();<br>
		<br>
		// implementación del custom verificator<br>
		<br>
		return result; <br>
    }<br>
}<br>
</code></pre></li></ul>

<blockquote><i>NOTA: las clases verificators custom sólo son instanciadas una única vez, por lo que sus método, especialmente <code>execute()</code>, debe ser thread-safe, para ello es recomendable solo utilizar variables locales del método y no atributos de la clase, cuyos valores podrían ser accedidos por multiples hilos simultáneamente.</i></blockquote>

<h3>3.4. Viewers Personalizadas</h3>
Una vez visto los conceptos básicos de los <a href='#2.3._Viewers.md'>Viewers</a>, en el siguiente punto mostraremos como implementar viewers personalizados y añadirlos a la factoría de viewers.<br>
<br>
JTestMe permite redifinir todos los viewers por defecto: HTML, JSON, TXT y XML, así como un tipo propio CUSTOM. Además permite cambiar el viewer por defecto al mostrar las páginas en el navegador si no se recibe el parámetro de formato (<code>{{{http://.../jtestme?format=xml</code>}}}).<br>
<br>
Para cambiar el viewer por defecto, HTML, se debe hacer mediante el parámetro <b>default-viewer</b> en la configuración del <code>javax.servlet.Filter</code>:<br>
<pre><code>...<br>
&lt;init-param&gt;<br>
	&lt;param-name&gt;default-viewer&lt;/param-name&gt;<br>
	&lt;param-value&gt;custom&lt;/param-value&gt;<br>
&lt;/init-param&gt;<br>
...<br>
</code></pre>

Para cambiar cualquier tipo de viewer disponible es necesario definirlo en la configuración del filtro:<br>
<pre><code>...<br>
&lt;init-param&gt;<br>
	&lt;param-name&gt;xml-viewer-class&lt;/param-name&gt;<br>
	&lt;param-value&gt;es.jtestme.test.viewers.MyXMLViewer&lt;/param-value&gt;<br>
&lt;/init-param&gt;<br>
...<br>
</code></pre>

La clase que reemplaza el viewer por defecto debe implementar el interface <code>es.jtestme.viewers.Viewer</code>, o bien puede heredar de la clase <code>es.jtestme.viewers.impl.AbstractViewer</code> que implementa el interface además de disponer de métodos comunes.<br>
<pre><code>public class MyXMLViewer extends es.jtestme.viewers.impl.AbstractViewer {<br>
<br>
    public String getContentType() {<br>
        return "text/xml";<br>
    }<br>
<br>
    public String getContentViewer(final List&lt;es.jtestme.domain.VerificatorResult&gt; results) {<br>
        final StringBuilder builder = new StringBuilder();<br>
        builder.append("&lt;?xml version='1.0' encoding='ISO-8859-15'?&gt;").append(NEW_LINE);<br>
        builder.append("&lt;verificarSistema nodo='").append(getHostName()).append("'&gt;").append(NEW_LINE);<br>
        if (results != null &amp;&amp; !results.isEmpty()) {<br>
            for (final es.jtestme.domain.VerificatorResult result : results) {<br>
                builder.append("&lt;servicio&gt;").append(NEW_LINE);<br>
                builder.append("&lt;nombre&gt;").append(result.getName()).append("&lt;/nombre&gt;").append(NEW_LINE);<br>
                builder.append("&lt;estado&gt;").append(result.isSuccess() ? "OK" : "KO").append("&lt;/estado&gt;")<br>
                        .append(NEW_LINE);<br>
                if (!result.isSuccess()) {<br>
                    builder.append("&lt;descError&gt;").append(result.getMessage()).append("&lt;/descError&gt;").append(NEW_LINE);<br>
                    builder.append("&lt;accionError&gt;").append(result.getResolution()).append("&lt;/accionError&gt;")<br>
                            .append(NEW_LINE);<br>
                }<br>
                builder.append("&lt;/servicio&gt;").append(NEW_LINE);<br>
            }<br>
        }<br>
        builder.append("&lt;/verificarSistema&gt;").append(NEW_LINE);<br>
        return builder.toString();<br>
    }<br>
}<br>
</code></pre>

Otra forma de modificar los viewers por defecto es registrar los nuevos viewers en su factoría, <code>es.jtestme.viewers.Viewer</code>, la cual permite registrarlos de forma programática:<br>
<pre><code>// permite definir un tipo propio "custom"<br>
es.jtestme.viewers.ViewerFactory.registerViewer(es.jtestme.viewers.ViewerType.CUSTOM, new es.jtestme.viewers.Viewer());<br>
<br>
// o incluso, reemplazar un viewer por defecto<br>
es.jtestme.viewers.ViewerFactory.registerViewer(es.jtestme.viewers.ViewerType.XML, new MyXMlViewer());<br>
<br>
// para obtener el viewer basta con invocarlo en la factoría <br>
es.jtestme.viewers.Viewer customViewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.CUSTOM);<br>
es.jtestme.viewers.Viewer xmlViewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.XML);<br>
</code></pre>

<i>NOTA: los viewers, al igual que los verificators, sólo se instancian una única vez por lo que sus métodos deben ser thread-safe: evitar atributos compartidos.</i>

<h3>3.5. Verificators Programados</h3>
JTestMe permite ejecutar de programada los verificators para comprobar el estado de los recursos configurados de la aplicación, mostrando el resultado de esta ejecución mediante una traza LOG.<br>
<br>
La configuración del programador de JTestMe permite configurar los siguientes parámetros:<br>
<ul><li>activar/desactivar el programador.<br>
</li><li>definir que viewer se utilizará para mostrar los resultados de la ejecución de los verificators.<br>
</li><li>establecer el periodo de tiempo entre las ejecuciones de los verificators (en minutos).</li></ul>

Esta configuración se puede definir tanto para aplicaciones Web, como Standalone:<br>
<ul><li>Web, sólo será necesario definir correctamente los parámetros de configuración del <code>javax.servlet.Filter</code> de JTestMe:<br>
<pre><code>...<br>
&lt;init-param&gt;<br>
	&lt;param-name&gt;schedule&lt;/param-name&gt;<br>
	&lt;param-value&gt;true&lt;/param-value&gt;<br>
&lt;/init-param&gt;<br>
&lt;init-param&gt;<br>
	&lt;param-name&gt;schedule-period&lt;/param-name&gt;<br>
	&lt;param-value&gt;60&lt;/param-value&gt;<br>
&lt;/init-param&gt;<br>
&lt;init-param&gt;<br>
	&lt;param-name&gt;schedule-viewer&lt;/param-name&gt;<br>
	&lt;param-value&gt;XML&lt;/param-value&gt;<br>
&lt;/init-param&gt;<br>
...<br>
</code></pre></li></ul>

<ul><li>Standalone, será necesario activar de forma programada su ejecución:<br>
<pre><code>// Primero será necesaria la configuración de JTestMeBuilder<br>
...<br>
<br>
// Configuración y Activación de JTestMeScheduler<br>
es.jtestme.schedule.JTestMeScheduler scheduler = es.jtestme.schedule.JTestMeScheduler.getInstance();  <br>
scheduler.setPeriod(60l);<br>
scheduler.setViewer("XML");<br>
scheduler.start();<br>
...<br>
<br>
// Para detener la ejecución programada<br>
scheduler.stop();<br>
</code></pre></li></ul>

<i>NOTA: la ejecución de los verificators de forma programada está desactivado por defecto, será necesario su configuración para activarlo.</i>

La ejecución de los verificators de se ejecutará de forma programada cada X periodo de tiempo definido, si se produce un error en alguno de los verificators se mostrará un mensaje en el Logger con nivel de ERROR, por lo que será necesario que esté activado para poder ver los resultados de la ejecución. En el punto <a href='#3.1._Log.md'>Log</a> se puede ver como configurarlo.<br>
<br>
Gracias a este ejecución programada y a la librería logger, se puede configurar nuestra aplicación para cuando se produzca un error en cualquiera de los verificators definidos nos avise, por ejemplo mediante un correo electrónico. Ejemplo de configuración de Log4j:<br>
<pre><code>log4j.rootCategory=INFO<br>
log4j.logger.es.jtestme.schedule=ERROR, mail<br>
<br>
# mail es configurado para utilizar SMTPAppender<br>
log4j.appender.mail=org.apache.log4j.net.SMTPAppender<br>
log4j.appender.mail.Threshold=ERROR<br>
log4j.appender.mail.BufferSize=512<br>
log4j.appender.mail.To=<br>
log4j.appender.mail.From=jtestme@noreply.com<br>
log4j.appender.mail.SMTPHost=<br>
log4j.appender.mail.Subject=[JTestMe] Error Verificators<br>
log4j.appender.mail.layout=org.apache.log4j.PatternLayout<br>
log4j.appender.mail.layout.ConversionPattern=%m<br>
</code></pre>

<i>NOTA: al producirse fallo en la ejecución de alguno de los verificator sólo se mostrará una única traza de log hasta que solucione el problema.</i>

<h3>3.6. Seguridad</h3>
The monitoring page does not contain data such as passwords, but before using it in production, you may probably want that this page is in restricted access. You may secure it by using your own means. Otherwise, it is possible to restrict its access by a regular expression on ip address of client with the parameter allowed-addr-pattern (regular expression with a range of internal ip addresses or fixed ip addresses of administrators). For example, "192\.168\..<b>|123\.123\.123\.123" to allow the ip addresses in the "192.168.</b>.<b>" range plus any pc hiding behind the gateway 123.123.123.123.</b>

Note that if you use a http proxy server like Apache in front of the server, the ip address of the client will be the one of Apache. So you may not use allowed-addr-pattern in this case, or do not use Apache to access this page, or you may enable mod_proxy_ajp in order that the monitored server knows the ip address of the clients. Example of httpd conf with ajp:<br>
<br>
<pre><code>&lt;location /webapp&gt;<br>
	ProxyPass ajp://localhost:8080/webapp<br>
&lt;/location&gt; <br>
</code></pre>

Or if some security-constraint et security-role(s) are defined in the web.xml file of the application, you can also restrict in the web.xml file the access to the monitoring page to "monitoring" role for example.<br>
<br>
Example of content of the web.xml file for authentication by login and password:<br>
<pre><code>&lt;login-config&gt;<br>
	&lt;auth-method&gt;BASIC&lt;/auth-method&gt;<br>
	&lt;realm-name&gt;JTestMe&lt;/realm-name&gt;<br>
&lt;/login-config&gt;<br>
&lt;security-role&gt;<br>
	&lt;role-name&gt;jtestme&lt;/role-name&gt;<br>
&lt;/security-role&gt;<br>
&lt;security-constraint&gt;<br>
	&lt;web-resource-collection&gt;<br>
		&lt;web-resource-name&gt;JTestMe&lt;/web-resource-name&gt;<br>
		&lt;url-pattern&gt;/jtestme/*&lt;/url-pattern&gt;<br>
	&lt;/web-resource-collection&gt;<br>
	&lt;auth-constraint&gt;<br>
		&lt;role-name&gt;jtestme&lt;/role-name&gt;<br>
	&lt;/auth-constraint&gt;<br>
	&lt;!-- if SSL enabled (SSL and certificate must then be configured in the server)<br>
	&lt;user-data-constraint&gt;<br>
		&lt;transport-guarantee&gt;CONFIDENTIAL&lt;/transport-guarantee&gt;<br>
	&lt;/user-data-constraint&gt; <br>
	--&gt;<br>
&lt;/security-constraint&gt;<br>
</code></pre>

The realm and the users must be defined in the application server, and the users must have the "jtestme" role to have access to the reports. For example, if tomcat is used with the default realm, modify the content of the conf/tomcat-users.xml file as follows:<br>
<pre><code>&lt;?xml version='1.0' encoding='utf-8'?&gt;<br>
&lt;tomcat-users&gt;<br>
	&lt;role rolename="jtestme"/&gt;<br>
	&lt;user username="jtestme" password="jtestme" roles="jtestme"/&gt;<br>
&lt;/tomcat-users&gt;<br>
</code></pre>

<h2>4. Desarrollo</h2>
TODO