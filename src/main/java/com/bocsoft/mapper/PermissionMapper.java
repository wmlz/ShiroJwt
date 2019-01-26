package com.bocsoft.mapper;

import com.bocsoft.model.PermissionDto;
import com.bocsoft.model.RoleDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PermissionMapper
 * @author Wang926454
 * @date 2018/8/31 14:42
 */
public interface PermissionMapper extends Mapper<PermissionDto> {
    /**
     * 根据Role查询Permission
     * @param roleDto
     * @return java.util.List<com.bocsoft.model.PermissionDto>
     * @author Wang926454
     * @date 2018/8/31 11:30
     */
    public List<PermissionDto> findPermissionByRole(RoleDto roleDto);
}