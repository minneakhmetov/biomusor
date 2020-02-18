package com.bioinf.demo.services;

import com.bioinf.demo.models.Answer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MainService {

    private final static List<String> endCodonList = Arrays.asList("TAA", "TAG", "TGA");
    private final static String[] dnaList = {"A", "T", "G", "C"};
    private final static String START_CODON = "ATG";
    private final static String DEFAULT = "";
    private final static int MINIMUM_ORF_LENGTH = 10;
    private final static int TRIPLET_LENGTH = 3;

    public Answer generate(int length, int gcPercentCount){
        int gcCount = (int) Math.round((length * (gcPercentCount / 10)) / 10.0);
        int atCount = (length - gcCount) / 2;
        gcCount /= 2;

        length = gcCount * 2 + atCount * 2;

        String dna = this.buildDna(length, gcCount, atCount);

        return new Answer(dna, this.findOrf(dna));
    }

    public Answer manual(String dna){
        return new Answer(dna, this.findOrf(dna));
    }

    private String findOrf(String dna){
        String maxORFFromBegin = findMaxORF(dna, false);
        String maxORFFromEnd = findMaxORF(dna, true);

        String answer = maxORFFromBegin.length() > maxORFFromEnd.length() ? maxORFFromBegin : maxORFFromEnd;

        return answer.equals(DEFAULT) ? null : this.editOrf(answer);
    }

    private String editOrf(String orf) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < orf.length(); i += TRIPLET_LENGTH) {
            result
                    .append(orf, i, i + TRIPLET_LENGTH)
                    .append(" ");
        }

        return result
                .deleteCharAt(result.length() - 1)
                .toString();
    }

    private String findMaxORF(String dna, boolean reverse) {
        if (reverse) {
            dna = new StringBuilder(dna).reverse().toString();
        }

        String maxResult = DEFAULT;
        StringBuilder currentSeq = new StringBuilder();

        for (int i = 0; i < dna.length() && i + TRIPLET_LENGTH < dna.length(); i += TRIPLET_LENGTH) {
            String currentTriplet = dna.substring(i, i + TRIPLET_LENGTH);

            if (currentTriplet.equals(START_CODON) && currentSeq.length() == 0) {
                currentSeq.append(currentTriplet);
            } else {
                if (currentSeq.length() > 0) {
                    currentSeq.append(currentTriplet);
                }
            }

            if (endCodonList.contains(currentTriplet)) {
                maxResult = currentSeq.length() > maxResult.length() ?
                        currentSeq.toString() : maxResult;

                currentSeq = new StringBuilder();
            }
        }

        return maxResult.length() > MINIMUM_ORF_LENGTH ? maxResult : DEFAULT;
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
                String element = dnaList[random.nextInt(dnaList.length)];

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
}
