package com.tgp.erp.newsync.syncform.syncmethod;

import com.tgp.erp.newsync.syncform.syncmethod.syndao.FieldDAO;
import com.tgp.erp.newsync.syncform.syncmethod.syndao.FlowDAO;
import com.tgp.erp.newsync.syncform.syncmethod.syndao.FormDAO;
import com.tgp.erp.newsync.syncform.vo.*;
import com.tgp.erp.newsync.util.Utils;
import com.tgp.erp.newsync.vo.SyncTable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by reph on 2017/6/26.
 */
public class DefaultFormSyncMethod {
	private final static Map<String, Integer> ITEMTYPE = new HashMap<>();
	private final static Map<String, Integer> STATUS = new HashMap<>();
	private final static Map<String, Integer> HANDLER = new HashMap<>();
	private final static Map<String, Integer> ROLE = new HashMap<>();
	private final static Map<Integer, Map<String, Integer>> OPTIONS = new HashMap<>();
	private final static Map<Integer, String> ORGINFO = new HashMap<>();
	private final static Map<Integer, String> COMPANYINFO = new HashMap<>();
	private final static Properties CONFIGE = Utils.config;
	private final FormDAO formDAO = new FormDAO();
	private final FieldDAO fieldDAO = new FieldDAO();
	private final FlowDAO flowDAO = new FlowDAO();
	private final static Logger logger = LoggerFactory
			.getLogger(DefaultFormSyncMethod.class);
	protected final static Set<String> HandlerType = new HashSet<>();

	private Connection conn = null;

	static {
		// try {
		// Connection groupDBConn=null;
		// groupDBConn = DBUtil.buildConnection(CONFIGE.getProperty("GroupDB.url"),
		// "mysql");
		// Connection tpgerpConn=null;
		// tpgerpConn = DBUtil.buildConnection(CONFIGE.getProperty("Tpgerp.url"),
		// "mysql");
		// setItemType(groupDBConn, tpgerpConn);
		// setRole(tpgerpConn);
		// setOptions(tpgerpConn);
		// setOrgInfo(tpgerpConn);
		// setCompanyinfo(tpgerpConn);
		// setHandler(tpgerpConn);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// }

		STATUS.put("完了", 1);
		STATUS.put("取下	", 2);
		STATUS.put("差戻	", 3);
		STATUS.put("否認", 4);
		STATUS.put("確認待ち", 5);

		HANDLER.put("回収済", 1);
		HANDLER.put("確認", 2);
		HANDLER.put("確認済", 3);
		HANDLER.put("確認中", 4);
		HANDLER.put("差戻", 5);
		HANDLER.put("再報告待ち", 6);
		HANDLER.put("支払済", 7);
		HANDLER.put("処理済", 8);
		HANDLER.put("処理中", 9);
		HANDLER.put("承認", 10);
		HANDLER.put("精算済", 11);
		HANDLER.put("否認", 12);
		HANDLER.put("保管済", 13);
		HANDLER.put("保留", 14);
		HANDLER.put("報告受済", 15);
	}

	/**
	 * @param res
	 *          res.next()的类.
	 * @param formTypeId
	 *          表单类型
	 * @return
	 * @throws SQLException
	 */
	public Form getFormByRes(ResultSet res, int formTypeId) throws SQLException {
		// String flowString = res.getString("承認経過詳細");
		// if (null == flowString || "".equals(flowString)) {
		// return null;
		// }
		Form form = new Form();
		form.setFormTypeId(formTypeId);
		form.setUserId(stringToInt(res.getString("申請者ID")));
		form.setFormNo(res.getString("ID"));
		form.setTitle(res.getString("件名"));
		form.setEntryTime(res.getDate("申請日時"));
		String proxUserId = res.getString("代理者ID");
		User entryUser = null;
		if (!(null == proxUserId || "".equals(proxUserId))) {
			entryUser = new User(Integer.valueOf(proxUserId), res.getInt("代理者所属行ID"),
					res.getString("代理者名"));
		}
		form.setEntryUser(entryUser);
		form.setOrg(new Organization(res.getInt("申請者所属行ID")));
		form.setUserId(stringToInt(res.getString("申請者ID")));
		// linyishu需要.直接复制就可以;
		// form.setItemTypeId(ITEMTYPE.get(res.getString("小区分")));
		form.setStatus(getStatus(res.getString("状態")));
		form.setUpdateTime(res.getDate("最終更新日時"));
		String finalUserId = res.getString("最終更新者");
		User updateUser = null;
		finalUserId = finalUserId.replaceAll(" ", "");
		if (!(finalUserId == null || "".equals(finalUserId))) {
			updateUser = new User(Integer.valueOf(finalUserId.substring(0, 5)));
		}
		form.setUpdateUser(updateUser);
		// try {
		// List<Flow> flowsByRes = getFlowsByString(flowString);
		// if(flowsByRes==null){
		// return null;
		// }
		// form.setFlows(flowsByRes);
		// } catch (Exception e) {
		// System.out.println(form.getFormNo());
		// return null;
		// }
		return form;
	}

