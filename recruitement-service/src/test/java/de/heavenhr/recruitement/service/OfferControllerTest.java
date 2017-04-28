package de.heavenhr.recruitement.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import de.heavenhr.recruitement.service.rest.OfferController;;

/**
 * The class <code>OfferControllerTest</code> contains tests for the class <code>{@link OfferController}</code>.
 *
 * @author Hossam Yahya
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class OfferControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // **************************************** GET:/api/offers/{jobTitle} **************************************** 
	
    /**
 	 * Run the GET:/api/offers/{jobTitle} test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 1: Nonexistent offer
 	 */
    @Test
    public void testGETOffer_1() throws Exception {
        HttpStatus result = this.restTemplate.getForEntity("/api/offers/{jobTitle}"
        		, String.class, String.valueOf(System.nanoTime())).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, result);
    }
    
    /**
 	 * Run the GET:/api/offers/{jobTitle} test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 2: Success
 	 */
    @Test
    public void testGETOffer_2() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	String jobTitle = String.valueOf(System.nanoTime());
    	String requestJson = "{"+
				    			"\"jobTitle\": \"" + jobTitle + "\","+
				    			"\"startDate\": 1493229767700,"+
				    			"\"numberOfApplications\": 0"+
				    		 "}";
    	
		HttpEntity<String> entity = new HttpEntity<String>(requestJson ,headers);
    	this.restTemplate.postForEntity("/api/offers/", entity, String.class);
    	
        HttpStatus result = this.restTemplate.getForEntity("/api/offers/{jobTitle}"
        		, String.class, jobTitle).getStatusCode();
        assertEquals(HttpStatus.OK, result);
    }
    
    // **************************************** Test POST:/api/offers/ **************************************** 
	
  	/**
  	 * Run the POST:/api/offers/ test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 1: Empty job title
  	 */
     @Test
	public void testPOSTOffer_1() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = "";
		System.out.println(StringUtils.isEmpty(jobTitle));
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		HttpStatus result = this.restTemplate.postForEntity("/api/offers/", entity, String.class).getStatusCode();

		assertEquals(HttpStatus.CONFLICT, result);
	}
     
     /**
  	 * Run the POST:/api/offers/ test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 2: preexistent offer
  	 */
     @Test
	public void testPOSTOffer_2() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class).getStatusCode();

		// Try to create same offer again.
		HttpStatus result = this.restTemplate.postForEntity("/api/offers/", entity, String.class).getStatusCode();

		assertEquals(HttpStatus.CONFLICT, result);
	}
     
     /**
  	 * Run the POST:/api/offers/ test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 3: Success
  	 */
     @Test
	public void testPOSTOffer_3() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		HttpStatus result = this.restTemplate.postForEntity("/api/offers/", entity, String.class).getStatusCode();

		assertEquals(HttpStatus.CREATED, result);
	}


     // **************************************** GET:/api/offers/ **************************************** 
 	
     /**
  	 * Run the GET:/api/offers/ test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 1: Success
  	 */
     @Test
	public void testGETOffers_1() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		HttpStatus result = this.restTemplate.getForEntity("/api/offers/", String.class).getStatusCode();

		assertEquals(HttpStatus.OK, result);
	}

     // **************************************** GET:/api/offers/{jobTitle}/applications **************************************** 
 	
     /**
  	 * Run the GET:/api/offers/{jobTitle}/applications test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 1: Nonexistent offer
  	 */
     @Test
     public void testGETOfferApplications_1() throws Exception {
         HttpStatus result = this.restTemplate.getForEntity("/api/offers/{jobTitle}/applications"
         		, String.class, String.valueOf(System.nanoTime())).getStatusCode();
         assertEquals(HttpStatus.NOT_FOUND, result);
     }
     
     /**
  	 * Run the GET:/api/offers/{jobTitle}/applications test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 3: Success
  	 */
     @Test
	public void testGETOfferApplications_2() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);
		
		HttpStatus result = this.restTemplate.getForEntity("/api/offers/{jobTitle}/applications", String.class, jobTitle)
				.getStatusCode();
		assertEquals(HttpStatus.OK, result);
	}
    
  // **************************************** GET:/api/offers/{jobTitle}/applications-count **************************************** 
  	
     /**
  	 * Run the GET:/api/offers/{jobTitle}/applications-count test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 1: Nonexistent offer
  	 */
     @Test
     public void testGETOfferApplicationsCount_1() throws Exception {
         HttpStatus result = this.restTemplate.getForEntity("/api/offers/{jobTitle}/applications-count"
         		, String.class, String.valueOf(System.nanoTime())).getStatusCode();
         assertEquals(HttpStatus.NOT_FOUND, result);
     }
     
     /**
  	 * Run the GET:/api/offers/{jobTitle}/applications-count test.
  	 *
  	 * @throws Exception
  	 *
  	 * Case 3: Success
  	 */
     @Test
	public void testGETOfferApplicationsCount_2() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);
		
		HttpStatus result = this.restTemplate.getForEntity("/api/offers/{jobTitle}/applications-count", String.class, jobTitle)
				.getStatusCode();
		assertEquals(HttpStatus.OK, result);
	}
}
