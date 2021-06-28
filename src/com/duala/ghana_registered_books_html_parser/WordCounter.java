package com.duala.ghana_registered_books_html_parser;

/**
 * Class to count the number of words in a line of code
 *
 * Words are identified by separation with spaces
 */
public class WordCounter extends LineReader implements Runnable{
    private String line;

    WordCounter(String fileLine){
        this.line = fileLine;
    }



    @Override
    public void run() {
        this.wordCount += line.split(" ").length;
    }
}
