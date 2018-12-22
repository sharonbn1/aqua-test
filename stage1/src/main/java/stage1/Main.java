package stage1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.concurrent.*;
import org.apache.commons.cli.*;

import common.CliUtils;
import common.HttpUtils;
import common.api.*;

/**
 * main class for stage 1:
 * parse args, walk dir tree, visit files and collect file attributes, send post requests in thread pool 
*/
public class Main
{
	// global settings
	public static final int THREAD_POOL_SIZE = 4;

	// command line args options
	public static Option dirArgOption =
		Option.builder("d").longOpt("dir")
			.hasArg().argName("dir")
			.desc("root directory (required)")
			.build();

	public static Option serverUrlArgOption =
		Option.builder("u").longOpt("url")
			.hasArg().argName("url")
			.desc("server url to send post requests (required). full url expected. example: \"http://aqua.com:1234/api\"")
			.build();

	public static Option mediaTypeArgOption =
		Option.builder("m").longOpt("media")
			.hasArg().argName("media type")
			.desc("media type of http request payload. values: xml|json. default value: json")
			.build();

	public static Option helpArgOption =
		Option.builder("h").longOpt("help")
			.hasArg(false)
			.desc("display usage")
			.build();

	// command line args values
	public static String dirArg, serverUrlArg, mediaTypeArg;

	// fixed thread pool
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

	public static void main(String[] args) {
		try {
			parseArgs(args);
			if (dirArg != null && serverUrlArg != null) {
				Files.walkFileTree(Paths.get(dirArg), new RegularFilesVisitor());
			}
			threadPool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseArgs(String... args) throws ParseException {
		CommandLine cmd;
		Options options = new Options();
		options.addOption(dirArgOption);
		options.addOption(serverUrlArgOption);
		options.addOption(mediaTypeArgOption);
		options.addOption(helpArgOption);
		CommandLineParser parser = new DefaultParser();
		cmd = parser.parse(options, args);
		dirArg = CliUtils.getOptionValue(cmd, dirArgOption);
		serverUrlArg = CliUtils.getOptionValue(cmd, serverUrlArgOption);
		mediaTypeArg = CliUtils.getOptionValue(cmd, mediaTypeArgOption);
		if (mediaTypeArg != null && mediaTypeArg.equals("xml")) mediaTypeArg = HttpUtils.xmlContentType;
		else mediaTypeArg = HttpUtils.jsonContentType;
		// print help if requested or any of required args not specified
		if (dirArg == null || serverUrlArg == null || 
				CliUtils.isSpecified(cmd, helpArgOption)) {
			new HelpFormatter().printHelp("java -jar stage1-1.0.jar", options);
		}
	}

	// dir tree visitor: collect file attributes, send post requests in thread pool 
	static class RegularFilesVisitor extends SimpleFileVisitor<Path>
	{
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (attrs.isRegularFile()) {
				threadPool.submit(new FileInfoSender(new FileInfo(file, attrs)));
			}
			return FileVisitResult.CONTINUE;
		}
	}
}
