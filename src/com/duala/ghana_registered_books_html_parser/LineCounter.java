package com.duala.ghana_registered_books_html_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that reads the number of words and lines in a file
 */
public class LineCounter extends LineReader implements Runnable{
    private File file;

    LineCounter(File r){
        super();

        this.file = r;
    }

    private void openFile()throws FileNotFoundException{
        ArrayList<String> endCharacters = new ArrayList<String>();
        endCharacters.add("\n");
        endCharacters.add("");
        endCharacters.add("[");
        endCharacters.add("]");
        endCharacters.add("{");
        endCharacters.add("}");


        Scanner s = new Scanner(file);

        while (s.hasNext()){
            String line = s.nextLine();



            increasePackedLineCount();
            if(endCharacters.contains(line))
                increaseSpacedLineCount();
            else{
                Thread word = new Thread(new WordCounter(line));
                word.run();
            }

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
