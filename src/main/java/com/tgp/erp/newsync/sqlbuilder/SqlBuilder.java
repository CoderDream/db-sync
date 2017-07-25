package com.tgp.erp.newsync.sqlbuilder;

import com.tgp.erp.newsync.exception.NoTimeException;
import com.tgp.erp.newsync.vo.Table;
import com.tgp.erp.newsync.util.Utils;

import java.util.Properties;

/**
 * Created by reph on 2017/6/16.
 */
public interface SqlBuilder {
    Properties SQLRULE = Utils.getSqlrule();
    String DATETIME=Utils.DATETIME;
    String buildSql(Table table) throws NoTimeException;
}

