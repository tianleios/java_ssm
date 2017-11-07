package grammar.thread;

/**
 * Created by tianlei on 2017/十月/29.
 */
public class MyThread extends Thread {

    // start 方法为启动线程，会自动调用Run 方法, start 在调用线程中执行
    @Override
    public synchronized void start() {
        super.start();


        System.out.print("开始执行" + "--name:" + Thread.currentThread().getName() + "--id:" + Thread.currentThread().getId() + "\n");
    }

    // 会在常见的线程
    @Override
    public void run() {
        super.run();

        System.out.print("正在执行中" + "--name:" + Thread.currentThread().getName() +  "--id:" + Thread.currentThread().getId()  + "\n");

    }

}
