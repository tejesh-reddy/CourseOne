package com.tcodes;

import com.tcodes.assignment2.WebLinks;

public class Main {

    public static void main(String[] args) {
        WebLinks webLinks = new WebLinks();
        webLinks.findWebLinks("https://www.dukelearntoprogram.com/course2/data/manylinks.html", "youtube.com");
    }
}
