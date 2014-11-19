/**
 * <p>Title: CountryIpController.java</p>
 * <p></p>
 * @author damon
 * @date 2014年10月23日
 * @version 1.0
 */
package com.xiaoma.kf.config.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xiaoma.kf.common.taglib.Pager;
import com.xiaoma.kf.config.model.CountryIpEntity;
import com.xiaoma.kf.config.service.CountryIpService;

/**
 * <p>Title: CountryIpController</p>
 * <p></p> 
 * @author damon
 * @date 2014年10月23日
 */
@Controller
@RequestMapping("country/ip")
public class CountryIpController {

	@Autowired
	private CountryIpService countryIpService;
	
	@RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(@ModelAttribute("model") CountryIpEntity model){
		ModelAndView mav = new ModelAndView("/country/ip/index");
    	Integer pageNum = model.getPageNum(); 
    	Integer pageSize = model.getPageSize();
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("pageNum", pageNum);
    	searchParams.put("pageSize", pageSize);
    	searchParams.put("model", model);
    	
    	Page<CountryIpEntity> ips = countryIpService.search(searchParams);
    	Pager<CountryIpEntity> pager = new Pager<CountryIpEntity>(pageNum,(int)ips.getTotalElements(), 
    			pageSize, (List<CountryIpEntity>)ips.getContent());
    	
    	mav.addObject("ips", ips.getContent());
    	mav.addObject("pager", pager);
    	mav.addObject("model", model);
		
		return mav;
	}
}
