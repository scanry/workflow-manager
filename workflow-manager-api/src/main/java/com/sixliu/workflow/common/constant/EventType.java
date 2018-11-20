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
 * @author: sixliu
 * @email: 359852326@qq.com
 * @date: 2018年8月20日 下午7:52:16
 * @version V1.0
 * @Description:流程任务事件类型,监听事件的listener都是独立运行
 */
public enum EventType {

	/**
	 * 已创建-事件类型 CREATED,
	 * 
	 * 已取消-事件类型 CANCELED,
	 * 
	 * 已通过-事件类型 PASSED,
	 * 
	 * 已拒绝-事件类型 REJECTED,
	 * 
	 * 已冻结-事件类型 FROZEN;
	 */
	/** 流程任务创建 **/
	CREATED,

	/** 流程审批通过 **/
	PASSED,

	/** 流程任务拒绝 **/
	REJECTED,

	/** 流程任务驳回 **/
	OVERRULED,

	/** 流程任务转移 **/
	TRANSFERRED;

	private final static Set<String> names;

	static {
		names = new HashSet<>(EventType.values().length);
		for (EventType item : EventType.values()) {
			names.add(item.name());
		}
	}

	public static boolean validate(String value) {
		return names.contains(value);
	}

	@MappedTypes(value = EventType.class)
	@MappedJdbcTypes(value = { JdbcType.VARCHAR, JdbcType.CHAR })
	public static class TaskStatusHandler extends BaseTypeHandler<EventType> {

		@Override
		public void setNonNullParameter(PreparedStatement ps, int i, EventType parameter, JdbcType jdbcType)
				throws SQLException {
			ps.setString(i, parameter.name());
		}

		@Override
		public EventType getNullableResult(ResultSet rs, String columnName) throws SQLException {
			String value = rs.getString(columnName);
			return valueOf(value);
		}

		@Override
		public EventType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
			String value = rs.getString(columnIndex);
			return valueOf(value);
		}

		@Override
		public EventType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
			String value = cs.getString(columnIndex);
			return valueOf(value);
		}

	}

}
