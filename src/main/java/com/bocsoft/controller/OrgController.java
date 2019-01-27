package com.bocsoft.controller;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
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
        PageHelper.startPage(baseDto.getPage(), baseDto.getRows(), baseDto.getSortCondition());
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

    @DeleteMapping("/delete")
    @Transactional
    public ResponseBean deleteByPkList(@RequestBody JSONObject reqObj) {
        List<Integer> a= (List<Integer>)reqObj.get("pkList");
        System.out.println(a);
        Integer[] b = a.toArray(new Integer[0]);
        for (int i=0;i<b.length;i++){
            System.out.println(b[i]);
        }

        int num = organizationService.deleteByPkList(b);
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("count", num);
        return new ResponseBean(HttpStatus.OK.value(), "删除成功", result);
    }
}
