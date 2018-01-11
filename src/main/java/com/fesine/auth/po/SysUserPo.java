package com.fesine.auth.po;
import com.fesine.dao.BasePo;
import java.util.Date;
import java.math.BigDecimal;
/**
 * @description: sys_user表对应的实体类
 * @author: Fesine
 * @createTime:2018/01/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/01/11
 */
public class SysUserPo extends BasePo {
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "SysUser";
	public static final String ALIAS_ID = "用户id";
	public static final String ALIAS_USERNAME = "用户姓名";
	public static final String ALIAS_TELEPHONE = "手机号码";
	public static final String ALIAS_EMAIL = "用户邮箱";
	public static final String ALIAS_PASSWORD = "用户密码";
	public static final String ALIAS_DEPT_ID = "用户所在部门id";
	public static final String ALIAS_STATUS = "用户状态 0:冻结，1:有效，2:删除";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_OPERATOR = "操作者";
	public static final String ALIAS_OPERATE_TIME = "最后一次操作时间";
	public static final String ALIAS_OPERATE_IP = "最后一次操作ip";
	

	//columns START
    /**
     * 用户id       db_column: id 
     */ 	
	private Integer id;
    /**
     * 用户姓名       db_column: username 
     */ 	
	private String username;
    /**
     * 手机号码       db_column: telephone 
     */ 	
	private String telephone;
    /**
     * 用户邮箱       db_column: email 
     */ 	
	private String email;
    /**
     * 用户密码       db_column: password 
     */ 	
	private String password;
    /**
     * 用户所在部门id       db_column: dept_id 
     */ 	
	private Integer deptId;
    /**
     * 用户状态 0:冻结，1:有效，2:删除       db_column: status 
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
     * 最后一次操作ip       db_column: operate_ip 
     */ 	
	private String operateIp;
	//columns END

	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
	public Integer getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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

