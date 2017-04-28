package de.heavenhr.recruitement.service.event;

import java.io.Serializable;

/**
 * The class <code>Event</code> is a the base model class for events.
 *
 * @author Hossam Yahya
 */
public class Event<T> implements Serializable{

    /**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 7996806330813802233L;
	
	/**
	 * The event target object
	 */
	private T target;
	
    /**
     * The event type, as one of { STATUS_CHANGE, APPLICATION_CREATED, APPLICATION_DELETED }
     */
    private Type type;
    
    
    /**
     * The event timestamp
     */
    private long timestamp;
    
    public Event(T target, Type type, long timestamp) {
		super();
		this.target = target;
		this.type = type;
		this.timestamp = timestamp;
	}

	public T getTarget() {
		return target;
	}

	protected void setTarget(T target) {
		this.target = target;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [target=").append(target).append(", type=").append(type).append(", timestamp=")
				.append(timestamp).append("]");
		return builder.toString();
	}

	public enum Type {
		STATUS_CHANGE, 
		APPLICATION_CREATED, 
		APPLICATION_DELETED
	}
}
