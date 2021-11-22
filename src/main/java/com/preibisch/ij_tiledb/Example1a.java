package com.preibisch.ij_tiledb;

import ij.ImageJ;
import ij.ImagePlus;
import ij.io.Opener;
import net.imglib2.Cursor;
import net.imglib2.img.ImagePlusAdapter;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.NativeType;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.NumericType;

import java.io.File;

/**
 * Opens a file with ImageJ and wraps it into an ImgLib {@link Img}.
 *
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 */
public class Example1a
{
    // within this method we define <T> to be a NumericType (depends on the type of ImagePlus)
    // you might want to define it as RealType if you know it cannot be an ImageJ RGB Color image
    public < T extends NumericType< T > & NativeType< T > > Example1a()
    {
        // define the file to open
        File file = new File( "DrosophilaWing.tif" );
        System.out.println(file.getAbsolutePath());

        // open a file with ImageJ
        final ImagePlus imp = new Opener().openImage( file.getAbsolutePath() );

        // display it via ImageJ
        imp.show();

        // wrap it into an ImgLib image (no copying)
        final Img< T > image = ImagePlusAdapter.wrap( imp );

        // display it via ImgLib using ImageJ
        ImageJFunctions.show( image );
    }

    public < T extends Type< T >> Img< T > copyImage(final Img< T > input )
    {
        // create a new Image with the same properties
        // note that the input provides the size for the new image as it implements
        // the Interval interface
        Img< T > output = input.factory().create( input );

        // create a cursor for both images
        Cursor< T > cursorInput = input.cursor();
        Cursor< T > cursorOutput = output.cursor();

        // iterate over the input
        while ( cursorInput.hasNext())
        {
            // move both cursors forward by one pixel
            cursorInput.fwd();
            cursorOutput.fwd();

            // set the value of this pixel of the output image to the same as the input,
            // every Type supports T.set( T type )
            cursorOutput.get().set( cursorInput.get() );
        }

        // return the copy
        return output;
    }
    public static void main( String[] args )
    {
        // open an ImageJ window
        new ImageJ();

        // run the example
        new Example1a();
    }
}
