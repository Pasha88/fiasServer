package com.fias.web.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "databaseupdate")
public class DatabaseConfig {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "databasedeltaupdate")
	private String databasedeltaupdate;
	
	@Column(name = "httpaddress")
	private String httpaddress;
	
	@Column(name = "checkupdatetime")
	private String checkupdatetime;
	
	@Column(name = "databasefullupdate")
	private String databasefullupdate;
	
	@Column(name = "updatestatus")
	private String updatestatus;
	
	public String getUpdatestatus() {
		return updatestatus;
	}
	public void setUpdatestatus(String updatestatus) {
		this.updatestatus = updatestatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDatabasedeltaupdate() {
		return databasedeltaupdate;
	}
	public void setDatabasedeltaupdate(String databasedeltaupdate) {
		this.databasedeltaupdate = databasedeltaupdate;
	}
	public String getHttpaddress() {
		return httpaddress;
	}
	public void setHttpaddress(String httpaddress) {
		this.httpaddress = httpaddress;
	}
	public String getCheckupdatetime() {
		return checkupdatetime;
	}
	public void setCheckupdatetime(String checkupdatetime) {
		this.checkupdatetime = checkupdatetime;
	}
	public String getDatabasefullupdate() {
		return databasefullupdate;
	}
	public void setDatabasefullupdate(String databasefullupdate) {
		this.databasefullupdate = databasefullupdate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkupdatetime == null) ? 0 : checkupdatetime.hashCode());
		result = prime * result + ((databasedeltaupdate == null) ? 0 : databasedeltaupdate.hashCode());
		result = prime * result + ((databasefullupdate == null) ? 0 : databasefullupdate.hashCode());
		result = prime * result + ((httpaddress == null) ? 0 : httpaddress.hashCode());
		result = prime * result + id;
		result = prime * result + ((updatestatus == null) ? 0 : updatestatus.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseConfig other = (DatabaseConfig) obj;
		if (checkupdatetime == null) {
			if (other.checkupdatetime != null)
				return false;
		} else if (!checkupdatetime.equals(other.checkupdatetime))
			return false;
		if (databasedeltaupdate == null) {
			if (other.databasedeltaupdate != null)
				return false;
		} else if (!databasedeltaupdate.equals(other.databasedeltaupdate))
			return false;
		if (databasefullupdate == null) {
			if (other.databasefullupdate != null)
				return false;
		} else if (!databasefullupdate.equals(other.databasefullupdate))
			return false;
		if (httpaddress == null) {
			if (other.httpaddress != null)
				return false;
		} else if (!httpaddress.equals(other.httpaddress))
			return false;
		if (id != other.id)
			return false;
		if (updatestatus == null) {
			if (other.updatestatus != null)
				return false;
		} else if (!updatestatus.equals(other.updatestatus))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DatabaseConfig [id=" + id + ", databasedeltaupdate=" + databasedeltaupdate + ", httpaddress="
				+ httpaddress + ", checkupdatetime=" + checkupdatetime + ", databasefullupdate=" + databasefullupdate
				+ ", updatestatus=" + updatestatus + "]";
	}

}
