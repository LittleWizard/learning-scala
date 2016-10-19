package threading;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Concurrency {

    public static void main(String ... args) {

        final UserService service = new UserService();

        LocalDateTime start = LocalDateTime.now();

        CompletableFuture<List<UserDetail>> userDetailsFuture = service.findUserIdentifiers()
                .thenCompose(identifiers -> {

                    final List<CompletableFuture<UserDetail>> details = identifiers.stream()
                            .map(service::findUserDetail)
                            .collect(Collectors.toList());

                    return CompletableFuture.allOf(details.toArray(new CompletableFuture[details.size()]))
                            .thenApply(aVoid -> details.stream().map(CompletableFuture::join).collect(Collectors.toList()));
                });

        LocalDateTime beforeJoin = LocalDateTime.now();

        List<UserDetail> userDetails = userDetailsFuture.join();

        LocalDateTime end = LocalDateTime.now();

        final String format = "%-12s: %s%n";

        System.out.printf(format, "start", DateTimeFormatter.ISO_LOCAL_TIME.format(start));
        System.out.printf(format, "before-join", DateTimeFormatter.ISO_LOCAL_TIME.format(beforeJoin));
        System.out.printf(format, "end", DateTimeFormatter.ISO_LOCAL_TIME.format(end));

        userDetails.forEach(System.out::println);

        System.exit(0);

    }
}
