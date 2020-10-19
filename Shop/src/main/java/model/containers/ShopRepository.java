package model.containers;

import model.Shop;

import java.util.HashMap;

public class ShopRepository {
    private final HashMap<Long, Shop> shops = new HashMap<>();

    public void addShop(Shop shop) {
        shops.put(shop.getId(), shop);
    }

    public Shop getShopById(long id) {
        return shops.get(id);
    }

    public HashMap<Long, Shop> toMap() {
        return shops;
    }
}
