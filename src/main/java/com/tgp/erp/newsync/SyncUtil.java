package com.tgp.erp.newsync;

import com.tgp.erp.newsync.util.ConnectionUtils;
import com.tgp.erp.newsync.util.Utils;
import com.tgp.erp.newsync.vo.DataBase;
import com.tgp.erp.newsync.vo.SyncDateBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author lockem
 *         OldSyncUtil
 *         2017年5月2日
 *         主入口
 */
public class SyncUtil {
    private static final Logger logger = LoggerFactory.getLogger(SyncUtil.class);
    private final String SELECTTEMP = "select * from #table";
    private Properties config = Utils.config;

    /**
     * 通过配置信息获取同步信息
     *
     * @return 需同步的数据库集合
     * @throws Exception
     */
    public List<SyncDateBase> getDataBases() throws Exception {
        List<SyncDateBase> list = new LinkedList<>();
        Connection conn = ConnectionUtils.getConnection();
        String sql = SELECTTEMP.replaceAll("#table", config.getProperty("db.dbinfo.name"));
        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery(sql);
        while (res.next()) {
            String dbid = res.getString("db_id");
            String srcurl = res.getString("src_dburl");
            String srcuser = res.getString("src_dbuser");
            String srcpwd = new String(Utils.decryptBASE64(res.getString("src_dbpwd")));
            String srctype = res.getString("src_dbtype");
            String srcdbname = res.getString("src_dbname");
            String desturl = res.getString("dest_dburl");
            String destuser = res.getString("dest_dbuser");
            String destpwd = new String(Utils.decryptBASE64(res.getString("dest_dbpwd")));
            String desttype = res.getString("dest_dbtype");
            String destdbname = res.getString("dest_dbname");
            boolean runable = res.getBoolean("runable");
            if (runable) {
                DataBase srcDB = new DataBase(srcurl,srcuser, srcpwd, srctype, srcdbname);
                DataBase destDB = new DataBase(desturl,destuser, destpwd, desttype, destdbname);
                list.add(new SyncDateBase(dbid, srcDB, destDB));
            } else {
                logger.info("db_id= " + dbid + " is not allow to sync");
            }

        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        SyncUtil sync = new SyncUtil();
        List<SyncDateBase> list = sync.getDataBases();
        for (SyncDateBase dataBases : list) {
            dataBases.sync();
        }
    }
}

