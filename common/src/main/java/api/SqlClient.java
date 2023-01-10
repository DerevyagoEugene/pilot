package api;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import resources.PropertiesManager;
import utility.Logger;
import java.sql.*;

import static encryptor.EncryptionFunctions.decryptValue;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;
import static utility.FileHelper.getMainResourceFileByName;

public class SqlClient {

    private static SqlClient sqlClient = null;

    private final Session session;

    private final PropertiesManager propertiesManager = new PropertiesManager("sql.properties");

    private final Logger logger = Logger.getInstance();

    private SqlClient() {
        JSch jsch = new JSch();
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        try {
            jsch.addIdentity(getMainResourceFileByName("dfundak.ppk").getAbsolutePath());
            String host = decryptValue(propertiesManager.getProperty("jumpserver.host"));
            session = jsch.getSession(
                    decryptValue(propertiesManager.getProperty("jumpserver.name")),
                    host,
                    22
            );
            session.setConfig(config);
            logger.info(String.format("Connecting to the remote machine via jumpserver by url '%s'", host));
            session.connect();
            session.setPortForwardingL(
                    parseInt(decryptValue(propertiesManager.getProperty("remote.port"))),
                    decryptValue(propertiesManager.getProperty("remote.host")),
                    parseInt(decryptValue(propertiesManager.getProperty("remote.port")))
            );
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlClient getInstance() {
        if (isNull(sqlClient)) {
            sqlClient = new SqlClient();
        }
        return sqlClient;
    }

    public void terminate() {
        session.disconnect();
    }

    private Connection getConnection() {
        Connection con;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String dbUrl = decryptValue(propertiesManager.getProperty("db.url"));
            logger.info(String.format("Connecting to DB by url '%s'", dbUrl));
            con = DriverManager.getConnection(
                    dbUrl,
                    decryptValue(propertiesManager.getProperty("db.username")),
                    decryptValue(propertiesManager.getProperty("db.password"))
            );
            DbUtils.loadDriver("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public <T> T makeRequestAndGet(String sqlQuery, ResultSetHandler<T> rsh) {
        logger.info("DB Request: " + sqlQuery);
        T response;
        try (Connection connection = getConnection()) {
            response = new QueryRunner().query(connection, sqlQuery, rsh);
            logger.info("Closing DB connection...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
