package com.bocsoft.model;

import com.bocsoft.model.entity.Organization;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
@Table(name = "organization")
public class OrgDto extends Organization {

    public List<OrgDto> getChildren() {
        return children;
    }

    public void setChildren(List<OrgDto> children) {
        this.children = children;
    }

    @Transient
    private List<OrgDto> children;
}
