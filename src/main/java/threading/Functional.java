package threading;

//lambda expression is a cute little anonymous function

public class Functional {

    //function has 4 things
    //1. name
         //2. parameter list  <--
         //3 body             <--
    //4 return type

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







    }
}
