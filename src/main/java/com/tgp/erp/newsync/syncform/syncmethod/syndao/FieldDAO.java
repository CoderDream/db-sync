package com.tgp.erp.newsync.syncform.syncmethod.syndao;

import com.tgp.erp.newsync.syncform.vo.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/28.
 */
public class FieldDAO {
	private Connection conn;

	public void setConnection(Connection connection) {
		this.conn = connection;
	}

	public Connection getConn() {
		return conn;
	}

	public void saveFormGroup(Field field) throws SQLException {
		String sql = "replace into syn_form_group (form_id,group_id,section_id,sort_id,update_time) values "
				+ "(?,?,?,?,?)";
		PreparedStatement pps = null;
		ResultSet res = null;
		try {
			pps = conn.prepareStatement(sql);
			pps.setObject(1, field.getFormId());
			pps.setObject(2, field.getGroupId());
			pps.setObject(3, field.getSectionId());
			pps.setObject(4, field.getSortId());
			pps.setObject(5, field.getEntryTime());
			pps.executeUpdate();
			res = pps.getGeneratedKeys();
			if (res.next()) {
				field.setFormGroupId(res.getInt(1));
			}
		} finally {
			if (res != null) {
				res.close();
			}
			if (pps != null) {
				pps.close();
			}

		}
	}

	public void saveFormFieldOption(Field child) throws SQLException {
		String sql = "replace into syn_form_field_option (form_group_id,form_id,field_id,section_id,sort_id,option_id,text,update_time,entry_time) values "
				+ "(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pps = null;
		try {
			pps = conn.prepareStatement(sql);
			pps.setObject(1, child.getFormGroupId());
			pps.setObject(2, child.getFormId());
			pps.setObject(3, child.getFieldId());
			pps.setObject(4, child.getSectionId());
			pps.setObject(5, child.getSortId());
			pps.setObject(6, child.getOptionId());
			pps.setObject(7, child.getFieldText());
			pps.setObject(8, child.getUpdateTime());
			pps.setObject(9, child.getEntryTime());
			pps.executeUpdate();
		} finally {
			if (pps != null) {
				pps.close();
			}

		}
	}

	public void saveFormFieldText(Field child) throws SQLException {
		String sql = "replace into syn_form_field_text (form_group_id,form_id,field_id,section_id,sort_id,field_value,update_time,entry_time) values "
				+ "(?,?,?,?,?,?,?,?)";
		PreparedStatement pps = null;
		ResultSet res = null;
		try {
			pps = conn.prepareStatement(sql);
			pps.setObject(1, child.getFormGroupId());
			pps.setObject(2, child.getFormId());
			pps.setObject(3, child.getFieldId());
			pps.setObject(4, child.getSectionId());
			pps.setObject(5, child.getSortId());
			pps.setObject(6, child.getFieldValue());
			pps.setObject(7, child.getUpdateTime());
			pps.setObject(8, child.getEntryTime());
			pps.executeUpdate();
		} finally {
			if (res != null) {
				res.close();
			}
			if (pps != null) {
				pps.close();
			}

		}
	}

	public void saveFormFieldFile(Field child) throws SQLException {
		String sql = "replace into syn_form_field_file(form_group_id,form_id,file_id,section_id,sort_id,status,update_time,entry_time,entry_id,field_id) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pps = null;
		ResultSet res = null;
		try {
			pps = conn.prepareStatement(sql);
			pps.setObject(1, child.getFormGroupId());
			pps.setObject(2, child.getFormId());
			pps.setObject(3, child.getFileId());
			pps.setObject(4, child.getSectionId());
			pps.setObject(5, child.getSortId());
			pps.setObject(6, child.getState());
			pps.setObject(7, child.getUpdateTime());
			pps.setObject(8, child.getEntryTime());
			pps.setObject(9, child.getFieldId());
			pps.executeUpdate();
		} finally {
			if (res != null) {
				res.close();
			}
			if (pps != null) {
				pps.close();
			}

		}
	}

	public void saveFormField(Field child) throws SQLException {
		String sql = "replace into syn_form_field (form_group_id,form_id,field_id,section_id,sort_id,field_value,update_time,entry_time) values "
				+ "(?,?,?,?,?,?,?,?)";
		PreparedStatement pps = null;
		ResultSet res = null;
		try {
			pps = conn.prepareStatement(sql);
			pps.setObject(1, child.getFormGroupId());
			pps.setObject(2, child.getFormId());
			pps.setObject(3, child.getFieldId());
			pps.setObject(4, child.getSectionId());
			pps.setObject(5, child.getSortId());
			pps.setObject(6, child.getFieldValue());
			pps.setObject(7, child.getUpdateTime());
			pps.setObject(8, child.getEntryTime());
			pps.executeUpdate();

		} finally {
			if (res != null) {
				res.close();
			}
			if (pps != null) {
				pps.close();
			}

		}
	}
}
