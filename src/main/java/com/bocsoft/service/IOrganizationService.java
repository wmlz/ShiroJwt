package com.bocsoft.service;

import com.bocsoft.model.OrgDto;
import com.bocsoft.model.UserDto;
import com.bocsoft.model.entity.Organization;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IOrganizationService extends IBaseService<OrgDto> {

    int deleteByPkList(Integer[] pkList);
}
