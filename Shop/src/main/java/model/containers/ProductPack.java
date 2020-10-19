package model.containers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductPack {
    // ProductID and quantity
    private final List<Map.Entry<Long, Long>> products = new ArrayList<>();

    public ProductPack add(Long id, Long quantity) {
        products.add(new AbstractMap.SimpleEntry<>(id, quantity));
        return this;
    }

    public List<Map.Entry<Long, Long>> toList() {
        return products;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product pack: \n");
        for(var product : products)
            builder
                    .append("Product ID: ")
                    .append(product.getValue())
                    .append(", quantity: ")
                    .append(product.getValue())
                    .append("\n");
        return builder.toString();
    }
}
