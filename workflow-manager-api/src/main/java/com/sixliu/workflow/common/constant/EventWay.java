package com.sixliu.workflow.common.constant;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author:MG01867
 * @date:2018年11月16日
 * @email:359852326@qq.com
 * @version:
 * @describe 事件方式
 */
public enum EventWay {

	/** 必须执行成功-共享当前事务 **/
	REQUIRED_SHARED_TRANSACTION,

	/** 必须执行成功-独立事务 **/
	REQUIRED_NEW_TRANSACTION,

	/** 必须执行成功-无事务 **/
	REQUIRED_NON_TRANSACTION,

	/** 非必须执行成功-独立事务 **/
	UNREQUIRED_NEW_TRANSACTION,

	/** 非必须执行成功-无事务 **/
	UNREQUIRED_NON_TRANSACTION;

	private final static Set<String> names;

	static {
		names = new HashSet<>(JobStatus.values().length);
		for (JobStatus item : JobStatus.values()) {
			names.add(item.name());
		}
	}

	public static boolean validate(String value) {
		return names.contains(value);
	}

	@MappedTypes(value = EventWay.class)
	@MappedJdbcTypes(value = { JdbcType.VARCHAR, JdbcType.CHAR })
	public static class Handler extends BaseTypeHandler<EventWay> {

		@Override
		public void setNonNullParameter(PreparedStatement ps, int i, EventWay parameter, JdbcType jdbcType)
				throws SQLException {
			ps.setString(i, parameter.name());
		}

		@Override
		public EventWay getNullableResult(ResultSet rs, String columnName) throws SQLException {
			String value = rs.getString(columnName);
			return EventWay.valueOf(value);
		}

		@Override
		public EventWay getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
			String value = rs.getString(columnIndex);
			return EventWay.valueOf(value);
		}

		@Override
		public EventWay getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
			String value = cs.getString(columnIndex);
			return EventWay.valueOf(value);
		}
	}
}
