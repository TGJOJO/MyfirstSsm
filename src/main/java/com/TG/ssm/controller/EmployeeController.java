package com.TG.ssm.controller;

import com.TG.ssm.pojo.Department;
import com.TG.ssm.pojo.Employee;
import com.TG.ssm.pojo.Msg;
import com.TG.ssm.service.DepartmentService;
import com.TG.ssm.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TG
 * @date 2022-04-06-22:40
 * @ClassName com.TG.ssm.controller
 * @Description
 * @Version 1.0
 */

@Controller
public class EmployeeController {

    final EmployeeService employeeService;
    final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    /**
     * 查询员工数据，分页查询
     * @return
     */
    @GetMapping("/emp")
    @ResponseBody
    public Msg getEmps(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {
        //引入PageHelper分页插件(记得在mybatis配置文件中开启分页插件)
        //传入页码和每页的条数
        PageHelper.startPage(pageNo, 5);
        //startPage后紧跟的查询就是分页查询
        List<Employee> allEmployee = employeeService.getAll();
        //将查询后的结果封装到pageInfo中，其中包括很多其他信息，包括导航条页数
        PageInfo<Employee> pageInfo = new PageInfo<>(allEmployee, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 添加员工
     * @return
     */
    //@Valid:表示这个变量需要进行校验
    //BindingResult:校验结果
    @PostMapping("/emp")
    @ResponseBody
    public Msg addEmp(@Valid Employee employee, BindingResult result) {
        if(result.hasErrors()) {
            //校验失败，返回失败，在模态框中显示错误信息
            Map<String, String> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError error : errors) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", errors);
        }

        employeeService.addEmp(employee);
        return Msg.success();
    }

    /**
     * 检验信息
     * @param empName
     * @return
     */
    @GetMapping("/checkEmpName")
    @ResponseBody
    public Msg checkEmpName(@RequestParam("empName") String empName) {
        Employee emp = employeeService.getEmpByName(empName);
        if(emp != null) {
            return Msg.fail();
        }

        return Msg.success();
    }

    @GetMapping("/emp/{eid}")
    @ResponseBody
    public Msg getEmpById(@PathVariable("eid") Integer eid) {
        Employee emp = employeeService.getEmpById(eid);
        if(emp != null) {
            return Msg.success().add("emp", emp);
        }

        return Msg.fail();
    }

    @PutMapping("/emp")
    @ResponseBody
    public Msg updateEmp(@Valid Employee employee, BindingResult result) {
        if(result.hasErrors()) {
            //校验失败，返回失败，在模态框中显示错误信息
            Map<String, String> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError error : errors) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", errors);
        }

        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 单个，批量二合一
     * @param eids
     * @return
     */
    @DeleteMapping("/emp/{eids}")
    @ResponseBody
    public Msg delEmp(@PathVariable("eids") String eids) {
        if(eids.contains("-")) {
            List<Integer> intEids = new ArrayList<>();
            String[] strEids = eids.split("-");
            for(String strEid : strEids) {
                intEids.add(Integer.parseInt(strEid));
            }
            employeeService.deleteBatch(intEids);
        }else {
            Integer eid = Integer.parseInt(eids);
            employeeService.delEmpById(eid);
        }
        return Msg.success();
    }


    //@RequestMapping("/emp/{pageNo}")
    /*public String getEmps(@PathVariable("pageNo") Integer pageNo, Model model) {
        //引入PageHelper分页插件(记得在mybatis配置文件中开启分页插件)
        //传入页码和每页的条数
        System.out.println(pageNo);
        PageHelper.startPage(pageNo, 5);
        //startPage后紧跟的查询就是分页查询
        List<Employee> allEmployee = employeeService.getAll();
        //将查询后的结果封装到pageInfo中，其中包括很多其他信息，包括导航条页数
        PageInfo<Employee> pageInfo = new PageInfo<>(allEmployee, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "list";
    }*/
}
