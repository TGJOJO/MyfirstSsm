package com.TG.ssm;

import com.TG.ssm.mapper.EmployeeMapper;
import com.TG.ssm.pojo.Employee;
import com.TG.ssm.pojo.EmployeeExample;
import com.TG.ssm.pojo.Msg;
import com.TG.ssm.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

/**
 * @author TG
 * @date 2022-04-06-23:14
 * @ClassName com.TG.ssm
 * @Description
 * @Version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
//通过这个标签可以将ioc容器本身Autowired出来
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springMVC.xml"})
public class testMVC {

    //传入springMVC的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟的mvc，获取请求
    MockMvc mockMvc;

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmployeeService employeeService;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟发送请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emp").param("pageNo", "1")).andReturn();
        //请求成功后，请求对象域中会有pageInfo这个参数
        MockHttpServletRequest request = result.getRequest();
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页面页码：" + pageInfo.getPageNum());
        System.out.println("总页码" + pageInfo.getPages());
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("导航栏中显示的页码：");
        int[] nums = pageInfo.getNavigatepageNums();
        for(int i : nums) {
            System.out.print(" " + i);
        }
        System.out.println();

        List<Employee> list = pageInfo.getList();
        list.forEach(System.out::println);

    }

    @Test
    public void test() throws Exception {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmpNameLike("TG");
        List<Employee> employees = employeeMapper.selectByExample(employeeExample);

        employees.forEach(System.out::println);

    }

    @Test
    public void test2() throws Exception {
        PageHelper.startPage(1, 5);
        //startPage后紧跟的查询就是分页查询
        List<Employee> allEmployee = employeeService.getAll();
        //将查询后的结果封装到pageInfo中，其中包括很多其他信息，包括导航条页数
        PageInfo<Employee> pageInfo = new PageInfo<>(allEmployee, 5);
        System.out.println(Msg.success().add("pageInfo", pageInfo));
        Msg msg = Msg.success().add("pageInfo", pageInfo);
        Object pageInfo1 = msg.getExtend().get("pageInfo");
        System.out.println(pageInfo1);
    }

    @Test
    public void test3() {
        Employee employee1 = new Employee(null, "TG", 19, "男", "TG@qq.com", 1);
        Employee employee2 = new Employee(null, "Rem", 18, "女", "Rem@qq.com", 1);
        Employee employee3 = new Employee(null, "Alan", 18, "男", "Freedom@qq.com", 3);
        employeeService.addEmp(employee1);
        employeeService.addEmp(employee2);
        employeeService.addEmp(employee3);
        for(int i = 0; i < 100; i++) {
            String empName = UUID.randomUUID().toString().substring(0, 5);
            Employee employee = new Employee(null, empName, 20, "男", empName + "@qq.com", 2);
            employeeService.addEmp(employee);
        }
        System.out.println("执行完成");
        System.out.println("first commit后修改1");
        System.out.println("second commit后修改2");
        System.out.println("hot-fix commit");
        System.out.println("test master");
        System.out.println("test hot-fix");
        System.out.println("push test");
    }

}
