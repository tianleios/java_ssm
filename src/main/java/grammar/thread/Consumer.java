package grammar.thread;

import java.util.Queue;
import java.util.Random;

/**
 * Created by tianlei on 2017/十月/30.
 */
public class Consumer extends Thread {

    private int maxSize;
    private Queue<Integer> queue;

    public Consumer(Queue queue,int maxSize,String name) {
        super(name);
        this.maxSize = maxSize;
        this.queue = queue;
    }


    @Override
    public void run() {
        super.run();

        while (true) {
            synchronized (queue) {

                while (queue.isEmpty()) {
                    System.out.println("Queue is empty," + "Consumer thread is waiting" + " for producer thread to put something in queue");

                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                Integer removeInt = queue.remove();
                System.out.println("Consuming value : " + removeInt);
                queue.notifyAll();

            }

        }


    }
}
