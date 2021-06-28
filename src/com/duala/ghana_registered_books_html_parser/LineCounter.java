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

    /**
     * Default constructor
     * @param r file to be analysed
     */
    LineCounter(File r){
        super();

        this.file = r;
    }

    /**
     * Opens the passed file and iterates through the lines, updating line counts and creating
     * new instances of word counts for each line
     * @throws FileNotFoundException
     */
    private void openFile()throws FileNotFoundException{
        /** List of characters that pass as end characters and not actual code*/
        ArrayList<String> endCharacters = new ArrayList<String>();
        endCharacters.add("\n");
        endCharacters.add("");
        endCharacters.add("[");
        endCharacters.add("]");
        endCharacters.add("{");
        endCharacters.add("}");
        endCharacters.add(";");
        endCharacters.add(",");
        endCharacters.add("'");
        endCharacters.add("\"");


        Scanner s = new Scanner(file);

        while (s.hasNext()){//while more lines exist in the file
            String line = s.nextLine().trim();



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
