package de.heavenhr.recruitement.service.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class <code>Offer</code> is the model class to represent an offer.
 *
 * @author Hossam Yahya
 */
@XmlRootElement
public class Offer {

	/**
	 * Represents the offer unique id
	 */
	private String jobTitle;
	/**
	 * Is the offer start date
	 */
    private Date startDate;
    /**
	 * represents the number of applications submitted for this offer
	 */
    private long numberOfApplications;
    
	public Offer() {
	}
    
	public Offer(String jobTitle, Date startDate, long numberOfApplications) {
		super();
		this.jobTitle = jobTitle;
		this.startDate = startDate;
		this.numberOfApplications = numberOfApplications;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public long getNumberOfApplications() {
		return numberOfApplications;
	}
	
	public void setNumberOfApplications(long numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Offer [jobTitle=").append(jobTitle).append(", startDate=").append(startDate)
				.append(", numberOfApplications=").append(numberOfApplications).append("]");
		return builder.toString();
	}
    
}
