package es.map.jtestme.executors.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import es.map.jtestme.domain.JTestMeResult;

public class JNDIExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_DATASOURCE = "datasource";
    private static final String PARAM_TEST_QUERY = "testquery";

    public JNDIExecutor(final Map<String, String> params) {
        super(params);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        final String datasourceName = params.get(PARAM_DATASOURCE);
        final String testQuery = params.get(PARAM_TEST_QUERY);

        Connection connection = null;
        Statement statement = null;
        try {
            final Context initContext = new InitialContext();
            final DataSource datasource = (DataSource) initContext.lookup(datasourceName);
            connection = datasource.getConnection();
            if (testQuery != null) {
                statement = connection.createStatement();
                statement.executeQuery(testQuery);
            }
            result.setSuscess(true);
        } catch (final NamingException e) {
            result.setMessage(e.toString());
        } catch (final SQLException e) {
            result.setMessage(e.toString());
        } catch (final Throwable e) {
            result.setMessage(e.toString());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                }
            }
        }
        return result;
    }
}
