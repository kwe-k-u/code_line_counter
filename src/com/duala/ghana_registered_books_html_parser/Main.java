package com.duala.ghana_registered_books_html_parser;

import javax.sound.sampled.Line;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the root directory: ");
        String path = input.next();
        LineReader reader = new LineReader(path);


        reader.displayStats();

    }
}



