package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Server {

    public static void main (String [] args)
            throws IOException {

        new Server().go();
    }

    private Thread currentThread;

    private void go()
            throws IOException {

        final AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(5, Executors.defaultThreadFactory());

        final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);

        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 3883);
        listener.bind(hostAddress);

        System.out.println("Server channel bound to port: " + hostAddress.getPort());
        System.out.println("Waiting for client to connect... ");

        currentThread = Thread.currentThread();

        final String att1 = "First connection";

        listener.accept(att1, new CompletionHandler() {

            private void work(AsynchronousSocketChannel ch, Object att) {

                System.out.println("Completed: " + att);
                String msg = handleConnection(ch);

                if (msg.equals("Bye")) {

                    if (! group.isTerminated()) {

                        System.out.println("Terminating the group...");

                        try{
                            group.shutdownNow();
                            group.awaitTermination(10, TimeUnit.SECONDS);
                        }
                        catch (IOException | InterruptedException e) {

                            System.out.println("Exception during group termination");
                            e.printStackTrace();
                        }

                        currentThread.interrupt();
                    }
                }

                att = "Next connection";
                System.out.println("Waiting for - " + att);
                listener.accept(att, this);
            }

            @Override
            public void completed(Object result, Object attachment) {
                work((AsynchronousSocketChannel)result, attachment);
            }

            @Override
            public void failed(Throwable e, Object att) {

                System.out.println(att + " - handler failed");
                e.printStackTrace();
                currentThread.interrupt();
            }
        });

        try {
            currentThread.join();
        }
        catch (InterruptedException e) {
        }

        System.out.println ("Exiting the server");
    }

    private String handleConnection(AsynchronousSocketChannel ch) {

        ByteBuffer buffer = ByteBuffer.allocate(32);
        Future result = ch.read(buffer);
        while (! result.isDone()) {
            // do nothing
        }

        buffer.flip();
        String msg = new String(buffer.array()).trim();
        System.out.println("Message from client: " + msg);
        buffer.clear();

        return msg;
    }
}
