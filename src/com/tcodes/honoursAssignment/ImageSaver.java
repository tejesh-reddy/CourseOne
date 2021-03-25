package com.tcodes.honoursAssignment;

import edu.duke.DirectoryResource;
import edu.duke.ImageResource;

import java.io.File;

public class ImageSaver {
    public void doSave(String prefix)
    {
        DirectoryResource directoryResource = new DirectoryResource();
        for(File f : directoryResource.selectedFiles()){
            ImageResource imageResource = new ImageResource(f);
            String newName = prefix + "-" + f.getName();
            imageResource.setFileName(newName);
            imageResource.save();
        }
    }

    public void saveAsGray(ImageResource image, String prefix)
    {
        String newName = prefix+image.getFileName();
        image.setFileName(newName);
        image.save();
    }
}
