package threading;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Java8Concurrency {

    public static void one() {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };
        task.run();
        Thread th = new Thread(task);
        th.start();
        System.out.println("Done");
    }

    public static void two() {
        Runnable runnable = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Foo " + threadName);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + threadName);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void three() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }

        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        ExecutorService executor2 = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor2.submit(task);
        System.out.println("future done? " + future.isDone());
        Integer result = future.get();
        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
    }

    public static void four() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException ex) {
                throw new IllegalStateException("task interrupted", ex);
            }
        });
        future.get(1, TimeUnit.SECONDS);

    }

    public static void five() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        Callable<String> callable = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Foo " + threadName);
                TimeUnit.SECONDS.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Value";
        };

        List<Callable<String>> calls = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3",
                callable);

        executor.invokeAll(calls) //blocks all
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);

        System.out.println("after executing calls");

        Thread.sleep(10000);

        System.out.println("experiment finished");
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //one();
      //  two();
    //    three();
        five();

    }

}
