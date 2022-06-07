package ui;

import crud.BookService;
import utils.Utils;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 05:15
 * @project: BookLibrary
 */
public class UserMenu {

    public static void menu() {
        while (true) {
            Utils.println("Get book            -> 1");
            Utils.println("Show books          -> 2");
            Utils.println("Give book           -> 3");
            Utils.println("Show own rent books -> 4");
            Utils.println("Return book         -> 5");
            Utils.println("Back                -> 0");

            String choice = Utils.readText(" => ");

            switch (choice) {
                case "1":
                    BookService.getbook(App.sessionUser);
                    break;
                case "2":
                    BookService.showbooks(BookService.getBooks());
                    break;
                case "3":
                    BookService.giveBook(App.sessionUser);
                    break;
                case "4":
                    BookService.showOwn(App.sessionUser);
                    break;
                case "5":
                    BookService.returnBook();
                    break;
                default:
                Utils.println("Wrong option", Utils.RED);
                break;
                case "0": return;
            }
        }
    }


}
