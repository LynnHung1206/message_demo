package com.lynn.message_demo.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @Author: Lynn on 2024/11/28
 */
public class LongToTimestampTypeHandler extends BaseTypeHandler<Long> {
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
    // 将 long 转换为 Timestamp
    ps.setTimestamp(i, new Timestamp(parameter));
  }

  @Override
  public Long getNullableResult(ResultSet rs, String columnName) throws SQLException {
    // 将 Timestamp 转换为 long
    Timestamp timestamp = rs.getTimestamp(columnName);
    return timestamp != null ? timestamp.getTime() : null;
  }

  @Override
  public Long getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnIndex);
    return timestamp != null ? timestamp.getTime() : null;
  }

  @Override
  public Long getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
    Timestamp timestamp = cs.getTimestamp(columnIndex);
    return timestamp != null ? timestamp.getTime() : null;
  }

}
