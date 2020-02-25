package com.bioinf.demo.app;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static Map<String, String> getProteinMap() {
        HashMap<String, String> map = new HashMap<>();

        map.put("TTT", "PHE");
        map.put("TTC", "PHE");
        map.put("TTA", "LEU");
        map.put("TTG", "LEU");

        map.put("CTT", "LEU");
        map.put("CTC", "LEU");
        map.put("CTA", "LEU");
        map.put("CTG", "LEU");

        map.put("ATT", "LLE");
        map.put("ATC", "LLE");
        map.put("ATA", "LLE");
        map.put("ATG", "MET");

        map.put("GTT", "VAL");
        map.put("GTC", "VAL");
        map.put("GTA", "VAL");
        map.put("GTG", "VAL");

        map.put("TCT", "SER");
        map.put("TCC", "SER");
        map.put("TCA", "SER");
        map.put("TCG", "SER");

        map.put("CCT", "PRO");
        map.put("CCC", "PRO");
        map.put("CCA", "PRO");
        map.put("CCG", "PRO");

        map.put("ACT", "THR");
        map.put("ACC", "THR");
        map.put("ACA", "THR");
        map.put("ACG", "THR");

        map.put("GCT", "ALA");
        map.put("GCC", "ALA");
        map.put("GCA", "ALA");
        map.put("GCG", "ALA");

        map.put("TAT", "TYR");
        map.put("TAC", "TYR");
        map.put("TAA", "STOP");
        map.put("TAG", "STOP");

        map.put("CAT", "HIS");
        map.put("CAC", "HIS");
        map.put("CAA", "GIN");
        map.put("CAG", "GIN");

        map.put("AAT", "ASN");
        map.put("AAC", "ASN");
        map.put("AAA", "LYS");
        map.put("AAG", "LYS");

        map.put("GAT", "ASP");
        map.put("GAC", "ASP");
        map.put("GAA", "GLU");
        map.put("GAG", "GLU");

        map.put("TGT", "CYS");
        map.put("TGC", "CYS");
        map.put("TGA", "STOP");
        map.put("TGG", "TRP");

        map.put("CGT", "ARG");
        map.put("CGC", "ARG");
        map.put("CGA", "ARG");
        map.put("CGG", "ARG");

        map.put("AGT", "SER");
        map.put("AGC", "SER");
        map.put("AGA", "ARG");
        map.put("AGG", "ARG");

        map.put("GGT", "GLY");
        map.put("GGC", "GLY");
        map.put("GGA", "GLY");
        map.put("GGG", "GLY");

        return map;
    }
}
