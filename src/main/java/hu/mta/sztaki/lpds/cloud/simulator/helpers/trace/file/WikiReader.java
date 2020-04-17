package hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.file;

import java.lang.reflect.InvocationTargetException;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

public class WikiReader extends TraceFileReaderFoundation {

	
	
	/**
	 * This trace loader was added as a test and is not considered complete,
	 * please look at PreziReader instead
	 * 
	 * 
	 * @param fileName
	 * @param from
	 * @param to
	 * @param allowReadingFurther
	 * @param jobType
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public WikiReader(String fileName, int from, int to, boolean allowReadingFurther, Class<? extends Job> jobType)
			throws SecurityException, NoSuchMethodException {
		super("Wiki Format", fileName, from, to, allowReadingFurther, jobType);

	}

	@Override
	protected boolean isTraceLine(String line) {
	return true;
	}



	@Override
	protected void metaDataCollector(String line) {
		// TODO Auto-generated method stub

	}
	String item;
	@Override
	protected Job createJobFromLine(String line)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {

		String lineSplit[] = line.trim().split(" ");
		
		if(!lineSplit[3].equals("-")) {
			System.out.println(lineSplit[3]);
		}
		return jobCreator.newInstance(
				// id
				lineSplit[0],
				// submit time:
				(int)Float.parseFloat(lineSplit[1])/1000,
				// queueing time:
				0,
				// execution time:
				(int)1.4,
				// Number of processors
				1,
				// average execution time
				-1,
				// no memory
				512000,
				// User name:
				null,
				// Group membership:
				null,
				// executable name:
				lineSplit[3],
				// No preceding job
				null, 0);
		
		
	
	


	}

}
