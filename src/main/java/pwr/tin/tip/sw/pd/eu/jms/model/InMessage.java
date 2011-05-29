package pwr.tin.tip.sw.pd.eu.jms.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="algorithm")
public class InMessage {

	private Integer sessionId;
	private Integer algorithmId;
	private String algorithmName;
	private String paramFilePath;
	private String sourceFilePath;
	private String resultFilePath;
	
	@XmlElement(name="sessionId")
	public Integer getSessionId() {
		return sessionId;
	}
	
	@XmlElement(name="id")
	public Integer getAlgorithmId() {
		return algorithmId;
	}
	
	@XmlElement(name="algorithmName")
	public String getAlgorithmName() {
		return algorithmName;
	}
	
	@XmlElement(name="paramFilePath")
	public String getParamFilePath() {
		return paramFilePath;
	}
	
	@XmlElement(name="sourceFilePath")
	public String getSourceFilePath() {
		return sourceFilePath;
	}
	
	@XmlElement(name="resultFilePath")
	public String getResultFilePath() {
		return resultFilePath;
	}
	
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public void setAlgorithmId(Integer algorithmId) {
		this.algorithmId = algorithmId;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public void setParamFilePath(String paramFilePath) {
		this.paramFilePath = paramFilePath;
	}
	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}
	public void setResultFilePath(String resultFilePath) {
		this.resultFilePath = resultFilePath;
	}
}
