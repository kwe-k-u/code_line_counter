package com.duala.ghana_registered_books_html_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that reads the number of words and lines in a file
 */
public class Counter extends LineReader implements Runnable{
    private File file;

    Counter(File r){
        super();

        this.file = r;
    }

    private void openFile()throws FileNotFoundException{
        Scanner s = new Scanner(file);

        while (s.hasNext()){
            String line = s.nextLine();



            increasePackedLineCount();
            if(line.compareTo("") == 0 ||line.compareTo("\n") == 0 )
                increaseSpacedLineCount();


        }

    }

    @Override
    public void run() {

        try {
            openFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
