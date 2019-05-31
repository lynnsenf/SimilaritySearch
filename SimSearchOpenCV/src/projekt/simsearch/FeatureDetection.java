package projekt.simsearch;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.features2d.Features2d;
//import org.opencv.features2d.ORB;
//import org.opencv.features2d.BRISK;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.*;
//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.Highgui;
import org.opencv.features2d.FeatureDetector;


public class FeatureDetection {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//imports images, converts to matrix
		String img1 = "C:/Sara/images/redroses.jpg";
		String img2 = "C:/Sara/images/redroses2.jpg";
		Mat mat1 = Highgui.imread(img1);
		Mat mat2 = Highgui.imread(img2);
		
		//detects features, stores them in MatOfKeyPoint
		//Keypoints unter drawKeypoints sehen mit SURF besser aus als mit ORB etc.
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SURF); 

		MatOfKeyPoint keysImg1 = new MatOfKeyPoint();
		MatOfKeyPoint keysImg2 = new MatOfKeyPoint();
		
		featureDetector.detect(mat1, keysImg1);
		featureDetector.detect(mat2, keysImg2);
		
		
		//extracts descriptors for features, stores them in MatOfKeypoint
		//brauchen wir das schon? oder erst nach dem Clustern der Keypoints?
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SURF);
		
		Mat descriptorsImg1 = new Mat();
		Mat descriptorsImg2 = new Mat();
		
		descriptorExtractor.compute(mat1, keysImg1, descriptorsImg1);
		descriptorExtractor.compute(mat2, keysImg2, descriptorsImg2);	
		
		/**
		//shows img1 with keypoints
		//Keypoints mit ORB sind zu wenig und komisch verteilt, WARUM?? 
		Scalar color = new Scalar(255, 0, 0);
		Mat outImg = new Mat();
		Features2d.drawKeypoints(mat2, keysImg1, outImg, color, 0); //statt 0: Features2d.DRAW_RICH_KEYPOINTS oder Features2d.NOT_DRAW_SINGLE_POINTS
		Highgui.imwrite("C:/Sara/images/flagsSURF=0.jpg", outImg);
		**/
		
		
		//clustering
		
		//funktioniert nicht
		//Mat clustered = new Mat();
	    //TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER,100,0.1);
	    //Core.kmeans(mat1, 2, clustered, criteria, 10, Core.KMEANS_PP_CENTERS);
	    
	    int i = 0;
	    int y = 0;
		List<Mat> clusters1 = Cluster.cluster(mat1, 5); //keysImg1 und keysImg2 klappt nicht
		List<Mat> clusters2 = Cluster.cluster(mat2, 5);
		
		/** generates images of the cluster matrices
		for(Mat clusteredMat : clusters1) {
	    	Highgui.imwrite("C:/Sara/images/cluster1_" + i++ + ".jpg", clusteredMat);
		}
	    
	    for(Mat clusteredMat : clusters2) {
	    	Highgui.imwrite("C:/Sara/images/cluster2_" + y++ + ".jpg", clusteredMat);	
	    }
	    **/
	    
	    /**
	    //print cluster matrices
		//get(y).dump() druckt Matrix-Inhalt aus, 5x 2000x1500
	    for(int z = 0; z < clusters1.size(); z++) {
	    	System.out.println(clusters1.get(z)); 
	    }
	    
	    for(int z = 0; z < clusters2.size(); z++) {
	    	System.out.println(clusters2.get(z)); 
	    }
	    **/
	    
	    

	    
		
		
		//-------------------------------------------------------------------------------------------
		
		/** Matchen noch nicht nötig, erst nur Keypoints erstellen und Clustern
		//matches the features of two images
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
		matcher.knnMatch(descriptorsImg1, descriptorsImg2, matches, 2);
		**/
		
		
		
		
		
		/** ALT

		Mat mask1 = new Mat();
		Mat mask2 = new Mat();
		Mat maskOut = new Mat();
		
		//ORB or BRISK detector
		ORB detector = ORB.create();
		//BRISK detector = BRISK.create();
		detector.detect(mat1, keypoints1, mask1); 
		detector.detect(mat2, keypoints2, mask2);
		//draws the matches between two images, creates new image
		
		
		Mat newImg = new Mat();
		Features2d.drawMatches(mat1, keysImg1, mat2, keysImg2, matofdmatch, newImg);
		Highgui.imwrite("D:/Sara/matches.jpg", newImg);
		**/
	}

}
