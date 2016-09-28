package threading;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


    }


}
