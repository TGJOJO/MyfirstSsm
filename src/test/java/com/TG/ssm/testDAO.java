package com.TG.ssm;

import com.TG.ssm.mapper.DepartmentMapper;
import com.TG.ssm.mapper.EmployeeMapper;
import com.TG.ssm.pojo.Employee;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author TG
 * @date 2022-04-06-21:14
 * @ClassName com.TG.ssm
 * @Description
 * @Version 1.0
 */
/*
    导入spring test包
    @ContextConfiguration指定spring配置文件的位置
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class testDAO {
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD() {
//        Employee employee = employeeMapper.selectByPrimaryKeyWithDept(1);
//        System.out.println(employee);

//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        for(int i = 0; i < 1000; i++) {
//            String uuid = UUID.randomUUID().toString().substring(0, 5);
//            mapper.insertSelective(new Employee(null, uuid, 20, "男", uuid + "@qq.com", 2));
//        }
//
//        System.out.println("批量完成");
    }
}
