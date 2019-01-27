package com.bocsoft.service.impl;

import com.bocsoft.mapper.OrganizationMapper;
import com.bocsoft.model.UserDto;
import com.bocsoft.model.entity.Organization;
import com.bocsoft.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements IOrganizationService {

    @Autowired
    private  OrganizationMapper organizationMapper;



    @Override
    public int deleteByPkList(Integer[] pkList){
        return organizationMapper.deleteByPkList(pkList);
    }
}
