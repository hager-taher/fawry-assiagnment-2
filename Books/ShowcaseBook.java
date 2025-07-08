package Books;

import Books.Interfaces.SaleableProduct;

public class ShowcaseBook extends Book implements SaleableProduct {
    public ShowcaseBook(String isbn, String title,  double price, int publishYear) {
        super(isbn, title, price, publishYear);
    }

    @Override
    public boolean isForSale() {
        return false;
    }
}
