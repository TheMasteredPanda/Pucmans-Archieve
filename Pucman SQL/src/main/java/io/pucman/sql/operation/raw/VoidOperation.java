package io.pucman.sql.operation.raw;

import io.pucman.common.exception.DeveloperException;
import io.pucman.sql.database.Database;
import io.pucman.sql.operation.DatabaseStatement;
import io.pucman.sql.util.OperationUtil;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Used in other predefined operations. This will build and execute a statement that is not expecting
 * a ResultSet.
 */
public class VoidOperation extends DatabaseStatement<Void>
{
    private String query;
    private Object[] values;

    private VoidOperation(Database database)
    {
        super(database);
    }

    public static VoidOperation create(Database database)
    {
        return new VoidOperation(database);
    }

    public VoidOperation query(String query)
    {
        this.query = query;
        return this;
    }

    public VoidOperation values(Object... values)
    {
        this.values = values;
        return this;
    }

    @Override @SneakyThrows
    protected PreparedStatement construct()
    {
        PreparedStatement statement = getConnection().prepareStatement(query);

        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            statement.setObject(i + 1, value);
        }

        return statement;
    }

    @Override
    public Void async()
    {
        PreparedStatement statement = construct();

        CompletableFuture.runAsync(() -> {
            try {
                statement.execute();
            } catch (SQLException e) {
                throw new DeveloperException(e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                OperationUtil.close(statement, getConnection());
            }
        }, getService());

        return null;
    }

    @Override
    public Void sync()
    {
        CountDownLatch latch = new CountDownLatch(1);

        getService().submit(() -> {
            PreparedStatement statement = this.construct();

            try {
                statement.execute();
            } catch (SQLException e) {
                throw new DeveloperException(e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                OperationUtil.close(statement, this.getConnection());
                latch.countDown();
            }

        });

        try {
            latch.await(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}