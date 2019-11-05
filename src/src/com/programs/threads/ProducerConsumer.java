package com.programs.threads;

import java.util.ArrayDeque;
import java.util.Deque;

public class ProducerConsumer {
    private static final int MAX_ELEMENTS = 1;
    Deque<Integer> deque = new ArrayDeque(MAX_ELEMENTS);
    Integer producerLast = 0;
    Integer consumerLast = 0;

    public void produce() {
        // produce until full
        synchronized (deque) {
            while (true) {
                while (deque.size() < MAX_ELEMENTS) {
                    deque.addLast(producerLast++);
                }
                deque.notifyAll();
                try {
                    deque.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void consume() {
        // consume until empty
        synchronized (deque) {
            while (true) {
                while (deque.size() > 0) {
                    consumerLast = deque.removeFirst();
                    System.out.println(Thread.currentThread().getThreadGroup().getName() + ":"+consumerLast);
                }
                deque.notifyAll();
                try {
                    deque.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        public static void main (String[]args){
            ProducerConsumer producerConsumer = new ProducerConsumer();
            new Thread(() -> producerConsumer.produce()).start();
            for (int i = 0; i < 10; i++) {
                new Thread(new ThreadGroup(Integer.toString(i)), () -> producerConsumer.consume()).start();
            }
        }
    }
