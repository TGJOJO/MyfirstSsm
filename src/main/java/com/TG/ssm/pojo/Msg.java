package com.TG.ssm.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TG
 * @date 2022-04-08-12:34
 * @ClassName com.TG.ssm.pojo
 * @Description 通用的返回类
 * @Version 1.0
 */
public class Msg {
    //状态码，成功或失败
    private int code;
    //提示信息
    private String msg;
    //用户向浏览器发送的数据
    private Map<String, Object> extend = new HashMap<>();

    public Msg() {
        
    }

    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("处理成功");
        return msg;
    }

    public static Msg fail() {
        Msg msg = new Msg();
        msg.setCode(200);
        msg.setMsg("处理失败");
        return msg;
    }

    public Msg add(String key, Object value) {
        this.extend.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
