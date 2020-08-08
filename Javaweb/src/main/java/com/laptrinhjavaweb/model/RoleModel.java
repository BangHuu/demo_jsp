package com.laptrinhjavaweb.model;

import java.sql.Timestamp;

public class RoleModel extends AbstractModel<RoleModel> {
    private String code;
    private String name;
    private Timestamp createDate;
    private Timestamp modifiedDate;
    private String createBy;
    private String modifiedBy;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
