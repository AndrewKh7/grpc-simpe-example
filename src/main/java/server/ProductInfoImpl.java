package server;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import product.api.v1.ProductApi;
import product.api.v1.ProductApi.Product;
import product.api.v1.ProductInfoGrpc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {
    private final Map<String, Product> bd = new HashMap<>();

    @Override
    public void addProduct(Product request,
                           StreamObserver<ProductApi.ProductID> response) {
        var uuid = UUID.randomUUID();
        var randomId = uuid.toString();
        bd.put(randomId, request);
        var id = ProductApi.ProductID.newBuilder().setValue(randomId).build();
        response.onNext(id);
        response.onCompleted();
    }

    @Override
    public void getProduct(ProductApi.ProductID request,
                           StreamObserver<Product> response) {
        var id = request.getValue();
        if (bd.containsKey(id)) {
            response.onNext(bd.get(id));
            response.onCompleted();
        } else {
            response.onError(new StatusException(Status.NOT_FOUND));
        }
    }
}
