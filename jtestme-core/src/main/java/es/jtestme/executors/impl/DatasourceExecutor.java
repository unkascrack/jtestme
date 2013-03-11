package es.jtestme.executors.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import es.jtestme.domain.JTestMeResult;

public class DatasourceExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_DATASOURCE = "datasource";
    private static final String PARAM_TEST_QUERY = "testquery";

    private final String datasourceName;
    private final String testQuery;

    public DatasourceExecutor(final Map<String, String> params) {
        super(params);
        datasourceName = getParamString(PARAM_DATASOURCE);
        testQuery = getParamString(PARAM_TEST_QUERY);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Context context = null;
        try {
            context = new InitialContext();
            final DataSource datasource = (DataSource) context.lookup(datasourceName);
            connection = datasource.getConnection();
            if (testQuery != null && testQuery.trim().length() > 0) {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(testQuery);
            }
            result.setSuscess(true);
        } catch (final NamingException e) {
            result.setCause(e);
        } catch (final SQLException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                }
            }

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

            if (context != null) {
                try {
                    context.close();
                } catch (final NamingException e) {
                }
            }
        }
        return result;
    }
}
