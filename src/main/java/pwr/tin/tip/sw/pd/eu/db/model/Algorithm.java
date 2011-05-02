package pwr.tin.tip.sw.pd.eu.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pwr.tin.tip.sw.pd.eu.db.BaseEntity;

@Entity
@Table(name="eu_algorithm")
@SequenceGenerator(name="seq", allocationSize=1, sequenceName="eu_algorithm_seq")
public class Algorithm extends BaseEntity {
	
	private Integer algorithmId;
	private String name;
	private String appName;
	private byte[] appPackage;
	private String runPath;
	private Integer executiveUnitId;
	
	public Algorithm() {}
	
	public Algorithm(Integer algorithmId, String name, String appName, byte[] appPackage, String runPath) {
		this.algorithmId = algorithmId;
		this.name = name;
		this.appName = appName;
		this.appPackage = appPackage;
		this.runPath = runPath;
	}
	
	@Override
	@Id
	@Column(name="id")
	@GeneratedValue(generator="seq", strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}
	@Column(name="algorithm_id")
	public Integer getAlgorithmId() {
		return algorithmId;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	@Column(name="app_name")
	public String getAppName() {
		return appName;
	}
	@Column(name="app_package")
	public byte[] getAppPackage() {
		return appPackage;
	}
	@Column(name="run_path")
	public String getRunPath() {
		return runPath;
	}
	@Column(name="executive_unit_id")
	public Integer getExecutiveUnitId() {
		return executiveUnitId;
	}

	public void setAlgorithmId(Integer algorithmId) {
		this.algorithmId = algorithmId;
	}
	public void setExecutiveUnitId(Integer executiveUnitId) {
		this.executiveUnitId = executiveUnitId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public void setAppPackage(byte[] appPackage) {
		this.appPackage = appPackage;
	}
	public void setRunPath(String runPath) {
		this.runPath = runPath;
	}
}
