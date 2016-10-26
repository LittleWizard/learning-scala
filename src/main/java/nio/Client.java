package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {

    public static void main (String [] args)
            throws Exception {

        new Client().go();
    }

    private void go()
            throws IOException, InterruptedException, ExecutionException {

        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 3883);
        Future future = client.connect(hostAddress);
        future.get(); // returns null

        System.out.println("Client is started");
        System.out.println("Sending message to server: ");

        byte [] bytes = new String("Bye").getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        Future result = client.write(buffer);

        while (! result.isDone()) {
            System.out.println("... ");
        }

        System.out.println(new String(buffer.array()).trim());
        buffer.clear();
        client.close();
    }
}
