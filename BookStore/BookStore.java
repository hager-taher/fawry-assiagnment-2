package BookStore;
import java.util.*;

import Books.*;
import Books.Interfaces.SaleableProduct;
import Handlers.Interfaces.*;
import Handlers.*;

public class BookStore {

    private List<Product> inventory;

    public BookStore() {
        inventory = new ArrayList<>();
    }

    public void addBook(Product book) {
        Product oldProduct = getBook(book.getId());
        if(oldProduct instanceof PaperBook ){
            ((PaperBook)oldProduct).increaseStock(((PaperBook)book).getStock());
            return ;
        }else if(oldProduct instanceof EBook) {
            return;
        }
        inventory.add(book);
        System.out.println(" Added book: " + book.getTitle() + " (" + book.getId() + ")" + "for : "
                + book.getPrice() + ".LE");
    }

    public Product getBook(String isbn) {
          for (Product book : inventory) {
            if (book.getId().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public double buyBook(String isbn, int quantity, Object customerInfo) {
        Product book = getBook(isbn);

        if (book == null) {
            throw new IllegalArgumentException("Book is not exist !!");
        }

        if (!(book instanceof SaleableProduct))
            throw new IllegalArgumentException("Book is not saleable !!");
        if (!((SaleableProduct) book).isForSale())
            throw new IllegalArgumentException("Book is not saleable !!");

        PurchaseHandler handler = PurchaseHandlerFactory.createHandler(book);
        double totalPrice = handler.handlePurchase(book, quantity, customerInfo);
        return totalPrice;
    }

   public List<Product> removeOutdatedBooks(int maxYears) {
    List<Product> res = new ArrayList<>();
    Iterator<Product> iterator = inventory.iterator();
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    while (iterator.hasNext()) {
        Product book = iterator.next();
        int bookYear = book.getYear();
        if (currentYear - bookYear > maxYears) {
            res.add(book);
            iterator.remove(); 
        }
    }

    return res;
}


    public int getInventoryCount() {
        return inventory.size();
    }
}
