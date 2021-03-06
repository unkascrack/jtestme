# JTestMe: Changelog #

## 1.7 ##
 * Added new verificators: 
   - Java Cryptography Extension (JCE)
   - File Permission (file)
   - Memory (memory)
   - Property (property)

## 1.6 ##

## 1.5 ##

## 1.4 ##
  * [Issue 8](https://code.google.com/p/jtestme/issues/detail?id=8): Permitir configurar los test para que se ejecuten de forma programada.
  * [Issue 7](https://code.google.com/p/jtestme/issues/detail?id=7):  Problem with `DatasourceVerificator` doesn't close the connection.
  * Revisada la documentación.

## 1.3 ##
  * [Issue 3](https://code.google.com/p/jtestme/issues/detail?id=3): Interfaz Web no se debe mostrar el botón desplegable + si no hay exception.
  * [Issue 4](https://code.google.com/p/jtestme/issues/detail?id=4): Permitir configurar los viewers por defecto y nuevos mediante configuración.
  * Revisión viewer XML.
  * Revisión viewer JSON.
  * Modificado sistema de configuración de parámetros en el Servlet Filter.

## 1.2 ##
  * [Issue 1](https://code.google.com/p/jtestme/issues/detail?id=1): Error en los tests webservice, cuando se introduce en el endpoint la terminación ?wsdl.
  * [Issue 2](https://code.google.com/p/jtestme/issues/detail?id=2): `ConnectorVerificator`, modificar el comportamiento de los componentes `HttpUrlConnection` y `HttpsUrlConnection`.
  * Refactoring de todas los packages y clases.
  * Revisión viewer HTML.
  * Verificación conexiones FTP.
  * Verificación web service REST.
  * Mejora de rendimiento en la ejecución de multiples verificators utilizando threads en paralelo.

## 1.1 ##
  * Corregido verificator webservice.
  * Añadida posibilidad de asignar system properties (`System.getProperty()`) y classpath para los valores de las propiedades de los verificators.

## 1.0 ##
  * Versión inicial del proyecto.
  * Implementado el módulo core (jtesme-core).
  * Implementado el módulo test-webapp (jtestme-test-webapp).
  * Creado los verificators: connection, datasource, graphics, jndi, jdbc, ldap, openoffice, smtp, webservice y custom.
  * Creada los views: html, xml, text y json.