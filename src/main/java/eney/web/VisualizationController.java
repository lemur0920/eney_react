
package eney.web;

import eney.service.DashboardService;
import eney.service.VisualizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/api/service/visual/*")
@Api(value="service", produces="application/json", protocols="http/https(권장)")
public class VisualizationController {

    @Resource
    VisualizationService visualService;

    private static final Logger logger = LoggerFactory.getLogger(VisualizationController.class);


    @RequestMapping(value="{userid}", method=RequestMethod.GET)
    public @ResponseBody String gets(@PathVariable("userid") String userid) throws JsonProcessingException {
        return visualService.gets(userid);
    }

    @RequestMapping(value="{userid}/{id}", method=RequestMethod.GET)
    public @ResponseBody String get(@PathVariable("userid") String userid,@PathVariable("id") Integer id) throws JsonProcessingException {
        return visualService.get(userid,id);
    }

    @RequestMapping(value="{userid}", method=RequestMethod.POST)
    public @ResponseBody Integer post(@PathVariable("userid") String userid, @RequestBody String json) throws JsonProcessingException {

        Integer idx = visualService.post(userid, json);
         return idx;
    }

    @RequestMapping(value="{userid}/{id}", method=RequestMethod.PUT)
    public @ResponseBody void put(@PathVariable("userid") String userid, @PathVariable("id") Integer id,  @RequestBody String json) throws JsonProcessingException {
        visualService.put(userid,id, json);
    }

    @RequestMapping(value="{userid}/{id}", method=RequestMethod.DELETE)
    public @ResponseBody void delete(@PathVariable("userid") String userid, @PathVariable("id") Integer id) throws JsonProcessingException {
        visualService.delete(userid,id);
    }





}
