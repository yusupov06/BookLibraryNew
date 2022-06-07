package crud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Book;
import domain.User;
import domain.UserRole;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static crud.BookService.getBooks;
import static utils.Utils.*;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 04:35
 * @project: BookLibrary
 */
public class UserService {


    public static boolean addUser(boolean isUser) {

        User user = createUser();
//        String[] line = new String[7];
//
//        line[0] = user.getId();
//        line[1] = user.getFirstName();
//        line[2] = user.getLastName();
//        line[3] = user.getUsername();
//        line[4] = user.getPassword();
//        line[5] = user.getRole();
//        line[6] = Boolean.toString(user.isActive());
        if (!isUser) {
            user.setRole(UserRole.ADMIN.name());
        }

        writeUserToFile(user);

        return true;
    }

    private static User createUser() {

        String firstname = readText("Enter your first name: ");
        String secondname = readText("Enter your last name: ");

        String username = readText("Enter your user name: ");
        while (!isUniqueUsername(username)) {
            username = readText("This is used username. Enter your user name: ");
        }
        String pass = readText("Enter password: ");
        User user = new User(firstname, secondname, username, pass, UserRole.USER.toString());
        return user;
    }


    public static boolean writeUserToFile(User user) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        List<User> users = getUsers();

        if (getUsers() == null) {
            users = new ArrayList<>();
        }

        users.add(user);
        write(users);
        return true;
    }

    public static List<User> getUsers() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        List<User> users = new ArrayList<>();

        Type type = new TypeToken<List<User>>() {
        }.getType();

        try {


            users = gson.fromJson(new BufferedReader(new FileReader(usersFile)), type);
            return users;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        List<User> users = new ArrayList<>();
//        try {
//
//            CSVReader reader = new CSVReader(new FileReader(usersFile));
//            Iterator<String[]> iterator = reader.iterator();
//            User user;
//            String[] line;
//            while (iterator.hasNext()){
//                line = iterator.next();
//                user = new User();
//                user.setFirstName(line[1]);
//                user.setLastName(line[2]);
//                user.setUsername(line[3]);
//                user.setPassword(line[4]);
//                user.setRole(line[5]);
//            }
//
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return users;
    }

    private static boolean isUniqueUsername(String username) {
        List<User> users = getUsers();
        if (Objects.isNull(users)) return true;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }


    public static void removeUser(String sessionUser) {

        String userId = readText("Enter user id: ");

        for (User user : getUsers()) {
            if (user.getId().equals(userId)) {
                if (rewriteUserToFile(user, true)) {
                    println("User removed", RED);
                    return;
                } else {
                    println("User can not removed", RED);
                    return;
                }
            }
        }


    }

    public static boolean rewriteUserToFile(User user, boolean removeable) {

        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.remove(i);
                if (!removeable) {
                    users.add(user);
                }
                write(users);
                return true;
            }
        }
        return false;
    }

    private static void write(List<User> users) {
        try (FileWriter writer = new FileWriter(usersFile);) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            writer.write(gson.toJson(users));
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showUsers(List<User> users) {
        /**
         * String id
         *  firstName;
         *  lastName;
         *  username;
         *  password;
         *  role;
         * r numOfRentedBooks;
         * n active = true;
         */
        println("\t\t\t *** Users ***", BLUE);
        String[] texts = {" User ID", " first name ", " last name ", " username ", " password ", " role ", " number of rented books", " Active ",};
        printlnLefts(texts, 45, ' ');

        if (users.size() == 0) {
            println(" \t\t\t\t\t No users her yet ", RED);
        }
        for (User user : users) {
            texts = new String[]{(user.getId()), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getRole(), Integer.toString(user.getNumOfRentedBooks()), Boolean.toString(user.isActive())};
            printlnLefts(texts, 45, ' ');
        }

    }

    public static void showUser() {
        String id = readText("Enter user id: ");

        for (User user : getUsers()) {
            if (user.getId().equals(id)) {

                showSingle(user);

            }
        }
        clear();
        println("User not found", RED);
    }

    private static void showSingle(User user) {

        /**
         * String id = UUID.rando
         *  firstName;
         *  lastName;
         *  username;
         *  password;
         *  role;
         * r numOfRentedBooks = 0
         * n active = true;
         */

        println("Id:                " + user.getId());
        println("First name:        " + user.getFirstName());
        println("Last name:         " + user.getLastName());
        println("Username:          " + user.getUsername());
        println("Password:          " + user.getPassword());
        println("Role:              " + user.getRole());
        println("Number of books:   " + user.getNumOfRentedBooks());
        println("Active:            " + user.isActive());

        println("*** Books ***");

        if (user.getNumOfRentedBooks()==0){
            println("No books here yet");
            readText("Press any key to back: ");
            return;
        }
        int k=1;
        for (Book book : getBooks()) {
            if (book.getRentedTo().equals(user.getId())){
                println((k++) + ") Name: " + book.getName() + " Author: " + book.getAuthor() + " Genre: " + book.getGenre() + " Pages: " + book.getNumOfPages(), PURPLE);
                readText("Press any key to back: ");
            }
        }

    }
}
