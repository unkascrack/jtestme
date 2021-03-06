package es.jtestme.verificators.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.utils.JTestMeUtils;

public final class DatasourceVerificator extends AbstractVerificator {

    private static final String PARAM_DATASOURCE = "datasource";
    private static final String PARAM_TEST_QUERY = "testquery";

    private final String datasourceName;
    private final String testQuery;

    public DatasourceVerificator(final Map<String, String> params) {
        super(params);
        this.datasourceName = getParamString(PARAM_DATASOURCE);
        this.testQuery = getParamString(PARAM_TEST_QUERY);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Context context = null;
        try {
            context = new InitialContext();
            final DataSource datasource = (DataSource) context.lookup(this.datasourceName);
            connection = datasource.getConnection();
            if (this.testQuery != null && this.testQuery.trim().length() > 0) {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(this.testQuery);
            }
            result.setSuccess(true);
        } catch (final NamingException e) {
            result.setCause(e);
        } catch (final SQLException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            JTestMeUtils.closeQuietly(resultSet);
            JTestMeUtils.closeQuietly(statement);
            JTestMeUtils.closeQuietly(connection);
            JTestMeUtils.closeQuietly(context);
        }
        return result;
    }
}
