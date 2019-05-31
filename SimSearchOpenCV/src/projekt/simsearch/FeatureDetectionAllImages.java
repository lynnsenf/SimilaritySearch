package projekt.simsearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;

public class FeatureDetectionAllImages {
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		File folder = new File("C:/Sara/allimages");
		File[] allFiles = folder.listFiles();

		List<String> namesOfFiles = new ArrayList<String>();
		for(File file : allFiles){
			namesOfFiles.add(file.getName());
		}

		List<Mat> allImageMats = new ArrayList<Mat>();
		for(String imageName : namesOfFiles){
		allImageMats.add(Highgui.imread(imageName));
		}
		
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SURF);
		List<MatOfKeyPoint> allKeypoints = new ArrayList<MatOfKeyPoint>();
		featureDetector.detect(allImageMats, allKeypoints);
		
		/** UNNÖTIG
		int m = 0;
		for(Mat mat : allImageMats) {
			featureDetector.detect(mat, allKeypoints.get(m));
			m++;
		}**/
		
		/**
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		List<Mat> allDescriptors = new ArrayList<Mat>();		
		descriptorExtractor.compute(allImageMats, allKeypoints, allDescriptors);
		**/
		
		//TODO klappt noch nicht
		Scalar color = new Scalar(255, 0, 0);
		Mat[] outImg = new Mat[allImageMats.size()];
		Mat m = new Mat();
		Arrays.fill(outImg, m);
		int k = 0;
		int o = 0;
		int nr = 0;
		for(Mat mat : allImageMats){
			Features2d.drawKeypoints(mat, allKeypoints.get(k), outImg[o], color, 0); 
			Highgui.imwrite("C:/Sara/allimages/keypoints/key" +nr+ ".jpg", outImg[o]);
			k++;
			o++;
			nr++;
		}
	}
}
