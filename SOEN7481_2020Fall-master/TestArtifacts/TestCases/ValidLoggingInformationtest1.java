package TestCases;
import java.util.logging.Logger;

import core.visitor.common.InadequateLoggingInformation;

public class ValidLoggingInformationtest1 {
public static void main(String Args[]) {
	final Logger LOGGER = Logger.getLogger(InadequateLoggingInformation.class.getName());
	try {	int[] arr= {1,2,3,4};
	System.out.print(arr[5]);
	}
catch(IndexOutOfBoundsException e) {
	System.out.println("Index Out of Bound");
	 LOGGER.warning("Index Out of Bounds Exception");
	 LOGGER.warning("Index Out of Bounds Exception");
	
}
catch(Exception e) {
	System.out.println("Ishpreet");
}
}
}
