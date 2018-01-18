package com.fesine.auth.po;
import com.fesine.dao.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @description: sys_log表对应的实体类
 * @author: Fesine
 * @createTime:2018/01/11
 * @update:修改内容
 * @author: Fesine
 * @updateTime:2018/01/11
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLogPo extends BasePo {
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "SysLog";
	public static final String ALIAS_ID = "操作日志id";
	public static final String ALIAS_TYPE = "权限更新的类型 1:部门 2:用户 3:权限模块 4:权限 5:角色 6:角色用户关系 7:角色权限关系";
	public static final String ALIAS_TARGET_ID = "基于type后指定的对象id，比如用户，权限，角色等表的主键";
	public static final String ALIAS_OLD_VALUE = "旧值";
	public static final String ALIAS_NEW_VALUE = "新值";
	public static final String ALIAS_OPERATOR = "操作者";
	public static final String ALIAS_OPERATE_TIME = "最后一次操作时间";
	public static final String ALIAS_OPERATE_IP = "最后一次操作者IP";
	public static final String ALIAS_STATUS = "是否被复原 0:没有 1:有";
	

	//columns START
    /**
     * 操作日志id       db_column: id 
     */ 	
	private Integer id;
    /**
     * 权限更新的类型 1:部门 2:用户 3:权限模块 4:权限 5:角色 6:角色用户关系 7:角色权限关系       db_column: type 
     */ 	
	private Integer type;
    /**
     * 基于type后指定的对象id，比如用户，权限，角色等表的主键       db_column: target_id 
     */ 	
	private Integer targetId;
    /**
     * 旧值       db_column: old_value 
     */ 	
	private String oldValue;
    /**
     * 新值       db_column: new_value 
     */ 	
	private String newValue;
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
    /**
     * 是否被复原 0:没有 1:有       db_column: status 
     */ 	
	private Integer status;
	//columns END

	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getType() {
		return this.type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	
	public Integer getTargetId() {
		return this.targetId;
	}
	
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	
	public String getOldValue() {
		return this.oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	
	public String getNewValue() {
		return this.newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
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

	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}


	

}

