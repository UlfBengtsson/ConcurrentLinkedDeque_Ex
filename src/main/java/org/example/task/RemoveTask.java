package org.example.task;

import java.util.concurrent.ConcurrentLinkedDeque;

public class RemoveTask implements Runnable {

    private ConcurrentLinkedDeque<String> list;

    public RemoveTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            list.pollFirst();
            list.pollLast();
        }
    }
}
