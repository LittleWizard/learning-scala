package threading;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Changes {


    public static void nio2One() {
        try
        {
            //do sbt package first
            Path file = Paths.get("target/scala-2.11/learning-scala_2.11-1.0-SNAPSHOT.jar");
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(file);
            ByteBuffer buffer = ByteBuffer.allocate(100_000);
            Future<Integer> result = channel.read(buffer, 0);
            while(!result.isDone())
            {
               System.out.println("working in main thread");
            }
            Integer bytesRead = result.get();
            System.out.println("Bytes read [" + bytesRead + "]");
        }
        catch (IOException | ExecutionException | InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }


    public static void exampleTryWith() {
        String fileName = "someFile.bin";
        try(FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fin)) {

        }
        catch (FileNotFoundException e) {
            System.err.println("Config file '" + fileName + "' is missing or malformed");
        } catch (IOException iox) {
            System.err.println("Error while processing file '" + fileName + "'");
        }
    }


    public static void main(String[] args) {


        System.out.println("Hello World");

        nio2One();


    }

}
