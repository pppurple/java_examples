package junit.db;

import org.dbunit.AbstractDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.junit.internal.Classes;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by pppurple on 2016/06/23.
 */
public abstract class DbUnitTester extends AbstractDatabaseTester implements TestRule {
    private final String connectionUrl;
    private final String username;
    private final String password;

    public DbUnitTester(String driverClass, String connectionUrl) {
        this(driverClass, connectionUrl, null, null);
    }

    public DbUnitTester(String driverClass, String connectionUrl, String username, String password) {
        this(driverClass, connectionUrl, username, password, null);
    }

    public DbUnitTester(String driverClass, String connectionUrl, String username, String password, String schema) {
        super(schema);
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        assertNotNullNorEmpty("driverClass", driverClass);
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public IDatabaseConnection getConnection() throws Exception {
        Connection conn = null;
        if (username == null && password == null) {
            conn = DriverManager.getConnection(connectionUrl);
        } else {
            conn = DriverManager.getConnection(connectionUrl, username, password);
        }
        DatabaseConnection dbConnection = new DatabaseConnection(conn, getSchema());
        DatabaseConfig config = dbConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        return dbConnection;
    }

    protected void executeQuery(String sql) throws Exception {
        Connection conn = getConnection().getConnection();
        conn.createStatement().execute(sql);
        conn.commit();
        conn.close();
    }

    protected void before() throws Exception{

    }

    protected void after() throws Exception {

    }

    abstract protected IDataSet createDataSet() throws Exception;

    @Override
    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                setDataSet(createDataSet());
                onSetup();
                try {
                    statement.evaluate();
                } finally {
                    try {
                        after();
                    } finally {
                        onTearDown();
                    }
                }
            }
        };
    }
}
