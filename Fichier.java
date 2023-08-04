package fr.limayrac.b3rpi.util;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fichier {

    public ArrayList <String> mots;
    private static final String FICHIER = "/fr/limayrac/b3rpi/util/liste_francais.txt";
    private ArrayList <String> listeDeMots = null;
    private JButton[][] gridButtons;

    public Fichier() {
        super();
        listeDeMots = new ArrayList<>();
        ouvrirFichier();

    }
    public void setGridButtons(JButton[][] gridButtons) {
        this.gridButtons = gridButtons;
    }

    private void ouvrirFichier(){
        try{
            InputStream flux = Fichier.class.getResourceAsStream(FICHIER);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne=buff.readLine())!=null){
                listeDeMots.add(ligne);
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void printMotsDebut(String d, Integer e, int x, int y, String dir, int rows, int columns, JPanel[][] gridButtons, JLabel[][] lettre) {
        System.out.printf("affichage des mots commençant par %s%n", d);
        mots = new ArrayList<>();
        ArrayList<String> Words = new ArrayList<>();
        ArrayList<String> filteredWords = new ArrayList<>();

        int down = x +1;
        int right = y +1;
        int rightdown = x;
        int downright = y;
        int count =0;

        while (down < rows && rightdown < rows && right < columns && downright < columns &&
                down >= 0 && rightdown >= 0 && right >= 0 && downright >= 0) {

            if (dir.equals("DOWN")) {
                String buttonText = lettre[down][downright].getText();
                if (!buttonText.isEmpty()) {
                    char letter = buttonText.charAt(0);
                    if(!Words.isEmpty()){
                        for (String word : Words) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                            if(!filteredWords.isEmpty()) {
                                Words.clear();
                            }
                        }
                    }else{
                        for (String word : listeDeMots) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                    }
                }
            }

            if (dir.equals("RIGHTDOWN")) {
                String buttonText =lettre[rightdown][right].getText();
                if (!buttonText.isEmpty()) {
                    char letter = buttonText.charAt(0);
                    if(!Words.isEmpty()){
                        for (String word : Words) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                        if(!filteredWords.isEmpty()) {
                            Words.clear();
                        }
                    }else{
                        for (String word : listeDeMots) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                    }
                }
            }

            if (dir.equals("RIGHT")) {
                String buttonText =lettre[rightdown][right].getText();
                if (!buttonText.isEmpty()) {
                    char letter = buttonText.charAt(0);
                    if(!Words.isEmpty()){
                        for (String word : Words) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                        if(!filteredWords.isEmpty()) {
                            Words.clear();
                        }
                    }else{
                        for (String word : listeDeMots) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                    }
                }
            }

            if (dir.equals("DOWNRIGHT")) {
                String buttonText = lettre[down][downright].getText();
                if (!buttonText.isEmpty()) {
                    char letter = buttonText.charAt(0);
                    if(!Words.isEmpty()){
                        for (String word : Words) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                        if(!filteredWords.isEmpty()) {
                            Words.clear();
                        }
                    }else{
                        for (String word : listeDeMots) {
                            if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
                                filteredWords.add(word);
                                System.out.println(word);
                            }
                        }
                    }
                }
            }
            if(!filteredWords.isEmpty()) {
                Words.addAll(filteredWords);
                filteredWords.clear();
            }

            // Move in the specified direction
            if (dir.equals("DOWN")) {
                down++;
                count++;
            }
            if (dir.equals("DOWNRIGHT")){
                downright++;
                count++;
            }
            if (dir.equals("RIGHT")) {
                right++;
                count++;
            }
            if (dir.equals("RIGHTDOWN")){
                rightdown++;
                count++;
            }
        }
        mots = Words;
        if (mots.isEmpty()) {
            // If no words are found in the specified direction, display words starting with the selected letter
            for (String m : listeDeMots) {
                if (m.startsWith(d) && m.length() <= e) {
                    mots.add(m);
                    System.out.println(m);
                }
            }
        }
    }
   /* public void printMotsDebut(String d, Integer e, int x, int y, String dir, int rows, int columns, JButton[][] gridButtons) {
        System.out.printf("affichage des mots commençant par %s%n", d);
        mots = new ArrayList<>();
        int count = 0;
        if (dir.equals("DOWN")) {
            int down = x + 1;
            while (down < rows && gridButtons[down][y].getText().isEmpty()) {
                down++;
                count++;
            }
            if (down < rows && !gridButtons[down][y].getText().isEmpty()) {
                char letter = gridButtons[down][y].getText().charAt(0);
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e && m.length() > count && m.charAt(count) == letter) {
                        mots.add(m);
                        System.out.println(m);
                        count++;
                    }
                }
            }else{
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e ) {
                        mots.add(m);
                        System.out.println(m);
                    }
                }
            }
        } else if (dir.equals("RIGHT")) {
            int right = y + 1;
            while (right < columns && gridButtons[x][right].getText().isEmpty()) {
                right++;
                count++;
            }
            if (right < columns && !gridButtons[x][right].getText().isEmpty()) {
                char letter = gridButtons[x][right].getText().charAt(0);
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e && m.length() > count && m.charAt(count) == letter) {
                        mots.add(m);
                        System.out.println(m);
                        count++;
                    }
                }
            }else{
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e ) {
                        mots.add(m);
                        System.out.println(m);
                    }
                }
            }
        } else if (dir.equals("DOWNRIGHT")) {
            int down = x + 1;
            int right = y;
            while (down < rows && right < columns && gridButtons[down][right].getText().isEmpty()) {
                right++;
                count++;
            }
            if (down < rows && right < columns && !gridButtons[down][right].getText().isEmpty()) {
                char letter = gridButtons[down][right].getText().charAt(0);
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e && m.length() > count && m.charAt(count) == letter) {
                        mots.add(m);
                        System.out.println(m);
                        count++;
                    }
                }
            }else{
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e ) {
                        mots.add(m);
                        System.out.println(m);
                    }
                }
            }
        } else if (dir.equals("RIGHTDOWN")) {
            int down = x;
            int right = y + 1;
            while (down < rows && right < columns && gridButtons[down][right].getText().isEmpty()) {
                down++;
                count++;
            }
            if (down < rows && right < columns && !gridButtons[down][right].getText().isEmpty()) {
                char letter = gridButtons[down][right].getText().charAt(0);
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e && m.length() > count && m.charAt(count) == letter) {
                        mots.add(m);
                        System.out.println(m);
                        count++;
                    }
                }
            }else{
                for (String m : listeDeMots) {
                    if (m.startsWith(d) && m.length() <= e ) {
                        mots.add(m);
                        System.out.println(m);
                    }
                }
            }
        }
    }*/
}
