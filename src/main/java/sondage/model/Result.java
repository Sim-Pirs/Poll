package sondage.model;

import javax.persistence.*;
import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(ResultId.class)
@Table(name = "RESULTS")
public class Result implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String surveyId;
	
	@Id
	private String respondentId;
	
	@Column
	private String result;
	
	public String getSurveyId() {
		return surveyId;
	}
	
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	
	public String getRespondentId() {
		return respondentId;
	}

	public void setRespondentId(String respondentId) {
		this.respondentId = respondentId;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
}
