package com.twocater.daocodegen.codegen.dao.sql;

import java.util.Arrays;

public class SqlStatement {

	public static final String SEPARATOR = " ";
	public static final String MORE_LIKE = "%";
	public static final String ONE_LIKE = "_";
	private StringBuilder sql = new StringBuilder();

	public SqlStatement sql(int i) {
		sql.append(SEPARATOR).append(i);
		return this;
	}

	public SqlStatement sql(long l) {
		sql.append(SEPARATOR).append(l);
		return this;
	}

	public SqlStatement sql(float f) {
		sql.append(SEPARATOR).append(f);
		return this;
	}

	public SqlStatement sql(double d) {
		sql.append(SEPARATOR).append(d);
		return this;
	}

	public SqlStatement sql(String str) {
		sql.append(SEPARATOR).append(str);
		return this;
	}

	public SqlStatement literal(String str) {
		sql.append(SEPARATOR).append("'").append(str).append("'");
		return this;
	}

	public SqlStatement literal(String[] strs) {
		sql.append("(");
		String separator = "";
		for (String str : strs) {
			sql.append(separator).append("'").append(str).append("'");
			separator = ",";
		}
		sql.append(")");
		return this;
	}

	public SqlStatement literalLike(String str) {
		sql.append(SEPARATOR).append("`").append(MORE_LIKE).append(str).append(MORE_LIKE).append("`");
		return this;
	}

	public SqlStatement sql(Context context) {
		sql.append(context.toSql());
		return this;
	}

	public String toString() {
		return sql.toString();
	}

	public static void main(String[] args) {
		SqlStatement SqlStatement = new SqlStatement();
		System.out.println(SqlStatement.literal(new String[] { "1", "2", "3" }));
		System.out.println(Arrays.toString(new int[] { 1, 2, 3 }));
	}
}
