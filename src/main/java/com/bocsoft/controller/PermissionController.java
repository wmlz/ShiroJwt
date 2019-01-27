package com.bocsoft.controller;

import com.bocsoft.exception.CustomException;
import com.bocsoft.model.PermissionDto;
import com.bocsoft.model.UserDto;
import com.bocsoft.model.common.BaseDto;
import com.bocsoft.model.common.ResponseBean;
import com.bocsoft.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zer0ne
 * @date 2019/1/27 15:27
 */

@RestController
@RequestMapping("/permission")
@PropertySource("classpath:config.properties")
public class PermissionController {

    private final IPermissionService permissionService;

    @Autowired
    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @RequiresPermissions(logical = Logical.AND, value = {"permission:view"})
    public ResponseBean get(@Validated BaseDto baseDto){
        if (baseDto.getPage() == null || baseDto.getRows() == null) {
            baseDto.setPage(1);
            baseDto.setRows(10);
        }
        PageHelper.startPage(baseDto.getPage(), baseDto.getRows(), baseDto.getSortCondition());
        List<PermissionDto> permissionDtos = permissionService.selectAll();
        PageInfo<PermissionDto> selectPage = new PageInfo<PermissionDto>(permissionDtos);
        if (permissionDtos == null || permissionDtos.size() <= 0) {
            throw new CustomException("查询失败(Query Failure)");
        }
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("count", selectPage.getTotal());
        result.put("data", selectPage.getList());
        return new ResponseBean(HttpStatus.OK.value(), "查询成功(Query was successful)", result);
    }


}
