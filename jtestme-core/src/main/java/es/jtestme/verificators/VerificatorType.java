package es.jtestme.verificators;

import es.jtestme.verificators.impl.ConnectionVerificator;
import es.jtestme.verificators.impl.CustomVerificator;
import es.jtestme.verificators.impl.DatasourceVerificator;
import es.jtestme.verificators.impl.FTPVerificator;
import es.jtestme.verificators.impl.FilePermissionVerificator;
import es.jtestme.verificators.impl.GraphicsVerificator;
import es.jtestme.verificators.impl.JDBCVerificator;
import es.jtestme.verificators.impl.JNDIVerificator;
import es.jtestme.verificators.impl.JavaCryptographyExtensionVerificator;
import es.jtestme.verificators.impl.LDAPVerificator;
import es.jtestme.verificators.impl.MemoryVerificator;
import es.jtestme.verificators.impl.OpenOfficeVerificator;
import es.jtestme.verificators.impl.PropertyVerificator;
import es.jtestme.verificators.impl.SMTPVerificator;
import es.jtestme.verificators.impl.WebServiceVerificator;

public enum VerificatorType {

    CONNECTION(ConnectionVerificator.class),
    CUSTOM(CustomVerificator.class),
    DATASOURCE(DatasourceVerificator.class),
    FILE(FilePermissionVerificator.class),
    FTP(FTPVerificator.class),
    GRAPHICS(GraphicsVerificator.class),
    JCE(JavaCryptographyExtensionVerificator.class),
    JDBC(JDBCVerificator.class),
    JNDI(JNDIVerificator.class),
    LDAP(LDAPVerificator.class),
    MEMORY(MemoryVerificator.class),
    OPENOFFICE(OpenOfficeVerificator.class),
    PROPERTY(PropertyVerificator.class),
    SMTP(SMTPVerificator.class),
    WEBSERVICE(WebServiceVerificator.class);

    private Class<? extends Verificator> verificatorClass;

    VerificatorType(final Class<? extends Verificator> verificatorClass) {
        this.verificatorClass = verificatorClass;
    }

    public Class<? extends Verificator> getVerificatorClass() {
        return this.verificatorClass;
    }

    public String getVerificatorClassName() {
        return this.verificatorClass.getName();
    }

    public static VerificatorType toType(final String str) {
        VerificatorType type = null;
        try {
            type = str != null ? valueOf(str.toUpperCase()) : null;
        } catch (final IllegalArgumentException e) {
            type = null;
        }
        return type;
    }
}
