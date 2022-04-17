package com.TG.ssm.controller;

import com.TG.ssm.pojo.Department;
import com.TG.ssm.pojo.Msg;
import com.TG.ssm.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TG
 * @date 2022-04-10-1:42
 * @ClassName com.TG.ssm.controller
 * @Description
 * @Version 1.0
 */

@Controller
public class DepartmentController {

    final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

//    @RequestMapping("/dept/{deptName}")
//    @ResponseBody
//    public Msg getDeptByName(@PathVariable("deptName") String deptName) {
//        Department dept = departmentService.getDeptByDeptName(deptName);
//        if(dept != null){
//            return Msg.success().add("dept", dept);
//        }
//        return null;
//    }

    @GetMapping("/dept")
    @ResponseBody
    public Msg getDeptList() {
        List<Department> deptList = departmentService.getAllDept();
        return Msg.success().add("deptList", deptList);
    }

}
