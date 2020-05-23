package com.bioinf.demo.app;

import java.io.*;
import java.util.Random;

public class Maina {

    public static char[] DNA_Generator(int length, int GC_content){
        int G_or_C_count = length * GC_content / 200;
        int A_or_T_count = (length - 2 * G_or_C_count) / 2;
        String dna = "";
        for (int i = 0; i < G_or_C_count; i++){
            dna += 'g';
            dna += 'c';
        }
        for (int i = 0; i < A_or_T_count; i++){
            dna += 'a';
            dna += 't';
        }

        Random random = new Random(System.nanoTime());
        char[] dnaArray = dna.toCharArray();
        for(int i = 0; i < dnaArray.length; i++){
            swap(dnaArray, i, random.nextInt(dnaArray.length));
        }
        return dnaArray;
    }

    public static void swap(char[] line, int i, int j){
        char t = line[i];
        line[i] = line[j];
        line[j] = t;
    }

    public static char getAminoAcid(String codon){
        char amino_acid;
        switch (codon) {
            case ("ttt"):
            case ("ttc"):
                amino_acid = 'F';
                break;
            case ("tta"):
            case ("ttg"):
            case ("ctt"):
            case ("ctc"):
            case ("cta"):
            case ("ctg"):
                amino_acid = 'L';
                break;
            case ("att"):
            case ("atc"):
            case ("ata"):
                amino_acid = 'I';
                break;
            case ("atg"):
                amino_acid = 'M';
                break;
            case ("gtt"):
            case ("gtc"):
            case ("gta"):
            case ("gtg"):
                amino_acid = 'V';
                break;
            case ("tct"):
            case ("tcc"):
            case ("tca"):
            case ("tcg"):
            case ("agt"):
            case ("agc"):
                amino_acid = 'S';
                break;
            case ("cct"):
            case ("ccc"):
            case ("cca"):
            case ("ccg"):
                amino_acid = 'P';
                break;
            case ("act"):
            case ("acc"):
            case ("aca"):
            case ("acg"):
                amino_acid = 'T';
                break;
            case ("gct"):
            case ("gcc"):
            case ("gca"):
            case ("gcg"):
                amino_acid = 'A';
                break;
            case ("tat"):
            case ("tac"):
                amino_acid = 'Y';
                break;
            case ("cat"):
            case ("cac"):
                amino_acid = 'H';
                break;
            case ("caa"):
            case ("cag"):
                amino_acid = 'Q';
                break;
            case ("aat"):
            case ("aac"):
                amino_acid = 'N';
                break;
            case ("aaa"):
            case ("aag"):
                amino_acid = 'K';
                break;
            case ("gat"):
            case ("gac"):
                amino_acid = 'D';
                break;
            case ("gaa"):
            case ("gag"):
                amino_acid = 'E';
                break;
            case ("tgt"):
            case ("tgc"):
                amino_acid = 'C';
                break;
            case ("tgg"):
                amino_acid = 'W';
                break;
            case ("cgt"):
            case ("cgc"):
            case ("cga"):
            case ("cgg"):
            case ("aga"):
            case ("agg"):
                amino_acid = 'R';
                break;
            case ("ggt"):
            case ("ggc"):
            case ("gga"):
            case ("ggg"):
                amino_acid = 'G';
                break;
            default:
                amino_acid = '_';
        }
        return amino_acid;
    }

    public static char[] getReverseComplementaryDNA(char[] DNA){
        char[] ReverseComplementaryDNA = new char[DNA.length];
        for (int i = 0; i < DNA.length; i++){
            switch (DNA[DNA.length - i - 1]){
                case ('a'):
                    ReverseComplementaryDNA[i] = 't';
                    break;
                case ('t'):
                    ReverseComplementaryDNA[i] = 'a';
                    break;
                case ('g'):
                    ReverseComplementaryDNA[i] = 'c';
                    break;
                case ('c'):
                    ReverseComplementaryDNA[i] = 'g';
                    break;
            }
        }
        return ReverseComplementaryDNA;
    }

    public void main(PrintWriter writer) {
       // BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\razil\\Desktop\\Task_02_table.txt")));

        int length = 1000;

        for(int GC_content = 20; GC_content <= 80; GC_content ++){
            int orfsCount = 0;
            for(int index = 0; index < 10000; index++){
                char[] DNA = DNA_Generator(length, GC_content);
                String maxProtein = "";
                for(int i = 0; i < length - 5; i++){
                    String start_codon = "" + DNA[i] + DNA[i + 1] + DNA[i + 2];
                    if(start_codon.equals("atg")){
                        String ORF = "atg";
                        String protein = "M";
                        for (int j = i + 3; j < length - 2; j += 3){
                            String codon = "" + DNA[j] + DNA[j + 1] + DNA[j + 2];
                            ORF = ORF + " " + codon;
                            if(codon.equals("tga") || codon.equals("tag") || codon.equals("taa")){
                                protein += '*';
                                break;
                            } else {
                                protein += getAminoAcid(codon);
                            }
                        }
                        if(protein.length() > maxProtein.length() && protein.charAt(protein.length() - 1) == '*'){
                            maxProtein = protein;
                        }
                    }
                }
                DNA = getReverseComplementaryDNA(DNA);

                for(int i = 0; i < length - 5; i++){
                    String start_codon = "" + DNA[i] + DNA[i + 1] + DNA[i + 2];
                    if(start_codon.equals("atg")){
                        String ORF = "atg";
                        String protein = "M";
                        String isItDuplicate;
                        for (int j = i + 3; j < length - 2; j += 3){
                            String codon = "" + DNA[j] + DNA[j + 1] + DNA[j + 2];
                            ORF = ORF + " " + codon;
                            if(codon.equals("tga") || codon.equals("tag") || codon.equals("taa")){
                                isItDuplicate = "no, it isn't duplicate";
                                protein += '*';
                                break;
                            } else {
                                protein += getAminoAcid(codon);
                            }
                        }
                        if(protein.length() > maxProtein.length() && protein.charAt(protein.length() - 1) == '*'){
                            maxProtein = protein;
                        }
                    }
                }
                if(maxProtein.length() >= 30){
                    orfsCount++;
                }
            }
            double orfsPercent = orfsCount / 10000.0;
            writer.println(GC_content + " " + Math.round(orfsPercent * 10000) / 100.0);
            //writer.newLine();
            System.out.println(GC_content + " " + Math.round(orfsPercent * 10000) / 100.0);
        }
        writer.close();
    }
}