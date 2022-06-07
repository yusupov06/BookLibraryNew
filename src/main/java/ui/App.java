package ui;

import crud.UserService;
import domain.User;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static crud.UserService.addUser;
import static crud.UserService.getUsers;
import static utils.Utils.*;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 04:33
 * @project: BookLibrary
 */
public class App {



    public static String sessionUser;



    public static void main(String[] args) {

//        {
//
//            try {
//                usersFile.createNewFile();
//                booksFile.createNewFile();
//                addUser(false);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }


            println("\t\t\t Book Library",Utils.GREEN);

            println(    "Sign In     -> 1");
            println("Quit        -> 3",Utils.RED);

            String choice = readText("=> ");
            switch (choice){

                case "1"-> {

                    clear();
                    println("\t\t\t *** Sign In ***");
                    String username = readText("\n\t Enter your username: ");
                    String pass = readText("\n\t Enter your password: ");

                    for (User user : getUsers()) {
                        if (user.getUsername().equals(username) && user.getPassword().equals(pass)){
                            sessionUser = user.getId();
                            if(user.getRole().equals("ADMIN")){
                                clear();
                                AdminMenu.menu();
                            } else {
                                clear();
                                UserMenu.menu();
                            }
                        }
                    }
//                    println("User not found", RED);

                }
                case "q" -> {
                    clear();
                    println("See you");
                    System.exit(0);
                }
                default -> {
                    clear();
                    println("Wrong choice", Utils.RED);
                }

            }
        main(args);
    }




}
