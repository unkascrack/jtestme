package es.jtestme.listener;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;

import com.dumbster.smtp.SimpleSmtpServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;

import es.jtestme.logger.JTestMeLogger;

public class StartupListener implements ServletContextListener {

    private SimpleSmtpServer smtpServer;
    private InMemoryDirectoryServer ldapServer;
    private Process sOffice;
    private FakeFtpServer fakeFtpServer;

    public void contextInitialized(final ServletContextEvent context) {
        smtpServer = SimpleSmtpServer.start(2525);

        try {
            final InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("cn=admin,dc=es");
            final InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig("test", null, 33390, null, null,
                    null);
            config.setListenerConfigs(listenerConfig);
            config.addAdditionalBindCredentials("cn=admin,dc=es", "cambiame");
            ldapServer = new InMemoryDirectoryServer(config);
            ldapServer.startListening();
        } catch (final LDAPException e) {
            JTestMeLogger.error(e.toString(), e);
        }

        try {
            final InitialContext initialContext = new InitialContext();
            initialContext.bind("myresource", new Object());
        } catch (final NamingException e) {
            JTestMeLogger.error(e.toString(), e);
        }

        try {
            final String[] commands = new String[] { "soffice", "-headless",
                    "-accept=socket,host=localhost,port=8100;urp;" };
            sOffice = Runtime.getRuntime().exec(commands);
            final int code = sOffice.waitFor();
            if (code != 0) {
                JTestMeLogger.error("No se ha podido levantar le servicio soffice");
            }
        } catch (final IOException e) {
            JTestMeLogger.error(e.toString(), e);
        } catch (final InterruptedException e) {
            JTestMeLogger.error(e.toString(), e);
        }
        
        try {
            final String dir = "C:\\data";

            fakeFtpServer = new FakeFtpServer();
            fakeFtpServer.setServerControlPort(2121);
            fakeFtpServer.addUserAccount(new UserAccount("anonymous", "anonymous", dir));
            fakeFtpServer.addUserAccount(new UserAccount("user", "password", dir));

            final FileSystem fileSystem = new WindowsFakeFileSystem();
            fileSystem.add(new DirectoryEntry(dir));
            fakeFtpServer.setFileSystem(fileSystem);

            fakeFtpServer.start();
        } catch (final Throwable e) {
            JTestMeLogger.error(e.toString(), e);
        }
    }

    public void contextDestroyed(final ServletContextEvent context) {
        smtpServer.stop();
        ldapServer.shutDown(true);
        sOffice.destroy();
        fakeFtpServer.stop();
    }

}
