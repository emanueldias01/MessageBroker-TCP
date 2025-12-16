package br.com.emanueldias.queue;

import br.com.emanueldias.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueMessages {
    private String id;
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private List<String> producersIP;
    private List<String> consumersIP;

    public QueueMessages(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addMessage(Message message) {
        queue.offer(message);
    }

    public Message takeMessage() throws InterruptedException {
        return queue.take();
    }

    public List<String> getConsumersIP() {
        return consumersIP;
    }

    public List<String> getProducersIP() {
        return producersIP;
    }

}
