package TestCases;

public class NoUselessControlFlowTest {
	public static void main (String args[]) {
		int i=15;
		if( i>10) {
			System.out.print(i);
		}
		else{
			System.out.print("10");
		}
		while(i>10) {
			System.out.print(i);
			i--;
		}
		for(int j=13;j>i;j++) {
			System.out.print(j);
			j++;
		}
		
	}
}
