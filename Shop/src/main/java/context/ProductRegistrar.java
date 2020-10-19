package context;

import exception.ProductIsAlreadyRegisteredException;
import model.Product;

import java.util.HashMap;

public class ProductRegistrar {
    private final HashMap<Long, Product> registered = new HashMap<>();
    private static ProductRegistrar instance;

    private ProductRegistrar() {
    }

    public static ProductRegistrar getInstance() {
        if(instance==null)
            instance = new ProductRegistrar();
        return instance;
    }

    public void register(Product product) {
        if(!registered.containsKey(product.getId())) {
            registered.put(product.getId(), product);
        } else {
            throw new ProductIsAlreadyRegisteredException(product.getId());
        }
    }

    public Product getProduct(Long id) {
        return registered.get(id);
    }

    public void setProductName(Long id, String name) {
        registered.get(id).setName(name);
    }

    public boolean isRegistered(Long id) {
        return registered.containsKey(id);
    }
}