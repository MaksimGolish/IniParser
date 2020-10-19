package model.containers;

import exception.IllegalPriceException;
import exception.IllegalQuantityException;
import exception.ProductNotFoundException;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

// Contains prices and quantity. 
public class Pricing {
    private final HashMap<Long, Map.Entry<Long, Long>> prices = new HashMap<>();

    public void addPosition(Long id, Long price, Long quantity) {
        prices.put(id, new AbstractMap.SimpleEntry<>(price, quantity));
    }

    public Long getPrice(Long id) {
        if(prices.containsKey(id)){
            return prices.get(id).getKey();
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    public Long getQuantity(Long id) {
        if(prices.containsKey(id)){
            return prices.get(id).getValue();
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    public void setPrice(Long id, Long newPrice) {
        if(newPrice<0)
            throw new IllegalPriceException();
        if(!prices.containsKey(id))
            throw new ProductNotFoundException(id);
        prices.replace(id, new AbstractMap.SimpleEntry<>(newPrice, prices.get(id).getValue()));
    }

    public void setQuantity(Long id, Long newQuantity) {
        if(newQuantity<0)
            throw new IllegalQuantityException();
        if(!prices.containsKey(id))
            throw new ProductNotFoundException(id);
        if(newQuantity!=0)
            prices.replace(id, new AbstractMap.SimpleEntry<>(prices.get(id).getKey(), newQuantity));
        else
            prices.remove(id);
    }

    public boolean exists(Long id) {
        return prices.containsKey(id);
    }

    public HashMap<Long, Map.Entry<Long, Long>> toMap() {
        return prices;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(var pos : prices.entrySet()) {
            builder.append("Id: ")
                    .append(pos.getKey())
                    .append(", price: ")
                    .append(pos.getValue().getKey())
                    .append(", available: ")
                    .append(pos.getValue().getValue())
                    .append("\n");
        }
        return builder.toString();
    }
}
