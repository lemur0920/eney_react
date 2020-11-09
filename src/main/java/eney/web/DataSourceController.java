
package eney.web;

import eney.service.DataSourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/api/service/ds/*")
@Api(value="service", produces="application/json", protocols="http/https(권장)")
public class DataSourceController {

    @Resource
    DataSourceService dataSourceService;

    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @RequestMapping(value="tables/{userId}", method= RequestMethod.GET)
    public @ResponseBody String GetTables(@PathVariable("userId") String userid) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dataSourceService.GetTables(userid));
    }

    @RequestMapping(value="fields", method= RequestMethod.GET)
    public @ResponseBody String GetFields(String tablename) throws JsonProcessingException {


        return new ObjectMapper().writeValueAsString(dataSourceService.GetFields(tablename));
    }

    @RequestMapping(value="data", method= RequestMethod.GET)
    public @ResponseBody String GetDatas(String tablename) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dataSourceService.GetDatas(tablename));
    }

    @RequestMapping(value="batchdata", method= RequestMethod.GET)
    public @ResponseBody String GetBatchDatas(String tablename) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dataSourceService.GetBatchDatas(tablename));
    }

}
