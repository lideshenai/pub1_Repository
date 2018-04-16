package calculator.sign.impl;

import calculator.data.DoubleUtil;
import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.AbstractUnterminalSign;
import calculator.sign.Arithmetic;

public class Dot extends AbstractUnterminalSign{
	private static Arithmetic dotArithmetic; 
	private char sign = '.';
	
	public char getSign() {
		return sign;
	}



	@Override
	public boolean isMe(char c) {
		return '.' == c;
	}
	@Override
	public String toString() {
		return String.valueOf(getSign());
	}



	@Override
	public int getPrority() {
		return 10000;
	}
	private static class DotArithmetic implements Arithmetic {
		@Override
		public Double compute(Num bef, Num lat) throws InterpreterSyatnxException {
			if(bef == null || lat == null)
				throw new RuntimeException("bef  = " + bef + " lat = " + lat);
			String b = DoubleUtil.getValidValue(bef.getValue());
			String e = DoubleUtil.getValidValue(lat.getValue());
			String l = b + "." + e;
			System.out.println(l);
			Double r = Double.valueOf(l);
			return r;
		}
	}
	static {
		dotArithmetic = new DotArithmetic();
	}


	@Override
	public Arithmetic getArithmetic() {
		return dotArithmetic;
	}
}
