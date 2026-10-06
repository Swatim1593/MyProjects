package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Demo8 {

    static void readFile() throws FileNotFoundException {

        FileInputStream fis =
                new FileInputStream("emp.txt");

        System.out.println("File Opened Successfully");
    }

    public static void main(String[] args) {

        try {

            readFile();

        } catch (FileNotFoundException e) {

            System.out.println("File Missing");
        }
    }
}