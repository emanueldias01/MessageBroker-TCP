package br.com.emanueldias.message;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ipFrom;
    private String ipTo;
    private Object bodyMessage;

    public Message(String ipFrom, String ipTo, Object bodyMessage) {
        this.ipFrom = ipFrom;
        this.ipTo = ipTo;
        this.bodyMessage = bodyMessage;
    }

    public String getIpFrom() {
        return ipFrom;
    }

    public String getIpTo() {
        return ipTo;
    }

    public Object getBodyMessage() {
        return bodyMessage;
    }

    @Override
    public String toString() {
        return "[" + ipFrom + ", " + ipTo + ", " + bodyMessage + "]";
    }
}
