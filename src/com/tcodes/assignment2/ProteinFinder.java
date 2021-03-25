package com.tcodes.assignment2;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import edu.duke.StorageResource;

import java.io.File;
import java.util.Locale;

/* Combined all the String DNA assignments here */

public class ProteinFinder {

    public void processGenes(StorageResource geneList)
    {
        String maxGene = "";
        int count = 0;

        System.out.println("---------------------------\nLength > 9");
        for(String s : geneList.data()){
            if(s.length() > 9)
                count++;
        }
        System.out.println(count);

        count = 0;
        System.out.println("---------------------------\nLength < 9");
        for(String s : geneList.data()){
            if(s.length() < 9)
                count++;
        }
        System.out.println(count);

        count = 0;
        System.out.println("---------------------------\nCG > 0.35");
        for(String s : geneList.data()){
            if(cgRatio(s) > 0.35)
                count++;
        }
        System.out.println(count);

        count = 0;
        System.out.println("---------------------------\nCG < 0.35");
        for(String s : geneList.data()){
            if(cgRatio(s) < 0.35)
                count++;
        }
        System.out.println(count);

        System.out.println("---------------------------\n");
        for(String s : geneList.data()){
            if(maxGene.length() < s.length())
                maxGene = s;
        }
        System.out.println("Maximum length of gene: "+ maxGene.length());
    }

    public float cgRatio(String dna){
        int cCount = howMany("c", dna);
        int gCount = howMany("g", dna );

        return (float)cCount/gCount;
    }

    public int countCTG(String dna){
        return howMany("CTG", dna);
    }

    public StorageResource getAllGenes(String dna)
    {
        int startIndex = 0;
        StorageResource geneList = new StorageResource();
        while (true){
            String currGene = findSmallestProtein(dna, startIndex);
            int geneLen = currGene.length();
            if(geneLen == 0)
                break;
            startIndex = dna.indexOf(currGene, startIndex)+geneLen;
            geneList.add(currGene);
        }
        return geneList;
    }

    public int findAndCountAllGenes(String dna)
    {
        int startIndex;
        int stopIndex = 0;
        int count = 0;

        while(true)
        {
            startIndex = stopIndex+1;
            String currGene = findSmallestProtein(dna, startIndex);
            stopIndex = startIndex+currGene.length();
            if(currGene.length() == 0 || stopIndex == startIndex)
                break;
            count++;
            System.out.println(currGene);
        }

        return count;
    }

    public String findSmallestProtein(String dna, int startIndex)
    {
        String taaProtein = findProtein(dna, startIndex, "TAA");
        String tagProtein = findProtein(dna, startIndex, "TAG");
        String tgaProtein = findProtein(dna, startIndex, "TGA");

        return minString(taaProtein, tagProtein, tgaProtein);
    }

    private int howMany(String one, String two)
    {
        two = two.toLowerCase();
        one = one.toLowerCase();
        int startIndex = two.indexOf(one);
        int count = 0;
        while(startIndex != -1){
            count++;
            startIndex = two.indexOf(one, startIndex+1);
        }

        return count;
    }

    private String minString(String first, String second, String third)
    {
        int firstLen = first.length();
        int secondLen = second.length();
        int thirdLen = third.length();

        if(firstLen == 0)
            firstLen = Integer.MAX_VALUE;
        if(secondLen == 0)
            secondLen = Integer.MAX_VALUE;
        if(thirdLen == 0)
            thirdLen = Integer.MAX_VALUE;

        if(firstLen < secondLen) {
            if (firstLen < thirdLen)
                return first;
            else
                return third;
        }
        else{
            if(secondLen < thirdLen)
                return second;
            else
                return third;
        }
    }

    public String findProtein(String dna, int startIndex, String stopCodon) {
        // For making the method case-agnostic
        String dnaUppercase = dna.toLowerCase();
        stopCodon = stopCodon.toLowerCase();

        if (startIndex == -1) {
            return "";
        }

        int stopIndex = findStopCodon(dnaUppercase, startIndex, stopCodon);

        if(stopIndex == -1)
            return "";

        return dna.substring(startIndex, stopIndex+3);
    }

    private int findStopCodon(String dna, int startIndex, String stopCodon)
    {
        int stopIndex = dna.indexOf(stopCodon, startIndex+3);

        while(stopIndex != -1){
            if((stopIndex-startIndex)%3 == 0){
                return stopIndex;
            }
            else {
                stopIndex = dna.indexOf(stopCodon, stopIndex+1);
            }
        }

        return -1;
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
        String result = findProtein(a, 0, "TAG");
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
            processGenes(getAllGenes(s));
            //System.out.println("found " + result + " Genes");
        }
    }
}
