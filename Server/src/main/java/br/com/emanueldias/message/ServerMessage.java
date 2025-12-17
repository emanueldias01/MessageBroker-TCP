package br.com.emanueldias.message;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private StatusMessage statusMessage;
    private String content;

    public ServerMessage(String content, StatusMessage statusMessage) {
        this.content = content;
        this.statusMessage = statusMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "content='" + content + '\'' +
                ", statusMessage=" + statusMessage +
                '}';
    }
}
