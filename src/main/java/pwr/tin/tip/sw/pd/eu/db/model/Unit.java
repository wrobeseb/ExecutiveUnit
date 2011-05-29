package pwr.tin.tip.sw.pd.eu.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pwr.tin.tip.sw.pd.eu.db.model.enums.UnitType;


@Entity
@Table(name="unit")
@SequenceGenerator(name="sequence", allocationSize=1, sequenceName="unit_seq")
public class Unit {
	
	private Integer id;
	private Integer idUnit;
	private UnitType type;
	private Boolean overloadFlg;
	private String addressIp;
	private Date lastUpdateDt;
	private Integer maxProcessNo;
	
	@Id
	@Column(name="id")
	@GeneratedValue(generator="sequence", strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="id_unit")
	public Integer getIdUnit() {
		return idUnit;
	}
	public void setIdUnit(Integer idUnit) {
		this.idUnit = idUnit;
	}
	
	@Column(name="type")
	@Enumerated(EnumType.ORDINAL)
	public UnitType getType() {
		return type;
	}
	public void setType(UnitType type) {
		this.type = type;
	}
	
	@Column(name="overload_flg")
	public Boolean getOverloadFlg() {
		return overloadFlg;
	}
	public void setOverloadFlg(Boolean overloadFlg) {
		this.overloadFlg = overloadFlg;
	}
	
	@Column(name="address_ip")
	public String getAddressIp() {
		return addressIp;
	}
	public void setAddressIp(String addressIp) {
		this.addressIp = addressIp;
	}
	
	@Column(name="last_update_dt")
	public Date getLastUpdateDt() {
		return lastUpdateDt;
	}
	public void setLastUpdateDt(Date lastUpdateDt) {
		this.lastUpdateDt = lastUpdateDt;
	}
	
	@Column(name="max_process_no")
	public Integer getMaxProcessNo() {
		return maxProcessNo;
	}
	public void setMaxProcessNo(Integer maxProcessNo) {
		this.maxProcessNo = maxProcessNo;
	}
}