	public void syncInfo(List<Form> forms) throws SQLException {
		try {
			for (Form form : forms) {
				syncInfo(form);
				conn.commit();
			}
		} catch (SQLException e) {
			conn.rollback();
		}

	}

	/**
	 * 数据存储
	 *
	 * @param form
	 * @throws SQLException
	 */
	private void syncInfo(Form form) throws SQLException {
		formDAO.saveForm(form);
		List<Field> fields = form.getFields();
		// List<Flow> flows = form.getFlows();
		for (Field field : fields) {
			field.setFormId(form.getFormId());
			if (field.getChildren() != null) {
				fieldDAO.saveFormGroup(field);
				List<Field> children = field.getChildren();
				for (Field child : children) {
					child.setFormId(form.getFormId());
					child.setFormGroupId(field.getFormGroupId());
					// 判断保存类型
					if (child.getOptions() != null && child.getOptions().size() != 0) {
						for (int index = 0; index < child.getOptions().size(); index++) {
							child.setOptionId(child.getOptions().get(index).getOptionId());
							fieldDAO.saveFormFieldOption(child);
						}
					} else if ("テキストエリア".equals(child.getEditorType())) {
						fieldDAO.saveFormFieldText(child);
					} else if ("ファイルアップロード".equals(child.getEditorType())) {
						child.setEntryId(form.getUserId());
						fieldDAO.saveFormFieldFile(child);
					} else {
						fieldDAO.saveFormField(child);
					}
				}
			} else if (field.getOptions() != null && field.getOptions().size() != 0) {
				for (int index = 0; index < field.getOptions().size(); index++) {
					field.setOptionId(field.getOptions().get(index).getOptionId());
					fieldDAO.saveFormFieldOption(field);
				}
			} else if ("テキストエリア".equals(field.getEditorType())) {
				fieldDAO.saveFormFieldText(field);
			} else if ("ファイルアップロード".equals(field.getEditorType())) {
				field.setEntryId(form.getUserId());
				fieldDAO.saveFormFieldFile(field);
			} else {
				fieldDAO.saveFormField(field);
			}
		}
		// for (Flow flow : flows) {
		// flow.setFormId(form.getFormId());
		// //判断是否是承认类型，如果是，就将承认者放入synUser中
		// if (flow.getHandlerType() == 25) {
		// flowDAO.saveFormFlowU(flow);
		// } else {
		// flowDAO.saveFormFlow(flow);
		// }
		//
		// }
	}

