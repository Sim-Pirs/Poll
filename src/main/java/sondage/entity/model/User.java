package sondage.entity.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

@Component()
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pollster pollster;

    private boolean isConnected = false;

    private boolean asError = false;

    private ArrayList<String> errorMessages;




    public Pollster getPollster() {
        return pollster;
    }

    public void setPollster(Pollster pollster) {
        this.pollster = pollster;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isconnected) {
        this.isConnected = isconnected;
    }

    public boolean asError() {
        return asError;
    }

    public void setAsError(boolean asError) {
        this.asError = asError;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public void addErrorMessage(String errorMessage){
        if(this.errorMessages == null)
            this.errorMessages = new ArrayList<>();

        this.errorMessages.add(errorMessage);
    }

    public void cleanErrors(){
        this.asError = false;
        this.errorMessages = new ArrayList<>();
    }

    public void setErrorMessages(ArrayList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "pollster=" + pollster +
                ", isConnected=" + isConnected +
                ", asError=" + asError +
                ", errorMessages=" + errorMessages +
                '}';
    }
}