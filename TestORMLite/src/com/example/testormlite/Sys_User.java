package com.example.testormlite;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="SYS_USER")
public class Sys_User {

	@DatabaseField(id = true,columnName="ID")
	String id;
	@DatabaseField(columnName="CODE")
	String code;
	@DatabaseField(columnName="PASSWORD")
	String password;
	@DatabaseField(columnName="NAME")
	String name;
	@DatabaseField(columnName="SEX")
	String sex;
	@DatabaseField(columnName="DEPT_ID")
	String dept_id;
	@DatabaseField(columnName="COMPANY_ID")
	String company_id;
	@DatabaseField(columnName="SORT_NUM")
	int sort_num;
	@DatabaseField(columnName="EMAIL")
	String email;
	@DatabaseField(columnName="MOBILE_PHONE")
	String mobile_phone;
	@DatabaseField(columnName="OFFICE_PHONE")
	String office_phone;
	@DatabaseField(columnName="ID_NO")
	String id_no;
	@DatabaseField(columnName="LAST_TIME")
	Date last_time;
	@DatabaseField(columnName="RETRY_COUNT")
	int retry_count;	
	@DatabaseField(columnName="LOCKED")
	String locked;
	@DatabaseField(columnName="DEL_STATUS")
	String del_status;
	
	public Sys_User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public int getSort_num() {
		return sort_num;
	}

	public void setSort_num(int sort_num) {
		this.sort_num = sort_num;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	public int getRetry_count() {
		return retry_count;
	}

	public void setRetry_count(int retry_count) {
		this.retry_count = retry_count;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getDel_status() {
		return del_status;
	}

	public void setDel_status(String del_status) {
		this.del_status = del_status;
	}
}
