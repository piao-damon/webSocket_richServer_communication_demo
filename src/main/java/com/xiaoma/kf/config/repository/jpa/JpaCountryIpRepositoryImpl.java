/**
 * <p>Title: JpaCountryIpRepositoryImpl.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月27日
 * @version 1.0
 */
package com.xiaoma.kf.config.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.xiaoma.kf.config.model.CountryIpEntity;

/**
 * <p>Title: JpaCountryIpRepositoryImpl</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月27日
 */
@Repository("jpaCountryIpRepository")
public class JpaCountryIpRepositoryImpl implements JpaCountryIpRepository {

	@PersistenceContext
    private EntityManager em;
	
	private Query query;
	
	/* (non-Javadoc)
	 * <p>findAll</p>
	 * <p></p>
	 * @return
	 * @see com.xiaoma.kf.config.repository.jpa.JpaRepository#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CountryIpEntity> findAll() {
		query = this.em.createQuery(" select h from CountryIpEntity h ");
		return query.getResultList();
	}

}
