package com.tcodes.assignment2;

import edu.duke.URLResource;

public class WebLinks {
    public void findWebLinks(String url, String toFind)
    {
        URLResource urlResource = new URLResource(url);
        for(String word: urlResource.words())
        {
            String dupWord = word.toLowerCase();
            int pos = dupWord.indexOf(toFind);
            if(pos != -1){
                int fp = word.indexOf("\"");
                int lp = word.indexOf("\"", pos+1);
                System.out.println(word.substring(fp+1, lp));
            }
        }
    }
}
