package com.niubimq.pojo;


import java.io.Serializable;

/**
 * 
 */
public class JsonResult implements Serializable{
	
    private static final long serialVersionUID = -30002886874112287L;

    //0成功;1失败
	private int status;
	
	//失败信息
	private String errMsg = "";
	
	//返回信息
	private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
