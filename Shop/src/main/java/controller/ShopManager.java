package controller;

import exception.NotEnoughProductsException;
import exception.ProductNotFoundException;
import model.Shop;
import model.containers.ProductPack;
import model.containers.ShopRepository;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {
    private final ShopRepository repository;

    public ShopManager() {
        repository = new ShopRepository();
    }

    public ShopManager(ShopRepository repository) {
        this.repository = repository;
    }

    public void addShop(Shop shop) {
        repository.addShop(shop);
    }

    public List<Long> getCheapestShopsId(Long productId) {
        List<Long> shopsList = new ArrayList<>();
        Long lowestPrice = Long.MAX_VALUE;
        for(var shopEntry : repository.toMap().entrySet()) {
            if(!shopEntry.getValue().getPricing().exists(productId))
                continue;
            Long price = shopEntry.getValue()
                    .getPricing()
                    .getPrice(productId);
            if (price < lowestPrice) {
                shopsList.clear();
                shopsList.add(shopEntry.getKey());
                lowestPrice = price;
            } else if (price.equals(lowestPrice)) {
                shopsList.add(shopEntry.getKey());
            }
        }
        if(!shopsList.isEmpty())
            return shopsList;
        else
            throw new ProductNotFoundException(productId);
    }

    public List<Long> getCheapestShopsId(ProductPack productPack) {
        List<Long> shopList = new ArrayList<>();
        long lowestPrice = Long.MAX_VALUE;
        boolean productFound;
        for(var shop : repository.toMap().entrySet()) {
            productFound = true;
            long localPrice = 0L;
            for(var product : productPack.toList()) {
                if(!shop.getValue().getPricing().exists(product.getKey()) ||
                        product.getValue() > shop.getValue().getPricing().getQuantity(product.getKey())) {
                    productFound = false;
                    break;
                } else {
                    localPrice += shop.getValue()
                            .getPricing()
                            .getPrice(product.getKey()) * product.getValue();
                }
            }
            if(productFound) {
                if(localPrice < lowestPrice) {
                    shopList.clear();
                    shopList.add(shop.getKey());
                    lowestPrice = localPrice;
                } else if(localPrice == lowestPrice) {
                    shopList.add(shop.getKey());
                }
            }
        }
        if(!shopList.isEmpty())
            return shopList;
        else
            throw new NotEnoughProductsException();
    }

    public ShopRepository getShops() {
        return repository;
    }
}
