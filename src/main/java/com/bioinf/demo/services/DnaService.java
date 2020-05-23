package com.bioinf.demo.services;

import com.bioinf.demo.app.Maina;
import com.bioinf.demo.models.ORF;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.*;

import static com.bioinf.demo.app.Constants.getProteinMap;

@Service
public class DnaService {

    private final static List<String> END_CODON_LIST = Arrays.asList("TAA", "TAG", "TGA");

    private final static String[] DNA_LIST = {"A", "T", "G", "C"};

    private final static String START_CODON = "ATG";
    private final static String DEFAULT = "";

    private final static int MINIMUM_ORF_LENGTH = 10;
    private final static int TRIPLET_LENGTH = 3;

    private static final int START_PERCENT = 20;

    private static final int END_PERCENT = 80;

    private static final int LOOP_TIME = 10000;

    private static final int MIN_ORF = 50;

    private static final int MAX_LENGTH = 1000;


    public void task2(PrintWriter writer){
       // Map<Integer, Double> map = this.getDependency();

        writer.println("sep=,");
        writer.println("Percent,Count");
        Maina maina = new Maina();

        maina.main(writer);

//        for (int percent : map.keySet()){
//            writer.println(percent + "," + map.get(percent));
//        }

    }

    public Map<Integer, Double> getDependency() {

        Map<Integer, Double> map = new HashMap<>();

        for (int percent = START_PERCENT; percent <= END_PERCENT; percent++) {

            int countMinOrf = 0;

            for (int i = 0; i < LOOP_TIME; i++) {
                ORF orf = this.generate(MAX_LENGTH, percent);
                if (orf.getOrf().length() >= MIN_ORF)
                    countMinOrf++;
            }

            System.out.print(percent + " ");

            double p = (double) countMinOrf * 0.8 / (double) LOOP_TIME;

            map.put(percent, p);

        }

        return map;
    }


    public ORF pretty(int length, int gcPercentCount) {
        return this.editOrf(this.generate(length, gcPercentCount));
    }

    public ORF pretty(String dna) {
        return this.editOrf(this.manual(dna));
    }

    private ORF generate(int length, int gcPercentCount) {
        int gcCount = (int) Math.round((length * (gcPercentCount / 10)) / 10.0);
        int atCount = (length - gcCount) / 2;
        gcCount /= 2;

        length = gcCount * 2 + atCount * 2;

        String dna = this.buildDna(length, gcCount, atCount);

        return this.manual(dna);

    }

    private ORF manual(String dna) {
        ORF maxORFFromEnd = this.findMaxORF(dna, true);
        ORF maxORFFromBegin = this.findMaxORF(dna, false);

        ORF answer = maxORFFromBegin.getOrf().length() > maxORFFromEnd.getOrf().length() ?
                maxORFFromBegin : maxORFFromEnd;

        answer.setDna(dna);

        return answer;
    }

    private ORF editOrf(ORF orf) {
        Map<String, String> proteinMap = getProteinMap();

        StringBuilder result = new StringBuilder();
        StringBuilder protein = new StringBuilder();
        String dna = orf.getOrf();

        for (int i = 0; i < dna.length(); i += TRIPLET_LENGTH) {
            String triplet = dna.substring(i, i + TRIPLET_LENGTH);

            result
                    .append(triplet)
                    .append(" ");

            protein
                    .append(proteinMap.get(triplet))
                    .append(" ");
        }

        orf.setOrf(result.toString());
        orf.setProtein(protein.toString());

        return orf;
    }

    private ORF findMaxORF(String dna, boolean reverse) {
        if (reverse) {
            dna = reverseDNA(dna);
        }

        String maxResult = DEFAULT;

        int index = dna.indexOf(START_CODON);
        int startMax = 0;
        int endMax = 0;

        StringBuilder currentSeq = new StringBuilder();

        while (index != -1) {
            for (int i = index; i + TRIPLET_LENGTH < dna.length(); i += TRIPLET_LENGTH) {
                String currentTriplet = dna.substring(i, i + TRIPLET_LENGTH);

                if (currentTriplet.equals(START_CODON) && currentSeq.length() == 0) {
                    currentSeq.append(currentTriplet);
                } else {
                    if (currentSeq.length() > 0) {
                        currentSeq.append(currentTriplet);
                    }
                }

                if (END_CODON_LIST.contains(currentTriplet)) {
                    if (currentSeq.length() > maxResult.length()) {
                        startMax = index + 1;
                        endMax = i + TRIPLET_LENGTH;

                        maxResult = currentSeq.toString();
                    }

                    currentSeq = new StringBuilder();
                }
            }

            index = dna.indexOf(START_CODON, index + 1);
        }

        if (maxResult.length() > MINIMUM_ORF_LENGTH) {
            return new ORF(maxResult, startMax, endMax, reverse);
        } else return new ORF(DEFAULT, 0, 0, reverse);
    }

    private String buildDna(int length, int gcCount, int atCount) {
        Random random = new Random();

        int aCounter = 0;
        int tCounter = 0;
        int cCounter = 0;
        int gCounter = 0;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            boolean check = false;

            while (!check) {
                String element = DNA_LIST[random.nextInt(DNA_LIST.length)];

                if (element.equals("A") && aCounter < atCount) {
                    aCounter++;
                    result.append(element);
                    check = true;
                }

                if (element.equals("G") && gCounter < gcCount) {
                    gCounter++;
                    result.append(element);
                    check = true;
                }

                if (element.equals("T") && tCounter < atCount) {
                    tCounter++;
                    result.append(element);
                    check = true;
                }

                if (element.equals("C") && cCounter < gcCount) {
                    cCounter++;
                    result.append(element);
                    check = true;
                }
            }
        }

        return result.toString();
    }


    private static String reverseDNA(String DNA) {
        StringBuilder reversedDNA = new StringBuilder();
        for (int i = DNA.length() - 1; i >= 0; i--) {
            switch (DNA.charAt(i)) {
                case 'A':
                    reversedDNA.append('T');
                    break;
                case 'T':
                    reversedDNA.append('A');
                    break;
                case 'G':
                    reversedDNA.append('C');
                    break;
                case 'C':
                    reversedDNA.append('G');
                    break;
            }
        }
        //System.out.println("Reversed = " + reversedDNA);
        return String.valueOf(reversedDNA);
    }


}
