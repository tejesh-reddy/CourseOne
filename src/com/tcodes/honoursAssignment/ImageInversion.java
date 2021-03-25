package com.tcodes.honoursAssignment;

import edu.duke.DirectoryResource;
import edu.duke.ImageResource;
import edu.duke.Pixel;

import java.io.File;

public class ImageInversion {
    public ImageResource makeInversion(ImageResource im)
    {
        ImageResource outImage = new ImageResource(im.getWidth(), im.getHeight());
        for(Pixel p : outImage.pixels()){
            Pixel inPixel = im.getPixel(p.getX(), p.getY());

            p.setRed(255 - inPixel.getRed());
            p.setBlue(255 - inPixel.getBlue());
            p.setGreen(255 - inPixel.getGreen());
        }

        return outImage;
    }

    public void selectAndConvert () {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeInversion(inImage);
            gray.draw();
        }
    }

    public void saveInvert(ImageResource imageResource)
    {
        ImageResource invert = makeInversion(imageResource);
        ImageSaver saver = new ImageSaver();
        saver.saveAsGray(invert, "inverted-");
        invert.draw();
    }

    public void testInvert() {
        DirectoryResource directoryResource = new DirectoryResource();
        for(File f : directoryResource.selectedFiles()) {
            ImageResource ir = new ImageResource(f);
            saveInvert(ir);
        }
    }
}
