package es.jtestme.verificators;

import es.jtestme.verificators.impl.ConnectionVerificator;
import es.jtestme.verificators.impl.CustomVerificator;
import es.jtestme.verificators.impl.DatasourceVerificator;
import es.jtestme.verificators.impl.FTPVerificator;
import es.jtestme.verificators.impl.GraphicsVerificator;
import es.jtestme.verificators.impl.JDBCVerificator;
import es.jtestme.verificators.impl.JNDIVerificator;
import es.jtestme.verificators.impl.LDAPVerificator;
import es.jtestme.verificators.impl.OpenOfficeVerificator;
import es.jtestme.verificators.impl.SMTPVerificator;
import es.jtestme.verificators.impl.WebServiceVerificator;

public enum VerificatorType {

    CONNECTION(ConnectionVerificator.class),
    CUSTOM(CustomVerificator.class),
    DATASOURCE(DatasourceVerificator.class),
    FTP(FTPVerificator.class),
    GRAPHICS(GraphicsVerificator.class),
    JDBC(JDBCVerificator.class),
    JNDI(JNDIVerificator.class),
    LDAP(LDAPVerificator.class),
    OPENOFFICE(OpenOfficeVerificator.class),
    SMTP(SMTPVerificator.class),
    WEBSERVICE(WebServiceVerificator.class);

    private Class<? extends Verificator> verificatorClass;

    private VerificatorType(final Class<? extends Verificator> verificatorClass) {
        this.verificatorClass = verificatorClass;
    }

    public Class<? extends Verificator> getVerificatorClass() {
        return verificatorClass;
    }

    public String getVerificatorClassName() {
        return verificatorClass.getName();
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
