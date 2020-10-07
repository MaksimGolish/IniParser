package model;

import context.ProductRegistrar;

public class Product {
    private static Long nextId = 0L;
    private final Long id;
    private String name;

    public Product(String name) {
        this.id = nextId;
        this.name = name;
        nextId++;
        ProductRegistrar.getInstance().register(this);
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
}
