# JTestMe: Guía del Usuario #

### Índice
* [Introducción](#introducción)
* [Configuración Básica](#configuración-básica)
 * [Tipos Verificators](#tipos-verificators)
 * [Properties](#properties)
 * [Viewers](#viewers)
 * [Java SE](#java-se)
 * [Java EE](#java-ee)
 * [Dependencias](#dependencias)
* [Configuración Avanzada](#configuración-avanzada)
 * [Log](#log)
 * [Parámetros JTestMeFilter](#parámetros-jtestmefilter)
 * [Verificators Personalizados](#verificators-personalizados)
 * [Viewers Personalizados](#viewers-personalizados)
 * [Verificators Programados](#verificators-programados)
 * [Seguridad](#seguridad)

### Introducción
El objetivo de JTestMe es facilitar la comprobación de los servicios y/o recursos necesarios de una aplicación para su correcta ejecución, de esta forma será posible tener monitorizado en todo momento las dependencias externas de una aplicación.

JTestMe es una librería Java, que es compatible tanto con aplicaciones Java SE, como aplicaciones Java EE, disponiendo incluso de un `javax.servlet.Filter` para poder visualizar de forma sencilla el estado de los recursos de una aplicación.

### Configuración Básica
JTestMe se puede configurar tanto de forma programática, como mediante archivo properties de configuración externo.

#### Tipos Verificators
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
* **custom**, permite implementar una solución propia para poder verificar un recurso de la aplicación. Más adelante se mostrará como implementar este tipo de verificación en el punto [Verificators Personalizados](#verificators-personalizados).

#### Properties
Para cada uno de los tipos de test será necesario definir una serie de parámetros para que JTestMe ejecuta de forma correcta la verificación.

Los parámetros de configuración comunes para todos los tipos de test son:
* **type**, indica el tipo de test que se trata, este parámetro es obligatorio.
* **name**, representa el nombre del verificador, este parámetro es obligatorio.
* **description**, almacena la descripción del verificador, campo opcional.
* **resolution**, almacena el comentario de qué hacer en caso de que falle la verificación, campo opcional.
* **optional**, indica si la verificación es opcional o no, campo booleano opcional cuyo valor por defecto es false.

Para cada tipo de test existen toda una serie de parámetros propios de configuración:
* **connection**:
  * **url**, representa la URL sobre la que se efectuará la verificación, campo obligatorio.
  * timeout, indica el tiempo máximo de espera antes de cancelar al verificación de la conexión, por defecto 5''.
  * proxyhost, host del proxy de la red, campo opcional.
  * proxyport, puerto del proxy de la red, campo opcional.
  * truststore, almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.
  * truststorepassword, password del almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.

* **datasource**:
  * **datasource**, nombre del contexto del DataSource, campo obligatorio.
  * testquery, consulta SQL para verificar el correcto funcionamiento de la conexión a la fuente de datos, campo opcional.

* **file**:
  * **path**, ruta de carpeta para verificar, campo obligatorio.

* **ftp**:
  * **host**, host del servidor FTP para conectarse, campo obligatorio.
  * port, puerto del servidor FTP a conectarse, campo opcional su valor por defecto es 21.
  * username, usuario de conexión al servidor FTP, campo opcional.
  * password, password de conexión al servidor FTP, campo opcional.

* **graphics**: No tiene ningún parámetro de configuración exclusivo.

* **jdbc**:
  * **driver**, nombre de la clase con el driver JDBC para cargarlo, campo obligatorio.
  * **url**, cadena de conexión a la base de datos, campo obligatorio.
  * username, usuario de conexión a la base de datos, campo opcional.
  * password, password de conexión a la base de datos, campo opcional.
  * testquery, consulta SQL para verificar el correcto funcionamiento de la conexión a la fuente de datos, campo opcional.

* **jndi**:
  * **lookup**, nombre del recurso JNDI para buscarlo en el contexto, campo obligatorio.
  * factory, parámetro que permite modificar el parámetro `Context.INITIAL_CONTEXT_FACTORY`, campo opcional.
  * url, parámetro que permite modificar el parámetro `Context.PROVIDER_URL`, campo opcional.
  * pkgs, parámetro que permite modificar el parámetro `Context.URL_PKG_PREFIXES`, campo opcional.

* **ldap**:
  * **url**, cadena de conexión al servidor LDAP, campo obligatorio.
  * principal, usuario de conexión al servidor LDAP, campo opcional.
  * credentials, password de conexión al servidor LDAP, campo opcional.

* **memory**:
  * **memorytype**, tipo de memoria, campo obligatorio. Los valores posibles son: `HEAP`, `NONHEAP`, `PERMGEN`, `EDEN` y `OLDGEM`.
  * **minsize**, tamaño mínimo de memoría en megabytes que debe estar disponible, campo obligatorio.
  
* **openoffice**:
  * host, host del servidor donde se encuentra disponible el servicio OpenOffice, campo opcional su valor por defecto es localhost.
  * port, puerto del servicio OpenOffice, campo opcional por defecto es 8700.

* **property**:
  * **properties**, listado de propiedades, separadas por comas, que deberían estar definidas.

* **smtp**:
  * **host**, host de conexión al servidor SMTP, campo obligatorio.
  * port, puerto de escucha del servidor SMTP, campo opcional por defecto su valor es 25.
  * auth, valor booleano que nos indica si es necesaria autorización para conectarnos al servidor SMTP, por defecto su valor es "false".
  * username, usuario de conexión al servidor SMTP, campo opcional.
  * password, password de conexión al servidor SMTP, campo opcional.
  * starttls
  * startssl

* **webservice**:
  * **protocol**, protocolo de conexión al web service, campo obligatorio cuyo valores pueden ser: "rpc" o "soap" o "rest".
  * **endpoint**, URL con la ubicación del endpoint web service, campo obligatorio.
  * namespaceuri, namespace del web service, campo opcional.
  * localpart, nombre del servicio del web service que se utilizará, campo opcional.
  * truststore, almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.
  * truststorepassword, password del almacén de claves para aceptar conexiones seguras (HTTPS), campo opcional.

* **custom**:
  * **class**, classpath completo de la clase propia que implementa un verificador, esta clase debe implementar el interface `Verificator`, campo obligatorio.

Para los parámetros es posible introducir claves de variables alojadas en las propiedades del sistema (`System.getProperty`) o recursos del classpath (`classpath:`).

Ejemplo:
```properties
....param.truststore=${pathTrustStore}/trustore
....param.truststore=${trustStorePassword}
....param.truststore=classpath:resource
```

Ejemplo de archivo de configuración con verificadores JTestMe:
```properties
# Test Connection
jtestme.connection.type=connection
jtestme.connection.name=Connection Test
jtestme.connection.description=Connection Test
jtestme.connection.resolution=Qué hacer si falla el test
jtestme.connection.optional=true
jtestme.connection.param.url=
jtestme.connection.param.timeout=
jtestme.connection.param.proxyhost=
jtestme.connection.param.proxyport=
jtestme.connection.param.truststore=
jtestme.connection.param.truststorepassword=

# Test Datasource
jtestme.datasource.type=datasource
jtestme.datasource.name=Datasource Test
jtestme.datasource.description=Datasource Test
jtestme.datasource.resolution=Qué hacer si falla el test
jtestme.datasource.param.datasource=
jtestme.datasource.param.testquery=#optional
jtestme.datasource.optional=true

# Test FTP
jtestme.ftp.type=ftp
jtestme.ftp.name=FTP Test
jtestme.ftp.description=FTP Test
jtestme.ftp.resolution=Qué hacer si falla el test
jtestme.ftp.param.host=
jtestme.ftp.param.port=#default 21
jtestme.ftp.param.username=#default anonymous
jtestme.ftp.param.password=#default anonymous

# Test Graphics
jtestme.graphics.type=graphics
jtestme.graphics.name=Graphics Test
jtestme.graphics.description=Graphics Test
jtestme.graphics.resolution=Qué hacer si falla el test

# Test JDBC
jtestme.jdbc.type=jdbc
jtestme.jdbc.name=JDBC Database Test
jtestme.jdbc.description=JDBC Database Test
jtestme.jdbc.resolution=Qué hacer si falla el test
jtestme.jdbc.param.driver=
jtestme.jdbc.param.url=
jtestme.jdbc.param.username=
jtestme.jdbc.param.password=
jtestme.jdbc.param.testquery=#optional
jtestme.jdbc.param.testquery=#optional

# Test JNDI
jtestme.jndi.type=jndi
jtestme.jndi.name=JNDI Test
jtestme.jndi.description=JNDI Test
jtestme.jndi.resolution=Qué hacer si falla el test
jtestme.jndi.param.factory=#Context.INITIAL_CONTEXT_FACTORY
jtestme.jndi.param.url=#Context.PROVIDER_URL
jtestme.jndi.param.pkgs=#Context.URL_PKG_PREFIXES
jtestme.jndi.param.lookup=#lookup name

# Test LDAP
jtestme.ldap.type=ldap
jtestme.ldap.name=LDAP Test
jtestme.ldap.description=LDAP Test
jtestme.ldap.resolution=Qué hacer si falla el test
jtestme.ldap.param.url=
jtestme.ldap.param.principal=
jtestme.ldap.param.credentials=

# Test OpenOffice
jtestme.openoffice.type=openoffice
jtestme.openoffice.name=OpenOffice Test
jtestme.openoffice.description=OpenOffice Test
jtestme.openoffice.resolution=Qué hacer si falla el test
jtestme.openoffice.param.host=#default localhost
jtestme.openoffice.param.port=#default 8700

# Test SMTP
jtestme.smtp.type=smtp
jtestme.smtp.name=SMTP Test
jtestme.smtp.description=SMTP Test
jtestme.smtp.resolution=Qué hacer si falla el test
jtestme.smtp.param.host=
jtestme.smtp.param.port=#default 25
jtestme.smtp.param.auth=#default false
jtestme.smtp.param.username=
jtestme.smtp.param.password=
jtestme.smtp.param.starttls=
jtestme.smtp.param.startssl=

# Test WebService : rpc / soap /rest
jtestme.webservice.type=webservice
jtestme.webservice.name=WebService Test
jtestme.webservice.description=WebService Test
jtestme.webservice.resolution=Qué hacer si falla el test
jtestme.webservice.param.protocol=#rpc|soap|rest
jtestme.webservice.param.endpoint=
jtestme.webservice.param.namespaceuri=
jtestme.webservice.param.localpart=
jtestme.webservice.param.truststore=
jtestme.webservice.param.truststorepassword= 

# Test Custom
jtestme.custom.type=custom
jtestme.custom.name=Custom Test
jtestme.custom.description=Custom Test
jtestme.custom.resolution=Qué hacer si falla el test
jtestme.custom.param.class=#className implements es.jtestme.verificators.Verificator
```

#### Viewers
JTestMe tiene definido una serie de visores, `es.jtestme.viewers.Viewer`, por defecto, lo que permite de forma sencilla formatear los resultados obtenidos de la ejecución de los verificators (`es.jtestme.domain.VerificatorResult`).

Los tipos de visores predefinidos están definidos en el enum `es.jtestme.viewers.ViewerType` y son:
* **HTML**, resultado HTML, implementado en la clase `es.jtestme.viewers.impl.HTMLViewer`.
* **TXT**, resultado en texto plano formateado, implementado en la clase `es.jtestme.viewers.impl.PlainTextViewer`.
* **JSON**, resultado anotación JSon, implementado en la clase `es.jtestme.viewers.impl.JSONViewer`.
* **XML**, resultado en formato XML, implementado en la clase `es.jtestme.viewers.impl.XMLViewer`.

Todas las clases constructoras de vistas, siguen el patrón Singleton, sólo existirá una instancia en ejecución.

Para facilitar la invocación del viewer adecuado JTestMe dispone de la factoría `es.jtestme.viewers.ViewerFactory`:
```java
// json viewer 
es.jtestme.viewers.Viewer jsonViewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.JSON);

// recuperar content-type
String contentType = jsonViewer.getContentType();

// construcción de vista
String content = jsonViewer.getContentViewer(List<es.jtestme.domain.VerificatorResult> verificatorsResults);	
```

#### Java SE
Para configurar JTestMe en una aplicación Java SE, es necesario invocar al constructor de JTestMe, `es.jtestme.JTestMeBuilder`, el cual permite tanto configurar los verifcators (añadiendolos directamente, o bien mediante archivo de propiedades), y ejecutar los verificators configurados.

El constructor `es.jtestme.JTestMeBuilder`, sigue el patrón Singleton, por lo que se puede invocar desde distintos sitios de la aplicación y siempre se tratará de la misma referencia, solo hay una única instancia.

Ejemplo:
```java
// invocamos al constructor de JTestMe
es.jtestme.JTestMeBuilder builder = es.jtestme.JTestMeBuilder.getInstance();

// cargar verificators de un archivo properties
builder.loadVerificators(configLocation);

// cargar verificator de forma programada
builder.addVerificator(new es.jtestme.verificators.Verificator());
builder.addVerificator(new es.jtestme.verificators.impl.SMTPVerificator(params));

// permite ejecutar un verificator de forma individual (uid), devolviendo su resultado 
es.jtestme.domain.VerificatorResult verificatorResult = builder.executeVerificator(verificatorUid);

// permite ejecutar todos los verificators, devolviendo sus resultados
List<es.jtestme.domain.VerificatorResult> verificatorsResults = builder.executeVerificators();

// dispone de un método para limpiar la lista de verificators configurados
builder.destroy();
```

JTestMe permite además de utilizar su motor de ejecución de verificatos, también permite construir facilmente una vista gracias a los visores ([Viewers](#viewers)):
```java
// ejecutamos los verificators obteniendo sus resultados
List<es.jtestme.domain.VerificatorResult> verificatorsResults = ...;

// cargamos el viewer deseado
es.jtestme.viewers.Viewer viewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.TXT);

// delegamos los resultados de los verificators al viewer para construya la vista 
String txt = viewer.getContentViewer(verificatorsResults);
```

#### Java EE
Para configurar JTestMe en una aplicación Web, se puede hacer de distintas formas:
* **automática**, definir en el web.xml el `javax.servlet.Filter` de JTestMe, `es.jtestme.filter.JTestMeFilter` y definir en un archivo properties los verificators.
* **manual**, invocar al constructor de JTestMe, `es.jtestme.JTestMeBuilder`, al igual que se hace en una aplicación Java SE. Además será necesario configurar los filtros o servlet necesario para mapear la ruta donde se desea mostrar visualmente los resultados de los verificators.

En este caso se muestra como efectuar la configuración de forma automática, la más sencilla y rápida; configurando el `javax.servlet.Filter` y definiendo el archivo de properties con los verificators definidos.

1.Configurar el `javax.servlet.Filter` en el **web.xml**, añadiendo el filtro el primero:
```xml
<filter>
	<filter-name>jtestmefilter</filter-name>
	<filter-class>es.jtestme.filter.JTestMeFilter</filter-class>
	<!--
	...
	<init-param>
		<param-name>config-location</param-name>
		<param-value>classpath:jtestme.properties</param-value>
	</init-param>
	<init-param>
		<param-name>log</param-name>
		<param-value>true</param-value>
	</init-param>
	...
	-->
</filter>
<filter-mapping>
	<filter-name>jtestmefilter</filter-name>
	<url-pattern>/jtestme/*</url-pattern>
</filter-mapping>
```

> Existen diferentes parámetros configuraciones para el `javax.servlet.Filter` de JTestMe, todos ellos son opcionales. Se puede ver todos los parámetros diponibles para la configuración de la librería en el punto [Parámetros JTestMeFilter](#parámetros-jtestmefilter).

2. Configurar el archivo properties, **jtestme.properties**, en la ruta indicada en los parámetros del filtro:
```properties
...
# Test Datasource
jtestme.datasource.type=datasource
jtestme.datasource.name=Datasource Test
jtestme.datasource.param.datasource=
...
```

> *NOTA: también es posible cargar en tiempo de ejecución verificadores, al igual que se muestra para aplicaciones Java SE. Igualmente se podría arrancar JTestMe sin definir ningún archivo de configuración externo.*

#### Dependencias
La librería JTestMe esta implementado con la versión **1.5** de Java.

Además existirá una serie de dependencias adiciones en función de los verificadores que se utilicen:
* **smpt**, `javax.mail`
```xml
<dependency>
	<groupId>javax.mail</groupId>
	<artifactId>mail</artifactId>
	<version>1.4.6</version>
	<optional>true</optional>
</dependency>
```

* **openoffice**, `jodconverter`
```xml
<dependency>
	<groupId>com.artofsolving</groupId>
	<artifactId>jodconverter</artifactId>
	<version>2.2.1</version>
	<optional>true</optional>
</dependency>
```

* **webservice rpc** `jaxrpc-impl`
```xml
 <dependency>
	<groupId>com.sun.xml.rpc</groupId>
	<artifactId>jaxrpc-impl</artifactId>
	<version>1.1.3_01</version>
	<optional>true</optional>
</dependency>
```

### Configuración Avanzada
A continuación se muestran conceptos avanzados de la librería JTestMe que se pueden aplicar.

#### Log
JTestMe cuenta con un Logger propio, `es.jtestme.logger.JTestMeLogger`. Esta clase no instanciable dispone de las funciones más comunes para lanzar trazas de log utilizando las librerías Java de Logger más utilizadas:
* **SLF4J**, `org.slf4j.Logger`.
* **LOG4J**, `org.apache.log4j.Logger`.
* **LOGGER**, `java.util.logging.Logger`.

La clase se encarga de detectar cual de las librerías está disponible para mostrar los las trazas log. No es necesario que en el classpath se encuentre la librería, slf4j o log4j, pero si se encuentra su uso tiene preferencia frente al logger por defecto de java (`java.util.logging.Logger`).

La clase de Logger de JTestMe, permite también desactivar todos las trazas de forma programática:
```java
// método para activar/desactivar todas las trazas log de JTestMe
es.jtestme.logger.JTestMeLogger.setLoggerEnabled(false);

// comprobamos si están activadas/desactivadas las trazas log
boolean jtestmeLoggerEnabled = es.jtestme.logger.JTestMeLogger.isLoggerEnabled();
```

Para una aplicación web, el filtro de JTestMe, dispone de un parámetro para definir su comportamiento:
```xml
<filter>
	<filter-name>jtestmefilter</filter-name>
	<filter-class>es.jtestme.filter.JTestMeFilter</filter-class>
	...
	<init-param>
		<param-name>log</param-name>
		<param-value>false</param-value>
	</init-param>
	...
</filter>
```

#### Parámetros JTestMeFilter
El filtro web de JTestMe, `es.jtestme.filter.JTestMeFilter`, dispone de una serie de parámetros que permiten modificar su configuración por defecto.

Para definir los parámetros de configuración del filtro sólo es necesario definirlos mediante un `<init-param>` en al configuración del `javax.servlet.Filter`.
```xml
<filter>
	<filter-name>jtestmefilter</filter-name>
	<filter-class>es.jtestme.filter.JTestMeFilter</filter-class>
	...
	<init-param>
		<param-name>...</param-name>
		<param-value>...</param-value>
	</init-param>
	...
</filter>
```

Los parámetros de configuración de `es.jtestme.filter.JTestMeFilter` son:
PROPIEDAD | VALOR POR DEFECTO | DESCRIPCIÓN
--------- | ----------------- | -----------
**config-location** | `classpath:jtestme.properties` | Ubicación del archivo de configuración de JTestMe donde se definen los verificators
**encoding** | `UTF-8` | Códificación por defecto de las respuestas HTTP a las peticiones el filtro de JTestMe
**log** | `true` | Activa/Desactiva las trazas log de la librería JTestMe
**param-type-format** | `format` | Nombre del parámetro request que debe recibir el filtro de JTestMe para cambiar el tipo de respuesta.
**default-viewer** | `html` | Viewer que se mostrará por defecto si no especifíca el formato.
**html-viewer-class** | `es.jtestme.viewers.impl.HTMLViewer` | Implementación por defecto del viewer HTML.
**json-viewer-class** | `es.jtestme.viewers.impl.JSONViewer` | Implementación por defecto del viewer JSON.
**txt-viewer-class** | `es.jtestme.viewers.impl.PlainTextViewer` | Implementación por defecto del viewer TXT.
**xml-viewer-class** | `es.jtestme.viewers.impl.XMLViewer` | Implementación por defecto del viewer XML.
**custom-viewer-class** | `es.jtestme.viewers.impl.CustomViewer` | Implementación por defecto del viewer CUSTOM.
**schedule** | `false` | Activa/Desactiva la ejecución programada de los verificators.
**schedule-period** | `10` | Periodo en minutos para la ejecución de todos los verificators configurados.
**schedule-viewer** | `TXT` | Tipo de Viewer utilizado por defecto para mostrar en el Log el resultado de la ejecución de los verificators programados, Los tipos de viewers disponibles son: HTML, JSON, XML, TXT y CUSTOM. 
 
El parámetro **param-type-format**, permite definir el nombre de la variable request que permitirá cambiar el tipo de respuesta que se devuelve: HTML, TXT, JSON y XML. El valor del tipo de respuesta es "case insensitive" y debe coincidir con uno de los tipos definidos en la clase `es.jtestme.viewers.ViewerType`.
Ejemplo:
* `http://.../jtestme` ==> resultado HTML
* `http://.../jtestme?format=txt` ==> resultado TXT
* `http://.../jtestme?format=json` ==> resultado JSON
* `http://.../jtestme?format=XML` ==> resultado XML

#### Verificators Personalizados
Uno de los tipos de verificators de JTestMe es **custom**, el cual permite definir un tipo de verificator personalizado, siendo necesario programar su comportamiento.

Para definir la configuración del verificator de un tipo **custom** es necesario definir la ruta de la clase que implementa el interface `es.jtestme.verificators.Verificator` en el parámetro `class`. Adicionalmente se pueden pasar parámetros a la clase personalizada que implementa el verificator.
```properties
....
jtestme.custom.type=custom
jtestme.custom.name=Custom Test
jtestme.custom.description=Custom Test Description
jtestme.custom.resolution=How to resolve the problema
jtestme.custom.param.class=#class implements es.jtestme.verificators.Verificator
jtestme.custom.param.paramRecivedCustomVerificatorClass=...
jtestme.custom.param.myCustomParameter=100
....
```

Para implementar un verificator personalizado este debe implementar el interface `es.jtestme.verificators.Verificator`, hay dos posibles soluciones:
* clase pública que implementa directamente interface `es.jtestme.verificators.Verificator`
```java
public class MyCustomVerificator implements es.jtestme.verificators.Verificator {

	private String uid = "myCustomVerificator";
	private Map<String, String> parameters;
	
	private String paramRecivedCustomVerificatorClass;
	private Integer myCustomParameter;
	
	// constructor opcional sin parámetros
	public MyCustomVerificator() {
	}
	
	// constructor opcional que recibe los parámetros de la configuración
	public MyCustomVerificator(Map<String, String> parameters) {
		this("myCustomVerificator", parameters);
	}
	
	// constructor opcional que recibe el identificador del verificator (uid) y los parámetros de la configuración
	public MyCustomVerificator(String uid, Map<String, String> parameters) {
		this.uid = uid;
		this.parameters = parameters;
		this.paramRecivedCustomVerificatorClass = parameters.get("paramRecivedCustomVerificatorClass");
		this.myCustomParameter = Integer.parseInt(parameters.get("myCustomParameter")); 
	}
	
	// devuelve el identificador único del verificator para poder identificarlo
	public String getUid() {
		return uid;
	}
	
	// ejecuta la lógica del verificator
	public VerificatorResult execute() {
		es.jtestme.domain.VerificatorResult result = new es.jtestme.domain.VerificatorResult();
		result.setType(es.jtestme.verificators.VerificatorType.CUSTOM.toString());
		result.setName("my custom verificator");
		result.setDescription("my custom verificator description");
		result.setResolution("my custom verificator resolution");
		result.setOptional(true|false);
		
		// implementación del custom verificator
		
		return result; 
	}
}
```

* clase pública que extiende de la clase `es.jtestme.verificators.impl.AbstractVerificator` la cual implementa el interface Verificator y además cuenta que varios métodos útiles para recuperar los valores de los parámetros de la configuración.
```java
public class MyCustomVerificator extends es.jtestme.verificators.impl.AbstractVerificator {

	// no es necesario definir los atributos "uid" y "parametes" ya están definidos en el padre

	private String paramRecivedCustomVerificatorClass;
	private Integer myCustomParameter;
	
	// uno de los siguientes constructores es obligatorio implementarlo
	
	// constructor recibe los parámetros de la configuración
	public MyCustomVerificator(Map<String, String> parameters) {
		super(parameters);
		this.paramRecivedCustomVerificatorClass = super.getParamString("paramRecivedCustomVerificatorClass");
		this.myCustomParameter = super.getParamInteger("myCustomParameter");
	}
	
	// constructor recibe el identificador del verificator (uid) y los parámetros de la configuración
	public MyCustomVerificator(String uid, Map<String, String> parameters) {
		super(uid, parameters);
		this.paramRecivedCustomVerificatorClass = super.getParamString("paramRecivedCustomVerificatorClass");
		this.myCustomParameter = super.getParamInteger("myCustomParameter"); 
	}
	
	// el método "getUid()" es implementado por el padre, no es necesario
	
	// ejecuta la lógica del verificator
	public VerificatorResult execute() {
		// clase padre facilita el VerificatorResult con la información por defecto: type, name, description,...
		es.jtestme.domain.VerificatorResult result = super.getResult();
		
		// implementación del custom verificator
		
		return result; 
    }
}
```

> *NOTA: las clases verificators custom sólo son instanciadas una única vez, por lo que sus método, especialmente `execute()`, debe ser thread-safe, para ello es recomendable solo utilizar variables locales del método y no atributos de la clase, cuyos valores podrían ser accedidos por multiples hilos simultáneamente.*

#### Viewers Personalizados
Una vez visto los conceptos básicos de los [Viewers](#viewers), en el siguiente punto mostraremos como implementar viewers personalizados y añadirlos a la factoría de viewers.

JTestMe permite redifinir todos los viewers por defecto: HTML, JSON, TXT y XML, así como un tipo propio CUSTOM. Además permite cambiar el viewer por defecto al mostrar las páginas en el navegador si no se recibe el parámetro de formato (`{{{http://.../jtestme?format=xml`}}}).

Para cambiar el viewer por defecto, HTML, se debe hacer mediante el parámetro **default-viewer** en la configuración del `javax.servlet.Filter`:
```xml
...
<init-param>
	<param-name>default-viewer</param-name>
	<param-value>custom</param-value>
</init-param>
...
```

Para cambiar cualquier tipo de viewer disponible es necesario definirlo en la configuración del filtro:
```xml
...
<init-param>
	<param-name>xml-viewer-class</param-name>
	<param-value>es.jtestme.test.viewers.MyXMLViewer</param-value>
</init-param>
...
```

La clase que reemplaza el viewer por defecto debe implementar el interface `es.jtestme.viewers.Viewer`, o bien puede heredar de la clase `es.jtestme.viewers.impl.AbstractViewer` que implementa el interface además de disponer de métodos comunes.
```java
public class MyXMLViewer extends es.jtestme.viewers.impl.AbstractViewer {

    public String getContentType() {
        return "text/xml";
    }

    public String getContentViewer(final List<es.jtestme.domain.VerificatorResult> results) {
        final StringBuilder builder = new StringBuilder();
        builder.append("<?xml version='1.0' encoding='ISO-8859-15'?>").append(NEW_LINE);
        builder.append("<verificarSistema nodo='").append(getHostName()).append("'>").append(NEW_LINE);
        if (results != null &amp;&amp; !results.isEmpty()) {
            for (final es.jtestme.domain.VerificatorResult result : results) {
                builder.append("<servicio>").append(NEW_LINE);
                builder.append("<nombre>").append(result.getName()).append("</nombre>").append(NEW_LINE);
                builder.append("<estado>").append(result.isSuccess() ? "OK" : "KO").append("</estado>")
                        .append(NEW_LINE);
                if (!result.isSuccess()) {
                    builder.append("<descError>").append(result.getMessage()).append("</descError>").append(NEW_LINE);
                    builder.append("<accionError>").append(result.getResolution()).append("</accionError>")
                            .append(NEW_LINE);
                }
                builder.append("</servicio>").append(NEW_LINE);
            }
        }
        builder.append("</verificarSistema>").append(NEW_LINE);
        return builder.toString();
    }
}
```

Otra forma de modificar los viewers por defecto es registrar los nuevos viewers en su factoría, `es.jtestme.viewers.Viewer`, la cual permite registrarlos de forma programática:
```java
// permite definir un tipo propio "custom"
es.jtestme.viewers.ViewerFactory.registerViewer(es.jtestme.viewers.ViewerType.CUSTOM, new es.jtestme.viewers.Viewer());

// o incluso, reemplazar un viewer por defecto
es.jtestme.viewers.ViewerFactory.registerViewer(es.jtestme.viewers.ViewerType.XML, new MyXMlViewer());

// para obtener el viewer basta con invocarlo en la factoría 
es.jtestme.viewers.Viewer customViewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.CUSTOM);
es.jtestme.viewers.Viewer xmlViewer = es.jtestme.viewers.ViewerFactory.loadViewer(es.jtestme.viewers.ViewerType.XML);
```

*NOTA: los viewers, al igual que los verificators, sólo se instancian una única vez por lo que sus métodos deben ser thread-safe: evitar atributos compartidos.*

#### Verificators Programados
JTestMe permite ejecutar de programada los verificators para comprobar el estado de los recursos configurados de la aplicación, mostrando el resultado de esta ejecución mediante una traza LOG.

La configuración del programador de JTestMe permite configurar los siguientes parámetros:
* activar/desactivar el programador.
* definir que viewer se utilizará para mostrar los resultados de la ejecución de los verificators.
* establecer el periodo de tiempo entre las ejecuciones de los verificators (en minutos).

Esta configuración se puede definir tanto para aplicaciones Web, como Standalone:
* Web, sólo será necesario definir correctamente los parámetros de configuración del `javax.servlet.Filter` de JTestMe:
```xml
...
<init-param>
	<param-name>schedule</param-name>
	<param-value>true</param-value>
</init-param>
<init-param>
	<param-name>schedule-period</param-name>
	<param-value>60</param-value>
</init-param>
<init-param>
	<param-name>schedule-viewer</param-name>
	<param-value>XML</param-value>
</init-param>
...
```

* Standalone, será necesario activar de forma programada su ejecución:
```java
// Primero será necesaria la configuración de JTestMeBuilder
...

// Configuración y Activación de JTestMeScheduler
es.jtestme.schedule.JTestMeScheduler scheduler = es.jtestme.schedule.JTestMeScheduler.getInstance();  
scheduler.setPeriod(60l);
scheduler.setViewer("XML");
scheduler.start();
...

// Para detener la ejecución programada
scheduler.stop();
```

*NOTA: la ejecución de los verificators de forma programada está desactivado por defecto, será necesario su configuración para activarlo.*

La ejecución de los verificators de se ejecutará de forma programada cada X periodo de tiempo definido, si se produce un error en alguno de los verificators se mostrará un mensaje en el Logger con nivel de ERROR, por lo que será necesario que esté activado para poder ver los resultados de la ejecución. En el punto [Log](#log) se puede ver como configurarlo.

Gracias a este ejecución programada y a la librería logger, se puede configurar nuestra aplicación para cuando se produzca un error en cualquiera de los verificators definidos nos avise, por ejemplo mediante un correo electrónico. Ejemplo de configuración de Log4j:
```properties
log4j.rootCategory=INFO
log4j.logger.es.jtestme.schedule=ERROR, mail

# mail es configurado para utilizar SMTPAppender
log4j.appender.mail=org.apache.log4j.net.SMTPAppender
log4j.appender.mail.Threshold=ERROR
log4j.appender.mail.BufferSize=512
log4j.appender.mail.To=
log4j.appender.mail.From=jtestme@noreply.com
log4j.appender.mail.SMTPHost=
log4j.appender.mail.Subject=[JTestMe] Error Verificators
log4j.appender.mail.layout=org.apache.log4j.PatternLayout
log4j.appender.mail.layout.ConversionPattern=%m
```

> NOTA: al producirse fallo en la ejecución de alguno de los verificator sólo se mostrará una única traza de log hasta que solucione el problema.

#### Seguridad
The monitoring page does not contain data such as passwords, but before using it in production, you may probably want that this page is in restricted access. You may secure it by using your own means. Otherwise, it is possible to restrict its access by a regular expression on ip address of client with the parameter allowed-addr-pattern (regular expression with a range of internal ip addresses or fixed ip addresses of administrators). For example, "192\.168\..**|123\.123\.123\.123" to allow the ip addresses in the "192.168.**.**" range plus any pc hiding behind the gateway 123.123.123.123.**

Note that if you use a http proxy server like Apache in front of the server, the ip address of the client will be the one of Apache. So you may not use allowed-addr-pattern in this case, or do not use Apache to access this page, or you may enable mod_proxy_ajp in order that the monitored server knows the ip address of the clients. Example of httpd conf with ajp:

```xml
<location /webapp>
	ProxyPass ajp://localhost:8080/webapp
</location> 
```

Or if some security-constraint et security-role(s) are defined in the web.xml file of the application, you can also restrict in the web.xml file the access to the monitoring page to "monitoring" role for example.

Example of content of the web.xml file for authentication by login and password:
```xml
<login-config>
	<auth-method>BASIC</auth-method>
	<realm-name>JTestMe</realm-name>
</login-config>
<security-role>
	<role-name>jtestme</role-name>
</security-role>
<security-constraint>
	<web-resource-collection>
		<web-resource-name>JTestMe</web-resource-name>
		<url-pattern>/jtestme/*</url-pattern>
	</web-resource-collection>
	<auth-constraint>
		<role-name>jtestme</role-name>
	</auth-constraint>
	<!-- if SSL enabled (SSL and certificate must then be configured in the server)
	<user-data-constraint>
		<transport-guarantee>CONFIDENTIAL</transport-guarantee>
	</user-data-constraint> 
	-->
</security-constraint>
```

The realm and the users must be defined in the application server, and the users must have the "jtestme" role to have access to the reports. For example, if tomcat is used with the default realm, modify the content of the conf/tomcat-users.xml file as follows:
```xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
	<role rolename="jtestme"/>
	<user username="jtestme" password="jtestme" roles="jtestme"/>
</tomcat-users>
```