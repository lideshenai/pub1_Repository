package calculator.sign.impl;

import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.AbstractUnterminalSign;
import calculator.sign.Arithmetic;

public class Plus extends AbstractUnterminalSign {

	private int prority = 10;
	public int getPrority() {
		return prority;
	}
	private static Arithmetic plusArithmetic; 
	private char sign = '+';
	
	public char getSign() {
		return sign;
	}

	private static class PlusArithmetic implements Arithmetic {
		@Override
		public Double compute(Num bef, Num lat) throws InterpreterSyatnxException {
			if(bef == null)
				throw new RuntimeException("bef  = " + bef);
			if(lat == null)
				return bef.getValue();
			return bef.getValue() + lat.getValue();
		}
	}
	static {
		plusArithmetic = new PlusArithmetic();
	}
	@Override
	public Arithmetic getArithmetic() {
		return plusArithmetic;
	}
}
