/**
 * <p>Title: CountryIpService.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月23日
 * @version 1.0
 */
package com.xiaoma.kf.config.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.xiaoma.kf.config.model.CountryIpEntity;

/**
 * <p>Title: CountryIpService</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月23日
 */
public interface CountryIpService {

	/**
	 * <p>search</p>
	 * <p></p>
	 * @param searchParams
	 * @return
	 */
	Page<CountryIpEntity> search(Map<String, Object> searchParams);
	
	Collection<CountryIpEntity> findAll();
	
}
