package TestCases;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import core.visitor.common.InadequateLoggingInformation;

public class InvalidLoggingInformation {
	public static void main(String args[]) {
		final Logger LOGGER = Logger.getLogger(InadequateLoggingInformation.class.getName());
	try {	int[] arr= {1,2,3,4};
		System.out.print(arr[5]);
		}
	catch(IndexOutOfBoundsException e) {
		 LOGGER.warning("Index Out of Bounds Exception");
	}
	catch(Exception e) {
		 LOGGER.warning("Index Out of Bounds Exception");
	}
	}

}
