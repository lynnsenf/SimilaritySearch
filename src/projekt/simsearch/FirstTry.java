package projekt.simsearch;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.*;
//import org.opencv.imgcodecs.Imgcodecs;

public class FirstTry {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		
		//Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
		//System.out.println("mat = " + mat.dump());
		
		
		
		//FastFeatureDetector ffd = FastFeatureDetector.create();
		//ffd.detect(imageMat, keypoints, mask);
		
		
		
		
		
		
		
		
	}
	
	//show the image, doesn't work
	private static BufferedImage ConvertMat2Image(Mat imgContainer){
	    MatOfByte byteMatData = new MatOfByte();
	    //image formatting
	    //Imgcodecs.imencode(".jpg", imgContainer,byteMatData);
	    // Convert to array
	    byte[] byteArray = byteMatData.toArray();
	    BufferedImage img= null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        //load image
	        img = ImageIO.read(in);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    return img;
	}

}
