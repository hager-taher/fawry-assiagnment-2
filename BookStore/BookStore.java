package BookStore;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import Books.Product;
import Books.Interfaces.SaleableProduct;
import Handlers.Interfaces.*;
import Handlers.*;

public class BookStore {

    private Map<String, Product> inventory;

    public BookStore() {
        inventory = new HashMap<>();
    }

    public void addBook(Product book) {
        inventory.put(book.getId(), book);
        System.out.println("Quantum book store added book: " + book.getTitle() + " (" + book.getId() + ")" + "for : "
                + book.getPrice() + ".LE");
    }

    public Product getBook(String isbn) {
        for (Map.Entry<String, Product> entry : inventory.entrySet()) {
            if (entry.getKey().equals(isbn)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public double buyBook(String isbn, int quantity, Object customerInfo) {
        Product book = getBook(isbn);

        if (book == null) {
            throw new IllegalArgumentException("Quantum book store: book with ISBN " + isbn + " is not found !!");
        }

        if (!(book instanceof SaleableProduct))
            throw new IllegalArgumentException("Quantum book store: book is not saleable");
        if (!((SaleableProduct) book).isForSale())
            throw new IllegalArgumentException("Quantum book store: book is not saleable");

        PurchaseHandler handler = PurchaseHandlerFactory.createHandler(book);
        double totalPrice = handler.handlePurchase(book, quantity, customerInfo);
        return totalPrice;
    }

    public List<Product> removeOutdatedBooks(int maxYears) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Product> outdatedBooks = new ArrayList<>();
        List<String> toRemove = new ArrayList<>();

        for (Map.Entry<String, Product> entry : inventory.entrySet()) {
            Product book = entry.getValue();
            if (currentYear - book.getYear() > maxYears) {
                outdatedBooks.add(book);
                toRemove.add(entry.getKey());
            }
        }

        for (String isbn : toRemove) {
            inventory.remove(isbn);
        }

        return outdatedBooks;
    }

    public int getInventoryCount() {
        return inventory.size();
    }
}
