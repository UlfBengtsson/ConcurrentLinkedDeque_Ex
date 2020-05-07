package org.example;

import org.example.task.AddTask;
import org.example.task.MinTask;
import org.example.task.RemoveTask;
import org.example.task.SecondsTask;

import java.util.concurrent.*;

public class App
{
    public static void main( String[] args )
    {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        MinTask minTask = new MinTask();
        SecondsTask secTask = new SecondsTask();

        Future<?> minThread = service.scheduleAtFixedRate(minTask, 0, 1, TimeUnit.MINUTES);
        Future<?> secThread = service.scheduleAtFixedRate(secTask, 0, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(62000); //main thread sleeps, not our Seconds thread.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        minThread.cancel(true);
        secThread.cancel(true);

        service.shutdownNow();
    }



    static void concurrentEx() {
        // prep
        ConcurrentLinkedDeque<String> myList = new ConcurrentLinkedDeque<>();
        Thread[] threads = new Thread[100];

        // start work
        for (int i = 0; i < threads.length ; i++) {
            AddTask task = new AddTask(myList);
            threads[i] = new Thread(task);
            threads[i].start();
        }

        System.out.printf("Main: %d task added and started\n", threads.length);

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();                  //wait until its done with its work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: %d stings added to list\n", myList.size());
// middle point

        for (int i = 0; i < threads.length ; i++) {
            RemoveTask task = new RemoveTask(myList);
            threads[i] = new Thread(task);
            threads[i].start();
        }

        System.out.printf("Main: %d task going to remove strings and started there work\n", threads.length);

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: %d stings remaining in list\n", myList.size());
    }

    //  Sync-list
    //  t1  t2  t3  t4
    //  list locked by t2
    //  t2 dose stuff
    //  t2 unlocks list
    //  ....

    //  Concurrent-list
    //  t1  t2  t3  t4
    //  t2 gets access to part of the list
    //  t2 dose stuff
    //  at the same time
    //  the other threads get there part of the list that thy can work with
}