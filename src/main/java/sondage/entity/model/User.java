package sondage.entity.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Représente un utilisateur de l'application.
 */
@Component()
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Si l'utilisateur est connecté, désigne le sondé en question.
     */
    private Pollster pollster;

    /**
     * Indique si l'utilisateur est connecté ou non.
     */
    private boolean isConnected = false;



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
}