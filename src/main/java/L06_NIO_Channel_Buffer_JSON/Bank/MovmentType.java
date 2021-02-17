package L06_NIO_Channel_Buffer_JSON.Bank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MovmentType {
	Bonifico, Accredito, Bollettino, F24, PagoBancomat;
	
	private static final List<MovmentType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	public static MovmentType randomEnum() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	}
