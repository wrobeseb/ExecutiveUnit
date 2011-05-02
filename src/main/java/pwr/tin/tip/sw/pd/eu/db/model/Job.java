package pwr.tin.tip.sw.pd.eu.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import pwr.tin.tip.sw.pd.eu.db.BaseEntity;
import pwr.tin.tip.sw.pd.eu.jms.model.InMessage;
import pwr.tin.tip.sw.pd.eu.jms.model.OutMessage;

@Entity
@Table(name="eu_job")
@SequenceGenerator(name="seq", allocationSize=1, sequenceName="eu_job_seq")
public class Job extends BaseEntity {

	private Integer sessionId;
	private Algorithm algorithm;
	private Integer algorithmPID;
	private String inputMessageBody;
	private InMessage inMessage;
	private String outputMessageBody;
	private OutMessage outMessage;
	private Date inputMessageArrivalDt;
	private Date outputMessageCreatedDt;
	private Boolean outputMessageSended = false;
	
	@Override
	@Id
	@Column(name="job_id")
	@GeneratedValue(generator="seq", strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return super.id;
	}

	@Column(name="session_id")
	public Integer getSessionId() {
		return sessionId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="algorithm_id")
	public Algorithm getAlgorithm() {
		return algorithm;
	}

	@Column(name="algorithm_pid")
	public Integer getAlgorithmPID() {
		return algorithmPID;
	}
	
	@Column(name="input_message_body")
	public String getInputMessageBody() {
		return inputMessageBody;
	}

	@Column(name="output_message_body")
	public String getOutputMessageBody() {
		return outputMessageBody;
	}

	@Column(name="input_message_arrival_dt")
	public Date getInputMessageArrivalDt() {
		return inputMessageArrivalDt;
	}

	@Column(name="output_message_created_dt")
	public Date getOutputMessageCreatedDt() {
		return outputMessageCreatedDt;
	}

	@Column(name="output_message_sended_flg")
	public Boolean getOutputMessageSended() {
		return outputMessageSended;
	}
	
	@Transient
	public InMessage getInMessage() {
		return inMessage;
	}

	@Transient
	public OutMessage getOutMessage() {
		return outMessage;
	}
	
	public void setInMessage(InMessage inMessage) {
		this.inMessage = inMessage;
	}
	public void setOutMessage(OutMessage outMessage) {
		this.outMessage = outMessage;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	public void setAlgorithmPID(Integer algorithmPID) {
		this.algorithmPID = algorithmPID;
	}
	public void setInputMessageBody(String inputMessageBody) {
		this.inputMessageBody = inputMessageBody;
	}
	public void setOutputMessageBody(String outputMessageBody) {
		this.outputMessageBody = outputMessageBody;
	}
	public void setInputMessageArrivalDt(Date inputMessageArrivalDt) {
		this.inputMessageArrivalDt = inputMessageArrivalDt;
	}
	public void setOutputMessageCreatedDt(Date outputMessageCreatedDt) {
		this.outputMessageCreatedDt = outputMessageCreatedDt;
	}
	public void setOutputMessageSended(Boolean outputMessageSended) {
		this.outputMessageSended = outputMessageSended;
	}
}
