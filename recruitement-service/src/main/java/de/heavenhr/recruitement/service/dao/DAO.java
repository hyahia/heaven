package de.heavenhr.recruitement.service.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import de.heavenhr.recruitement.service.exception.DataException;
import de.heavenhr.recruitement.service.exception.NoDataFoundException;
import de.heavenhr.recruitement.service.model.Application;
import de.heavenhr.recruitement.service.model.Offer;

/**
 * The class <code>DAO</code> contains logic to manage model persistence functionality.
 *
 * @author Hossam Yahya
 */
public class DAO {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * The collection of all offers in the system. TODO should be changed to a database table
	 */
	private static HashMap<String,Offer> allOffers  = new HashMap<String,Offer>();
	/**
	 * The collection of all applications in the system. TODO should be changed to a database table
	 */
	private static HashMap<String,Application> allApplications  = new HashMap<String,Application>();
	/**
	 * The local instance used in the singleton
	 */
	private static DAO instance;
	
	private DAO(){
		
	}
	
	/**
	 * Create the singleton object if not already created
	 * @return the created object
	 */
	public static DAO newInstance(){
		if(instance == null){
			instance = new DAO();
		}
		
		return instance;
	}
	
	/**
	 * Finds the list of all offers in the system
	 * @return list of all offers in the system
	 * @throws NoDataFoundException
	 */
	public Collection<Offer> getAllOffers() throws NoDataFoundException {
		logger.debug("getAllOffers() is called");
		
		if(allOffers.isEmpty()){
			logger.error("getAllOffers() failed, no data found");

			throw new NoDataFoundException("No offers found");
		}
		
		return allOffers.values();
	}
	
	/**
	 * Finds an {@link Offer} by job title
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @return the found {@link Offer}
	 * @throws NoDataFoundException
	 */
	public Offer findOfferByJobTitle(String jobTitle) throws NoDataFoundException {
		logger.debug("findOfferByJobTitle() is called: " + jobTitle);

		Offer offer = allOffers.get(jobTitle);
		if(offer == null){
			logger.error("findOfferByJobTitle() no offer found: " + jobTitle);

			throw new NoDataFoundException("No offer found for job title: " + jobTitle);
		}
		
		logger.debug("findOfferByJobTitle() found offer: " + offer);

		return offer;
	}
	
	/**
	 * Updates the {@link Application} status
	 * @param application the {@link Application} to be updated
	 * @throws NoDataFoundException
	 */
	public void updateApplicationStatus(Application application) throws NoDataFoundException {
		logger.debug("updateApplicationStatus() is called: " + application);

		Application applicationOld = allApplications.get(application.getJobTitle() + "_" + application.getCandidateEmail());
		
		if (applicationOld == null) {
			logger.error("updateApplicationStatus() no application found: " + application);

			throw new NoDataFoundException("Application doesnot exist!");
		}
		
		// Only the status is required to be changed.
		applicationOld.setStatus(application.getStatus());
		allApplications.put(application.getJobTitle() + "_" + application.getCandidateEmail(), application);
		
		logger.debug("updateApplicationStatus() application status changed: " + application);
	}
	
	/**
	 * Creates an {@link Application}
	 * @param application the {@link Application} to be created
	 * @throws DataException when invalid data provided or application already exist
	 * @throws NoDataFoundException when the related offer does not exist
	 */
	public void createApplication(Application application) throws DataException, NoDataFoundException{
		logger.debug("createApplication() called: " + application);
		
		// Validate job title
		if (StringUtils.isEmpty(application.getJobTitle())) {
			logger.error("createApplication() failed, empty job title");

			throw new DataException("Job Title is mandatory");
		}
		
		// Validate candidate email
		if (StringUtils.isEmpty(application.getCandidateEmail())) {
			logger.error("createApplication() failed empty candidate email");

			throw new DataException("Candidate Email is mandatory");
		}
		
		// Validate that offer exists
		if (allOffers.get(application.getJobTitle()) == null) {
			logger.error("createApplication() failed offer does not exist: " + application.getJobTitle());

			throw new NoDataFoundException("Offer not found!");
		}
		
		//Validate that application does not exists already
		if (allApplications.containsKey(application.getJobTitle() + "_" + application.getCandidateEmail())) {
			logger.error("createApplication() failed application already exist: " + application.getJobTitle() + "_" + application.getCandidateEmail());

			throw new DataException("Application already exist!");
		}
		
		// Create the application (add it to the static map of applications)
		allApplications.put(application.getJobTitle() + "_" + application.getCandidateEmail(), application);
		
		// Increment number of applications for the related offer
		Offer offer = allOffers.get(application.getJobTitle());
		offer.setNumberOfApplications(offer.getNumberOfApplications()+1);
		allOffers.put(offer.getJobTitle(), offer);
		
		logger.debug("createApplication() application created: " + application);
	}
	
