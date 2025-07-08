package Books;

import Books.Interfaces.SaleableProduct;
import Books.Interfaces.ShippableProduct;

public class PaperBook extends Book implements ShippableProduct, SaleableProduct {
    private int stock;

    public PaperBook(String isbn, String title, double price, int publishYear, int stock) {
        super(isbn, title ,price, publishYear);
        this.stock = stock;
    }

    @Override
    public boolean isShippable() {
        return stock > 0;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    public int getStock() {
        return stock;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        }else
            throw new IllegalArgumentException("The requested quantity is greater than the stock you request " + quantity + " and in stock only " + stock);
    }

}
