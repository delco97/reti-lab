package L02_Threadpools.Es3;

import java.util.concurrent.Callable;

public class Power implements Callable<Double> {
	
	private final double n;
	private final double exp;
	
	Power(double p_n, int p_exp) {
		n = p_n;
		exp = p_exp;
	}
	
	@Override
	public Double call() throws Exception {
		return Math.pow(n, exp);
	}
	
}
