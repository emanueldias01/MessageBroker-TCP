package br.com.emanueldias.message;

import java.io.Serializable;


public class MessageQueueSelection implements Serializable {
    private String queueId;
    private Role role;

    public MessageQueueSelection(String queueId, Role role) {
        this.queueId = queueId;
        this.role = role;
    }

    public String getQueueId() {
        return this.queueId;
    }

    public Role getRole() {
        return role;
    }
}

