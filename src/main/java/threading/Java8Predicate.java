package threading;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Java8Predicate {

    public static void print(int number, Predicate<Integer> predicate, String msg) {
        System.out.println(
                number + " " + msg + ":" + predicate.test(number)
        );
    }


    public static double compute(int number) {
        System.out.println("called .........");
        return Math.sqrt(number);
    }

    public static void main(String[] args) {
        Predicate<Integer> isEven = e -> e % 2 == 0;
        Predicate<Integer> isGT4 = e -> e > 4;

        print(5, isEven, " is even ? ");
        print(10, isEven, " is even ? ");

        print(5, isGT4, " is > 4 ?");
        print(10, isEven, "is > 4 ?");

        print(5, isGT4.and(isEven), "is > 4 && is even?");
        print(10, isGT4.and(isEven), "is > 4 && is even?");
        print(4, isGT4.and(isEven), "is > 4 && is even?");

        Map<Integer, Double> sqrt = new HashMap<>();
        if(!sqrt.containsKey(4))
            sqrt.put(4, compute(4));

        System.out.println(sqrt.get(4));

        sqrt.computeIfAbsent(12, Java8Predicate::compute);
        System.out.println(sqrt.get(12));

    }

}
