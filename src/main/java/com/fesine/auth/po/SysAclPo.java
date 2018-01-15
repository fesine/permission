package com.fesine.auth.po;

import com.fesine.dao.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @description: sys_acl表对应的实体类
 * @author: Fesine
 * @createTime:2018/01/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/01/11
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
public class SysAclPo extends BasePo {
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "SysAcl";
	public static final String ALIAS_ID = "权限id";
	public static final String ALIAS_CODE = "权限码";
	public static final String ALIAS_NAME = "权限名称";
	public static final String ALIAS_ACL_MODULE_ID = "所有权限模块id";
	public static final String ALIAS_URL = "请求的url，可以填正则表达式";
	public static final String ALIAS_TYPE = "类型 1:菜单 2:按钮 3:其他";
	public static final String ALIAS_STATUS = "状态 0:冻结 1:有效";
	public static final String ALIAS_SEQ = "权限在当前模块下的排序，由小到大";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_OPERATOR = "操作者";
	public static final String ALIAS_OPERATE_TIME = "最后一次操作时间";
	public static final String ALIAS_OPERATE_IP = "最后一次操作者IP";
	

	//columns START
    /**
     * 权限id       db_column: id 
     */ 	
	private Integer id;
    /**
     * 权限码       db_column: code 
     */ 	
	private String code;
    /**
     * 权限名称       db_column: name 
     */ 	
	private String name;
    /**
     * 所有权限模块id       db_column: acl_module_id 
     */ 	
	private Integer aclModuleId;
    /**
     * 请求的url，可以填正则表达式       db_column: url 
     */ 	
	private String url;
    /**
     * 类型 1:菜单 2:按钮 3:其他       db_column: type 
     */ 	
	private Integer type;
    /**
     * 状态 0:冻结 1:有效       db_column: status 
     */ 	
	private Integer status;
    /**
     * 权限在当前模块下的排序，由小到大       db_column: seq 
     */ 	
	private Integer seq;
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

	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getAclModuleId() {
		return this.aclModuleId;
	}
	
	public void setAclModuleId(Integer aclModuleId) {
		this.aclModuleId = aclModuleId;
	}

	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	
	public Integer getType() {
		return this.type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
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

