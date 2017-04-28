package de.heavenhr.recruitement.service.rest;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.heavenhr.recruitement.service.dao.DAO;
import de.heavenhr.recruitement.service.event.EventListener;
import de.heavenhr.recruitement.service.event.StatusChangeEvent;
import de.heavenhr.recruitement.service.model.Application;

/**
 * The class <code>ApplicationController</code> contains APIs to manage applications.
 *
 * @author Hossam Yahya
 */
@RestController
@RequestMapping("/api/applications/")
public class ApplicationController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Finds candidate {@link Application} for a specified {@link Offer} 
	 * @param jobTitle the offer unique key
	 * @param candidateEmail the {@link Application} unique key per offer
	 * @return  the {@link Application} found with the supplied jobTitle and candidateEmail
	 * @throws Exception
	 */
	// TODO should be changed to /{id} when migrating to database persistence.
	@RequestMapping(value = "/{jobTitle}/{candidateEmail:.+}", method = RequestMethod.GET, produces = "application/json")
	public Application getApplication(@PathVariable String jobTitle, @PathVariable String candidateEmail) throws Exception {
		logger.info("getApplication() called: " + jobTitle + ", " + candidateEmail);
		return DAO.newInstance().getOfferApplication(jobTitle, candidateEmail);
	}
	
	
	/**
	 * Updates the status of a candidate {@link Application}
	 * @param application the {@link Application} to be updated
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json")
	public void updateApplicationStatus(@RequestBody Application application) throws Exception {
		logger.info("updateApplicationStatus() called: " + application);
		
		Application.Status oldApplicationStatus = DAO.newInstance().getOfferApplication(application.getJobTitle(), application.getCandidateEmail()).getStatus();
		DAO.newInstance().updateApplicationStatus(application);
		
		// Log the application status change event.
		logger.info(String.format("***** Application Status Change Event <Job Title:%s, Candidate Email:%s, New Status:%s> *****"
				, application.getJobTitle(), application.getCandidateEmail(), application.getStatus()));
		
		// Dispatch the application status change event.
		EventListener listener = new EventListener();
		StatusChangeEvent<Application> event = new StatusChangeEvent<Application>(application, System.currentTimeMillis()
				, oldApplicationStatus,application.getStatus());
		listener.handleEvent(event);
	}
	
	/**
	 * Creates a candidate {@link Application}
	 * @param application the {@link Application} to be created
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createApplication(@RequestBody Application application) throws Exception {
		logger.info("createApplication() called: " + application);
			
		DAO.newInstance().createApplication(application);
		
		// Create URI for the newly created  application
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{jobTitle}").path("/{candidateEmail}").buildAndExpand(application.getJobTitle(),application.getCandidateEmail()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	
	/**
	 * Calculates the total number of applications in the system
	 * @return the total number of applications in the system
	 * @throws Exception
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json")
	public long getApplicationCount() throws Exception {
		logger.info("getApplicationCount() called");
			
		long count = DAO.newInstance().getApplicationCount();
		
		return count;
	}
}
