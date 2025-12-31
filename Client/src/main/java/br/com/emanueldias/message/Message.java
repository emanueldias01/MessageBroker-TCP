package br.com.emanueldias.message;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceName;
    private String bodyMessage;

    public Message(String serviceName, String bodyMessage) {
        this.serviceName = serviceName;
        this.bodyMessage = bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    @Override
    public String toString() {
        return "[" + serviceName + ", " + bodyMessage + "]";
    }
}
