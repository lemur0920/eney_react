package eney.api.v1.service;

import javax.annotation.Resource;

import eney.api.v1.mapper.ApiUserDao;
import org.springframework.stereotype.Service;

import eney.api.v1.mapper.ApiUserDao;

@Service
public class ApiUserService {

	@Resource
    ApiUserDao apiUserDao;
	
	
}
