package com.fesine.auth.po;
import com.fesine.dao.BasePo;

import java.util.Date;
/**
 * @description: sys_acl_module表对应的实体类
 * @author: Fesine
 * @createTime:2018/01/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/01/11
 */
public class SysAclModulePo extends BasePo {
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "SysAclModule";
	public static final String ALIAS_ID = "权限模块id";
	public static final String ALIAS_NAME = "权限模块名称";
	public static final String ALIAS_PARENT_ID = "上级权限模块id";
	public static final String ALIAS_LEVEL = "权限模块层级";
	public static final String ALIAS_SEQ = "权限模块在当前层级下的排序，由小到大";
	public static final String ALIAS_STATUS = "状态 0:冻结 1:有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_OPERATOR = "操作者";
	public static final String ALIAS_OPERATE_TIME = "最后一次操作时间";
	public static final String ALIAS_OPERATE_IP = "最后一次操作者IP";
	

	//columns START
    /**
     * 权限模块id       db_column: id 
     */ 	
	private Integer id;
    /**
     * 权限模块名称       db_column: name 
     */ 	
	private String name;
    /**
     * 上级权限模块id       db_column: parent_id 
     */ 	
	private Integer parentId;
    /**
     * 权限模块层级       db_column: level 
     */ 	
	private String level;
    /**
     * 权限模块在当前层级下的排序，由小到大       db_column: seq 
     */ 	
	private Integer seq;
    /**
     * 状态 0:冻结 1:有效       db_column: status 
     */ 	
	private Integer status;
    /**
     * 备注       db_column: remark 
     */ 	
	private String remark;
    /**
     * 操作者       db_column: operator 
     */ 	
	private String operator;
    /**
     * 最后一次操作时间       db_column: operate_time 
     */ 	
	private Date operateTime;
    /**
     * 最后一次操作者IP       db_column: operate_ip 
     */ 	
	private String operateIp;
	//columns END

	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
	public String getLevel() {
		return this.level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}

	
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getOperator() {
		return this.operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	public Date getOperateTime() {
		return this.operateTime;
	}
	
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	
	public String getOperateIp() {
		return this.operateIp;
	}
	
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}


	

}

