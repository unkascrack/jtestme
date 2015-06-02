# JTestMe: To Do #

Here is a list of wishes of features.

JTestMe is opensource: Priorities and todos may change any time. There is no promise of dates or that a particular feature will ever be done.

<ol>
<blockquote><li>Fix <a href='http://code.google.com/p/jtestme/issues/list'>issues</a>.<br>
<li>Documentar el proyecto: <a href='http://code.google.com/p/jtestme/wiki/UserGuide'>wiki</a>.<br>
<li><del>Implementar el verificator para validación test web service REST.</del>
<li><del>Anadir nuevo verificator: FTP.</del>
<li>Anadir nuevo verificator: SSH.<br>
<li>Anadir nuevo verificator: IMAP.<br>
<li><del>Revisar la factoría de clases Verificator, VerificatorFactory.java, para que la instanciación de los tipos de verificators se realice utilizando Reflection, en vez de switch.</del>
<li>Mejorar el interfaz Web.<br>
<li><del>Estudiar la posibilidad de poder crear una vista de tipo "<b>custom</b>", así como se podrían reemplazar la JTestMeView que se generan por defecto.</del>
<li>Extraer los textos a messages resources para poder I18N.<br>
<li>Construcción de la aplicación jtestme-collector-server, para poder monitorizar el estado de N aplicaciones configuradas con jtestme-core.<br>
<li><del>Utilizar multiple threads para realizar simultáneamente varios test y ganar en rendimiento de respuesta.</del>
<li>Eliminar dependencias con la librería "jodconverter" del verificator OpenOffice. Construir una conexión con <code>Sockets</code>.<br>
<li><del>Permitir la ejecución de los verificators de forma programada, cada N minutos.</del>
<li>Revisar la ejecución del WebServiceVerificator para los tipos de Web Service rest