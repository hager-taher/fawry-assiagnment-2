package Services;

import Services.Interfaces.*;
import Books.*;

public class ShippingService implements DeliveryService {
    @Override
    public void deliver(Product product, int quantity, Object deliveryInfo) {
        if (!(product instanceof PaperBook) || !(deliveryInfo instanceof ShippingInfo)) {
            throw new IllegalArgumentException("Quantum Book Store: Invalid product or delivery info for shipping");
        }

        ShippingInfo shippingInfo = (ShippingInfo) deliveryInfo;
        PaperBook paperBook = (PaperBook) product;
        System.out.println("Quantum Book Store: Shipping " + quantity + "x \"" + paperBook.getTitle() + "\" to "
                + shippingInfo.getAddress());
    }
}
