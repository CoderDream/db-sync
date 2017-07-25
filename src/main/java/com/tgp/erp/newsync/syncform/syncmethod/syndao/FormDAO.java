package com.tgp.erp.newsync.syncform.syncmethod.syndao;

import com.tgp.erp.newsync.syncform.vo.Form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/28.
 */
public class FormDAO {
    Connection conn;

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void saveForm(Form form) throws SQLException {
        String sql = "replace into syn_form_head " +
                "(form_no,title,user_id,form_type_id,item_type_id,event_name,state,status,update_time,entry_time,org_id) " +
                "values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pps=null;
        ResultSet res=null;
        try {
            pps = conn.prepareStatement(sql);
            pps.setObject(1, form.getFormNo());
            pps.setObject(2, form.getTitle());
            pps.setObject(3,form.getUserId());
            pps.setObject(4, form.getFormTypeId());
            pps.setObject(5, form.getItemTypeId());
            pps.setObject(6, form.getEventName());
            pps.setObject(7, form.getState());
            pps.setObject(8, form.getStatus());
            pps.setObject(9, form.getUpdateTime());
            pps.setObject(10, form.getEntryTime());
            pps.setObject(11, form.getOrg().getId());
            pps.executeUpdate();
            res = pps.getGeneratedKeys();
            if(res.next()){
                int ID = res.getInt(1);
                form.setFormId(ID);
            }
        } finally {
            if(pps!=null){
                pps.close();
            }
            if(res!=null){
                res.close();
            }

        }
    }
}
