package threading;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class UserService {

    private final Executor executor = Executors.newFixedThreadPool(4);

    public CompletableFuture<List<String>> findUserIdentifiers() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1_000);
            return Arrays.asList("AliceId", "BobId", "CarolId", "DaveId");
        }, executor);
    }

    public CompletableFuture<UserDetail> findUserDetail(String identifier) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1_000);
            return new UserDetail(identifier, identifier.replace("Id", "Name"));
        }, executor);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
