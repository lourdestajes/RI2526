package uo.ri.cws.application.persistence.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

/**
 * Provides a template for JDBC operations, facilitating the execution of SQL
 * queries and updates. 

 * It simplifies connecting to a database, executing SQL commands, and 
 * processing results. 
 * 
 * It uses a fluent interface to chain method calls for setting SQL commands, 
 * binding parameters, and mapping ResultSet rows to domain objects. 
 *
 * <p>
 * Usage Explanation:
 * </p>
 * To utilize this class, instantiate it with your desired domain object type,
 * then use the fluent methods provided: 
 * - {@code forSql(String sql)} to specify the SQL query or update statement. 
 * - {@code forNamedSql(String key)} to specify an externalized SQL query or 
 * 		update statement. This will be loaded from the queries.properties file.
 *  
 * - {@code withBinder(Binder binder)} to attach a binder that will set the 
 * 		query parameters on the PreparedStatement.
 * - {@code withRowMapper(RowMapper<T> rowMapper)} to define how each row in the
 * 		ResultSet should be mapped to the domain object. 
 * 
 * After configuring these methods, you can execute the query or update: 
 * - Use {@code getOptionalResult()} for a potentially absent single result. 
 * - Use {@code getResultList()} to fetch a list of results. 
 * - Use {@code execute()} for non-select operations like INSERT, UPDATE, 
 * 		or DELETE that return an update count. 
 *
 * @param <T> the type of the domain object that results from each row mapping
 */
public class JdbcTemplate<T> {
	/**
	 * This interface is used to bind the parameters of a PreparedStatement
	 */
	@FunctionalInterface
	public interface Binder {
		void bind(PreparedStatement pst) throws SQLException;
	}

	/**
	 * Maps a ResultSet row to a domain object
	 */
	@FunctionalInterface
	public interface RowMapper<T> {
		T map(ResultSet rs) throws SQLException;
	}

	private String sql;
	private Binder binder = (pst) -> {}; // a default do-nothing binder
	private RowMapper<T> rowMapper;

	/**
	 * Sets the SQL of the query
	 * 
	 * @param sql the SQL
	 * @return this
	 */
	public JdbcTemplate<T> forSql(String sql) {
		ArgumentChecks.isNotBlank(sql);
		this.sql = sql;
		return this;
	}

	/**
	 * Sets the SQL of the query looking for it on the queries.properties file
	 * 
	 * @param sql the SQL
	 * @return this
	 */
	public JdbcTemplate<T> forNamedSql(String namedSql) {
		ArgumentChecks.isNotBlank(namedSql);
		return forSql( Queries.get(namedSql) );
	}

	/**
	 * Sets the binder for the prepared statement parameters
	 * 
	 * @param binder the binder
	 * @return this
	 */
	public JdbcTemplate<T> withBinder(Binder binder) {
		ArgumentChecks.isNotNull(binder);
		this.binder = binder;
		return this;
	}

	public JdbcTemplate<T> withRowMapper(RowMapper<T> rowMapper) {
		ArgumentChecks.isNotNull(rowMapper);
		this.rowMapper = rowMapper;
		return this;
	}

	public Optional<T> getOptionalResult() {
		StateChecks.isNotNull( rowMapper );
		return run(pst -> {
			binder.bind(pst);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next() 
						? Optional.of(rowMapper.map(rs))
						: Optional.empty();
			}
		});
	}

	public List<T> getResultList() {
		StateChecks.isNotNull( rowMapper );
		return run(pst -> {
			binder.bind(pst);
			try (ResultSet rs = pst.executeQuery()) {
				List<T> result = new ArrayList<>();
				while (rs.next()) {
					T res = rowMapper.map(rs);
					result.add(res);
				}
				return result;
			}
		});
	}

	/**
	 * Executes the update, either an INSERT, UPDATE or DELETE, but not a SELECT
	 * 
	 * @return the number of rows updated
	 */
	public int execute() {
		return run(pst -> {
			binder.bind(pst);
			return pst.executeUpdate();
		});
	}
	
	private interface Operation<T> {
		T apply(PreparedStatement pst) throws SQLException;
	}

	private <U> U run(Operation<U> operation) {
		StateChecks.isNotBlank(sql);
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(sql)) {
			return operation.apply(pst);
		} catch (SQLException e) {
			throw new PersistenceException("Persistence error", e);
		}	
	}	

}
