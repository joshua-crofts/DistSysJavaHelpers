package hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.file;

import java.lang.reflect.InvocationTargetException;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;


/**
 * Basic implementation of a trace file reader capable of reading files of a LOG format
 * see (https://s3.amazonaws.com/hr-testcases/750/assets/2013-fall-week2-abbreviated.log) 
 * 
 * @author Joshua
 *
 */
public class PreziReader extends TraceFileReaderFoundation {

	public PreziReader(String fileName, int from, int to, boolean allowReadingFurther, Class<? extends Job> jobType)
			throws SecurityException, NoSuchMethodException {
		super("LOG Format", fileName, from, to, allowReadingFurther, jobType);

	}

	/**
	 * Checks if a line is a trace line, following the format of a 
	 * int, float, string, string
	 */
	@Override
	protected boolean isTraceLine(String line) {
		try {
			String lineSplit[] = line.split(" ");
			if (lineSplit.length != 4) {
				return false;
			}
			if (isNumber(lineSplit[0], "int") && isNumber(lineSplit[1], "float")) {
				if (!lineSplit[2].equals(null)) {
					if (lineSplit[3].equals("url") || lineSplit[3].equals("default") || lineSplit[3].equals("export")) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {;
			return false;
		}
	}

	
	/**
	 * Checks whether the given input string is actually an int or a float.
	 * 
	 * 
	 * @param input The input string used in parsing
	 * @param type The type of number wanting to be checked (float/int)
	 * @return True if the string is of the type requested to be parsed else False.
	 */
	private boolean isNumber(String input, String type) {
		if (input == null)
			return false;
		if (type.equals("int")) {
			try {
				Integer.parseInt(input);
			} catch (Exception e) {
				return false;
			}
		} else if (type.equals("float")) {
			try {
				Float.parseFloat(input);
			} catch (Exception e) {
				return false;
			}
		}else {
			return false;
		}
		return true;
	}

	@Override
	protected void metaDataCollector(String line) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Job createJobFromLine(String line)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		
		String lineSplit[] = line.trim().split(" ");
		
		try {
			return jobCreator.newInstance(
					// id:
					lineSplit[2],
					// submit time:
					Integer.parseInt(lineSplit[0])/1000,
					// queueing time - Assumed 
					0,
					// execution time:
					(int)Float.parseFloat(lineSplit[1]),
					// Number of processors - Assumed 
					1,
					// average execution time - Assumed 
					-1,
					// no memory - Assumed 
					512000,
					// User name - Not Given
					null,
					// Group membership - Not Given
					null,
					// executable name:
					lineSplit[3],
					// Preceding job - Not Given
					null, 0);
		} catch(Exception e) {
			//Catch any issues with the line and return null
			return null;
		}
		
		
		
	
	


	}

}
