package de.heavenhr.recruitement.service;

import java.util.ArrayList;
import java.util.Collection;
import de.heavenhr.recruitement.service.exception.NoDataFoundException;
import de.heavenhr.recruitement.service.model.Application;
import de.heavenhr.recruitement.service.model.Application.Status;

import java.util.Date;
import org.junit.*;

import de.heavenhr.recruitement.service.dao.DAO;
import de.heavenhr.recruitement.service.exception.DataException;
import de.heavenhr.recruitement.service.model.Offer;
import static org.junit.Assert.*;

/**
 * The class <code>DAOTest</code> contains tests for the class <code>{@link DAO}</code>.
 *
 * @author Hossam Yahya
 */
public class DAOTest {
	
	// **************************************** Test createApplication(Application) **************************************** 
	
	/**
	 * Run the void createApplication(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Empty job title
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.DataException.class)
	public void testCreateApplication_1() throws Exception {
		DAO fixture = DAO.newInstance();
		Application application = new Application(null, "test@gmail.com", "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
	}

	/**
	 * Run the void createApplication(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 2: Empty candidate email
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.DataException.class)
	public void testCreateApplication_2() throws Exception {
		DAO fixture = DAO.newInstance();
		Application application = new Application("Senior Manager", null, "resume text", de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
	}
	
	/**
	 * Run the void createApplication(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 3: Nonexistent related offer
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.NoDataFoundException.class)
	public void testCreateApplication_3() throws Exception {
		DAO fixture = DAO.newInstance();
		
		// Application with impossibly existing related offer
		Application application = new Application(String.valueOf(System.nanoTime()), "test@gmail.com"
				, "resume text", de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
	}
	
	/**
	 * Run the void createApplication(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 4: Successful scenario
	 */
	@Test
	public void testCreateApplication_4() throws Exception {
		DAO fixture = DAO.newInstance();
		
		Offer offer = new Offer("Senior Manager", new Date(), 1L);
		fixture.createOffer(offer);
		
		// Application with assured unique candidate email for an offer
		Application application = new Application("Senior Manager", String.valueOf(System.nanoTime())+"_test@gmail.com", "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
	}
	
	/**
	 * Run the void createApplication(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 5: Duplicate
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.DataException.class)
	public void testCreateApplication_5() throws Exception {
		DAO fixture = DAO.newInstance();
		
		Offer offer = new Offer("Senior Manager", new Date(), 1L);
		fixture.createOffer(offer);
		
		Application application = new Application("Senior Manager", "test@gmail.com", "resume text", de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
		
		// Try to create the same application again
		fixture.createApplication(application);
	}
	
	// **************************************** Test createOffer(Offer) **************************************** 

	/**
	 * Run the boolean createOffer(Offer) method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Empty job title
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.DataException.class)
	public void testCreateOffer_1() throws Exception {
		DAO fixture = DAO.newInstance();
		Offer offer = new Offer(null, new Date(), 1L);

		fixture.createOffer(offer);

	}

	/**
	 * Run the boolean createOffer(Offer) method test.
	 *
	 * @throws Exception
	 *
	 * Case 2: Duplicate
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.DataException.class)
	public void testCreateOffer_2() throws Exception {
		DAO fixture = DAO.newInstance();
		Offer offer = new Offer("jobtitle", new Date(), 1L);

		fixture.createOffer(offer);
		
		// Try to create the same offer again
		fixture.createOffer(offer);
	}
	
	/**
	 * Run the boolean createOffer(Offer) method test.
	 *
	 * @throws Exception
	 *
	 * Case 3: Success
	 */
	@Test
	public void testCreateOffer_3()
		throws Exception {
		DAO fixture = DAO.newInstance();
		
		// Offer with assured unique job title
		Offer offer = new Offer(String.valueOf(System.nanoTime()), new Date(), 1L);

		fixture.createOffer(offer);

	}

	// **************************************** Test getAllApplicationsCount() **************************************** 

	/**
	 * Run the int getApplicationCount() method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Success
	 */
	@Test
	public void testGetApplicationCount_1() throws Exception {
		DAO fixture = DAO.newInstance();

		String jobTitle = String.valueOf(System.nanoTime())+"_Senior Manager";
		Offer offer = new Offer(jobTitle, new Date(), 1L);
		fixture.createOffer(offer);
		
		// Application with assured unique candidate email for an offer
		Application application = new Application(jobTitle, String.valueOf(System.nanoTime())+"_test@gmail.com", "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
		
		long result = fixture.getApplicationCount();

		assertTrue(result > 0);
	}

	// **************************************** Test getAllOffers() **************************************** 

	/**
	 * Run the Collection<Offer> getAllOffers() method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Success
	 */
	@Test
	public void testGetAllOffers_1() throws Exception {
		DAO fixture = DAO.newInstance();
		
		// Offer with assured unique job title
		Offer offer = new Offer(String.valueOf(System.nanoTime()), new Date(), 1L);

		fixture.createOffer(offer);

		Collection<Offer> result = fixture.getAllOffers();

		assertNotNull(result);
	}

	// **************************************** Test getOfferApplication(String,String) **************************************** 

	/**
	 * Run the Application getOfferApplication(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Success
	 */
	@Test
	public void testGetOfferApplication_1() throws Exception {
		DAO fixture = DAO.newInstance();
		String jobTitle = String.valueOf(System.nanoTime())+"_Senior Manager";
		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		
		Offer offer = new Offer(jobTitle, new Date(), 1L);
		fixture.createOffer(offer);
		
		// Application with assured unique candidate email for an offer
		Application application = new Application(jobTitle, candidateEmail, "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
		
		Application result = fixture.getOfferApplication(jobTitle, candidateEmail);

		assertNotNull(result);
	}

	/**
	 * Run the Application getOfferApplication(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * Case 2: Nonexistent application
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.NoDataFoundException.class)
	public void testGetOfferApplication_2() throws Exception {
		DAO fixture = DAO.newInstance();
		String jobTitle = String.valueOf(System.nanoTime());
		String candidateEmail = String.valueOf(System.nanoTime());

		fixture.getOfferApplication(jobTitle, candidateEmail);
	}

	// **************************************** Test getOfferApplications(String) **************************************** 

	/**
	 * Run the ArrayList<Application> getOfferApplications(String) method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Success
	 */
	@Test
	public void testGetOfferApplications_1() throws Exception {
		DAO fixture = DAO.newInstance();
		String jobTitle = String.valueOf(System.nanoTime())+"_Senior Manager";
		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		
		Offer offer = new Offer(jobTitle, new Date(), 1L);
		fixture.createOffer(offer);
		
		// Application with assured unique candidate email for an offer
		Application application = new Application(jobTitle, candidateEmail, "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
		
		ArrayList<Application> result = fixture.getOfferApplications(jobTitle);

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Run the ArrayList<Application> getOfferApplications(String) method test.
	 *
	 * @throws Exception
	 *
	 * Case 2: Nonexistent offer
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.NoDataFoundException.class)
	public void testGetOfferApplications_2() throws Exception {
		DAO fixture = DAO.newInstance();
		String jobTitle = String.valueOf(System.nanoTime());

		ArrayList<Application> result = fixture.getOfferApplications(jobTitle);

		assertNull(result);
	}

	// **************************************** Test getOfferApplicationsCount(String) **************************************** 

	/**
	 * Run the long getOfferApplicationsCount(String) method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Success
	 */
	@Test
	public void testGetOfferApplicationsCount_1() throws Exception {
		DAO fixture = DAO.newInstance();
		
		String jobTitle = String.valueOf(System.nanoTime())+"_Senior Manager";
		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		
		Offer offer = new Offer(jobTitle, new Date(), 1L);
		fixture.createOffer(offer);
		
		// Application with assured unique candidate email for an offer
		Application application = new Application(jobTitle, candidateEmail, "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
		
		long result = fixture.getOfferApplicationsCount(jobTitle);

		assertEquals(1, result);
	}

	/**
	 * Run the long getOfferApplicationsCount(String) method test.
	 *
	 * @throws Exception
	 *
	 * Case 2: Nonexistent offer
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.NoDataFoundException.class)
	public void testGetOfferApplicationsCount_2()
		throws Exception {
		DAO fixture = DAO.newInstance();
		String jobTitle = String.valueOf(System.nanoTime());

		long result = fixture.getOfferApplicationsCount(jobTitle);

		assertEquals(0L, result);
	}

	// **************************************** Test updateApplicationStatus(Application) **************************************** 

	/**
	 * Run the void updateApplicationStatus(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 1: Success
	 */
	@Test
	public void testUpdateApplicationStatus_1() throws Exception {
		DAO fixture = DAO.newInstance();
		String jobTitle = String.valueOf(System.nanoTime())+"_Senior Manager";
		String candidateEmail = String.valueOf(System.nanoTime())+"_test@gmail.com";
		
		Offer offer = new Offer(jobTitle, new Date(), 1L);
		fixture.createOffer(offer);
		
		// Application with assured unique candidate email for an offer
		Application application = new Application(jobTitle, candidateEmail, "resume text"
				, de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.createApplication(application);
		
		// Change the application status from APPLIED to HIRED
		application.setStatus(Status.HIRED);
		fixture.updateApplicationStatus(application);

		assertEquals(Status.HIRED, fixture.getOfferApplication(jobTitle, candidateEmail).getStatus());
	}

	/**
	 * Run the void updateApplicationStatus(Application) method test.
	 *
	 * @throws Exception
	 *
	 * Case 2: Nonexistent application
	 */
	@Test(expected = de.heavenhr.recruitement.service.exception.NoDataFoundException.class)
	public void testUpdateApplicationStatus_2()
		throws Exception {
		DAO fixture = DAO.newInstance();
		Application application = new Application(String.valueOf(System.nanoTime()), "", "", de.heavenhr.recruitement.service.model.Application.Status.APPLIED);

		fixture.updateApplicationStatus(application);

	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(DAOTest.class);
	}
}