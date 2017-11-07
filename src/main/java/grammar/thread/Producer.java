package grammar.thread;

import java.util.Queue;
import java.util.Random;

/**
 * Created by tianlei on 2017/十月/30.
 */
public class Producer extends Thread {

    private int maxSize;
    private Queue<Integer> queue;
    public Producer(Queue queue,int maxSize,String name) {
        super(name);
        this.maxSize = maxSize;
        this.queue = queue;
    }


    @Override
    public void run() {
        super.run();

        while (true) {
            synchronized (queue) {

                while (queue.size() == maxSize) {

                    System.out.println("Queue is full. Produce thread waiting for. Consumer to take something  from queue");

                    try {
                        //当期线程等待，知道有人 调用 queue，的notify 或者 notifyAll
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                Random random = new Random();
                int i = random.nextInt();
                System.out.println("Producing value : " + i);
                queue.add(i);
                //
                queue.notifyAll();

            }

        }


    }

}
