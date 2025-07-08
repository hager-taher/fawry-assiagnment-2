package Handlers;

import Books.EBook;
import Books.PaperBook;
import Books.Product;
import Handlers.Interfaces.PurchaseHandler;
import Services.EmailService;
import Services.ShippingService;

public class PurchaseHandlerFactory {

    public static PurchaseHandler createHandler(Product product) {
        if (product instanceof PaperBook) {
            return new PaperBookPurchaseHandler(new ShippingService());
        } else if (product instanceof EBook) {
            return new EBookPurchaseHandler(new EmailService());
        } else {
            throw new IllegalArgumentException("No purchase handler available for this product type !!");
        }
    }

}
