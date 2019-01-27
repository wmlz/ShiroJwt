package com.bocsoft.mapper;

import com.bocsoft.model.OrgDto;
import com.bocsoft.model.entity.Organization;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrganizationMapper extends Mapper<OrgDto> {
    int deleteByPkList(Integer[] pkList);
}