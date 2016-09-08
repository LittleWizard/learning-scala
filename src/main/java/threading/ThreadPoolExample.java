package threading;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ThreadPoolExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
      /*  ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        Runnable command = new Runnable() {
            public void run() {
                System.out.println("Hello World");
            }
        };
        service.scheduleAtFixedRate(command, 5, 5, TimeUnit.SECONDS);
        service.shutdown();*/

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("Hello World");
            }
        };
        executor.schedule(task, 5, TimeUnit.SECONDS);
        executor.schedule(task, 5, TimeUnit.SECONDS);
        executor.schedule(task, 5, TimeUnit.SECONDS);
        executor.schedule(task, 5, TimeUnit.SECONDS);
        System.out.println("Hello World Should First");

       // executor.shutdown();











        //=====================================================
       /*
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> System.out.println("Asynchronous task"));

        Future future = executorService.submit(new Callable(){
            public Object call() throws Exception {
                System.out.println("Asynchronous Callable");
                Thread.sleep(4000);
                return "Callable Result";
            }
        });
        System.out.println("future.get() = " + future.get());

        executorService.shutdown();


        testInvokeAny();*/
    }


    private static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("Task 1");
                return "Task 1";
            }
        });

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("Task 2");
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("Task 3");
                return "Task 3";
            }
        });

        String result = executorService.invokeAny(callables);

        System.out.println("result = " + result);

        executorService.shutdown();
    }


    private static void testInvokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

        List<Future<String>> futures = executorService.invokeAll(callables);

        for(Future<String> future : futures){
            System.out.println("future.get = " + future.get());
        }

        executorService.shutdown();
    }


}
