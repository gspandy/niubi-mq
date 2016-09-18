package com.niubimq.pojo;

import java.io.Serializable;

/**
 * @Description 生产者pojo
 * @author junjin4838
 *
 */
public class Producer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
     * 生产者标识
     * 
     * */
    private String producerSign;
    
    /**
     * 生产者描述
     * 
     * */
    private String description;
    
    /**
     * 创建时间
     * 
     * */
    private Integer createTime;

	/**
     * 更新时间
     * 
     * */
    private Integer updateTime;
    
    /**
     * 状态：1-正常 0-无效
     * 
     * */
    private Integer status;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProducerSign() {
		return producerSign;
	}

	public void setProducerSign(String producerSign) {
		this.producerSign = producerSign;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