	/**
	 * 设置承认经过,如果返回list=0则为无效数据.
	 *
	 * @return
	 * @throws SQLException
	 */
	public List<Flow> getFlowsByString(String flowString) throws SQLException {
		List<Flow> flows = new ArrayList<>();
		Flow flow = null;
		String[] flowsArray = flowString.split("\\|");
		String[] flowArray = null;
		try {
			for (int i = 0; i < flowsArray.length; i++) {
				// 人事受付,HA,010143,西山 あかね,受付済,2012/12/29 20:52:31,
				if (flowsArray[i].indexOf("不要") > -1) {
					continue;
				}
				flowArray = flowsArray[i].split(",");
				flow = new Flow();

				Integer flowId = ROLE.get(flowArray[0]);
				if (flowId == null) {
					return null;
				}
				flow.setFlowId(flowId);

				if (!(flowArray[2] == null || "".equals(flowArray[2]))) {
					flow.setSynUserId(Integer.valueOf(flowArray[2]));
				}
				HandlerType.add(flowArray[4]);
				if (flowArray.length <= 4) {
					flow.setHandlerType(0);
				} else {
					String[] split = flowArray[4].split("に");
					Integer handlerType = HANDLER.get(split[split.length - 1]);
					if (handlerType == null) {
						return null;
					}
					flow.setHandlerType(handlerType);
				}
				if (flowArray.length > 5) {
					flow.setUpdateTime(flowArray[5]);
				}
				flows.add(flow);
			}
		} catch (Exception e) {
			throw e;
		}
		return flows;
	}

	protected List<Option> getOptionID(Integer fieldID, String[] optionValues) {
		LinkedList<Option> options = new LinkedList<>();
		for (int i = 0; i < optionValues.length; i++) {
			Integer optionID = OPTIONS.get(fieldID).get(optionValues[i]);
			options.add(new Option(optionID));
		}
		return options;
	}

	protected String getOrgInfo(Object orgID) {
		return ORGINFO.get(orgID);
	}

	protected String getCompanyShortName(Object companyID) {
		return COMPANYINFO.get(companyID);
	}

	/**
	 * 通过syncTable中的数据源设置存储的数据源
	 *
	 * @param syncTable
	 */
	protected void setDAOConn(SyncTable syncTable) throws SQLException {
		conn = syncTable.getDestDB().getConn();
		if (fieldDAO.getConn() == null) {
			conn.setAutoCommit(false);
			fieldDAO.setConnection(conn);
			formDAO.setConnection(conn);
			flowDAO.setConnection(conn);
		}

	}

	private static void setCompanyinfo(Connection tpgConn) throws SQLException {

		String sql = "SELECT c.id,c.short_name FROM mst_company AS c";
		Statement sta = null;
		ResultSet res = null;
		try {
			sta = tpgConn.createStatement();
			res = sta.executeQuery(sql);
			while (res.next()) {
				COMPANYINFO.put(res.getInt(1), res.getString(2));
			}
		} finally {
			if (res != null) {
				res.close();
			}
			if (sta != null) {
				sta.close();
			}
		}
	}

	/**
	 * 查询部署信息
	 *
	 * @param tpgConn
	 * @throws SQLException
	 */
	private static void setOrgInfo(Connection tpgConn) throws SQLException {
		String sql = "SELECT o.org_id,o.org_name,o.corp_code,o.org_code FROM mst_org AS o";
		Statement sta = null;
		ResultSet res = null;
		try {
			sta = tpgConn.createStatement();
			res = sta.executeQuery(sql);
			String value = null;
			String corp_code = "0";
			while (res.next()) {

				if (res.getInt(3) < 10) {
					corp_code = corp_code + res.getInt(3);
				} else {
					corp_code = res.getString(3);
				}
				value = corp_code + "-" + res.getString(4) + "/" + res.getString(2);
				ORGINFO.put(res.getInt(1), value);
			}
		} finally {
			if (res != null) {
				res.close();
			}
			if (sta != null) {
				sta.close();
			}
		}

	}

	/**
	 * 获取状态并记录状态不存在现有系统的状态名
	 *
	 * @param type
	 * @return
	 */
	private Integer getStatus(String type) {
		Integer value = STATUS.get(type);
		if (value == null) {
			return 0;
		}
		return value;
	}

