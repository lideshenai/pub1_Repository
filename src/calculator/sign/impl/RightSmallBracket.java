package calculator.sign.impl;

import calculator.sign.Arithmetic;
import calculator.sign.Doubled;

public class RightSmallBracket implements Doubled {


	private char sign = ')';
	@Override
	public boolean isMe(char c) {
		return sign == c;
	}

	@Override
	public char getSign() {
		return sign;
	}

	@Override
	public int getPrority() {
		return 1000;
	}


	@Override
	public int getDoubleCount() {
		return 11;
	}

	@Override
	public Arithmetic getArithmetic() {
		return null;
	}
	@Override
	public String toString() {
		return String.valueOf(getSign());
	}
}
