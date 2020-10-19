import model.Product;
import model.Shop;
import model.containers.ProductPack;

public class Main {
    public static void main(String[] args) {
        Product cheese = new Product("Cheese");
        Product tv = new Product("TV");
        Product phone = new Product("Phone");
        Product glass = new Product("Glass");
        Product table = new Product("Table");

        Shop walmart = new Shop("Walmart", "USA");
        Shop amazon = new Shop("Amazon", "USA");

        walmart.addProduct(cheese.getId(), 100L, 100L);
        walmart.addProduct(tv.getId(), 1000L, 10L);
        walmart.addProduct(phone.getId(), 1500L, 5L);

        amazon.addProduct(phone.getId(), 999L, 10L);
        amazon.addProduct(glass.getId(), 10L, 1000L);
        amazon.addProduct(table.getId(), 150L, 1L);

        ProductPack firstPack = new ProductPack();
        ProductPack secondPack = new ProductPack();
        firstPack
                .add(cheese.getId(), 10L)
                .add(tv.getId(), 1L);
        secondPack
                .add(phone.getId(), 3L)
                .add(table.getId(), 1L);
        System.out.println("Price for 1st pack: " + walmart.buy(firstPack));
        System.out.println("Price for 2nd pack: " + amazon.buy(secondPack));

        ProductPack illegalPack = new ProductPack();
        illegalPack
                .add(cheese.getId(), 1L)
                .add(table.getId(), 1L);
        try {
            walmart.buy(illegalPack);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(walmart.getProductsByPrice(100L));
    }
}
