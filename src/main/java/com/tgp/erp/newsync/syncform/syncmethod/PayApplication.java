package com.tgp.erp.newsync.syncform.syncmethod;

import com.tgp.erp.newsync.syncbuilder.SyncMethod;
import com.tgp.erp.newsync.syncform.vo.Field;
import com.tgp.erp.newsync.syncform.vo.Form;
import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayApplication extends DefaultFormSyncMethod implements SyncMethod{


	@Override
	public void dealDate(ResultSet res, PreparedStatement sta, SyncTable syncTable) throws SQLException {
		setDAOConn(syncTable);
		List<Form> forms = new ArrayList<>();
		while (res.next()) {
			Form formByRes = getFormByRes(res,1);
			List<Field> fields = new ArrayList<Field>();
			//基本情報
			fields.add(new Field(9, 142, 1, res.getString("申請日時")));
			fields.add(new Field(7, 142, 2, res.getString("進捗")));
			fields.add(new Field(28, 142, 3, res.getString("申請者ID")+" "+res.getString("申請者名")));
			//申請者所属
			fields.add(new Field(29, 142, 4, getOrgInfo(res.getInt("申請者所属行ID"))));
			fields.add(new Field(30, 142, 5, res.getString("件名")));
			
			//支払情報
			fields.add(new Field(39, 76, 1, getOptionID(39, new String[]{res.getString("支払い金額")})));
			fields.add(new Field(8, 76, 2, res.getString("申請内容"), "テキストエリア"));
			fields.add(new Field(65, 76, 3, getOptionID(65, new String[]{res.getString("請求書有無")})));
			//添付資料(option)
			fields.add(new Field(41, 76, 4, getOptionID(41, new String[]{res.getString("添付資料")})));
			fields.add(new Field(66, 76, 5, res.getString("概算支出金額")));
			fields.add(new Field(42, 76, 6, res.getString("支払い金額")));
			//計上区分(options)
			fields.add(new Field(44, 76, 7, getOptionID(44, new String[]{res.getString("計上区分")})));
			fields.add(new Field(67, 76, 8, res.getString("費用計上年")+"/"+res.getString("費用計上月")));
			//明细
			int sort_id = 1;
			for(int i=1;i<=20;i++){
				if(res.getString("明細"+i)!=null && res.getString("明細"+i)!=""){
					Field group = new Field(4, 30, i);
					List<Field> gFields = new ArrayList<>();
					gFields.add(new Field(1, 30, 1, res.getString("明細"+i)));
					gFields.add(new Field(2, 30, 2, res.getString("個数"+i)));
					gFields.add(new Field(46, 30, 3, res.getString("小計"+i)));
					gFields.add(new Field(45, 30, 4, res.getString("小計税込"+i)));
					gFields.add(new Field(3, 30, 5, res.getString("単価"+i)));
					//科目（option）
					gFields.add(new Field(47, 30, 6, getOptionID(47, new String[]{res.getString("給与報酬計算")})));
					//負担会社
					gFields.add(new Field(48, 30, 7, getCompanyShortName(res.getInt("負担会社"+i))));
					//負担部署
					gFields.add(new Field(49, 30, 7, getOrgInfo(res.getInt("負担部署"+i))));
					gFields.add(new Field(38, 30, 9, res.getString("CLコード"+i)));
					
					group.setChildren(gFields);
					fields.add(group);
					sort_id++;
				}
			}
			fields.add(new Field(373, 30, sort_id, res.getString("合計税込")));
			
			//銀行情報
			fields.add(new Field(117, 28, 1, res.getString("支払先コード")));
			fields.add(new Field(52, 28, 2, res.getString("支払先1")+" "+res.getString("支払先2")));
//			fields.add(new field);
			Field bankGroup = new Field(5, 28, 3);
			List<Field> bfields= new ArrayList<>();
			bfields.add(new Field(53, 28, 1, res.getString("銀行名")));
			bfields.add(new Field(54, 28, 2, res.getString("銀行コード")));
			bfields.add(new Field(55, 28, 3, res.getString("支店名")));
			bfields.add(new Field(56, 28, 4, res.getString("支店番号")));
			bfields.add(new Field(57, 28, 5, getOptionID(43,new String[]{res.getString("種別")})));
			bfields.add(new Field(58, 28, 6, res.getString("口座番号")));
			bfields.add(new Field(59, 28, 7, res.getString("口座名義")));
			bankGroup.setChildren(bfields);
			fields.add(bankGroup);
			fields.add(new Field(462, 28, 4, res.getString("支払条件支払日")));
			fields.add(new Field(115, 28, 5, res.getString("支払方法")));
			fields.add(new Field(61, 28, 6, res.getString("申請備考"), "テキストエリア"));
			
			formByRes.setFields(fields);
			forms.add(formByRes);
		}
		syncInfo(forms);
	}

	/**
	 * 
	 */
}
