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
		
		//shows img1 with keypoints
		//Keypoints mit ORB sind zu wenig und komisch verteilt, WARUM?? 
		/**
		Scalar color = new Scalar(255, 0, 0);
		Mat outImg = new Mat();
		Features2d.drawKeypoints(mat1, keysImg1, outImg, color, 0); //statt 0: Features2d.DRAW_RICH_KEYPOINTS oder Features2d.NOT_DRAW_SINGLE_POINTS
		Highgui.imwrite("C:/Sara/images/TRY2.jpg", outImg);
		**/
		
		//extracts descriptors for features, stores them in Mat
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SURF);
		
		Mat descriptorsImg1 = new Mat();
		Mat descriptorsImg2 = new Mat();
		
		descriptorExtractor.compute(mat1, keysImg1, descriptorsImg1);
		descriptorExtractor.compute(mat2, keysImg2, descriptorsImg2);	
		//System.out.println(descriptorsImg1.dump());   //hier 93 descriptors gefunden, also Ausgabe ist 93x64 Matrix
		
		
		//CLUSTERING, kmeans returns the compactness measure, i.e. how good the labeling was done
		// Compactness(CP) measures the average distance between every pair of data
		// points in a cluster. If there are multiple clusters, Compactness is the
		// average of all clusters.
		// A low value of CP indicates better and more compact clusters.
		int k = 5;
		Mat labels = new Mat();
		TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER,100,0.1);
		int attempts = 10;
		Mat centers = new Mat();		
		Core.kmeans(descriptorsImg1, k, labels, criteria, attempts, Core.KMEANS_PP_CENTERS, centers);
		System.out.println(centers.dump());   //gibt k x 64 Matrix zurück
		
		//System.out.println(labels.rows()); 
		//Einträge/Zeilen in labels insgesamt --> 2635, labels have same size as that of test data, sind damit descriptors gemeint?
		//wie viele verschiedene labels muss es also geben, warum sind es so viele?
		//k = 2 --> labels = 0 oder 1,
		//k = 5 --> lables = 0,1,2,3 oder 4
		//wenn k = 2: bei Änderung von attempts ändert sich auch Reihenfolge von labels
	  
	
		
		
		/**
		* ab hier unwichtig
		**/
				
		//aus Klasse "Cluster", gibt {0=1013571, 1=246995, 2=794506, 3=211283, 4=733645}
		//{0=29861, 1=39980, 2=28539, 3=46867, 4=14753} zurück
		/**
		int i = 0;
	    	int y = 0;
		List<Mat> clusters1 = Cluster.cluster(mat1, 5); //keysImg1 und keysImg2 klappt nicht
		List<Mat> clusters2 = Cluster.cluster(mat2, 5);
		**/
		
		//generates images of the cluster matrices
		/**
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
		
	}

}
