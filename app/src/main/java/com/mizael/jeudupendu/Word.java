package com.mizael.jeudupendu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Word {
    private String word;
    private final ArrayList<String> wordList;


    public Word(InputStream file){
        this.wordList = new ArrayList<String>();
        this.word = new String();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(file));
            String line = "";
            while((line = reader.readLine()) != null)
                this.wordList.add(line);

            this.generate();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(reader != null){
                    reader.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void generate(){
        this.setWord(this.wordList.get(new Random().nextInt(this.wordList.size())));
    }

    public String hide(){
        String star = "";

        for(int i = 0; i < this.word.length(); i++)
            star += "*";

        return star;

    }

    public boolean isContent(char firstCharacter){
        return this.getWord().indexOf(firstCharacter) != -1;
    }

    public String show(char firstCharacter, String wordBefore){
        String characterToShow = "";

        for(int i = 0; i < this.word.length(); i++){
            if(this.getWord().charAt(i) == firstCharacter){
                characterToShow += firstCharacter;
            }

            else{
                characterToShow += wordBefore.charAt(i);
            }
        }
        return characterToShow;
    }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
}
