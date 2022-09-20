package interfaces;

/**
 * It is important that the object that implements this interface also implements the 
 * getHashCode and equals methods. 
 * <p>
 * Without these implementations, the graph may not work properly
 * @author kovalski
 *
 */
public interface Plotable {

	/**
	 * Gets label from graph vertex
	 * @return label
	 */
	public String getLabel();
	
	/**
	 * Sets a new label that will be used in the graph's vertex
	 * @param label the new label
	 */
	public void setLabel(String label);
	
	public int hashCode();
	public boolean equals(Object obj);
}
