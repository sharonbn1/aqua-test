package common;

import org.apache.commons.cli.*;

/**
 * static utility helper methods to parse cli args
 */
public class CliUtils
{
	/**
	 * check if arg was specified (either in short or long form)
	 * @param cmd {@code CommandLine} cli arg values container 
	 * @param option {@code Option} cli option to search
	 * @return {@code true} if option was found in cli args 
	 */
	public static boolean isSpecified(CommandLine cmd, Option option) {
		return cmd.hasOption(option.getOpt()) || cmd.hasOption(option.getLongOpt());
	}

	/**
	 * check if arg was specified (either in short or long form) and return value
	 * @param cmd {@code CommandLine} cli arg values container 
	 * @param option {@code Option} cli option to search
	 * @return {@code String} option value or {@code null} if not found (or if option doesn't require value)
	 */
	public static String getOptionValue(CommandLine cmd, Option option) {
		return getOptionValue(cmd, option, null);
	}

	/**
	 * check if arg was specified (either in short or long form) and return value
	 * @param cmd {@code CommandLine} cli arg values container 
	 * @param option {@code Option} cli option to search
	 * @param defaultValue {@code String} default return value
	 * @return {@code String} option value or defaultValue if not found (or if option doesn't require value)
	 */
	public static String getOptionValue(CommandLine cmd, Option option, String defaultValue) {
		return cmd.hasOption(option.getOpt()) ? 
				cmd.getOptionValue(option.getOpt()) :
					cmd.hasOption(option.getLongOpt()) ? 
						cmd.getOptionValue(option.getLongOpt()) : defaultValue;
	}
}
