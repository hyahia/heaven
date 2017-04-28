package de.heavenhr.recruitement.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.heavenhr.recruitement.service.model.Application;

/**
 * The class <code>EventListener</code> contains a basic implementation for event handling.
 *
 * @author Hossam Yahya
 */
public class EventListener {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Dispatches events to the correct method
	 * @param event the {@link Event} to be handled
	 */
	public void handleEvent(Event event){
		if(event.getType().equals(Event.Type.STATUS_CHANGE) && event.getTarget() instanceof Application){
			handleApplicationStatusChangeEvent((StatusChangeEvent<Application>)event);
		}else{
			handleOtherEvents(event);
		}
	}
	
	/**
	 * Handles {@link StatusChangeEvent} events
	 * @param event the {@link StatusChangeEvent} to be handled
	 */
	private void handleApplicationStatusChangeEvent(StatusChangeEvent<Application> event){
		logger.info(String.format("Event received <%s>", event.toString()));
		// Handle event.
		logger.info(String.format("Event handled <%s>", event.toString()));
	}
	
	/**
	 * Handles other kinds of {@link Event}
	 * @param event the {@link Event} to be handled
	 */
	private void handleOtherEvents(Event event){
		// Handle other events.
	}
}
