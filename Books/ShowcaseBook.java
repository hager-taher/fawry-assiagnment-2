package Books;

import Books.Interfaces.SaleableProduct;

public class ShowcaseBook extends Book implements SaleableProduct {
    public ShowcaseBook(String isbn, String title, String author, double price, int publishYear) {
        super(isbn, title, author, price, publishYear);
    }

    @Override
    public boolean isForSale() {
        return false;
    }
}
