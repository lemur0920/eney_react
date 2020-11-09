package eney.web;


import eney.service.DashboardGroupService;
import eney.service.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/api/service/dashboardgroup/*")
@Api(value="service", produces="application/json", protocols="http/https(권장)")
public class DashBoardGroupController {

    @Resource
    DashboardGroupService dashboardGroupService;

    @RequestMapping(value="{userId}", method= RequestMethod.GET)
    public @ResponseBody  String Gets(@PathVariable("userId") String userid) throws JsonProcessingException {

        String jsonData = new ObjectMapper().writeValueAsString(dashboardGroupService.gets(userid));

        return jsonData;
    }

    @RequestMapping(value="{userId}/{groupid}", method= RequestMethod.GET)
    public @ResponseBody String Get(@PathVariable("userId") String userid, @PathVariable("groupid") Integer groupid) throws JsonProcessingException {

        Map<String,Object> map = new HashMap<>();

        map.put("userid",userid);
        map.put("groupid",groupid);

        String jsonData = new ObjectMapper().writeValueAsString(dashboardGroupService.get(map));

        return jsonData;
    }

    @RequestMapping(value="/{userid}/{groupname}", method= RequestMethod.POST)
    public @ResponseBody Integer Post(@PathVariable("userid") String userid, @PathVariable("groupname") String groupname) {
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("groupname",groupname);

        dashboardGroupService.post(map);

        return Integer.parseInt(map.get("idx").toString());

    }


    @RequestMapping(value="{userid}/{groupid}/{groupname}", method= RequestMethod.PUT)
    public @ResponseBody  void Put(@PathVariable("userid") String userid, @PathVariable("groupid") Integer groupid, @PathVariable("groupname") String groupname) {
        dashboardGroupService.put(userid, groupid, groupname);
    }

    @RequestMapping(value="{userid}/{groupid}", method= RequestMethod.DELETE)
    public @ResponseBody  void Delete(@PathVariable("userid") String userid, @PathVariable("groupid") Integer groupid) {
        dashboardGroupService.delete(userid, groupid);
    }
}
