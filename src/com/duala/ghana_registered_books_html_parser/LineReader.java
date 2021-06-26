package com.duala.ghana_registered_books_html_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LineReader{
    private static File rootDirectory;
    static public int emptyLineCount = 0;
    static public int packedLineCount = 0;
    static public int wordCount = 0;
    static ArrayList<String> ignoredFolders = new ArrayList<>();
    static ArrayList<String> ignoredFiles =  new ArrayList<>();


    LineReader(){}
    LineReader(String p){
        this.rootDirectory = new File(p);
        readIgnore();


        try {
            exploreDirectory(this.rootDirectory);
        } catch (Exception e){
            System.out.println(e);
        }



    }

    private void readIgnore(){

        //open the readignore if one exists
        String p = System.getProperty("user.dir") + "\\.readignore";
        File file = new File(p);
        try {
            Scanner ignoreScanner = new Scanner(file);
            System.out.println("readignore found at " + p);

            while (ignoreScanner.hasNext()) {
                String current = ignoreScanner.nextLine().trim();
                //ignoring empty lines in the ignore file
                if (current == "" || current =="\n")
                    continue;


                if (!current.startsWith("#")) {//ignore comments


                    if (current.endsWith("\\")) //handling directory ignores
                        ignoredFolders.add(rootDirectory + "\\" + current.substring(0, current.length() - 1));
                    else //handing file ignores
                        ignoredFiles.add(current);

                }
            }
        }
        catch(FileNotFoundException e){
                System.out.println("No readignore found");
            }

    }


    private boolean isIgnored(File file){

        if (file.isFile()){
            String fileName = file.getName();

            for (String check : ignoredFiles) {


                if (check.endsWith(file.getPath()))
                    return true;

                if (file.getPath().endsWith(check))
                    return true;




                if (check.startsWith("*")){
                    if (fileName.endsWith(check.substring(1)))
                        return true;
                }
            }

        } else if (file.isDirectory()){
            return ignoredFolders.contains(file.getPath());
        }

        return false;
    }


    private void exploreDirectory(File file) throws IOException {


        if (!isIgnored(file)) {
            if (file.isDirectory()) {
                System.out.println("Opening folder " + file.getCanonicalPath());
                for (File p : file.listFiles()) {
                    exploreDirectory(p);
                }

                //read files
            } else if (file.isFile() && file.canRead()) {
                Thread t = new Thread(new LineCounter(file));
                t.start();


            }
        }
    }


    public void displayStats(){
        System.out.println("Statistics for "+ rootDirectory.getAbsolutePath());
        System.out.println("Number of lines (Including blank Lines): " + (this.emptyLineCount + this.packedLineCount));
        System.out.println("Number of lines (excluding blank Lines): " + this.packedLineCount);
        System.out.println("Number of words: "+ this.wordCount);
    }



    void increasePackedLineCount(){
        this.packedLineCount++;

    }

    void increaseSpacedLineCount(){
        this.emptyLineCount++;
    }


}
