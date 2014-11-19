/**
 * <p>Title: JdbcCountryIpRepositoryImpl.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月27日
 * @version 1.0
 */
package com.xiaoma.kf.config.repository.jdbc;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xiaoma.kf.config.model.CountryIpEntity;

/**
 * <p>Title: JdbcCountryIpRepositoryImpl</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月27日
 */
@Repository("jdbcCountryIpRepository")
public class JdbcCountryIpRepositoryImpl implements JdbcCountryIpRepository {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcCountryIpRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * <p>findAll</p>
	 * <p></p>
	 * @return
	 * @see com.xiaoma.kf.config.repository.jdbc.JdbcRepository#findAll()
	 */
	@Override
	public Collection<CountryIpEntity> findAll() {
		
		return this.jdbcTemplate.query(" select * from country_ip ", ParameterizedBeanPropertyRowMapper
                .newInstance(CountryIpEntity.class));
	}

}
