package stage2;

import org.apache.commons.cli.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import common.CliUtils;

@SpringBootApplication
public class Application implements ApplicationRunner
{
	// command line args options
	public static Option protocolArgOption =
		Option.builder("p").longOpt("protocol")
			.hasArg().argName("protocol")
			.desc("http protocol: http|https. default value: http")
			.build();

	public static Option portArgOption =
		Option.builder("").longOpt("server.port")
			.hasArg().argName("=port")
			.valueSeparator()
			.desc("port number. this is SpringMVC argument. only long format --server.port=<port> is valid. default value: 8080")
			.build();

	public static Option helpArgOption =
		Option.builder("h").longOpt("help")
			.hasArg(false)
			.desc("display usage")
			.build();

	// command line args values
	public static Options options;
	public static String protocolArg; 
	public static boolean helpArgSpecified;

	public static void main(String... args) {
		try {
			parseArgs(args);
			SpringApplication application = new SpringApplication(Application.class);
			// if https requested - enable https profile that will read relevant properties file
			if (protocolArg.equals("https")) application.setAdditionalProfiles("https");
			application.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseArgs(String... args) throws ParseException {
		CommandLine cmd;
		options = new Options();
		options.addOption(protocolArgOption);
		options.addOption(portArgOption);
		options.addOption(helpArgOption);
		CommandLineParser parser = new DefaultParser();
		cmd = parser.parse(options, args);
		protocolArg = CliUtils.getOptionValue(cmd, protocolArgOption, "http");
		// print help if requested
		if (CliUtils.isSpecified(cmd, helpArgOption)) {
			helpArgSpecified = true;
		}
	}

	// print usage after Spring logging
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (helpArgSpecified) new HelpFormatter().printHelp("java -jar stage2-1.0.jar", options);
	}
}
