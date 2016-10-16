package threading;

//lambda expression is a cute little anonymous function

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Functional {

    //function has 4 things
    //1. name
         //2. parameter list  <--
         //3 body             <--
    //4 return type

    public static long countFactors(int number) {
        IntPredicate isDivisible = index -> number % index == 0;
        return IntStream.rangeClosed(1, number)
                .filter(isDivisible)
                .count();
    }

    public static void main(String[] args) {

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("In another thread");
            }
        });
        th.start();
        System.out.println("In main thread");

        Thread th2 = new Thread(() -> System.out.println("In another thread 2"));
        th2.start();

        //single abstract method interfaces support lambda

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //external iterators

        for(int i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i));
        }

        //you control the iteration all by yourself, you say what you want to do, how you want to do, when to do it.
        //you have complete control over things
        //having complete control over things is not generally a good thing, even though it appears to be a good thing
        //the way it is being said
        //the more you control the more you have to change when you have to change the thing you control
        //this is a big disadvantage you have to work with


        // simple means it easy to use and has not any unnecessary complexity in it, it gives you focus and it does not give you unnecessary burden you have to do.
        // familiar means you looked at it so many time you need not look it again and in this case this is familiar
        //you have to deal with many moving parts in this case

        //external iterators
        for(int e : numbers) {

        }
        //internal iterator
        numbers.forEach(new Consumer<Integer>() { //anonymous inner class
            @Override
            public void accept(Integer integer) {

            }
        });
        numbers.forEach(b -> {

        });
        numbers.forEach(b -> System.out.println(b));
        numbers.forEach(System.out::println); // pass through (receiving something and passing things) ---> method reference actually passing

        //not write many code in lambdas // two line may be too many

        //if you have logic in lambdas , take this into a function and unit test it and call this function from your lambdas

        //putting multiple line of code in lambda is not recommended


        numbers.forEach(e -> System.out.println(e));
        numbers.forEach(System.out::println); // println is an instance method

        numbers.stream()
                .map(String::valueOf)  // valueOf is a static method
                .forEach(System.out::println);

        numbers.stream()
                .map(e -> String.valueOf(e)) // target
                .map(String::toString)  // argument
                .forEach(System.out::println);

        List<Person> people = Java8.createPeople();
        people.stream()
                .map(Person::getAge) // calling the getAge of the passed people from stream
                .forEach(System.out::println);


        //with 2 arguments

        numbers.stream()
                .reduce(0, (total, e) -> Integer.sum(total, e));

        numbers.stream()
                .reduce(0,  Integer::sum);


        numbers.stream()
                .map(String::valueOf)
                .reduce("", (carry, star) -> carry.concat(star));

        numbers.stream()
                .map(String::valueOf)
                .reduce("", String::concat);


        numbers.stream()
                .filter(e -> e % 2 == 0)
                .map(e -> e * 2)
                .reduce(0, Integer::sum);

        numbers.stream()
                .filter(e -> e % 2 == 0)
                .mapToInt(e -> e * 2)
                .sum();


        numbers.stream()
                .filter(e -> e % 2 == 0)
                .mapToInt(Functional::compute)
                .sum();

    }

    public static int compute(int number) {
        //assume this is time consuming
        return number * 2;
    }
}
