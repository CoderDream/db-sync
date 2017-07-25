package com.tgp.erp.newsync.util;

import com.tgp.erp.newsync.vo.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by reph on 2017/6/16.
 * 查询同步信息数据库的工具类
 */
public class ConnectionUtils {
    static Connection conn;

    private ConnectionUtils() {
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            synchronized (ConnectionUtils.class) {
                if (conn == null) {
                    Properties config = Utils.getConfig();
                    String url = config.getProperty("db.info.mysql.url");
                    String user = config.getProperty("db.info.mysql.user");
                    String pwd = config.getProperty("db.info.mysql.pwd");
                    String dbname = config.getProperty("db.info.mysql.dbname");
                    conn = new DataBase(url, user, pwd, "mysql", dbname).getConn();
                }
            }
        }
        return conn;
    }
}
