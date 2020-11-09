package eney.service;

import eney.domain.XTechApply;
import eney.mapper.XTechDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XTechApplyService {

    @Autowired
    XTechDao xTechDao;

    public void XtechApplySave(XTechApply xTechApply){
        xTechDao.XtechApplySave(xTechApply);
    }

}
