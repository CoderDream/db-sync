package com.tgp.erp.newsync.vo;

import com.tgp.erp.newsync.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/15.
 */
public class DataBase {
    private Connection conn;
    private String url;
    private String user;
    private String password;
    private String dbType;
    private String dbName;

    public DataBase(String url, String user, String password, String dbType, String dbname) throws SQLException, ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dbType = dbType;
        this.dbName = dbname;
        conn = DBUtil.buildConnection(this);
    }

    public Connection getConn() {
        return conn;
    }

    public String getDbType() {
        return dbType;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getdbname() {
        return dbName;
    }
}
