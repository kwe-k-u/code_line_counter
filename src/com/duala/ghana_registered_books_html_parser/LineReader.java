package com.duala.ghana_registered_books_html_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class LineReader{

    /** Root folder within which analyses is done*/
    private static File rootDirectory;
    /** Number of lines of code analysed files (Including lines with spaces and end characters)*/
    static public int emptyLineCount = 0;
    /** Number of lines of code in analysed files (Excluding lines with spaces and end characters)*/
    static public int packedLineCount = 0;
    /** Number of words in the analysed files*/
    static public int wordCount = 0;
    /** List of folders added to the .readignore that will be ignored during analyses*/
    static ArrayList<String> ignoredFolders = new ArrayList<>();
    /** List of files added to the .readignore that will be ignored during analyses*/
    static ArrayList<String> ignoredFiles =  new ArrayList<>();


    /**Default constructor*/
    LineReader(){}
    /** Constructor that takes the path for the root directory as a parameter
     * @param p Absolute path for the location of the root directory
     * */
    LineReader(String p){
        this.rootDirectory = new File(p);


        readIgnore();


        try {
            exploreDirectory(this.rootDirectory);
        } catch (Exception e){
            System.out.println(e);
        }



    }


    /**
     * Locates and reads the .readignore file, excluding added files and folders from analyses
     * Expects the .readignore file to be saved in the directory from which this code is run
     */
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


    /** Checks if the passed file has been added to the list of folders/files to be ignored during analyses
     * @param file File to be checked
     * @return true if file is ignored, false if otherwise
     * */
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


    /**
     * Iteratively navigates through directories and subdirectories, passing over readable files to the Line Counter class
     * Files and folders added to the .readignore file are excluded
     * @param file directory to be navigated
     * @throws IOException
     */
    private void exploreDirectory(File file) throws IOException {



        if (!isIgnored(file)) {
            //if directory, navigate through sub directories and files
            if (file.isDirectory()) {
                System.out.println("Opening folder " + file.getCanonicalPath());
                for (File p : file.listFiles()) {
                    exploreDirectory(p);
                }

                //read files
            } else if (file.isFile() && file.canRead()) {//if a file start a new instance of line counter
                Thread t = new Thread(new LineCounter(file));
                t.start();


            }
        }
    }


    /**
     * Displays information pertaining to the directory analysed
     */
    public void displayStats(){
        System.out.println("Statistics for "+ rootDirectory.getAbsolutePath());
        System.out.println("Number of lines (Including blank Lines): " + (this.emptyLineCount + this.packedLineCount));
        System.out.println("Number of lines (excluding blank Lines): " + this.packedLineCount);
        System.out.println("Number of words: "+ this.wordCount);
    }


    /**
     * Increase number of lines (inclusive of empty lines and end characters) by 1
     */
    void increasePackedLineCount(){
        this.packedLineCount++;

    }

    /**
     * Increase number of lines (exclusive of empty lines and end characters) by 1
     */
    void increaseSpacedLineCount(){
        this.emptyLineCount++;
    }


}
