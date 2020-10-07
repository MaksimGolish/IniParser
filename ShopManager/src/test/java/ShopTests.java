import context.ProductRegistrar;
import controller.ShopManager;
import model.Product;
import model.containers.ProductPack;
import model.Shop;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class ShopTests {
    private String generateName() {
        Random random = new Random();
        return RandomStringUtils
                .randomAlphabetic(random.nextInt(10)+1);
    }
    @Test
    public void testProductCreation() {
        String name = generateName();
        Product product = new Product(name);
        Assert.assertEquals(product.getName(),name);
        Assert.assertTrue(ProductRegistrar.getInstance().isRegistered(product.getId()));
    }

    @Test
    public void testShopCreation() {
        String name = generateName();
        String address = generateName();
        Shop shop = new Shop(name, address);
        Assert.assertEquals(name, shop.getName());
        Assert.assertEquals(address, shop.getAddress());
    }

    @Test
    public void testShopManager() {
        String name = generateName();
        String address = generateName();
        Shop shop = new Shop(name, address);
        String anotherName = generateName();
        String anotherAddress = generateName();
        Shop anotherShop = new Shop(anotherName, anotherAddress);
        ShopManager shopManager = new ShopManager();
        shopManager.addShop(shop);
        shopManager.addShop(anotherShop);
        Assert.assertEquals(shop, shopManager.getShops().getShopById(shop.getId()));
        Assert.assertEquals(anotherShop, shopManager.getShops().getShopById(anotherShop.getId()));
    }

    @Test
    public void tryLowestPrice() {
        // Generate manager, shops and product
        String name = generateName();
        String address = generateName();
        Shop shop = new Shop(name, address);
        String anotherName = generateName();
        String anotherAddress = generateName();
        Shop anotherShop = new Shop(anotherName, anotherAddress);
        Product testProduct = new Product("stuff");
        shop.addProduct(testProduct.getId(), 50L, 100L);
        anotherShop.addProduct(testProduct.getId(), 25L, 200L);
        ShopManager shopManager = new ShopManager();
        shopManager.addShop(shop);
        shopManager.addShop(anotherShop);
        // Check
        Assert.assertEquals(shopManager
                .getCheapestShopsId(testProduct.getId()).size(), 1);
        Assert.assertEquals(shopManager
                .getCheapestShopsId(testProduct.getId()).get(0), anotherShop.getId());
    }

    @Test
    public void tryToBuy() {
        Product cheese = new Product("Cheese");
        Product tv = new Product("TV");
        Product phone = new Product("Phone");
        Shop shop = new Shop("Shop", "Russia");
        shop.addProduct(cheese.getId(), 100L, 50L);
        shop.addProduct(tv.getId(), 1000L, 5L);
        shop.addProduct(phone.getId(), 500L, 3L);
        ProductPack productPack = new ProductPack();
        productPack
                .add(cheese.getId(), 30L)
                .add(tv.getId(), 3L)
                .add(phone.getId(), 3L);

        Long price = shop.buy(productPack);
        // Is final price correct
        Assert.assertEquals(30L*100L + 3L*1000L + 3L*500L, (long)price);
        Assert.assertEquals(50L-30L, (long)shop.getPricing().getQuantity(cheese.getId()));
        Assert.assertEquals(2L, (long)shop.getPricing().getQuantity(tv.getId()));
        Assert.assertFalse(shop.getPricing().exists(phone.getId()));
    }

    @Test
    public void tryLowestPriceForPack() {
        Product cheese = new Product("Cheese");
        Product tv = new Product("TV");
        Product phone = new Product("Phone");
        Shop shop = new Shop("Shop", "USA");
        shop.addProduct(cheese.getId(), 100L, 50L);
        shop.addProduct(tv.getId(), 1000L, 5L);
        shop.addProduct(phone.getId(), 500L, 3L);
        Shop anotherShop = new Shop("Another Shop", "Europe");
        anotherShop.addProduct(cheese.getId(), 10L, 50L);
        anotherShop.addProduct(tv.getId(), 100L, 5L);
        anotherShop.addProduct(phone.getId(), 50L, 3L);
        ProductPack productPack = new ProductPack();
        productPack
                .add(cheese.getId(), 1L)
                .add(tv.getId(), 1L)
                .add(phone.getId(), 1L);
        ShopManager manager = new ShopManager();
        manager.addShop(shop);
        manager.addShop(anotherShop);
        List<Long> result = manager.getCheapestShopsId(productPack);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(anotherShop.getId(), result.get(0));
    }
}
