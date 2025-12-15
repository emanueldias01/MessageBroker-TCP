package br.com.emanueldias.message;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ipFrom;
    private String ipTo;

    private Object body;

    public Message(String ipFrom, String ipTo, Object body) {
        this.ipFrom = ipFrom;
        this.ipTo = ipTo;
        this.body = body;
    }

    public String getIpFrom() {
        return ipFrom;
    }

    public String getIpTo() {
        return ipTo;
    }

    public Object getBody() {
        return body;
    }
}
