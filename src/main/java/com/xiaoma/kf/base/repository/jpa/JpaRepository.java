/**
 * <p>Title: JpaRepository.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月27日
 * @version 1.0
 */
package com.xiaoma.kf.base.repository.jpa;

import java.util.Collection;

/**
 * <p>Title: JpaRepository</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月27日
 */
public interface JpaRepository<T> {

	Collection<T> findAll();
}
