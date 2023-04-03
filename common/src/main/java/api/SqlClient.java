package api;

import com.jakewharton.fliptables.FlipTableConverters;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import resource.PropertiesManager;
import utility.Logger;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static constatnt.Constants.PPK_PLACEHOLDER;
import static constatnt.Constants.RETURN_NEW_LINE;
import static encryptor.EncryptionFunctions.decryptValue;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

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
            String content = System.getProperty("private_key");
            jsch.addIdentity(
                    "dfundak",
                    content.replace(PPK_PLACEHOLDER, RETURN_NEW_LINE).getBytes(StandardCharsets.UTF_8),
                    null,
                    null
            );
            String host = decryptValue(propertiesManager.getProperty("jumpserver.host"));

            logger.info(String.format("Connecting to the remote machine via jumpserver by url '%s'", host));

            session = jsch.getSession(
                    decryptValue(propertiesManager.getProperty("jumpserver.name")),
                    host,
                    22
            );
            session.setConfig(config);
            session.connect();
            session.setPortForwardingL(
                    parseInt(decryptValue(propertiesManager.getProperty("remote.port"))),
                    decryptValue(propertiesManager.getProperty("remote.host")),
                    parseInt(decryptValue(propertiesManager.getProperty("remote.port")))
            );
        } catch (JSchException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public static SqlClient getInstance() {
        if (isNull(sqlClient)) sqlClient = new SqlClient();

        return sqlClient;
    }

    public void terminate() {
        session.disconnect();
    }

    private Connection getConnection2() {
        String dbUrl = decryptValue(propertiesManager.getProperty("db.url"));

        logger.info(String.format("Connecting to DB by url '%s'", dbUrl));

        return new Sql2o(
                dbUrl,
                decryptValue(propertiesManager.getProperty("db.username")),
                decryptValue(propertiesManager.getProperty("db.password"))
        ).open();
    }

    public <T> List<T> makeRequestAndFetch(String sqlQuery, Class<T> clazz) {
        logger.info("DB Request:\n" + sqlQuery);

        List<T> response;

        try (Query query = getConnection2().createQuery(sqlQuery)) {
            response = query.executeAndFetch(clazz);
        }
        if (isNull(response)) throw new Sql2oException("Response is NULL");

        logger.infoLn(FlipTableConverters.fromIterable(response, clazz));
        logger.info("Closing DB connection...");

        return response;
    }
}
