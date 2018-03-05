package com.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ProducerConsumer {

    private Producer producer;
    private Consumer consumer;

    public Producer getProducer() {
        return producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public ProducerConsumer(List<Object> queue) {
        this.producer =    new Producer(queue);
        this.consumer = new Consumer(queue);
    }

    public static void main(String[] args) {
        List<Object> queue = new ArrayList<>(1);
        ProducerConsumer pc = new ProducerConsumer(queue);

        Thread prodcuerThread = new Thread(pc.getProducer());
        Thread consumerThread = new Thread(pc.getConsumer());

        prodcuerThread.start();
        consumerThread.start();

    }

    public class Producer implements Runnable {
        private List<Object> queue;
        private Long counter = 0L;

        public Producer(List<Object> queue) {
            this.queue = queue;
        }

        public void run() {
            while(true) {
                synchronized (queue) {
                    if (queue.size() == 0) {
                        queue.add(counter.toString());
                        counter++;
                        System.out.println("Produced: " + queue.get(0));
                        queue.notify();
                    }
                }
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public class Consumer implements Runnable {
        private List<Object> queue;

        public Consumer(List<Object> queue) {
            this.queue = queue;
        }

        public void run() {
            while(true) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (queue) {
                    if (queue.size() > 0) {
                        System.out.println("Consumed: " + queue.get(0));
                        queue.remove(0);
                        queue.notify();
                    }
                }
            }
        }

    }


    // producer consumer sync on queue of 1 element
}
