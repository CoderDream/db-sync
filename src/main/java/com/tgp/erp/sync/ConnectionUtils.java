package com.tgp.erp.sync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    /**
     * 存放映射信息的数据库connection单例
     */
    private static Properties config = Utils.config;
    private static volatile Connection conn = null;

    private ConnectionUtils() {
    }

    public static Connection getConn() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            synchronized (ConnectionUtils.class) {
                if (conn == null) {
                    String urltemp = Utils.sqlrule.getProperty("url.mysql");
                    String url = config.getProperty("db.info.mysql.url");
                    String user = config.getProperty("db.info.mysql.user");
                    String password = config.getProperty("db.info.mysql.pwd");
                    String dbname = config.getProperty("db.info.mysql.dbname");
                    url = urltemp.replaceAll("#url", url).replaceAll("#user", user).replaceAll("#pwd", password).replaceAll("#dbname", dbname);
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(url);
                }
            }
        }
        return conn;
    }

}
