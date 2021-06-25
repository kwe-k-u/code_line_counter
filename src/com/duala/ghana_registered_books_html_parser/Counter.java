package com.duala.ghana_registered_books_html_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that reads the number of words and lines in a file
 */
public class Counter extends LineReader{
    private File file;
//    int numberWords = 0;
//    int numLines = 0;

    Counter(File r){
        super();
        System.out.println("Prints");
        System.out.println(this.packedLineCount);
        System.out.println(this.spacedLineCount);

        this.file = r;
        try {
            openFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openFile()throws FileNotFoundException{
        Scanner s = new Scanner(file);

        while (s.hasNext()){
            String line = s.nextLine();
            if(line.compareTo("") == 0 ||line.compareTo("\n") == 0 )
                increaseSpacedLineCount();

            increasePackedLineCount();

        }

    }
}
