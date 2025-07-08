package Books;

import Books.Interfaces.EmailableProduct;
import Books.Interfaces.SaleableProduct;

public class EBook extends Book implements EmailableProduct, SaleableProduct {
    private String fileType;

    public EBook(String isbn, String title,double price, int publishYear, String fileType) {
        super(isbn, title, price, publishYear);
        this.fileType = fileType;
    }

    @Override
    public boolean isEmailable() {
        return true;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    public String getFileType() {
        return fileType;
    }
}
