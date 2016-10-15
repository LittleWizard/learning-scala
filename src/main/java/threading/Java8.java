package threading;


import java.util.Arrays;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import java.util.Comparator;
import java.util.List;


interface Util {
    static int numberOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}

interface Fly {
    default void takeOff(){System.out.println("Fly::takeOff");}
    default void turn(){System.out.println("Fly::turn");}
    default void cruise(){System.out.println("Fly::cruise");}
    default void land(){System.out.println("Fly::land");}
}

interface FastFly extends Fly {
    default void takeOff() {System.out.println("FastFly::takeOff");}
}

class Vehicle {
    public void land() {System.out.println("Vehicle::land");}
}

interface Sail {
    default void cruise() {System.out.println("Sail::cruise");}
}

class SeaPlane extends Vehicle implements FastFly, Sail {
    public void cruise() {
        System.out.println("SeaPlane::cruise");
        FastFly.super.cruise();
    }
}


public class Java8 {

    public static List<Person> createPeople() {
        return Arrays.asList(
                new Person("Sara", Gender.FEMALE, 20),
                new Person("Sara", Gender.FEMALE, 22),
                new Person("Bob", Gender.MALE, 20),
                new Person("Paula", Gender.FEMALE, 32),
                new Person("Paul", Gender.MALE, 32),
                new Person("Jack", Gender.MALE, 2),
                new Person("Jack", Gender.MALE, 72),
                new Person("Jill", Gender.FEMALE, 12));
    }


    public static void printSorted(List<Person> people, Comparator<Person> comparator) {
        people.stream()
               .sorted(comparator)
               .forEach(System.out::println);
    }

    public void use() {

        //Four rules of default methods
        //1. you get what is in the base class
        //2. you may override a default method
        //3. if a method is there in the class hierarchy then it takes precedence
        //4. if there is no method on any of the classes in the hierarchy,
        //but two of your interfaces that you implements has the default method
        //to solve this use rule 3

        SeaPlane seaPlane = new SeaPlane();
        seaPlane.takeOff();
        seaPlane.turn();
        seaPlane.cruise();
        seaPlane.land();
    }

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Tom", "Jerry", "Jane", "Jack");
        System.out.println(
                names.stream().map(String::toUpperCase)
                .collect(joining(", "))
        );
        System.out.println(Util.numberOfCores());

        new Java8().use();

        List<Person> people = createPeople();

        //Collections.sort(people);  //evil method cz 1.
        // it's intrusive (it wants the Person class to implement Comparable interface
        //it change the collection

        //printSorted(people, comparing(Person::getName));
        //printSorted(people, comparing(Person::getAge));
        printSorted(people, comparing(Person::getAge).thenComparing(Person::getName).reversed());


        System.out.println(
                people.stream()
                .collect(
                        groupingBy(Person::getAge, mapping(Person::getName, toList()))
                )
        );

    }
}
