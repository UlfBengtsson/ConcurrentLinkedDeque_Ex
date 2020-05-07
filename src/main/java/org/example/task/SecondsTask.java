package org.example.task;

public class SecondsTask implements Runnable {

    private int seconds = 0;

    @Override
    public void run() {
        System.out.println(seconds++ + "s");
        if (seconds >= 60) {
            seconds = 0;
        }
    }
}
