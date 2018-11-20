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
 * @describe 阶段类型
 */
public enum PhaseType {

	START,

	MIDWAY,

	END;

	private final static Set<String> names;

	static {
		names = new HashSet<>(PhaseType.values().length);
		for (PhaseType item : PhaseType.values()) {
			names.add(item.name());
		}
	}

	public static boolean validate(String value) {
		return names.contains(value);
	}

	@MappedTypes(value = JobStatus.class)
	@MappedJdbcTypes(value = { JdbcType.VARCHAR, JdbcType.CHAR })
	public static class Handler extends BaseTypeHandler<PhaseType> {

		@Override
		public void setNonNullParameter(PreparedStatement ps, int i, PhaseType parameter, JdbcType jdbcType)
				throws SQLException {
			ps.setString(i, parameter.name());
		}

		@Override
		public PhaseType getNullableResult(ResultSet rs, String columnName) throws SQLException {
			String value = rs.getString(columnName);
			return PhaseType.valueOf(value);
		}

		@Override
		public PhaseType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
			String value = rs.getString(columnIndex);
			return PhaseType.valueOf(value);
		}

		@Override
		public PhaseType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
			String value = cs.getString(columnIndex);
			return PhaseType.valueOf(value);
		}

	}
}
