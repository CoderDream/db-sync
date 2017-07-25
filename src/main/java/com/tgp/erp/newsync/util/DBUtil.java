package com.tgp.erp.newsync.util;

import com.tgp.erp.newsync.vo.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by reph on 2017/6/16.
 * 建立数据库连接工具类
 */
public class DBUtil {
    private final static String DRIVER_TYPE = "class.";
    private final static String URL = "url.";
    private final static Properties sqlrule = Utils.getSqlrule();

    public static Connection buildConnection(DataBase dateBase) throws ClassNotFoundException, SQLException {
        String driverPath = sqlrule.getProperty(DRIVER_TYPE + dateBase.getDbType());
        Class.forName(driverPath);
        String url = sqlrule.getProperty(URL + dateBase.getDbType());
        url = url.replaceAll("#url", dateBase.getUrl()).replaceAll("#user", dateBase.getUser()).replaceAll("#pwd", dateBase.getPassword())
                .replaceAll("#dbname", dateBase.getdbname());
        return DriverManager.getConnection(url);
    }

    public static Connection buildConnection(String url,String DBType) throws SQLException, ClassNotFoundException {
        String driverPath = sqlrule.getProperty(DRIVER_TYPE + DBType);
        Class.forName(driverPath);
        return DriverManager.getConnection(url);
    }

}
