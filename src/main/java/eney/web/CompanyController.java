package eney.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	/*
	 * 회사 소개 페이지 연결
	 */
	
	@RequestMapping("/introduce.do")
	public ModelAndView companyIntroView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	/*
	 * 회사 연혁 페이지 연결
	 */
	@RequestMapping("/history.do")
	public ModelAndView companyHistory(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	/*
	 * 서비스 소개 페이지 연결
	 */
	@RequestMapping("/service.do")
	public ModelAndView companyService(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	/*
	 * 오시는길 페이지 연결
	 */
	@RequestMapping("/map.do")
	public ModelAndView companyMap(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
	@RequestMapping("/ci.do")
	public ModelAndView ciMap(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
}//end of class
