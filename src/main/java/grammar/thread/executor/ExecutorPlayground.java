package grammar.thread.executor;

import java.util.concurrent.*;

/**
 * Created by tianlei on 2018/一月/12.
 */
public class ExecutorPlayground {

    public static void main(String[] args)  {

     ExecutorService executorService = Executors.newFixedThreadPool(2);
     for (int i = 0; i < 5; i++) {

         Runnable runnable  = new Runnable() {
             @Override
             public void run() {

                 System.out.print(Thread.currentThread().getName());

             }
         };

         executorService.execute(runnable);

     }

     //
        FutureTask futureTask = new FutureTask(new Callable(){
            @Override
            public String call() throws Exception {
                return "result";
            }
        });

    Future future = executorService.submit(futureTask);
     executorService.shutdown();

//     future.get()

    }


}
