package de.heavenhr.recruitement.service.event;

import de.heavenhr.recruitement.service.model.Application;

/**
 * The class <code>StatusChangeEvent</code> is a subclass of {@link Event} that is only focused for status changes.
 *
 * @author Hossam Yahya
 */
public class StatusChangeEvent<T> extends Event<T> {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -794845065870389772L;
	
	/**
	 * The old status
	 */
	private Application.Status oldStatus;
	
	/**
	 * The new status
	 */
	private Application.Status newStatus;
	
	private StatusChangeEvent(T target, de.heavenhr.recruitement.service.event.Event.Type type, long timestamp) {
		super(target, type, timestamp);
	}

	public StatusChangeEvent(T target, long timestamp, Application.Status oldStatus, Application.Status newStatus) {
		super(target, Event.Type.STATUS_CHANGE, timestamp);
		this.newStatus = newStatus;
		this.oldStatus = oldStatus;
	}
	
	public Application.Status getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Application.Status oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Application.Status getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Application.Status newStatus) {
		this.newStatus = newStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatusChangeEvent [target=").append(getTarget()).append(", type=").append(getType()).append(", timestamp=")
				.append(getTimestamp()).append(", oldStatus=").append(oldStatus).append(", newStatus=").append(newStatus)
				.append("]");
		return builder.toString();
	}

}
