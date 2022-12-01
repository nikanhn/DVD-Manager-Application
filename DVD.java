public class DVD {

	// Fields:

	private String title;		// Title of this DVD
	private String rating;		// Rating of this DVD
	private int runningTime;	// Running time of this DVD in minutes

	
	/**
	 * Constructor 
	 * @param dvdTitle
	 * @param dvdRating
	 * @param dvdRunningTime
	 */
	public DVD(String dvdTitle, String dvdRating, int dvdRunningTime) {
		title = dvdTitle;
		rating = dvdRating;
		runningTime = dvdRunningTime;
	}
	
	
	/**
	 * Getter method to return the title result
	 * @return title
	 */
	public String getTitle() {
		return title;	
	}
	
	
	/**
	 * Getter method to return the rating result
	 * @return rating
	 */
	public String getRating() {
		return rating;	
	}
	
	
	/**
	 * Getter method to return the running time 
	 * @return runningTime
	 */
	public int getRunningTime() {
		return runningTime;	
	}

	
	/**
	 * Setter method to set the title
	 * @param newTitle
	 */
	public void setTitle(String newTitle) {
		title = newTitle;

	}

	
	/**
	 * Setter method to set the rating
	 * @param newRating
	 */
	public void setRating(String newRating) {
		rating = newRating;

	}

	
	/**
	 * Setter method to set the running time
	 * @param newRunningTime
	 */
	public void setRunningTime(int newRunningTime) {
		runningTime = newRunningTime;

	}

	public String toString() {


		//format the string to contain title, rating, and running time
		//each seperate by "/"
		String allDvd = getTitle()+ "/" + getRating() + "/" + getRunningTime(); 
		return allDvd;	
	}
	
	
}
