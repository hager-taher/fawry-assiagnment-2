package Services;

import Services.Interfaces.*;
import Books.*;

public class EmailService implements DeliveryService {
    @Override
    public void deliver(Product product, int quantity, Object deliveryInfo) {
        if (!(product instanceof EBook) || !(deliveryInfo instanceof EmailInfo)) {
            throw new IllegalArgumentException(
                    "Quantum Book Store: Invalid product or delivery info for email delivery");
        }

        EBook eBook = (EBook) product;
        EmailInfo emailInfo = (EmailInfo) deliveryInfo;
        System.out.println("Quantum Book Store: Delivering the product : \"" + eBook.getTitle() + "\" ("
                + eBook.getFileType() + ") to this email : " + emailInfo.getEmail());
    }
}