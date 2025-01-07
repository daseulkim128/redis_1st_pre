import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Order {
    private long userId;
    private final String productName;
    private int orderQuantity;

    ConcurrentHashMap<String, Map<String, Integer>> latestOrderDatabase = new ConcurrentHashMap<>();

    Order(long userId, String productName, int orderQuantity){
        this.userId = userId;
        this.productName = productName;
        this.orderQuantity = orderQuantity;
    }
    void order (Order order){

        latestOrderDatabase.compute(String.valueOf(order.getUserId()), (key, existingMap) -> {
            if (existingMap == null) {
                existingMap = new ConcurrentHashMap<>();
            }
            existingMap.put(order.getProductName(), order.getOrderQuantity());
            return existingMap;
        });

       /* Map<String, Integer> map = new HashMap<>();
        map.put(order.getProductName(),order.getOrderQuantity());
        latestOrderDatabase.put(String.valueOf(order.userId),map);*/
    }



}
