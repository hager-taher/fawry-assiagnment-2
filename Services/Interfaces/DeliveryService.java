package Services.Interfaces;
import Books.Product;
public interface DeliveryService {
    void deliver(Product product, int quantity, Object deliveryInfo);
}