package de.heavenhr.recruitement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import de.heavenhr.recruitement.service.rest.OfferController;;

/**
 * The class <code>ApplicationControllerTest</code> contains tests for the class <code>{@link ApplicationController}</code>.
 *
 * @author Hossam Yahya
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ApplicationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // **************************************** GET:/api/applications/{jobTitle}/{candidateEmail:.+} **************************************** 
	
    /**
 	 * Run the GET:/api/applications/{jobTitle}/{candidateEmail:.+} test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 1: Nonexistent application
 	 */
    @Test
    public void testGETApplication_1() throws Exception {
        HttpStatus result = this.restTemplate.getForEntity("/api/applications/{jobTitle}/{candidateEmail:.+}"
        		, String.class, String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime())).getStatusCode();
        
        assertEquals(HttpStatus.NOT_FOUND, result);
    }
    
    /**
 	 * Run the GET:/api/applications/{jobTitle}/{candidateEmail:.+} test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 2: Success
 	 */
    @Test
    public void testGETApplication_2() throws Exception {
    	
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
		
        HttpStatus result = this.restTemplate.getForEntity("/api/applications/{jobTitle}/{candidateEmail:.+}"
        		, String.class, jobTitle, candidateEmail).getStatusCode();
        assertEquals(HttpStatus.OK, result);
    }
    
    
    // **************************************** PUT:/api/applications/ **************************************** 
	
    /**
 	 * Run the PUT:/api/applications/ test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 1: Nonexistent application
 	 */
    @Test
    public void testUpdateApplication_1() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jobTitle = String.valueOf(System.nanoTime());
		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);

        ResponseEntity<String> out = this.restTemplate.exchange("/api/applications/", HttpMethod.PUT, applicationEntity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, out.getStatusCode());
    }
    
    /**
 	 * Run the PUT:/api/applications/ test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 2: Success
 	 */
    @Test
    public void testUpdateApplication_2() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		// Create offer
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		// Create application
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);

		// Update application
		HttpEntity<String> applicationEntityNew = new HttpEntity<String>(applicationRequestJson.replace("APPLIED", "HIRED"), headers);
        ResponseEntity<String> out = this.restTemplate.exchange("/api/applications/", HttpMethod.PUT, applicationEntityNew, String.class);

        assertEquals(HttpStatus.OK, out.getStatusCode());
    }
    
// **************************************** POST:/api/applications/ **************************************** 
	
    /**
 	 * Run the POST:/api/applications/ test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 1: Nonexistent application
 	 */
    @Test
    public void testCreateApplication_1() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jobTitle = String.valueOf(System.nanoTime());
		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);

        ResponseEntity<String> out = this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, out.getStatusCode());
    }
    
    /**
 	 * Run the POST:/api/applications/ test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 2: Success
 	 */
    @Test
    public void testCreateApplication_2() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		// Create offer
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		// Create application
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		ResponseEntity<String> out = this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);

        assertEquals(HttpStatus.CREATED, out.getStatusCode());
    }
    
    /**
 	 * Run the POST:/api/applications/ test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 3: Empty job title
 	 */
    @Test
    public void testCreateApplication_3() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = "";
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		// Create offer
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		// Create application
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		ResponseEntity<String> out = this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);

        assertEquals(HttpStatus.CONFLICT, out.getStatusCode());
    }
    
    /**
 	 * Run the POST:/api/applications/ test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 4: Empty candidate email
 	 */
    @Test
    public void testCreateApplication_4() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		// Create offer
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = "";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		// Create application
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		ResponseEntity<String> out = this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);

        assertEquals(HttpStatus.CONFLICT, out.getStatusCode());
    }
    
// **************************************** GET:/api/applications/count **************************************** 
	
    /**
 	 * Run the GET:/api/applications/count test.
 	 *
 	 * @throws Exception
 	 *
 	 * Case 1: Success
 	 */
    @Test
    public void testGETApplicationCount_1() throws Exception {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jobTitle = String.valueOf(System.nanoTime());
		String requestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," + "\"startDate\": 1493229767700,"
				+ "\"numberOfApplications\": 0" + "}";

		// Create offer
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		this.restTemplate.postForEntity("/api/offers/", entity, String.class);

		String candidateEmail = "";
		String applicationRequestJson = "{" + "\"jobTitle\": \"" + jobTitle + "\"," +
				"\"candidateEmail\": \"" + candidateEmail + "\"," +
				"\"resumeText\": \"This is my resume3\"," +
				"\"status\": \"APPLIED\"" + "}";
		
		// Create application
		HttpEntity<String> applicationEntity = new HttpEntity<String>(applicationRequestJson, headers);
		ResponseEntity<String> out = this.restTemplate.postForEntity("/api/applications/", applicationEntity, String.class);

		// Find count
		ResponseEntity<String> result = this.restTemplate.getForEntity("/api/applications/count"
        		, String.class, String.valueOf(System.nanoTime()), String.valueOf(System.nanoTime()));
        
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertThat(Long.parseLong(result.getBody()) > 0);
    }
}
