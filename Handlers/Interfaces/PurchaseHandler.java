package Handlers.Interfaces;

import Books.*;

public abstract class PurchaseHandler {
    public abstract double handlePurchase(Product product, int quantity, Object customerInfo);
}