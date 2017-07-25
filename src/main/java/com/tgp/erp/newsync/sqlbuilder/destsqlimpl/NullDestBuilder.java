package com.tgp.erp.newsync.sqlbuilder.destsqlimpl;

import com.tgp.erp.newsync.exception.NoTimeException;
import com.tgp.erp.newsync.sqlbuilder.SqlBuilder;
import com.tgp.erp.newsync.vo.Table;

/**
 * Created by reph on 2017/6/28.
 */
public class NullDestBuilder implements SqlBuilder {
    @Override
    public String buildSql(Table table) throws NoTimeException {
        return "select 1";
    }
}
