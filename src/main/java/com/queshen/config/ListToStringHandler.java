package com.queshen.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
 **/
public class ListToStringHandler  extends BaseTypeHandler<List> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List list, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(list));
    }

    @Override
    public List getNullableResult(ResultSet resultSet, String s) throws SQLException {
        JSONArray jsonArray = JSONArray.parseArray( resultSet.getString(s));
        return jsonArray;
    }

    @Override
    public List getNullableResult(ResultSet resultSet, int i) throws SQLException {
        JSONArray jsonArray = JSONArray.parseArray( resultSet.getString(i));
        return jsonArray;
    }

    @Override
    public List getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        JSONArray jsonArray = JSONArray.parseArray( callableStatement.getString(i));
        return jsonArray;
    }
}
