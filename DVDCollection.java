import java.io.*;

public class DVDCollection {

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	
	/**
	 * Method to return a string containing all DVDs info
	 * will call toString from DVD Class
	 * @return string
	 */
	public String toString() {
		 //define the variable to contain the formatted string
		String dvdConverted = "";
		//define a variable to contain the final result
		dvdConverted = "numsdvds = " + numdvds + '\n' + "dvdArray.length " 
				+ dvdArray.length;
		//check to make sure number of DVDs is not 0
		if(numdvds != 0)
		{
			dvdConverted += "\n";
		}
		//loop through number of DVDs in the collection
		for(int i = 0; i < numdvds; i++) {
			dvdConverted += "dvdArray["+i+"] = " + dvdArray[i].toString(); 
			//if the current index is not equal to number of DVDs - 1
			//update the converted DVDs with newline character
			if(i != numdvds-1)
			{
				dvdConverted += "\n";
			}
		}	
		return dvdConverted;
	}
	
	
	/**
	 * Method to add new DVD or modify the existing collection
	 * make sure the rating and running time are valid
	 * if collection is full, double the size of array
	 * @param title
	 * @param rating
	 * @param runningTime
	 */
	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.
		
		//modify the title to upper case letters
		String uppered_title = title.toUpperCase();
		
