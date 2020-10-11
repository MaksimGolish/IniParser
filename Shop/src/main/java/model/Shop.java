package model;

import exception.*;
import model.containers.Pricing;
import model.containers.ProductPack;

public class Shop {
    private static Long nextid = 0L;
    private final Long id;
    private String name;
    private String address;
    private Pricing pricing;

    public Shop(String name, String address) {
        this.id = nextid;
        this.name = name;
        this.address = address;
        nextid++;
        pricing = new Pricing();
    }

    public Long buy(ProductPack order) {
        long finalPrice = 0L;
        for(var productOrder : order.toList()) {
            if(!pricing.exists(productOrder.getKey()))
                throw new NotEnoughProductsException();
            if(pricing.getQuantity(productOrder.getKey()) - productOrder.getValue() < 0)
                throw new NotEnoughProductsException();
            long price = pricing.getPrice(productOrder.getKey()) * productOrder.getValue();
            pricing.setQuantity(productOrder.getKey(),
                    pricing.getQuantity(productOrder.getKey()) - productOrder.getValue());
            finalPrice += price;
        }
        return finalPrice;
    }

    public ProductPack getProductsByPrice(Long price) {
        ProductPack productPack = new ProductPack();
        for(var product : pricing.toMap().entrySet()) {
            if(product.getValue().getKey() <= price && product.getValue().getValue() != 0)
                productPack.add(product.getKey(),
                        Long.min(price / product.getValue().getKey(),
                                pricing.getQuantity(product.getKey())));
        }
        return productPack;
    }

    public void addProduct(Long id, Long price, Long quantity) {
        if(price < 0)
            throw new IllegalPriceException();
        if(quantity <= 0)
            throw new IllegalQuantityException();
        pricing.addPosition(id, price, quantity);
    }

    public void changePrice(Long id, Long newPrice) {
        pricing.setPrice(id, newPrice);
    }

    public void changeQuantity(Long id, Long newQuantity) {
        pricing.setQuantity(id, newQuantity);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }
}
