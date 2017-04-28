package de.heavenhr.recruitement.service.model;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * The class <code>Application</code> is the model class to represent an application.
 *
 * @author Hossam Yahya
 */
@XmlRootElement
public class Application {
	// TODO should be changed to referential key (the Offer primary key) when migrating to database persistence.
	/**
	 * Represents a referential key to the related offer
	 */
	private String jobTitle;
	/**
	 * Represents the application unique id per offer
	 */
	private String candidateEmail;
	/**
	 * Is the applicant's resume as a plain text
	 */
	private String resumeText;
	/**
	 * Is the application status, as one of {APPLIED, INVITED, REJECTED, HIRED}
	 */
	private Status status;
	
	public Application() {
	}
	
	public Application(String jobTitle, String candidateEmail, String resumeText, Status status) {
		this.jobTitle = jobTitle;
		this.candidateEmail = candidateEmail;
		this.resumeText = resumeText;
		this.status = status;
	}

	public String getJobTitle() {
		return jobTitle;
	}
	
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Application [jobTitle=").append(jobTitle)
				.append(", candidateEmail=").append(candidateEmail)
				.append(", resumeText=").append(resumeText)
				.append(", status=").append(status).append("]");
		return builder.toString();
	}

	public enum Status {
		APPLIED, 
		INVITED, 
		REJECTED, 
		HIRED
	}
}
