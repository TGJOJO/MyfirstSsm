package com.TG.ssm.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class Employee {
    private Integer eid;

    //后端校验
    //regexp:校验规则，message:错误信息
    @Pattern(regexp = "(^[A-Za-z0-9]{3,30}$)|(^[\\u4e00-\\u9fa5]{2,20}$)",
             message = "姓名必须为英文输入3-30位，中文输入2-20位")
    private String empName;

    private Integer age;

    private String sex;

    //@Email  害怕和前端不统一就自定义
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",
             message = "邮箱格式错误")

    private String email;

    private Integer did;

    //新增一个属性为其部门信息
    private Department department;

    public Employee() {

    }

    public Employee(Integer eid, String empName, Integer age, String sex, String email, Integer did) {
        this.eid = eid;
        this.empName = empName;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.did = did;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid=" + eid +
                ", empName='" + empName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", did=" + did +
                ", department=" + department +
                '}';
    }
}