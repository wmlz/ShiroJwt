package com.bocsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.bocsoft.exception.CustomException;
import com.bocsoft.model.OrgDto;
import com.bocsoft.model.common.BaseDto;
import com.bocsoft.model.common.ResponseBean;
import com.bocsoft.model.entity.Organization;
import com.bocsoft.service.IOrganizationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<OrgDto> orgList = organizationService.selectAll();
        PageInfo<OrgDto> selectPage = new PageInfo<OrgDto>(orgList);
        if (orgList == null || orgList.size() <= 0) {
            throw new CustomException("查询失败(Query Failure)");
        }
        List<OrgDto> orgTree = bulid(orgList);
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("count", selectPage.getTotal());
        result.put("data", orgTree);
        return new ResponseBean(HttpStatus.OK.value(), "查询成功(Query was successful)", result);
    }

    
    @DeleteMapping("/delete")
    @Transactional
    public ResponseBean deleteByPkList(@RequestBody JSONObject reqObj) {
        /**
        * @Description: 批量删除org
        * @Param: [reqObj] 
        * @return: com.bocsoft.model.common.ResponseBean 
        * @Author: zer0ne 
        * @Date: 2019/1/27 15:09
        */
        List<Integer> a = (List<Integer>) reqObj.get("pkList");
        System.out.println(a);
        Integer[] b = a.toArray(new Integer[0]);
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }

        int num = organizationService.deleteByPkList(b);
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("count", num);
        return new ResponseBean(HttpStatus.OK.value(), "删除成功", result);
    }


    private List<OrgDto> bulid(List<OrgDto> treeNodes) {
        /**
         * @Description: List转树形结构
         * @Param: [treeNodes]
         * @return: java.util.List<com.bocsoft.model.OrgDto>
         * @Author: zer0ne
         * @Date: 2019/1/27
         */

        List<OrgDto> trees = new ArrayList<OrgDto>();

        for (OrgDto treeNode : treeNodes) {
            if (0 == treeNode.getParentId()) {
                trees.add(treeNode);
            }
            for (OrgDto it : treeNodes) {
                if (it.getParentId().intValue() == treeNode.getId().intValue()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<OrgDto>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }


}
