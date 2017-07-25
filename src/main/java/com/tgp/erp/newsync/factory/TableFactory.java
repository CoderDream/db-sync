package com.tgp.erp.newsync.factory;

import com.tgp.erp.newsync.fieldbuilder.FieldBuilder;
import com.tgp.erp.newsync.vo.SyncTable;
import com.tgp.erp.newsync.sqlbuilder.SqlBuilder;
import com.tgp.erp.newsync.syncbuilder.SyncMethod;
import com.tgp.erp.newsync.util.Utils;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by reph on 2017/6/16.
 */
public class TableFactory {
    private static Properties tableType= Utils.tableType;
    private static final String SRCSQLTYPE="srcbuilder.";
    private static final String DESTSQLTYPE="destbuilder.";
    private static final String METHODTYPE="syncmethod.";
    private static final String FIELDTYPE="fieldtype.";

    public static SyncTable getInstance(String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        SqlBuilder srcBuilder= (SqlBuilder) Class.forName(tableType.getProperty(SRCSQLTYPE+type)).newInstance();
        SqlBuilder destBuilder= (SqlBuilder) Class.forName(tableType.getProperty(DESTSQLTYPE+type)).newInstance();
        SyncMethod syncMethod= (SyncMethod) Class.forName(tableType.getProperty(METHODTYPE+type)).newInstance();
        FieldBuilder fieldBuilder= (FieldBuilder) Class.forName(tableType.getProperty(FIELDTYPE+type)).newInstance();
        SyncTable table=new SyncTable();
        table.setDestBuilder(destBuilder);
        table.setSrcBuilder(srcBuilder);
        table.setSync(syncMethod);
        table.setFieldBuilder(fieldBuilder);
        return table;
    }

}
