package com.tgp.erp.newsync.syncform.syncmethod.syndao;

import com.tgp.erp.newsync.syncform.vo.Flow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/28.
 */
public class FlowDAO {
    private Connection conn;

    public void setConnection(Connection connection) {
        this.conn = connection;
    }

    public void saveFormFlowU(Flow flow) throws SQLException {
        String sql = "replace into syn_form_flow (form_id,flow_id,syn_user_id,status,update_time) " +
                "values (?,?,?,?,?)";
        PreparedStatement pps = null;
        try {
            pps = conn.prepareStatement(sql);
            pps.setObject(1, flow.getFormId());
            pps.setObject(2, flow.getFlowId());
            pps.setObject(3, flow.getSynUserId());
            pps.setObject(4, flow.getStatus());
            pps.setObject(5, flow.getUpdateTime());
            pps.executeUpdate();
        } finally {
            if (pps != null) {
                pps.close();
            }

        }
    }

    public void saveFormFlow(Flow flow) throws SQLException {
        String sql = "replace into syn_form_flow (form_id,flow_Id,status,update_time) values (?,?,?,?)";
        PreparedStatement pps = null;
        try {
            pps = conn.prepareStatement(sql);
            pps.setObject(1, flow.getFormId());
            pps.setObject(2, flow.getFlowId());
            pps.setObject(3, flow.getStatus());
            pps.setObject(4, flow.getUpdateTime());
            pps.executeUpdate();
        } finally {
            if (pps != null) {
                pps.close();
            }

        }
    }
}
/*
     PreparedStatement pps = null;
        ResultSet res = null;
      try {
        } finally {
            if (res != null) {
                res.close();
            }
            if (pps != null) {
                pps.close();
            }

        }
 */