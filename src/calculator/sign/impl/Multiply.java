package calculator.sign.impl;

import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.AbstractUnterminalSign;
import calculator.sign.Arithmetic;

public class Multiply extends AbstractUnterminalSign{
	
	private int prority = 100;
	public int getPrority() {
		return prority;
	}
	private static Arithmetic multiplyArithmetic; 
	private char sign = '*';
	
	public char getSign() {
		return sign;
	}

	static {
		multiplyArithmetic = new MultiplyArithmetic();
	}
	private static class MultiplyArithmetic implements Arithmetic {
		@Override
		public Double compute(Num bef, Num lat) throws InterpreterSyatnxException {
			if(bef == null || lat == null) {
				throw new RuntimeException("该符号要求前后数字必须不为空( bef=" + bef + ", lat=" + lat + " )");
			}
			return bef.getValue() * lat.getValue();
		}
		
	}
	@Override
	public Arithmetic getArithmetic() {
		return multiplyArithmetic;
	}
}
