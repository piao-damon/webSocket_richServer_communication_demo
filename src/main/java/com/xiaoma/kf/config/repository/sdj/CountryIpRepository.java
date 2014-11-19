/**
 * <p>Title: CountryIpRepository.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月23日
 * @version 1.0
 */
package com.xiaoma.kf.config.repository.sdj;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xiaoma.kf.base.repository.sdj.BaseRepository;
import com.xiaoma.kf.config.model.CountryIpEntity;

/**
 * <p>Title: CountryIpRepository</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月23日
 */
public interface CountryIpRepository extends BaseRepository<CountryIpEntity, Integer>, JpaSpecificationExecutor<CountryIpEntity> {

}
