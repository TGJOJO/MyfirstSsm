package com.TG.ssm.service;

import com.TG.ssm.mapper.DepartmentMapper;
import com.TG.ssm.pojo.Department;
import com.TG.ssm.pojo.DepartmentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TG
 * @date 2022-04-09-18:33
 * @ClassName com.TG.ssm.service
 * @Description
 * @Version 1.0
 */

@Service
public class DepartmentService {
    final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public Department getDeptByDeptName(String deptName) {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andDeptNameEqualTo(deptName);
        List<Department> departments = departmentMapper.selectByExample(departmentExample);
        if(departments.size() != 0) {
            return departments.get(0);
        }

        return null;
    }

    public List<Department> getAllDept() {
        return departmentMapper.selectByExample(null);
    }
}
