package stage2;

import java.util.*;
import org.springframework.stereotype.Service;
import common.*;
import common.api.*;

/**
 * calculate statistics on files.<br>
 * return current statistics when requested
 */
@Service
public class StatManager
{
	private long files = 0;
	private String biggestFileName;
	private long biggestFileSize = 0;
	private long sumSize = 0;
	private Map<String, Long> fileExtensions = new HashMap<>();

	/**
	 * add file info into statistics 
	 * @param fi {@code FileInfo} file to add
	 */
	public synchronized void acceptFile(FileInfo fi) {
		files++;
		if (biggestFileSize < fi.size) {
			biggestFileSize = fi.size;
			biggestFileName = fi.name;
		}
		sumSize += fi.size;
		fileExtensions.merge(FileUtils.getExtension(fi.name), 1L, Long::sum);
	}

	/**
	 * return current statistics
	 * @return {@code Statistics} accumulated statistics
	 */
	public Statistics getStatistics() {
		Statistics stat = new Statistics();
		stat.filesReceived = files;
		stat.biggestFileName = biggestFileName;
		stat.biggestFileSize = biggestFileSize;
		stat.avgFileSize = sumSize / files;
		stat.fileExtensions = fileExtensions.keySet();
		stat.fileExtensions.remove("");   // remove entry for no extension
		for (Map.Entry<String, Long> entry : fileExtensions.entrySet()) {
			if (entry.getKey().equals("")) continue;
			if (entry.getValue().longValue() > stat.mostFrequentExtensionOccurences) {
				stat.mostFrequentExtensionOccurences = entry.getValue().longValue();
				stat.mostFrequentExtension = entry.getKey();
			}
		}
		return stat;
	}
}
