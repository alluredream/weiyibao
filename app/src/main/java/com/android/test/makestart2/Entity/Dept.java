package com.android.test.makestart2.Entity;

public class Dept {
    private int deptId;
    private String deptName;
    private String deptCode;
    private String deptTel;
    private String deptDescript;
    private String parentDeptId;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptTel() {
        return deptTel;
    }

    public void setDeptTel(String deptTel) {
        this.deptTel = deptTel;
    }

    public String getDeptDescript() {
        return deptDescript;
    }

    public void setDeptDescript(String deptDescript) {
        this.deptDescript = deptDescript;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public Dept(int deptId, String deptName, String deptCode, String deptTel, String deptDescript, String parentDeptId) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.deptTel = deptTel;
        this.deptDescript = deptDescript;
        this.parentDeptId = parentDeptId;
    }

    public Dept() {
    }
}
