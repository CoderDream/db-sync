package com.tgp.erp.newsync.syncform.syncmethod;

import com.tgp.erp.newsync.syncbuilder.SyncMethod;
import com.tgp.erp.newsync.syncform.vo.Field;
import com.tgp.erp.newsync.syncform.vo.Form;
import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class linyishu extends DefaultFormSyncMethod implements SyncMethod {


    @Override
    public void dealDate(ResultSet res, PreparedStatement sta, SyncTable syncTable) throws SQLException {
        setDAOConn(syncTable);
        List<Form> forms = new ArrayList<>();
        Form formByRes;
        while (res.next()) {
            formByRes = getFormByRes(res, 1);
            if (formByRes == null) {
                continue;
            }
            String 小区分1 = res.getString("小区分");
            Integer 小区分 = getItemTypeId(小区分1);
            formByRes.setItemTypeId(小区分);
            List<Field> fields = new ArrayList<Field>();
            //基本情報
            fields.add(new Field(9, 142, 1, res.getString("申請日時")));
            fields.add(new Field(7, 142, 2, res.getString("進捗")));
            fields.add(new Field(28, 142, 3, res.getString("申請者ID") + " " + res.getString("申請者名")));
            //申請者所属
            fields.add(new Field(29, 142, 4, getOrgInfo(res.getInt("申請者所属行ID"))));
            fields.add(new Field(30, 142, 5, res.getString("件名")));

            //案件情報
            fields.add(new Field(38, 26, 1, res.getString("クライアントコード")));
            //支付会社(option)

            fields.add(new Field(39, 26, 2, getOptionID(39, new String[]{res.getString("支払い金額")})));
            fields.add(new Field(8, 26, 3, res.getString("申請内容"), "テキストエリア"));
            fields.add(new Field(40, 26, 4, res.getString("期待効果"), "テキストエリア"));
            //添付資料(option)
            fields.add(new Field(41, 26, 5, getOptionID(41, new String[]{res.getString("添付資料")})));
            fields.add(new Field(42, 26, 6, res.getString("支払い金額")));
            //給与報酬計算(option)
            fields.add(new Field(43, 26, 7, getOptionID(43, new String[]{res.getString("給与報酬計算")})));
            //計上区分(options)
            fields.add(new Field(44, 26, 8, getOptionID(44, new String[]{res.getString("計上区分")})));
            fields.add(new Field(62, 26, 9, res.getString("開始決済対象月")));
            fields.add(new Field(63, 26, 11, res.getString("終了決済対象月")));

            //明细
            int sort_id = 1;
            for (int i = 1; i <= 20; i++) {
                if (res.getString("明細" + i) != null && res.getString("明細" + i) != "") {
                    Field group = new Field(4, 27, i);
                    List<Field> gFields = new ArrayList<>();
                    gFields.add(new Field(1, 27, 1, res.getString("明細" + i)));
                    gFields.add(new Field(2, 27, 2, res.getString("個数" + i)));
                    gFields.add(new Field(46, 27, 3, res.getString("小計" + i)));
                    gFields.add(new Field(45, 27, 4, res.getString("小計税込" + i)));
                    gFields.add(new Field(3, 27, 5, res.getString("単価" + i)));
                    //科目（option）
                    gFields.add(new Field(47, 27, 6, getOptionID(47, new String[]{res.getString("給与報酬計算")})));
                    //負担会社
                    gFields.add(new Field(48, 27, 7, getCompanyShortName(res.getObject("負担会社" + i))));
                    //負担部署
                    gFields.add(new Field(49, 27, 7, getOrgInfo(res.getObject("負担部署" + i))));
                    gFields.add(new Field(38, 27, 9, res.getString("CLコード" + i)));

                    group.setChildren(gFields);
                    fields.add(group);
                    sort_id++;
                }
            }
            fields.add(new Field(373, 27, sort_id, res.getString("合計税込")));

            //銀行情報
            fields.add(new Field(52, 132, 1, res.getString("支払先1") + " " + res.getString("支払先2")));
//			fields.add(new field);
            Field bankGroup = new Field(5, 132, 2);
            List<Field> bfields = new ArrayList<>();
            bfields.add(new Field(53, 132, 1, res.getString("銀行名")));
            bfields.add(new Field(54, 132, 2, res.getString("銀行コード")));
            bfields.add(new Field(55, 132, 3, res.getString("支店名")));
            bfields.add(new Field(56, 132, 4, res.getString("支店番号")));
            bfields.add(new Field(57, 132, 5, getOptionID(43, new String[]{res.getString("種別")})));
            bfields.add(new Field(58, 132, 6, res.getString("口座番号")));
            bfields.add(new Field(59, 132, 7, res.getString("口座名義")));
            bankGroup.setChildren(bfields);
            fields.add(bankGroup);
            fields.add(new Field(455, 132, 3, res.getString("支払条件締め日")));
            fields.add(new Field(456, 132, 4, res.getString("支払条件支払日")));
            fields.add(new Field(115, 132, 5, res.getString("支払方法")));
            fields.add(new Field(198, 132, 6, res.getString("申請備考"), "テキストエリア"));

            formByRes.setFields(fields);
            forms.add(formByRes);
            while(forms.size()>2000){
                syncInfo(forms);
                forms=new LinkedList<>();
                System.gc();
            }
        }
//        System.out.println(HandlerType);
        syncInfo(forms);
    }

    /**
     *
     */
}
