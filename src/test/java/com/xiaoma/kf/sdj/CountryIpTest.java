/**
 * <p>Title: CountryIpTest.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月24日
 * @version 1.0
 */
package com.xiaoma.kf.sdj;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kf.config.model.CountryIpEntity;
import com.xiaoma.kf.config.repository.jdbc.JdbcCountryIpRepository;
import com.xiaoma.kf.config.repository.jpa.JpaCountryIpRepository;
import com.xiaoma.kf.config.repository.sdj.CountryIpRepository;

/**
 * <p>Title: CountryIpTest</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-root-context.xml"})
@ActiveProfiles("test")
public class CountryIpTest {
	
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
	
	@Test
	@Transactional
	public void test() {
		Collection<CountryIpEntity> set = countryIpRepository.findAll();
		assertEquals(set.size(), 50);
	}
	
	@Test
	@Transactional
	public void test0() {
		Collection<CountryIpEntity> set = jpaCountryIpRepository.findAll();
		assertEquals(set.size(), 50);
	}
	
	@Test
	@Transactional
	public void test1() {
		Collection<CountryIpEntity> set = jdbcCountryIpRepository.findAll();
		Iterator<CountryIpEntity> iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		assertEquals(set.size(), 50);
	}
	

}
