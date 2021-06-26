package com.duala.ghana_registered_books_html_parser;

import java.io.File;
import java.io.IOException;

public class LineReader{
    private static File rootDirectory;
    static public int emptyLineCount = 0;
    static public int packedLineCount = 0;


    void increasePackedLineCount(){
        this.packedLineCount++;

    }

    void increaseSpacedLineCount(){
        this.emptyLineCount++;
    }


    LineReader(){}
    LineReader(String p){
        this.rootDirectory = new File(p);
        try {
            exploreDirectory(this.rootDirectory);
        } catch (Exception e){
            System.out.println(e);
        }



    }

    private void exploreDirectory(File file) throws IOException {

        if (file.isDirectory()){
            System.out.println("Opening folder " + file.getCanonicalPath());
            for (File p : file.listFiles()) {
                exploreDirectory(p);
            }

            //read files and
        } else if (file.isFile() && file.canRead()){
            Thread t = new Thread(new Counter(file));
            t.run();


        }
    }


    public void displayStats(){
        System.out.println("Statistics for "+ rootDirectory.getAbsolutePath());
        System.out.println("Number of lines (Including blank Lines): " + (this.emptyLineCount + this.packedLineCount));
        System.out.println("Number of lines (excluding blank Lines): " + this.packedLineCount);
    }


}
