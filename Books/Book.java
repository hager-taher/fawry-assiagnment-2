package Books;

public class Book extends Product {
    protected String isbn;
    protected String title;
    protected String author;
    protected double price;
    protected int publishYear;

    public Book(String isbn, String title, String author, double price, int publishYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishYear = publishYear;
    }

    @Override
    public String getId() {
        return isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getYear() {
        return publishYear;
    }

    public String getAuthor() {
        return author;
    }

}
