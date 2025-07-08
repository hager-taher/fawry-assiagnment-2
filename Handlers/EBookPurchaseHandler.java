package Handlers;

import Handlers.Interfaces.*;
import Services.*;
import Books.*;

class EBookPurchaseHandler extends PurchaseHandler {
    private EmailService emailService;

    public EBookPurchaseHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public double handlePurchase(Product product, int quantity, Object customerInfo) {
        if (!(product instanceof EBook)) {
            throw new IllegalArgumentException("Invalid book type !!");
        }

        EBook eBook = (EBook) product;

        if (!eBook.isForSale()) {
            throw new IllegalStateException(
                    "THIS book is not for sale: " + eBook.getTitle() + "!!");
        }

        double totalPrice = eBook.getPrice() * quantity;
        emailService.deliver(eBook, quantity, customerInfo);
        return totalPrice;
    }
}
