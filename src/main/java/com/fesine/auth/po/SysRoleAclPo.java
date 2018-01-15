package com.fesine.auth.po;
import com.fesine.dao.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @description: sys_role_acl表对应的实体类
 * @author: Fesine
 * @createTime:2018/01/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/01/11
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleAclPo extends BasePo {
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "SysRoleAcl";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ROLE_ID = "角色id";
	public static final String ALIAS_ACL_ID = "权限id";
	public static final String ALIAS_OPERATOR = "操作者";
	public static final String ALIAS_OPERATE_TIME = "最后一次操作时间";
	public static final String ALIAS_OPERATE_IP = "最后一次操作者IP";
	

	//columns START
    /**
     * id       db_column: id 
     */ 	
	private Integer id;
    /**
     * 角色id       db_column: role_id 
     */ 	
	private Integer roleId;
    /**
     * 权限id       db_column: acl_id 
     */ 	
	private Integer aclId;
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

	
	public Integer getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
	public Integer getAclId() {
		return this.aclId;
	}
	
	public void setAclId(Integer aclId) {
		this.aclId = aclId;
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

