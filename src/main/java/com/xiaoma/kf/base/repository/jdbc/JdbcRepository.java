/**
 * <p>Title: JdbcRepository.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月27日
 * @version 1.0
 */
package com.xiaoma.kf.base.repository.jdbc;

import java.util.Collection;

/**
 * <p>Title: JdbcRepository</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月27日
 */
public interface JdbcRepository<T> {

	Collection<T> findAll();
}
