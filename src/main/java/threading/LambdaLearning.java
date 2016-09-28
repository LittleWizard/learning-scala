package threading;



public class LambdaLearning {

    public static void main(String[] args) {
        Thread th;
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th = new Thread(() -> System.out.println("In another thread"));
        th.start();
        System.out.println("In main thread");
    }


}
