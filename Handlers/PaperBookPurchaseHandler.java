package Handlers;

import Handlers.Interfaces.*;
import Books.*;
import Services.*;

class PaperBookPurchaseHandler extends PurchaseHandler {
    private ShippingService shippingService;

    public PaperBookPurchaseHandler(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Override
    public double handlePurchase(Product product, int quantity, Object customerInfo) {
        if (!(product instanceof PaperBook)) {
            throw new IllegalArgumentException("Quantum Book Store: Invalid book type !!");
        }

        PaperBook paperBook = (PaperBook) product;

        if (!paperBook.isForSale()) {
            throw new IllegalStateException(
                    "Quantum Book Store: This book is not for sale: " + paperBook.getTitle() + "!!");
        }

        paperBook.decreaseStock(quantity);
        double totalPrice = paperBook.getPrice() * quantity;
        shippingService.deliver(paperBook, quantity, customerInfo);
        return totalPrice;
    }
}
