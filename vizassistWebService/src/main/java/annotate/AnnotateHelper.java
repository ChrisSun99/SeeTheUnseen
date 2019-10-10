package annotate;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse; 
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature; 
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.common.collect.Lists;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnnotateHelper {
	public static enum Status {
		OK, ERROR; 
	}
	
	public static Status annotate(Image img, StringBuilder result) {
//		GoogleCredentials myCredentials;
//		try {
//			myCredentials = GoogleCredentials
//											.fromStream(new FileInputStream("/Users/apple/Desktop/VizAssist-3b20a9750d4b.json"))
//											.createScoped(Lists.newArrayList("http://www.googleapis.com/auth.cloud-platform"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return Status.ERROR; 
//		} catch (IOException e) {
//			e.printStackTrace();
//			return Status.ERROR; 
//		} ImageAnnotatorSettings imageAnnotatorSettings;
//		try {
//			imageAnnotatorSettings = ImageAnnotatorSettings.newBuilder()
//					.setCredentialsProvider(FixedCredentialsProvider.create(myCredentials)).build();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return Status.ERROR;
//		} 
		// Instantiate a client; 
		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
			// Build the image annotation request; 
			List<AnnotateImageRequest> requests =new ArrayList<>();
			Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);
			// Perform text detection on the image file; 
			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			for (AnnotateImageResponse res: responses) {
				if (res.hasError()) {
					System.out.printf("Error: %s\n", res.getError().getMessage());
					return Status.ERROR; 		
				} TextAnnotation annotation = res.getFullTextAnnotation();
				result.append(annotation.getText());
			}
		} catch (IOException e){
			e.printStackTrace();
			return Status.ERROR;
		} return Status.OK;
	}

}
