package ui;

import crud.BookService;
import crud.UserService;
import utils.Utils;

import static utils.Utils.*;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 05:15
 * @project: BookLibrary
 */
public class AdminMenu {

    public static void menu() {
        while (true) {
            Utils.println("Add book         -> 1");
            Utils.println("Remove book      -> 2");
            Utils.println("Show book        -> 3");
            Utils.println("Deadlines        -> 4");
            Utils.println("Manage user      -> 5");
            Utils.println("Back             -> 0");

            String choice = Utils.readText(" => ");

            switch (choice) {
                case "1":
                    BookService.addBook(App.sessionUser);
                    break;
                case "2":
                    BookService.removeBook();
                    break;
                case "3":
                    BookService.showbooks(BookService.getBooks());
                    break;
                case "4":
                    break;
                case "5":
                    manage();
                    break;
                default:
                    Utils.println("Wrong option", Utils.RED);
                    break;
                case "0":
                    return;
            }
        }
    }

    private static void manage() {
        while (true) {
            println("\t\t\t *** User management *** ", YELLOW);
            println("Add user         -> 1");
            println("Remove user      -> 2");
            println("Show users       -> 3");
            println("Show user        -> 4");
            println("Back             -> 0");

            String choice = Utils.readText(" => ");

            switch (choice) {
                case "1":
                    addUser();
                    break;
                case "2":
                    UserService.removeUser(App.sessionUser);
                    break;
                case "3":
                    UserService.showUsers(UserService.getUsers());
                    break;
                case "4":
                    UserService.showUser();
                    break;
                default:
                    Utils.println("Wrong option", Utils.RED);
                    break;
                case "0":
                    return;
            }

        }
    }

    private static void addUser() {

        clear();
        println("\t\t\t *** Registration *** ");

        println("Add admin -> 1");
        println("Add user -> 2");

        String choice = readText(" => ");

        if (choice.equals("1")) {
            if (UserService.addUser(false)) {
                println("User added successfully");
            }
        } else {
            if (UserService.addUser(true)) {
                println("User added successfully");
            }
        }

    }

}