		//check if the rating or runtime are not valid
		if(validRating(rating) == false || validRuntime(runningTime) == false) {
			return;
		}
		else{
			//update modified boolean expression
			modified = true;
			int existence_flag;
			//update the existence_flag to the result of search method
			existence_flag = search(uppered_title);
			//if the title doesn't already exist on the collection
			if(existence_flag == -1){
				//create a new object from DVD class 
				//convert running time to integer
				DVD new_one = new DVD(uppered_title, rating, Integer.parseInt(runningTime));
				//if the array is full
				if(numdvds == dvdArray.length){
					//create a new array from DVD class 
					//new array has double the size of dvdArray
					DVD[] new_dvd_list = new DVD[dvdArray.length*2];
					//loop through length of dvdArray
			        for(int i = 0; i < dvdArray.length; i++){
			        	//update the newly created array
			        	new_dvd_list[i] = dvdArray[i];
			        }
			        dvdArray =new_dvd_list; 
				}
				//update dvdArray
				dvdArray[numdvds] = new_one;
				//increment the size of the current number of DVDs
				numdvds = numdvds + 1;
			}
			else{
				//modify the rating and running time 
				dvdArray[existence_flag].setRating(rating);
				dvdArray[existence_flag].setRunningTime(Integer.parseInt(runningTime));
			}
		}
		//sort dvdArray alphabetically
		sort_dvds(dvdArray);
	
	}
	
	
	/**
	 * Method to remove the DVD by title and make no change if title
	 * doesn't match
	 * @param title
	 */
	public void removeDVD(String title) {
		
		//convert the title to upper case letters
		String uppered_title = title.toUpperCase();
		int existence_flag;
		//update the existence_flag to the result of search method
		existence_flag = search(uppered_title);
		//if the title doesn't exist return
		if(existence_flag == -1)
		{
			return;
		}
		else
		{
			//update modified
			modified = true;
			//create a new array from DVD class with length of dvdArray
			DVD[] new_dvd_list = new DVD[dvdArray.length];
			//loop though length of dvdArray
	        for(int i = 0; i < dvdArray.length; i++)
	        {
	        	//check if index is smaller than existence_flag
	        	if(i < existence_flag)
	        		//update the new array
	        		new_dvd_list[i] = dvdArray[i];
	        	else if(i > existence_flag)
	        	{
	        		//update the new array with removed item
	        		new_dvd_list[i-1] = dvdArray[i];
	        	}
	        	else
	        	{
	        		continue;
	        	}
	        }
	        dvdArray =new_dvd_list;
	        //decrement number of the current DVDs
	        numdvds = numdvds - 1;
		}
		return;
	}
	
	
	/**
	 * Method to get the ratings that match separated by newline char
	 * @param rating
	 * @return string
	 */
	public String getDVDsByRating(String rating) {
		
		//define a string variable to contain all ratings
		String allRating = "";
		//loop through number of DVDs to check if the rating matches
		for(int i = 0; i < numdvds; i++) {
			//if the DVD rating matches the given rating update allRating
			//ratings should be separated by newline character
			if(dvdArray[i].getRating().compareTo(rating) == 0)
				allRating += dvdArray[i].getTitle() + '\n'; 
		}
		return allRating;	

	}
	
	
	/**
	 * Method to get the total running time of all DVDs
	 * @return integer
	 */
	public int getTotalRunningTime() {
		int totalRunning = 0;
		//check if the collection is empty
		if(numdvds == 0)
			return 0;
		//loop through number of DVDS and update the totalRunning variable
		for (int i = 0; i < numdvds; i++) {
			//update the total running time by adding the results from
			//getRunningTime method in each iteration
			totalRunning += dvdArray[i].getRunningTime();
		}
		return totalRunning;

	}

	
	/**
	 * Method to open and read the input file
	 * @param filename
	 */
	public void loadData(String filename) {
		//create a new file and pass filename string to it
		File file = new File(filename);
		
		//update sourceName with filename
		sourceName = filename;
        try {
            //create a buffered reader called br and passing the reading file to it
        	try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                //loop until read line is null (end of the line)
                while ((line = br.readLine()) != null) {
                	//check if line is not empty
                	if(!line.isEmpty()) {
                		//create a new array of strings splitting by comma
	                    String[] stringValidTokens = line.split(",");
	                    //first index of array would be title
	                    String title = stringValidTokens[0].toUpperCase();
	                    //second index of array contains rating
	                    String rate = stringValidTokens[1];
	                    //third index of array contains running time
	                    String time = stringValidTokens[2];
	                    
	                    //call addOrModifyDVD method and pass three strings to it
	                    addOrModifyDVD(title, rate, time);
                	}
                	else {
                		//if line is empty, continue
                		System.out.println("cont");
                		continue;
                	}
                    
                }
            }
            catch(IOException f) {
            	System.out.println("IO Error");
            	//set the current number of DVDs to 0 and make an empty array
            	numdvds = 0;
        		dvdArray = new DVD[7];
            }
        } catch (Exception e) {
        	System.out.println("exception");
            return;
        }

		
	}
	
	
	/**
	 * Method to save the DVDs into the file we used in loadData method
	 */
	public void save() {
		//create a new file and pass the sourceName to it
		File fout = new File(sourceName);
		try {
			try {
				//create a file output stream called fos
				FileOutputStream fos = new FileOutputStream(fout);
				//create a buffer writer called bw
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				//loop through number of DVDS in collection
				for (int i = 0; i < numdvds; i++) {
					//write the titles, rating, and running time into the file
					bw.write(dvdArray[i].getTitle() + "," + dvdArray[i].getRating() + "," + String.valueOf(dvdArray[i].getRunningTime()));
					bw.newLine();
				}
				bw.close();
			}
			//handle FileNotFoundException
			catch(FileNotFoundException f)
			{
				fout.createNewFile();
			}
		}
		//handle IOException
		catch(IOException e)
		{
			return;
		}


	}

	// Additional private helper methods go here:

	
	/**
	 * Private method to check if the rating of DVD is valid or not
	 * @param rating
	 * @return boolean expression
	 */
	private boolean validRating(String rating) {
		//resource for valid ratings: https://www.spectrum.net/support/tv/tv-and-movie-ratings-descriptions
		if(rating.compareTo("R") == 0 ||rating.compareTo("NC-17") == 0 || rating.compareTo("PG-13") == 0 ||
				rating.compareTo("PG") == 0 ||rating.compareTo("G") == 0) 
			return true;
		else
			return false;
		
		
	}
	
	
	/**
	 * Private method to check if runtime is valid or not
	 * @param runningTime
	 * @return boolean expression
	 */
	private boolean validRuntime(String runningTime) {
		int runTime = 0;
		try {
			//convert runningTime to integer
			runTime = Integer.parseInt(runningTime);
		}
		//handle the exception when we try to parse the string to int
		catch(NumberFormatException e) {
			System.out.println("Invalid Runtime!");
		}
		//if runtime is negative, it's not valid
		if(runTime < 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	
	/**
	 * Private method to search through the collection
	 * @param x
	 * @return integer
	 */
	private int search(String x){
		//loop through the number of DVDS 
		for(int i = 0; i < numdvds; i++)
        {
			//check if the title resulting from calling getTitle method
			//matches the argument passing through the search method
			//make sure to change the result from getTitle to upper case
			if(dvdArray[i].getTitle().toUpperCase().equals(x))
			{
				//return the containing index
				return i;
			}
        }
		return -1;
    }
	/*
	private void getDVDs() {
		for(int i = 0; i < numdvds; i++)
        {
			System.out.println(dvdArray[i].getTitle() + ", " + dvdArray[i].getRating() + " -- " );
        }
		System.out.println();
	}*/
	public DVD[] getDVDArray(){
		return dvdArray;
	}
	
	
	/**
	 * Private method for sorting
	 * @param arr
	 */
	private void sort_dvds(DVD [] arr) {
		//new variable to be initialized to number of current DVDs
		int n = numdvds;
		
		//nested for loop through number of DVDs
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
            	//check if the titles match
            	//use compareTo to be able to compare the strings
                if (arr[j].getTitle().compareTo(arr[j + 1].getTitle()) > 0) {
                    // swap arr[j+1] and arr[j]
                    DVD temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
	}
	
	
	
	

	
}
