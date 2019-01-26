package com.bocsoft.controller;

import com.bocsoft.exception.CustomException;
import com.bocsoft.model.UserDto;
import com.bocsoft.model.common.BaseDto;
import com.bocsoft.model.common.ResponseBean;
import com.bocsoft.model.entity.Organization;
import com.bocsoft.service.IOrganizationService;
import com.bocsoft.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/org")
@PropertySource("classpath:config.properties")
public class OrgController {

    private final IOrganizationService organizationService;

    @Autowired
    public OrgController(IOrganizationService organizationService) {
        this.organizationService = organizationService;
    }



    @GetMapping
   // @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public ResponseBean user(@Validated BaseDto baseDto) {
        if (baseDto.getPage() == null || baseDto.getRows() == null) {
            baseDto.setPage(1);
            baseDto.setRows(10);
        }
        PageHelper.startPage(baseDto.getPage(), baseDto.getRows(),baseDto.getSortCondition());
        List<Organization> orgList = organizationService.selectAll();
        PageInfo<Organization> selectPage = new PageInfo<Organization>(orgList);
        if (orgList == null || orgList.size() <= 0) {
            throw new CustomException("查询失败(Query Failure)");
        }
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("count", selectPage.getTotal());
        result.put("data", selectPage.getList());
        return new ResponseBean(HttpStatus.OK.value(), "查询成功(Query was successful)", result);
    }
}
