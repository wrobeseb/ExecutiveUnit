package pwr.tin.tip.sw.pd.eu.jms.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pwr.tin.tip.sw.pd.eu.jms.model.enums.Status;
import pwr.tin.tip.sw.pd.eu.jms.model.utils.DateAdapter;

@XmlRootElement(name="algorithmOut")
@XmlType(propOrder={"sessionId", "algorithmId", "status", "warningDesc", "errorDesc", "startDate", "endDate", "time"})
public class OutMessage {
	
	private Integer sessionId;
	private Integer algorithmId;
	private Status  status;
	private String warningDesc;
	private String errorDesc;
	private Date   startDate;
	private Date   endDate;
	private Long   time;
	
	@XmlElement(name="sessionId")
	public Integer getSessionId() {
		return sessionId;
	}

	@XmlElement(name="algorithmId")
	public Integer getAlgorithmId() {
		return algorithmId;
	}
	
	@XmlElement(name="status")
	public Status getStatus() {
		return status;
	}
	
	@XmlElement(name="warningDesc", required=false)
	public String getWarningDesc() {
		return warningDesc;
	}
	
	@XmlElement(name="errorDesc", required=false)
	public String getErrorDesc() {
		return errorDesc;
	}
	
	@XmlElement(name="startDate")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStartDate() {
		return startDate;
	}
	
	@XmlElement(name="endDate")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getEndDate() {
		return endDate;
	}
	
	@XmlElement(name="time")
	public Long getTime() {
		return time;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public void setAlgorithmId(Integer algorithmId) {
		this.algorithmId = algorithmId;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setWarningDesc(String warningDesc) {
		this.warningDesc = warningDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
