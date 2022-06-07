package utils;

import java.io.File;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 04:33
 * @project: BookLibrary
 */
public class Utils {

    static Scanner scannerLine = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void println(Object obj) {
        println(obj, GREEN);
    }

    public static void println(Object obj, String color) {
        System.out.println(color + obj + RESET);
    }

    public static void print(Object obj) {
        print(obj, GREEN);
    }

    public static void print(Object obj, String color) {
        System.out.print(color + obj + RESET);
    }

    public static void printLeft(String text,int space, char  with){
        print(text,PURPLE);
        for (int i = 0; i < (space - text.length()); i++) {
            print(" ");
        }
    }

    public static void printlnLefts(String[] texts, int space, char  with){
        for (String text : texts) {
            printLeft(text,space,with);
        }
        println("");
    }

    public static String readText(String data) {
        print(data);
        return scannerLine.nextLine();
    }

    public static Integer readNum(String data) {
        print(data);
        return scannerInt.nextInt();
    }

    public static String genSecretId() {
        return UUID.randomUUID().toString();
    }

    public static String getRowName(int columnNumber){
        String res = "";
        StringBuilder st = new StringBuilder(res);

        while(columnNumber != 0){

            char val = (char)((columnNumber - 1) % 26 +65);

            columnNumber = (columnNumber -1 )/26;
            st.append(val);


        }

        return st.reverse().toString();
    }

    public static String[] printRows(int n) {
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            int columnNumber = 1 + i;
            StringBuilder columnName = new StringBuilder();
            while (columnNumber > 0) {
                int rem = columnNumber % 26;
                if (rem == 0) {
                    columnName.append("Z");
                    columnNumber = (columnNumber / 26) - 1;
                } else {
                    columnName.append((char) ((rem - 1) + 'A'));
                    columnNumber = columnNumber / 26;
                }
            }
            res[i] = columnName.reverse().toString();
        }
        return res;
    }

    public static int titleToNumber(String columnTitle) {
        int res = 0;

        for (char c : columnTitle.toCharArray()) {
            res = res * 26 + c - 'A' + 1;
        }

        return res;
    }

    public static void clear(){
        for (int i = 0; i < 20; i++) {
            println("");
        }
    }


    public static File usersFile = new File("src\\main\\resources\\users.json");
    public static File booksFile = new File("src\\main\\resources\\books.json");

    public static File booksFileCSV = new File("src\\main\\resources\\books.json");




}
