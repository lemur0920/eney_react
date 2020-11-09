package eney.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("omnichannel")
public class OmnichannelController {

	@RequestMapping("/index")
	public void omnichannelIndex(){}
	
}
