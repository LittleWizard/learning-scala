package threading;


import java.util.concurrent.TimeUnit;

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


    public static void main(String[] args) {

        //one();
        two();



    }

}
