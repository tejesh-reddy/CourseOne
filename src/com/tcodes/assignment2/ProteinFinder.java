package com.tcodes.assignment2;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;

public class ProteinFinder {
    public String findProtein(String dna, String startCodon, String stopCodon) {
        String dnaUppercase = dna.toLowerCase();
        startCodon = startCodon.toLowerCase();
        stopCodon = stopCodon.toLowerCase();

        int start = dnaUppercase.indexOf(startCodon);

        if (start == -1) {
            return "";
        }

        int stop = dnaUppercase.indexOf(stopCodon, start+3);

        while(stop != -1){
            if((stop-start)%3 == 0){
                return dna.substring(start, stop+3);
            }
            else {
                stop = dnaUppercase.indexOf(stopCodon, stop+1);
            }
        }
        return "";
    }

    public void testing() {
        //String a = "cccatggggtttaaataataataggagagagagagagagttt";
        //String ap = "atggggtttaaataataatag";
        //String a = "atgcctag";
        //String ap = "";
        String a = "ATGCCCTAG";
        String ap = "ATGCCCTAG";
        //String a = "ccAtgcotaaataatagokoktagkk";
        //String ap = "atgcotaaataatag";
        String result = findProtein(a, "ATG", "TAG");
        if (ap.equals(result)) {
            System.out.println("success for " + ap + " length " + ap.length());
        }
        else {
            System.out.println("mistake for input: " + a);
            System.out.println("got: " + result);
            System.out.println("not: " + ap);
        }
    }

    public void realTesting() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String s = fr.asString();
            System.out.println("read " + s.length() + " characters");
            String result = findProtein(s, "ATG", "TAA");
            System.out.println("found " + result);
        }
    }
}
