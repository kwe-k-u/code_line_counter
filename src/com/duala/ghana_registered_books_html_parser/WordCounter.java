package com.duala.ghana_registered_books_html_parser;

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
