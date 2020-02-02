package com.grexdev.nplusone.core.proxy.datasource;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@RequiredArgsConstructor
public class ProxyStatement implements Statement {

    @Delegate(excludes = StatementOverwrite.class)
    private final Statement delegate;

    private final ProxyContext context;

    @Override
    public boolean execute(String sql) throws SQLException {
        trackStatementExecution(sql);
        return delegate.execute(sql);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        trackStatementExecution(sql);
        return delegate.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        trackStatementExecution(sql);
        return delegate.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        trackStatementExecution(sql);
        return delegate.execute(sql, columnNames);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        trackStatementExecution(sql);
        return delegate.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        trackStatementExecution(sql);
        return delegate.executeUpdate(sql);
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        trackStatementExecution(sql);
        return delegate.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        trackStatementExecution(sql);
        return delegate.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        trackStatementExecution(sql);
        return delegate.executeUpdate(sql, columnNames);
    }

    private void trackStatementExecution(String sql) {
        if (context.isRecording()) {
            context.getStateListener().statementExecuted(sql);
        }
    }

    private interface StatementOverwrite {

        ResultSet executeQuery(String sql) throws SQLException;

        boolean execute(String sql) throws SQLException;

        boolean execute(String sql, int autoGeneratedKeys) throws SQLException;

        boolean execute(String sql, int columnIndexes[]) throws SQLException;

        boolean execute(String sql, String columnNames[]) throws SQLException;

        int executeUpdate(String sql) throws SQLException;

        int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException;

        int executeUpdate(String sql, int columnIndexes[]) throws SQLException;

        int executeUpdate(String sql, String columnNames[]) throws SQLException;
    }
}