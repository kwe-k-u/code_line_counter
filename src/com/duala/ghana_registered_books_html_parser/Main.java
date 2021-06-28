package com.duala.ghana_registered_books_html_parser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the root directory: ");
        String path = input.nextLine();
        LineReader reader = new LineReader(path);

        reader.displayStats();

    }
}

