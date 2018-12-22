package common.api;

import java.util.*;

/**
 * data structure for statistics
 * This is produced as json from stage2 in response to GET http request  
 */
public class Statistics
{
	public long        filesReceived  = 0;
	public String      biggestFileName;
	public long        biggestFileSize = 0;
	public long        avgFileSize = 0;
	public Set<String> fileExtensions = new HashSet<>();
	public String      mostFrequentExtension;
	public long        mostFrequentExtensionOccurences;
}
