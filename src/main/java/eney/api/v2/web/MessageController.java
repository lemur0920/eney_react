package eney.api.v2.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apis/message")
public class MessageController {
	
	@RequestMapping("/message")
	public void ApiMessagePage(Model model){}
	
}
