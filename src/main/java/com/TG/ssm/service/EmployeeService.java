package com.TG.ssm.service;

import com.TG.ssm.mapper.EmployeeMapper;
import com.TG.ssm.pojo.Employee;
import com.TG.ssm.pojo.EmployeeExample;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TG
 * @date 2022-04-06-22:46
 * @ClassName com.TG.ssm.service
 * @Description
 * @Version 1.0
 */

@Service
public class EmployeeService {

    final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    public int addEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public Employee getEmpByName(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmpNameLike(empName);
        List<Employee> employees = employeeMapper.selectByExample(employeeExample);
        if(employees.size() != 0) {
            return employees.get(0);
        }

        return null;
    }

    public Employee getEmpById(Integer eid) {
       return employeeMapper.selectByPrimaryKeyWithDept(eid);
    }

    public int updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKey(employee);
    }

    public int delEmpById(Integer eid) {
        return employeeMapper.deleteByPrimaryKey(eid);
    }

    public int deleteBatch(List<Integer> eids) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        //delete from t_emp where eid in()
        criteria.andEidIn(eids);
        return employeeMapper.deleteByExample(employeeExample);
    }
}
