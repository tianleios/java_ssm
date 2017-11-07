package grammar.thread;

/**
 * Created by tianlei on 2017/十月/29.
 */

// 任务
public class MyRunable implements Runnable {

    @Override
    public void run() {

        System.out.print("正在执行中" + "--name:" + Thread.currentThread().getName() +  "--id:" + Thread.currentThread().getId()  + "\n");

    }
}