	/**
	 * Creates an {@link Offer}
	 * @param offer the {@link Offer} to be created
	 * @throws DataException
	 */
	public void createOffer(Offer offer) throws DataException{
		logger.debug("createOffer() called: " + offer);
		
		// Validate job title
		if (StringUtils.isEmpty(offer.getJobTitle())) {
			logger.error("createOffer() failed, empty job title");
			
			throw new DataException("Job Title is mandatory");
		}
		
		// Validate that offer does not exists already
		if (allOffers.get(offer.getJobTitle()) != null) {
			logger.error("createOffer() failed, offer already exist: " + offer.getJobTitle());
			
			throw new DataException("Offer already exist!");
		}
		
		allOffers.put(offer.getJobTitle(), offer);
		
		logger.debug("createOffer() offer created: " + offer);
	}
	
	/**
	 * Finds all {@link Application} for a specific {@link Offer}
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @return collection of all applications under a specific offer
	 * @throws NoDataFoundException
	 */
	public ArrayList<Application> getOfferApplications(String jobTitle) throws NoDataFoundException{
		logger.debug("getOfferApplications() called: " + jobTitle);
		ArrayList<Application> applications = new ArrayList<Application>();
		
		// Find applications by jobTitle
		for(Application application :allApplications.values()){
			if(jobTitle.equalsIgnoreCase(application.getJobTitle()))
					applications.add(application);
		}
		
		if (applications.size() == 0) {
			logger.error("getOfferApplications() failed, no applications found for offer: " +jobTitle); 
			
			throw new NoDataFoundException("No data found!");
		}
		
		logger.debug("getOfferApplications() found applications: " + applications);

		return applications;
	}
	
	/**
	 * Finds the count of all {@link Application} for a specific {@link Offer}
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @return the count of all applications under a specific offer
	 * @throws NoDataFoundException
	 */
	public long getOfferApplicationsCount(String jobTitle) throws NoDataFoundException{
		logger.debug("getOfferApplicationsCount() called: " + jobTitle);

		long count = 0;
		
		// Find applications by jobTitle
		for(Application application :allApplications.values()){
			if(jobTitle.equalsIgnoreCase(application.getJobTitle()))
					count++;
		}
		
		if (count == 0) {
			logger.error("getOfferApplicationsCount() failed, no applications found for offer: " +jobTitle); 

			throw new NoDataFoundException("No data found!");
		}
		
		logger.debug("getOfferApplications() found applications#: " + count);

		return count;
	}
	
	/**
	 * Finds a specific {@link Application}
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @param candidateEmail the unique key to identify the {@link Application} along with the job title
	 * @return the found {@link Application}
	 * @throws NoDataFoundException
	 */
	public Application getOfferApplication(String jobTitle, String candidateEmail) throws NoDataFoundException{
		logger.debug("getOfferApplication() called: " + jobTitle + ", " + candidateEmail);
		
		Application application = allApplications.get(jobTitle + "_" + candidateEmail);
		
		if (application == null) {
			logger.error("getOfferApplication() failed, no application found for: " + jobTitle + "_" + candidateEmail); 

			throw new NoDataFoundException("Application doesnot exist!");
		}
		
		logger.debug("getOfferApplication() found application: " + application);

		return application;
	}

	/**
	 * Finds the count of all {@link Application} in the system
	 * @return the count of all applications  in the system
	 * @throws NoDataFoundException
	 */
	public long getApplicationCount() throws NoDataFoundException {
		logger.debug("getApplicationCount() called");

		// TODO Should be changed to return long as count of all applications from Application database table
		// when migrating to database persistence.
		if (allApplications.size() == 0) {
			logger.error("getOfferApplicationsCount() failed, no applications found"); 

			throw new NoDataFoundException("No applications found!");
		}
		long count = allApplications.size();
		
		logger.debug("getApplicationCount() found applications#: " + count);

		return count;
	}
}
