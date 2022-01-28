package client;

import io.grpc.ManagedChannelBuilder;
import product.api.v1.ProductApi;
import product.api.v1.ProductInfoGrpc;

public class ProductInfoClient {

    public static void main(String... args) {
        var channel = ManagedChannelBuilder.forAddress("localhost", 5051).usePlaintext().build();
        var stub = ProductInfoGrpc.newBlockingStub(channel);
        var productId = stub.addProduct(
                ProductApi.Product.newBuilder()
                .setName("iPhone 13")
                .setDescription("New iPhone")
                .build()
        );
        System.out.println("Get product id: " + productId.getValue());
        var product = stub.getProduct(productId);
        System.out.println("Get product by id: " + product);
        channel.shutdown();
    }
}
