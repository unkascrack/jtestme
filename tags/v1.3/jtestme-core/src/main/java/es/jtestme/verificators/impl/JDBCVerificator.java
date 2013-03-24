package es.jtestme.verificators.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.utils.JTestMeUtils;

public class JDBCVerificator extends AbstractVerificator {

    private static final String PARAM_DRIVER = "driver";
    private static final String PARAM_URL = "url";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_TEST_QUERY = "testquery";

    private final String driver;
    private final String url;
    private final String username;
    private final String password;
    private final String testQuery;

    public JDBCVerificator(final Map<String, String> params) {
        super(params);
        driver = getParamString(PARAM_DRIVER);
        url = getParamString(PARAM_URL);
        username = getParamString(PARAM_USERNAME);
        password = getParamString(PARAM_PASSWORD);
        testQuery = getParamString(PARAM_TEST_QUERY);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            if (testQuery != null && testQuery.trim().length() > 0) {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(testQuery);
            }
            result.setSuscess(true);
        } catch (final ClassNotFoundException e) {
            result.setCause(e);
        } catch (final SQLException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            JTestMeUtils.closeQuietly(resultSet);
            JTestMeUtils.closeQuietly(statement);
            JTestMeUtils.closeQuietly(connection);
        }
        return result;
    }
}
