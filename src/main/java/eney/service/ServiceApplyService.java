package eney.service;

import eney.domain.CustomerCaseVO;
import eney.domain.ServiceApplyVO;
import eney.mapper.CustomerCaseDao;
import eney.mapper.ServiceApplyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceApplyService {

    @Resource
    ServiceApplyDao serviceApplyDao;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public ServiceApplyVO getCloudServiceApply(int idx) {
        return serviceApplyDao.getCloudServiceApply(idx);
    }

    public Map<String, Object> getCloudServiceApplyList(ServiceApplyVO serviceApplyVO) {
        serviceApplyVO.setTotalCount(serviceApplyDao.getCloudServiceApplyCnt(serviceApplyVO));

        Map<String, Object> map = new HashMap<>();

        List<ServiceApplyVO> serviceApplyList = serviceApplyDao.getCloudServiceApplyList(serviceApplyVO);

        map.put("list", serviceApplyList);
        map.put("page", serviceApplyVO);

        return map;
    }

    public int editCloudServiceApplyStatus(ServiceApplyVO serviceApplyVO) {
        return serviceApplyDao.editCloudServiceApplyStatus(serviceApplyVO);
    }
}
