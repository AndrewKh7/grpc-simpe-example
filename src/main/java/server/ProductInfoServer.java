package server;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class ProductInfoServer {

    public static void main(String... args) throws InterruptedException, IOException {
        var port = 5051;
        var server = ServerBuilder.forPort(port)
                .addService(new ProductInfoImpl())
                .build()
                .start();
        System.out.println("Server started!");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down.");
            if(server != null) {
                server.shutdown();
            }
        }));
        server.awaitTermination();
    }
}
