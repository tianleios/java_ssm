package grammar.thread;

import org.junit.Test;
import grammar.thread.MyThread;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * Created by tianlei on 2017/十月/29.
 */
public class ThreadPlaygroundTest {

    //测试 Thread
    @Test
    public void testThread() {

        System.out.print("外部" + "--name:" + Thread.currentThread().getName() + "--id:" + Thread.currentThread().getId() + "\n");

        //
        MyThread myThread = new MyThread();
        myThread.start();

//        myThread.suspend();

        //
        MyThread myThread2 = new MyThread();
        myThread2.start();

        //



    }

    //测试
    @Test
    public void testRuable() {

        MyRunable myRunable = new MyRunable();

        // 给 runable 一个执行机构
        Thread thread = new Thread(myRunable);
        thread.start();
        //
    }

    //测试线程池
    @Test
    public void testPool() throws Exception {

        int taskSize = 4;

        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(6);

        //
        List<Future> futureList = new ArrayList<>();

        //
        for (int i = 0; i < taskSize; i++) {

            int finalI = i;
            //callable  有返回值
            //runable   无返回值
            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {

                    System.out.print("正在执行中" + "--" + finalI + "--" + "--name:" + Thread.currentThread().getName() + "--id:" + Thread.currentThread().getId() + "\n");
                    return "返回结果Future" + finalI;

                }

            };

            //添加到线程池
            Future future = pool.submit(callable);

            if (pool.isShutdown() == false) {

                futureList.add(future);

            }

        }

        int a = 10;

        //关闭线程池
        pool.shutdown();
        //
        int b = 10;




            futureList.forEach(future -> {

                try {

                    System.out.print(future.get() + "\n");

                } catch (Exception e) {

                }

            });

    }

    //测试线程 队列
    @Test
    public void testQueue() {

    }

    @Test
    public void testTimer() {

        Timer timer = new Timer();

        //
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        },10);

        //延迟 10s 触发, 周期为3
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        },10,3);

        // 指定时间触发, 周期20s
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {


            }

        },new Date(),20);

        //

    }

    @Test
    public void testProducerAndConsumer() {

        int maxSize = 5;

        Queue queue = new LinkedList();

        //生产者
        Producer producer = new Producer(queue,maxSize,"Producer");

        //消费者
        Consumer consumer = new Consumer(queue,maxSize,"Consumer");

        producer.start();
        consumer.start();


    }

}