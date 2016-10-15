package threading;


import java.util.Arrays;
import java.util.List;

public class ParallelStream {

    //it's running on a fork join pool thread
    //by default it's using same number threads as same number of core of the processor


    public static int doubleIt(int number) {
        System.out.println(number + " " + Thread.currentThread());
        return number * 2;
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.parallelStream()
                .map(ParallelStream::doubleIt)
                .reduce(0, Integer::sum);



    }

}
