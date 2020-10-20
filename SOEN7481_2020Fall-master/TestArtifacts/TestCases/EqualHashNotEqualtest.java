package TestCases;

public class EqualHashNotEqualtest {
	
		
		int a= 5;
		int b=10;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + a;
			result = prime * result + b;
			return result;
		}
		
		
		
	


}
