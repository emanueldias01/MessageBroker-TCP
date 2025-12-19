package br.com.emanueldias.queue;

import br.com.emanueldias.message.Message;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueMessages {
    private String id;
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private List<ObjectOutputStream> outputStreamsConsumers = new ArrayList<>();

    public List<ObjectOutputStream> getOutputStreamsConsumers() {
        return outputStreamsConsumers;
    }

    public void addOutputStreamConsumer(ObjectOutputStream objectOutputStream) {
        this.outputStreamsConsumers.add(objectOutputStream);
    }

    public void removeOutputStreamConsumer(ObjectOutputStream objectOutputStream) {
        this.outputStreamsConsumers.remove(objectOutputStream);
    }

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

}
