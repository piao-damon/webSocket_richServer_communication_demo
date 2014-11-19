/**
 * <p>Title: CountryIpEntity.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月23日
 * @version 1.0
 */
package com.xiaoma.kf.config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xiaoma.kf.base.model.BaseSearchEntity;

/**
 * <p>Title: CountryIpEntity</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月23日
 */
@Entity
@Table(name = "country_ip")
public class CountryIpEntity extends BaseSearchEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 5578392926069613849L;
	
	@Column(name = "start_ip")
	private Double start_ip;
	
	@Column(name = "end_ip")
	private Double end_ip;
	
	@Column(name = "code2")
	private String code2;
	
	@Column(name = "start_ip1")
	private String start_ip1;
	
	@Column(name = "end_ip1")
	private String end_ip1;
	
	@Column(name = "country_cn")
	private String country_cn;
	
	//***********************************	getter and setter	*************************************//
	
	/**
	 * @return the start_ip
	 */
	public Double getStart_ip() {
		return start_ip;
	}

	/**
	 * @param start_ip the start_ip to set
	 */
	public void setStart_ip(Double start_ip) {
		this.start_ip = start_ip;
	}

	/**
	 * @return the end_ip
	 */
	public Double getEnd_ip() {
		return end_ip;
	}

	/**
	 * @param end_ip the end_ip to set
	 */
	public void setEnd_ip(Double end_ip) {
		this.end_ip = end_ip;
	}

	/**
	 * @return the code2
	 */
	public String getCode2() {
		return code2;
	}

	/**
	 * @param code2 the code2 to set
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * @return the start_ip1
	 */
	public String getStart_ip1() {
		return start_ip1;
	}

	/**
	 * @param start_ip1 the start_ip1 to set
	 */
	public void setStart_ip1(String start_ip1) {
		this.start_ip1 = start_ip1;
	}

	/**
	 * @return the end_ip1
	 */
	public String getEnd_ip1() {
		return end_ip1;
	}

	/**
	 * @param end_ip1 the end_ip1 to set
	 */
	public void setEnd_ip1(String end_ip1) {
		this.end_ip1 = end_ip1;
	}

	/**
	 * @return the country_cn
	 */
	public String getCountry_cn() {
		return country_cn;
	}

	/**
	 * @param country_cn the country_cn to set
	 */
	public void setCountry_cn(String country_cn) {
		this.country_cn = country_cn;
	}

}
