
package Test;

import BookStore.BookStore;
import Books.EBook;
import Books.PaperBook;
import Books.Product;
import Books.ShowcaseBook;
import Services.*;
import java.util.Calendar;
import java.util.List;

public class Test {

    static private BookStore bookStore;

    static private void setUp() {
        bookStore = new BookStore();
        System.out.println("\n--- Setting up new test scenario ---");
    }

    static public void testAddBook() {
        setUp();
        System.out.println("Running testAddBook...");
        PaperBook paperBook = new PaperBook("111-2223334445", "The Silent Patient", 32.50, 2025, 8);
        bookStore.addBook(paperBook);
        Product retrievedBook = bookStore.getBook("111-2223334445");

        if (retrievedBook != null && "The Silent Patient".equals(retrievedBook.getTitle())) {
            System.out.println("TestAddBook completed successfully.");
        } else {
            System.out.println("TestAddBook FAILED. Book not found or title mismatch.");
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testBuyPaperBookSuccessfully() {
        setUp();
        System.out.println("Running testBuyPaperBookSuccessfully...");
        PaperBook paperBook = new PaperBook("222-3334445556", "Educated", 18.00, 2025, 6);
        bookStore.addBook(paperBook);

        ShippingInfo shippingInfo = new ShippingInfo("456 Baker St, London, UK");
        double finalPrice = 0.0;
        boolean success = false;
        try {
            finalPrice = bookStore.buyBook("222-3334445556", 2, shippingInfo);
            success = true;
        } catch (IllegalArgumentException e) {
            System.out.println("Exception during testBuyPaperBookSuccessfully: " + e.getMessage());
        }

        if (success && Math.abs(finalPrice - 36.00) < 0.001 && paperBook.getStock() == 4) {
            System.out.println("TestBuyPaperBookSuccessfully completed successfully. Remaining stock: " + paperBook.getStock());
        } else {
            System.out.println("TestBuyPaperBookSuccessfully FAILED. Final price or stock incorrect. Price: " + finalPrice + ", Stock: " + paperBook.getStock());
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testBuyEBookSuccessfully() {
        setUp();
        System.out.println("Running testBuyEBookSuccessfully...");
        EBook eBook = new EBook("333-4445556667", "Atomic Habits", 22.00, 2025, "EPUB");
        bookStore.addBook(eBook);

        EmailInfo emailInfo = new EmailInfo("reader@domain.com");
        double finalPrice = 0.0;
        boolean success = false;
        try {
            finalPrice = bookStore.buyBook("333-4445556667", 1, emailInfo);
            success = true;
        } catch (IllegalArgumentException e) {
            System.out.println("Exception during testBuyEBookSuccessfully: " + e.getMessage());
        }

        if (success && Math.abs(finalPrice - 22.00) < 0.001) {
            System.out.println("testBuyEBookSuccessfully completed successfully.");
        } else {
            System.out.println("testBuyEBookSuccessfully FAILED. Final price incorrect. Price: " + finalPrice);
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testBuyBookInsufficientStock() {
        setUp();
        System.out.println("Running testBuyBookInsufficientStock...");
        PaperBook paperBook = new PaperBook("444-5556667778", "Becoming", 20.00, 2025, 1);
        bookStore.addBook(paperBook);

        ShippingInfo shippingInfo = new ShippingInfo("789 Maple Rd, Toronto, Canada");
        boolean exceptionThrown = false;
        String exceptionMessage = "";
        try {
            bookStore.buyBook("444-5556667778", 5, shippingInfo);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }

        if (exceptionThrown && exceptionMessage.contains("The requested quantity is greater than the stock you request 5 and in stock only 1") && paperBook.getStock() == 1) {
            System.out.println("testBuyBookInsufficientStock completed successfully. Stock remains: " + paperBook.getStock());
        } else {
            System.out.println("testBuyBookInsufficientStock FAILED. No exception or wrong message, or stock changed. Message: " + exceptionMessage + ", Stock: " + paperBook.getStock());
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testBuyNonExistentBook() {
        setUp();
        System.out.println("Running testBuyNonExistentBook...");
        ShippingInfo shippingInfo = new ShippingInfo("101 Riverwalk Dr, Paris, France");
        boolean exceptionThrown = false;
        String exceptionMessage = "";
        try {
            bookStore.buyBook("000-0000000000", 1, shippingInfo);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }

        if (exceptionThrown && exceptionMessage.contains("Book is not exist !!")) {
            System.out.println("testBuyNonExistentBook completed successfully.");
        } else {
            System.out.println("testBuyNonExistentBook FAILED. No exception or wrong message. Message: " + exceptionMessage);
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testBuyShowCaseBook() {
        setUp();
        System.out.println("Running testBuyShowCaseBook...");
        ShowcaseBook showCaseBook = new ShowcaseBook("555-6667778889", "The Mona Lisa Mystery (Showcase)",
                75.00, 2025);
        bookStore.addBook(showCaseBook);

        ShippingInfo shippingInfo = new ShippingInfo("500 Museum St, Florence, Italy");
        boolean exceptionThrown = false;
        String exceptionMessage = "";
        try {
            bookStore.buyBook("555-6667778889", 1, shippingInfo);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }

        if (exceptionThrown && exceptionMessage.contains("Book is not saleable !!")) {
            System.out.println("testBuyShowCaseBook completed successfully.");
        } else {
            System.out.println("testBuyShowCaseBook FAILED. No exception or wrong message. Message: " + exceptionMessage);
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testRemoveAndReturnOutDatedBooks() {
        setUp();
        System.out.println("Running testRemoveAndReturnOutDatedBooks...");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -3);
        PaperBook oldBook1 = new PaperBook("OLD-A1", "Ancient Civilizations", 12.00, 2022, 4); // Adjusted year to be outdated
        bookStore.addBook(oldBook1);

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -6);
        EBook oldBook2 = new EBook("OLD-B2", "Old Tech Manual", 25.00, 2019, "MOBI"); // Adjusted year to be outdated
        bookStore.addBook(oldBook2);

        PaperBook currentBook = new PaperBook("NEW-C3", "The Future of AI", 40.00, 2025, 7);
        bookStore.addBook(currentBook);

        List<Product> removedBooks = bookStore.removeOutdatedBooks(2); // Assuming threshold is 2 years

        boolean testPassed = true;
        if (removedBooks.size() != 2) {
            System.out.println("testRemoveAndReturnOutDatedBooks FAILED. Incorrect number of removed books: " + removedBooks.size());
            testPassed = false;
        }
        if (!removedBooks.contains(oldBook1) || !removedBooks.contains(oldBook2)) {
            System.out.println("testRemoveAndReturnOutDatedBooks FAILED. Removed list does not contain expected books.");
            testPassed = false;
        }
        if (bookStore.getBook("OLD-A1") != null || bookStore.getBook("OLD-B2") != null) {
            System.out.println("testRemoveAndReturnOutDatedBooks FAILED. Outdated books still in inventory.");
            testPassed = false;
        }
        if (bookStore.getBook("NEW-C3") == null) {
            System.out.println("testRemoveAndReturnOutDatedBooks FAILED. Current book was removed.");
            testPassed = false;
        }

        if (testPassed) {
            System.out.println("testRemoveAndReturnOutDatedBooks completed successfully. Removed books count: " + removedBooks.size());
        } else {
            System.out.println("testRemoveAndReturnOutDatedBooks FAILED.");
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testDecreaseAndIncreaseStock() {
        setUp();
        System.out.println("Running testDecreaseAndIncreaseStock...");
        PaperBook paperBook = new PaperBook("STK-777", "Inventory Mastery", 14.50, 2025, 9);
        bookStore.addBook(paperBook);

        paperBook.decreaseStock(4);
        if (paperBook.getStock() == 5) {
            System.out.println("Stock decreased successfully to 5.");
        } else {
            System.out.println("Stock decrease FAILED. Expected 5, got " + paperBook.getStock());
        }

        paperBook.increaseStock(6);
        if (paperBook.getStock() == 11) {
            System.out.println("Stock increased successfully to 11.");
        } else {
            System.out.println("Stock increase FAILED. Expected 11, got " + paperBook.getStock());
        }

        boolean exceptionThrown = false;
        String exceptionMessage = "";
        try {
            paperBook.decreaseStock(20);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            exceptionMessage = e.getMessage();
        }

        if (exceptionThrown && exceptionMessage.contains("The requested quantity is greater than the stock")
                && paperBook.getStock() == 11) {
            System.out.println("testDecreaseAndIncreaseStock (insufficient decrease) completed successfully. Stock remains: " + paperBook.getStock());
        } else {
            System.out.println("testDecreaseAndIncreaseStock (insufficient decrease) FAILED. No exception or wrong message, or stock changed. Message: " + exceptionMessage + ", Stock: " + paperBook.getStock());
        }
        System.out.println("testDecreaseAndIncreaseStock completed. Final stock: " + paperBook.getStock());
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void testGetBookNotFound() {
        setUp();
        System.out.println("Running testGetBookNotFound...");
        Product book = bookStore.getBook("UNKNOWN-ISBN-1234");
        if (book == null) {
            System.out.println("testGetBookNotFound completed successfully.");
        } else {
            System.out.println("testGetBookNotFound FAILED. Book found when it should not be.");
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    static public void run() {
        System.out.println("=================================================================================");
        System.out.println("                         Starting all tests...");
        System.out.println("=================================================================================");

        testAddBook();
        testBuyPaperBookSuccessfully();
        testBuyEBookSuccessfully();
        testBuyBookInsufficientStock();
        testBuyNonExistentBook();
        testBuyShowCaseBook();
        testRemoveAndReturnOutDatedBooks();
        testDecreaseAndIncreaseStock();
        testGetBookNotFound();

        System.out.println("\n=================================================================================");
        System.out.println("                            All tests finished.");
        System.out.println("=================================================================================");
    }
}
