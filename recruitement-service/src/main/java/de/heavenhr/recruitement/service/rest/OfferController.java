package de.heavenhr.recruitement.service.rest;

import java.net.URI;
import java.util.Collection;

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
import de.heavenhr.recruitement.service.model.Application;
import de.heavenhr.recruitement.service.model.Offer;

/**
 * The class <code>OfferController</code> contains APIs to manage offers.
 *
 * @author Hossam Yahya
 */

@RestController
@RequestMapping("/api/offers/")
public class OfferController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Creates an {@link Offer}
	 * @param offer the {@link Offer} to be created
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createOffer(@RequestBody Offer offer) throws Exception {
		logger.info("createOffer() called: " + offer);
		
		DAO.newInstance().createOffer(offer);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{jobTitle}").buildAndExpand(offer.getJobTitle()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Finds an {@link Offer} by job title
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @return the found {@link Offer}
	 * @throws Exception
	 */
	@RequestMapping(value = "/{jobTitle}", method = RequestMethod.GET, produces = "application/json")
	public Offer findOfferByJobTitle(@PathVariable String jobTitle) throws Exception {
		logger.info("findOfferByJobTitle() called: " + jobTitle);
		return DAO.newInstance().findOfferByJobTitle(jobTitle);
	}
	
	/**
	 * Finds the list of all offers in the system
	 * @return list of all offers in the system
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public Collection<Offer> getAllOffers() throws Exception {
		logger.info("getOffer() called");
		return DAO.newInstance().getAllOffers();
	}
	
	/**
	 * Finds all {@link Application} for a specific {@link Offer}
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @return collection of all applications under a specific offer
	 * @throws Exception
	 */
	@RequestMapping(value = "/{jobTitle}/applications", method = RequestMethod.GET, produces = "application/json")
	public Collection<Application> getOfferApplications(@PathVariable String jobTitle) throws Exception {
		logger.info("getOfferApplications() called");
		return DAO.newInstance().getOfferApplications(jobTitle);
	}
	
	/**
	 * Finds the count of all {@link Application} for a specific {@link Offer}
	 * @param jobTitle the unique key to identify the {@link Offer}
	 * @return the count of all applications under a specific offer
	 * @throws Exception
	 */
	@RequestMapping(value = "/{jobTitle}/applications-count", method = RequestMethod.GET, produces = "application/json")
	public long getOfferApplicationsCount(@PathVariable String jobTitle) throws Exception {
		logger.info("getOfferApplicationsCount() called");
		return DAO.newInstance().getOfferApplicationsCount(jobTitle);
	}
}
