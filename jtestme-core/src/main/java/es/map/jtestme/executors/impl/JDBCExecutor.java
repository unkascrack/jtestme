package es.map.jtestme.executors.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import es.map.jtestme.domain.JTestMeResult;

public class JDBCExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_DRIVER = "driver";
    private static final String PARAM_URL = "url";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_TEST_QUERY = "testquery";

    public JDBCExecutor(final Map<String, String> params) {
        super(params);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        final String driver = params.get(PARAM_DRIVER);
        final String url = params.get(PARAM_URL);
        final String username = params.get(PARAM_USERNAME);
        final String password = params.get(PARAM_PASSWORD);
        final String testQuery = params.get(PARAM_TEST_QUERY);

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            if (testQuery != null) {
                statement = connection.createStatement();
                statement.executeQuery(testQuery);
            }
            result.setSuscess(true);
        } catch (final ClassNotFoundException e) {
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
