/**
 * <p>Title: CountryIpServiceImpl.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月23日
 * @version 1.0
 */
package com.xiaoma.kf.config.service;

import java.util.Collection;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xiaoma.kf.config.model.CountryIpEntity;
import com.xiaoma.kf.config.repository.jdbc.JdbcCountryIpRepository;
import com.xiaoma.kf.config.repository.jpa.JpaCountryIpRepository;
import com.xiaoma.kf.config.repository.sdj.CountryIpRepository;

/**
 * <p>Title: CountryIpServiceImpl</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月23日
 */
@Service
@Transactional
public class CountryIpServiceImpl implements CountryIpService {

	//spring data jpa
	@Autowired
	private CountryIpRepository countryIpRepository;
	
	//jpa
	@Autowired
    @Qualifier("jpaCountryIpRepository")
	private JpaCountryIpRepository jpaCountryIpRepository;
	
	//jdbc
	@Autowired
    @Qualifier("jdbcCountryIpRepository")
	private JdbcCountryIpRepository jdbcCountryIpRepository;

	/* (non-Javadoc)
	 * <p>search</p>
	 * <p></p>
	 * @param searchParams
	 * @return
	 * @see com.xiaoma.kf.config.service.CountryIpService#search(java.util.Map)
	 */
	@Override
	public Page<CountryIpEntity> search(Map<String, Object> searchParams) {
		int pageNum = (int) searchParams.get("pageNum"); 
		int pageSize = (int) searchParams.get("pageSize");
		//PageRequest page zero-based page index. size the size of the page to be returned.
		return countryIpRepository.findAll(buildSpecification(searchParams), new PageRequest(pageNum - 1, pageSize));
	}

	/**
	 * <p>buildSpecification</p>
	 * <p></p>
	 * @param searchParams
	 * @return
	 */
	private Specification<CountryIpEntity> buildSpecification(final Map<String, Object> searchParams) {
		return new Specification<CountryIpEntity>(){  
	        public Predicate toPredicate(Root<CountryIpEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	        	//CountryIpEntity model = (CountryIpEntity) searchParams.get("model");
	            //where 1=1
				Predicate p0 = cb.conjunction();
				
	            query.where(p0);  
	              
	            return query.getRestriction();  
	        }  
    	};
	}

	/* (non-Javadoc)
	 * <p>findAll</p>
	 * <p></p>
	 * @return
	 * @see com.xiaoma.kf.config.service.CountryIpService#findAll()
	 */
	@Override
	public Collection<CountryIpEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
