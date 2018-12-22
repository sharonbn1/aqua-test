package common;

import java.time.*;
import java.time.format.*;
import java.nio.file.attribute.*;

/**
 * static utility helper methods to retrieve file attributes
 */
public class FileUtils
{
	// although DateTimeFormatter class has a static iso8601 formatter instance,
	// its output sometimes contains milli/nano seconds that confuse the json parser...
	private static final DateTimeFormatter iso8601_formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

	/**
	 * converts {@code FileTime} value to ISO8601 formatted {@code String}
	 * @param fileTime {@code FileTime} typed file attribute 
	 * @return ISO8601 formatted ("yyyy-MM-dd'T'HH:mm:ss") {@code String} 
	 * @throws DateTimeException if an error occurs during formatting
	 */
	public static String toString(FileTime fileTime) throws DateTimeException {
		return iso8601_formatter.format(LocalDateTime.ofInstant(fileTime.toInstant(), ZoneOffset.UTC));
	}

	/**
	 * converts ISO8601 formatted {@code String} to {@code FileTime} value 
	 * @param  iso8601_timestamp ISO8601 formatted ("yyyy-MM-dd'T'HH:mm:ss") {@code String} value 
	 * @return {@code FileTime} value 
	 * @throws DateTimeException if an error occurs during parsing of input
	 */
	public static FileTime fromString(String iso8601_timestamp) throws DateTimeException {
		LocalDateTime dt = LocalDateTime.parse(iso8601_timestamp, iso8601_formatter);
		return FileTime.from(dt.toInstant(ZoneOffset.UTC));
	}

	/**
	 * finds file's extension. <br>
	 * extension is defined as the part of the file's name that follows the last dot.
	 * @param filename {@code String} file's name
	 * @return {@code String} file's extension, or an empty String if no extension was found.
	 */
	public static String getExtension(String filename) {
		int dotpos = filename.lastIndexOf('.');
		return dotpos > -1 ? filename.substring(dotpos) : "";
	}
}