	/**
	 * 职务权限与item_type的对应关系查询
	 *
	 * @param groupDBConnection
	 * @param tpgerpConnection
	 * @throws SQLException
	 */
	private static void setItemType(Connection groupDBConnection,
			Connection tpgerpConnection) throws SQLException {
		String groupSql = "SELECT a.ID,a.`大区分`,a.`小区分`,a.`枝番`,a.`子枝番` FROM `T_承認_職務権限` AS a ";
		String tpgerpSql = "SELECT syn_item_type.item_type_id,syn_item_type.item_type_code FROM syn_item_type";
		Statement groupSta = null;
		Statement tpgerpSta = null;
		ResultSet groupRes = null;
		ResultSet tpgerpRes = null;
		try {
			groupSta = groupDBConnection.createStatement();
			tpgerpSta = tpgerpConnection.createStatement();
			groupRes = groupSta.executeQuery(groupSql);
			tpgerpRes = tpgerpSta.executeQuery(tpgerpSql);
			HashMap<String, String> groupItemType = new HashMap<>();
			HashMap<String, Integer> tpgerpItemType = new HashMap<>();
			String value;
			StringBuilder keys;
			String v;
			while (groupRes.next()) {
				value = groupRes.getString(1);
				keys = new StringBuilder();
				if ((v = groupRes.getString(2)) != null && (!"".equals(v))) {
					keys.append(v);
				}
				if ((v = groupRes.getString(3)) != null && (!"".equals(v))) {
					keys.append("-" + v);
				}
				if ((v = groupRes.getString(4)) != null && (!"".equals(v))) {
					keys.append("-" + v);
				}
				if ((v = groupRes.getString(5)) != null && (!"".equals(v))) {
					keys.append("-" + v);
				}
				groupItemType.put(keys.toString(), value);
			}
			String key;
			Integer intValue;
			while (tpgerpRes.next()) {
				String tpgerpResString = tpgerpRes.getString(2);
				key = groupItemType.get(tpgerpResString);
				intValue = tpgerpRes.getInt(1);
				if (key == null) {
					System.out.println(key);
					System.out.println(tpgerpResString);
					groupItemType.get("D-15-①");
				}
				ITEMTYPE.put(key, intValue);
			}
		} finally {
			if (groupRes != null) {
				groupRes.close();
			}
			if (tpgerpRes != null) {
				tpgerpRes.close();
			}
			if (groupSta != null) {
				groupSta.close();
			}
			if (tpgerpSta != null) {
				tpgerpSta.close();
			}
		}
	}

	/**
	 * 选项数据关系
	 *
	 * @param tpgerpConn
	 */
	private static void setOptions(Connection tpgerpConn) throws SQLException {
		String sql = "SELECT o.field_id,o.field_value,o.option_id FROM syn_field_option AS o";
		Statement sta = tpgerpConn.createStatement();
		ResultSet res = sta.executeQuery(sql);
		Map<String, Integer> option;
		while (res.next()) {
			int fieldID = res.getInt(1);
			option = OPTIONS.get(fieldID);
			if (option == null) {
				option = new HashMap<>();
				OPTIONS.put(fieldID, option);
			}
			option.put(res.getString(2), res.getInt(3));
		}
	}

	private static void setHandler(Connection tpgerpConn) throws SQLException {
		String sql = "SELECT r.value,r.code FROM mst_list AS r WHERE r.type='handler_type'";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = tpgerpConn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				HANDLER.put(resultSet.getString(1), resultSet.getInt(2));
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	/**
	 * 权限关系数据
	 *
	 * @param tpgerpConn
	 * @throws SQLException
	 */
	private static void setRole(Connection tpgerpConn) throws SQLException {
		String sql = "SELECT r.role_id,r.item_name FROM syn_role AS r";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = tpgerpConn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				ROLE.put(resultSet.getString(2), resultSet.getInt(1));
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	public Integer stringToInt(String temp) {
		if ("".equals(temp) || temp == null) {
			return null;
		}
		return Integer.valueOf(temp);
	}

	protected Integer getItemTypeId(String formTypeId) {
		if (formTypeId == null || formTypeId.equals("")) {
			return 0;
		}
		return ITEMTYPE.get(Integer.valueOf(formTypeId));
	}

	@Test
	public void Test() throws SQLException, ClassNotFoundException {
		// Connection groupDBConn =
		// DBUtil.buildConnection(CONFIGE.getProperty("GroupDB.url"), "mssql");
		// Connection tpgerpConn =
		// DBUtil.buildConnection(CONFIGE.getProperty("Tpgerp.url"), "mysql");
		// setItemType(groupDBConn, tpgerpConn);
		System.out.println(ITEMTYPE);
	}
}
