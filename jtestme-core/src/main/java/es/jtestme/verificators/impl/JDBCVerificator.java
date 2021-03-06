package es.jtestme.verificators.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.utils.JTestMeUtils;

public final class JDBCVerificator extends AbstractVerificator {

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
        this.driver = getParamString(PARAM_DRIVER);
        this.url = getParamString(PARAM_URL);
        this.username = getParamString(PARAM_USERNAME);
        this.password = getParamString(PARAM_PASSWORD);
        this.testQuery = getParamString(PARAM_TEST_QUERY);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(this.driver);
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            if (this.testQuery != null && this.testQuery.trim().length() > 0) {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(this.testQuery);
            }
            result.setSuccess(true);
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
