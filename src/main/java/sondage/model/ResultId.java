package sondage.model;

import java.io.Serializable;

public class ResultId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String surveyId;
 
    private String respondentId;
 
    public ResultId() {
    	super();
    }
    
    public ResultId(String surveyId, String respondentId) {
        this.surveyId = surveyId;
        this.respondentId = respondentId;
    }
 
    // equals() and hashCode()
}

