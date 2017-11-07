package grammar.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tianlei on 2017/十月/29.
 */
public class ThreadPlayground {

    static public void main(String[] args) throws InterruptedException {

        System.out.print("外部" + "--name:" + Thread.currentThread().getName() + "--id:" + Thread.currentThread().getId() + "\n");

//        //
//        MyThread myThread = new MyThread();
//        myThread.start();
//
//        //
//        MyThread myThread2 = new MyThread();
//        myThread2.start();
//
//        //代码在测试类中


        int maxSize = 5;

        Queue queue = new LinkedList();

        //生产者
        Producer producer = new Producer(queue,maxSize,"Producer");

        //消费者
        Consumer consumer = new Consumer(queue,maxSize,"Consumer");

        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(producer);
        pool.submit(consumer);

        //
        Thread.sleep(10000);
//
//        producer.start();
//        consumer.start();

    }





}
