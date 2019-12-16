package com.atlantis.utils;

/**
 * @Description: 自定义响应数据结构
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */
public class ResultByJSON {
    private Integer status; // 响应业务状态
    private String msg; // 响应消息
    private Object data; // 响应中的数据
    private String ok;  // 提示信息

    public static ResultByJSON build(Integer status, String msg, Object data) {
        return new ResultByJSON(status, msg, data);
    }

    public static ResultByJSON ok(Object data) {
        return new ResultByJSON(data);
    }

    public static ResultByJSON ok() {
        return new ResultByJSON(null);
    }

    public static ResultByJSON errorMsg(String msg) {
        return new ResultByJSON(500, msg, null);
    }

    public static ResultByJSON errorMap(Object data) {
        return new ResultByJSON(501, "error", data);
    }

    public static ResultByJSON errorTokenMsg(String msg) {
        return new ResultByJSON(502, msg, null);
    }

    public static ResultByJSON errorException(String msg) {
        return new ResultByJSON(555, msg, null);
    }

    public ResultByJSON() {

    }

    public ResultByJSON(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultByJSON(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}