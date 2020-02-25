package com.bioinf.demo.models;


import lombok.Builder;
import lombok.Data;

@Data
public class ORF {
    private String dna;
    private String orf;
    private String protein;

    private int start;
    private int end;

    private boolean reverse;

    public ORF(String orf, int start, int end, boolean reverse) {
        this.orf = orf;
        this.start = start;
        this.end = end;
        this.reverse = reverse;
    }

}
