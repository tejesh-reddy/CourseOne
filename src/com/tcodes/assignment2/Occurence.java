package com.tcodes.assignment2;

public class Occurence {
    public boolean twoOccurrences(String stringa, String stringb)
    {
        int pos = stringb.indexOf(stringa);
        if(pos == -1)
            return false;
        else
            return (stringb.indexOf(stringa, pos+1) != -1);
    }

    public String lastPart(String stringa, String stringb){
        int pos = stringb.indexOf(stringa);
        if(pos != -1) {
            return stringb.substring(pos+stringa.length());
        }
        return stringb;
    }

    public void testing()
    {
        String b = "forest";
        String a = "zoo";
        System.out.println("\""+b+" has two '"+a+"'s \" is a "+twoOccurrences(a, b)+" statement.");
        System.out.println(lastPart(a, b)+": Last part of "+a+" after "+b);
    }
}
