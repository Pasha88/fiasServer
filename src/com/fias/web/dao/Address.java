package com.fias.web.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@Column(name = "aoid")
	private String aoid;

	@Column(name = "formalname")
	private String formalname;

	@Column(name = "shortname")
	private String shortname;

	@Column(name = "code")
	private String code;

	@Column(name = "postalcode")
	private String postalcode;

	@Column(name = "okato")
	private String okato;

	@Column(name = "oktmo")
	private String oktmo;

	@Column(name = "actstatus")
	private String actstatus;

	@Column(name = "regioncode")
	private String regioncode;

	@Column(name = "aoguid")
	private String aoguid;

	@Column(name = "parentguid")
	private String parentguid;

	@Column(name = "aolevel")
	private String aolevel;

	public String getAoid() {
		return aoid;
	}

	public void setAoid(String aoid) {
		this.aoid = aoid;
	}

	public String getFormalname() {
		return formalname;
	}

	public void setFormalname(String formalname) {
		this.formalname = formalname;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getOkato() {
		return okato;
	}

	public void setOkato(String okato) {
		this.okato = okato;
	}

	public String getOktmo() {
		return oktmo;
	}

	public void setOktmo(String oktmo) {
		this.oktmo = oktmo;
	}

	public String getActstatus() {
		return actstatus;
	}

	public void setActstatus(String actstatus) {
		this.actstatus = actstatus;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getAoguid() {
		return aoguid;
	}

	public void setAoguid(String aoguid) {
		this.aoguid = aoguid;
	}

	public String getParentguid() {
		return parentguid;
	}

	public void setParentguid(String parentguid) {
		this.parentguid = parentguid;
	}

	public String getAolevel() {
		return aolevel;
	}

	public void setAolevel(String aolevel) {
		this.aolevel = aolevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actstatus == null) ? 0 : actstatus.hashCode());
		result = prime * result + ((aoguid == null) ? 0 : aoguid.hashCode());
		result = prime * result + ((aoid == null) ? 0 : aoid.hashCode());
		result = prime * result + ((aolevel == null) ? 0 : aolevel.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((formalname == null) ? 0 : formalname.hashCode());
		result = prime * result + ((okato == null) ? 0 : okato.hashCode());
		result = prime * result + ((oktmo == null) ? 0 : oktmo.hashCode());
		result = prime * result + ((parentguid == null) ? 0 : parentguid.hashCode());
		result = prime * result + ((postalcode == null) ? 0 : postalcode.hashCode());
		result = prime * result + ((regioncode == null) ? 0 : regioncode.hashCode());
		result = prime * result + ((shortname == null) ? 0 : shortname.hashCode());
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
		Address other = (Address) obj;
		if (actstatus == null) {
			if (other.actstatus != null)
				return false;
		} else if (!actstatus.equals(other.actstatus))
			return false;
		if (aoguid == null) {
			if (other.aoguid != null)
				return false;
		} else if (!aoguid.equals(other.aoguid))
			return false;
		if (aoid == null) {
			if (other.aoid != null)
				return false;
		} else if (!aoid.equals(other.aoid))
			return false;
		if (aolevel == null) {
			if (other.aolevel != null)
				return false;
		} else if (!aolevel.equals(other.aolevel))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (formalname == null) {
			if (other.formalname != null)
				return false;
		} else if (!formalname.equals(other.formalname))
			return false;
		if (okato == null) {
			if (other.okato != null)
				return false;
		} else if (!okato.equals(other.okato))
			return false;
		if (oktmo == null) {
			if (other.oktmo != null)
				return false;
		} else if (!oktmo.equals(other.oktmo))
			return false;
		if (parentguid == null) {
			if (other.parentguid != null)
				return false;
		} else if (!parentguid.equals(other.parentguid))
			return false;
		if (postalcode == null) {
			if (other.postalcode != null)
				return false;
		} else if (!postalcode.equals(other.postalcode))
			return false;
		if (regioncode == null) {
			if (other.regioncode != null)
				return false;
		} else if (!regioncode.equals(other.regioncode))
			return false;
		if (shortname == null) {
			if (other.shortname != null)
				return false;
		} else if (!shortname.equals(other.shortname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [aoid=" + aoid + ", formalname=" + formalname + ", shortname=" + shortname + ", code=" + code
				+ ", postalcode=" + postalcode + ", okato=" + okato + ", oktmo=" + oktmo + ", actstatus=" + actstatus
				+ ", regioncode=" + regioncode + ", aoguid=" + aoguid + ", parentguid=" + parentguid + ", aolevel="
				+ aolevel + "]";
	}

}
