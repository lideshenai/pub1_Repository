package calculator.sign.impl;

import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.AbstractUnterminalSign;
import calculator.sign.Arithmetic;

public class Devide extends AbstractUnterminalSign{
	
	private int prority = 100;
	private static Arithmetic devideArithmetic; 
	private char sign = '/';
	
	public char getSign() {
		return sign;
	}

	private static class DevideArithmetic implements Arithmetic {

		@Override
		public Double compute(Num bef, Num lat) throws InterpreterSyatnxException {
			if(bef == null || lat == null)
				throw new RuntimeException("该符号要求前后数字必须不为空( bef=" + bef + ", lat=" + lat + " )");
			return bef.getValue() / lat.getValue();
		}
		
	}
	static {
		devideArithmetic = new DevideArithmetic();
	}
	@Override
	public int getPrority() {
		return prority;
	}
	@Override
	public Arithmetic getArithmetic() {
		return devideArithmetic;
	}
}
