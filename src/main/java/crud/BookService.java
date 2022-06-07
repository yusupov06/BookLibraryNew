package crud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import domain.Book;
import domain.BookStatus;
import domain.User;
import utils.Utils;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static crud.UserService.getUsers;
import static crud.UserService.rewriteUserToFile;
import static utils.Utils.*;


/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 04:34
 * @project: BookLibrary
 */

public class BookService {

    public static boolean getbook(String userId) {

        for (User user : getUsers()) {
            if (user.getId().equals(userId)) {
                if (user.getNumOfRentedBooks() >= 5) {
                    Utils.println("You can not get a book. First give back rented book");
                    return false;
                }
            }
        }

        int bookId = readNum("Enter book id: ");
        int days = readNum("Enter book rented days: ");

        for (Book book : getBooks()) {
            if (book.getId()==bookId) {
                if (book.getRentedTo().equals("Noone")) {
                    book.setRentedTo(userId);
                    book.setStatus(BookStatus.RENTED.name());
                    book.setRentedDays(days);
                    book.setRentedDate(LocalDate.now());
                    book.setDeadline(LocalDate.now().plusDays(days));
                    rewriteBookToFile(book,false);
                    println("Successfully rented", PURPLE);

                    for (User user : getUsers()) {
                        if (user.getId().equals(userId)){
                            user.setNumOfRentedBooks(user.getNumOfRentedBooks()+1);
                            rewriteUserToFile(user,false);
                        }
                    }

                    return true;
                }
                println("Book is rented", RED);
                return false;
            }
        }
        println("Book not found", RED);
        return false;
    }

    private static boolean rewriteBookToFile(Book book, boolean removeable) {

        List<Book> books = getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.remove(i);
                if (!removeable){
                    books.add(book);
                }

                write(books);
                return true;
            }
        }
        return false;
    }

    public static boolean addBook(String userId) {

        Book book = createBook(userId);

        if (Objects.isNull(book)) return false;

        writeBookToFile(book);

        return true;
    }

    private static Book createBook(String userId) {

        /**
         *  id;
         *  name;
         *  author;
         *  genre;
         * r numOfPa
         *  rentedTo
         *  givenFro
         *  status;
         */

        Integer id = getBooks().size();
        String name = readText("Enter Book name: ");
        String author = readText("Enter Book author: ");
        String genre = readText("Enter Books genre: ");
        Integer pages = readNum("Enter number of pages: ");

        Book book = Book.builder()
                .id(id)
                .name(name)
                .author(author)
                .genre(genre)
                .numOfPages(pages)
                .rentedTo("Noone")
                .givenFrom(userId)
                .status(BookStatus.FREE.name())
                .rentedDate(null)
                .deadline(null)
                .build();

        return book;
    }

    private static boolean isUniqueBook(String name, String author) {
        for (Book book : getBooks()) {
            if (book.getAuthor() == author && book.getName() == name) {
                return false;
            }
        }
        return true;
    }

    public static boolean writeBookToFile(Book book) {

        List<Book> books = getBooks();

        if (getBooks() == null) {
            books = new ArrayList<>();
        }

        books.add(book);

        write(books);
        return true;
    }

    private static void write(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFile));) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            writer.write(gson.toJson(books));

            writer.flush();

//            CSVWriter writer1 = new CSVWriter(writer,',','\"',',',"\n");
            /**
             *  id;
             *  name;
             *  author;
             *  genre;
             * r numOfPa
             *  rentedTo
             *  givenFro
             *  status;
             */

//            List<String> lines = new ArrayList<>();
//            for (Book book : books) {
//                lines.add("" + book.getId() + "," + book.getName()+ "," +
//                        book.getAuthor()+ "," + book.getGenre()+ "," + book.getNumOfPages() + "," +
//                        book.getRentedTo()+ "," + book.getGivenFrom()+ "," + book.getGivenFrom()+ "," + book.getStatus());
//            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> getBooks() {

        List<Book> books = new ArrayList<>();
//
//        try {
//            CSVReader reader = new CSVReader(new FileReader(booksFile));
//
//            reader.readNext();
//            List<String[]> strings = reader.readAll();
//
//                books.add(getBook(string));
//            }
//
//            return books;
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (CsvValidationException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (CsvException e) {
//            throw new RuntimeException(e);
//        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type type = new TypeToken<List<Book>>(){}.getType();

        try {

            books = gson.fromJson(new BufferedReader(new FileReader(booksFile)), type);

            if (Objects.isNull(books)){
                books = new ArrayList<>();
            }

            return books;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static Book getBook(String[] strings) {

        Book book = Book.builder()
                .id(Integer.parseInt(strings[0]))
                .name(strings[1])
                .author(strings[2])
                .genre(strings[3])
                .numOfPages(Integer.parseInt(strings[4]))
                .rentedTo(strings[5])
                .givenFrom(strings[6])
                .status(strings[7])
                .rentedDays(Integer.parseInt(strings[8]))
                .build();
        return book;
    }

    public static void showbooks(List<Book> books) {
        /**
         *
         *  id;
         * name;
         * author;
         * genre;
         *  numOfPages
         * rentedTo;
         * givenFrom;
         * status;
         *
         */
        println("\t\t\t *** Books ***", BLUE);
        String[] texts = {"Book ID", " Book name ", " author ", " genre ", " number Of Pages ", " Rented to ", " Given from", " Status ", " Rented days", "Rented Date", "DeadLine"};
        printlnLefts(texts, 50, ' ');

        if (books.size()==0) {
            println(" \t\t\t\t\t No books her yet ", RED);
        }

        for (Book book : books) {

            texts = new String[]{(book.getId()).toString(), book.getName(), book.getAuthor(),
                    book.getGenre(),book.getNumOfPages().toString(), book.getRentedTo(), book.getGivenFrom(), book.getStatus(),
                    Integer.toString(book.getRentedDays()),
                    book.getRentedDate()==null?"Not Rented":book.getRentedDate().toString(),
                    book.getDeadline()==null?"Not Rented":book.getDeadline().toString()};

            printlnLefts(texts, 50, ' ');
        }

        readText("Press any keyboard to back: ");

    }

    public static void giveBook(String userId) {
        if (addBook(userId)) {
            println("You added book", PURPLE);
        } else {
            println("You added book", RED);
        }

    }

    public static void showOwn(String userId) {
        List<Book> books = new ArrayList<>();

        for (Book book : getBooks()) {
            if (book.getRentedTo().equals(userId)) {
                books.add(book);
            }
        }

        showbooks(books);

    }

    public static void returnBook() {
        int bookId = readNum("Enter book id: ");
        for (Book book : getBooks()) {
            if (book.getId()==bookId) {
                book.setRentedTo("Noone");
                book.setStatus(BookStatus.FREE.name());
                book.setRentedDays(0);
                if (rewriteBookToFile(book,false)) {
                    println("Book returned");
                    return;
                }
            }
        }
    }

    public static void removeBook() {
        Integer bookId = readNum("Enter book id: ");

        for (Book book : getBooks()) {
            if (book.getId()==bookId) {
                if (rewriteBookToFile(book,true)) {
                    clear();
                    println("Book removed",RED);
                    return;
                } else{
                    clear();
                    println("Book can not removed",RED);
                    return;
                }
            }
        }

    }

}
