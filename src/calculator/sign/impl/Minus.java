package calculator.sign.impl;

import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.AbstractUnterminalSign;
import calculator.sign.Arithmetic;

public class Minus extends AbstractUnterminalSign{
	
	private int prority = 10;
	public int getPrority() {
		return prority;
	}
	private static Arithmetic minusArithmetic; 
	private char sign = '-';
	
	public char getSign() {
		return sign;
	}
	static {
		minusArithmetic = new MinusArithmetic();
	}
	private static class MinusArithmetic implements Arithmetic {

		@Override
		public Double compute(Num bef, Num lat) throws InterpreterSyatnxException {
			if(bef == null)
				throw new RuntimeException("该运算符不支持如右数据:bef = " + bef);
			if(lat == null)
				return 0 - bef.getValue();
			return bef.getValue() - lat.getValue();
		}
		
	}
	static {
		
	}
	@Override
	public Arithmetic getArithmetic() {
		return minusArithmetic;
	}
}
