package com.tgp.erp.newsync.syncbuilder.methodimpl;

import com.tgp.erp.newsync.syncbuilder.SyncMethod;
import com.tgp.erp.newsync.vo.Field;
import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by reph on 2017/6/22.
 */
public class M2MSyncMethod implements SyncMethod {
    @Override
    public void dealDate(ResultSet res, PreparedStatement sta, SyncTable syncTable) throws SQLException {
        ResultSetMetaData metaData = res.getMetaData();
        int len = metaData.getColumnCount();
        int count=0;
        while (res.next()) {
            List<Field> fields = syncTable.getSrcTable().getFields();
            Map<String, Field> m2mField = new HashMap<>();
            //将多对多的列数据以 fieldname,field 的形式存入map中
            for (Field field : fields) {
                String M2M = field.getM2MField();
                if (M2M != null) {
                    m2mField.put(field.getFieldName(), field);
                }
            }
            List<Map<Integer, String[]>> m2mDate = new ArrayList<>();
            HashMap<Integer, String[]> temp;
            String m2mValue;
            int maxLen = 0;
            //遍历resultset
            for (int i = 1; i <= len; i++) {
                String columnName = metaData.getColumnName(i);
                Field field = m2mField.get(columnName);
                //将多对多的列分成数组并以(column,value[])的形式存放在map中
                if (field != null) {
                    m2mValue = res.getString(i);
                    if (m2mValue == null || m2mValue.equals("")) {
                        continue;
                    }
                    String[] split = m2mValue.split(field.getM2MField());
                    temp = new HashMap<>();
                    temp.put(i, split);
                    m2mDate.add(temp);
                    if (maxLen < split.length) {
                        maxLen = split.length;
                    }
                //将一般数据直接放入statement
                } else {
                    String value = res.getString(i);
                    if ("".equals(value)) {
                        value = null;
                    }
                    sta.setObject(i, value);
                }
            }
//          maxlen 表示总共会有几条数据被拆分
            for (int i = 0; i < maxLen; i++) {
                for (Map<Integer, String[]> map : m2mDate) {
                    Set<Integer> columnSet = map.keySet();
//                  通过columnSet中的column可以将一条statement 设置所有列的数据
                    for (Integer column : columnSet) {
                        String[] values = map.get(column);
                        //当数组长度小于maxlen,或者,则判定为null数据
                        if (i < values.length&&(!values[i].equals(""))) {
                            sta.setObject(column, values[i]);
                        } else {
                            sta.setObject(column, null);
                        }
                    }

                }
                sta.addBatch();
            }
            count+=maxLen;
            if(count>2000){
                sta.executeBatch();
                count=0;
            }
        }
        sta.executeBatch();
    }
}
