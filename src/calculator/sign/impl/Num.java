package calculator.sign.impl;

import calculator.data.DoubleUtil;
import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.Sign;

public class Num implements Sign{
	
	private boolean combined;
	public boolean isCombined() {
		return combined;
	}
	private Double value;
	
	
	public Num(Double value) {
		this.value = value;
		String str = DoubleUtil.getValidValue(value);
		if(str.length() > 1)
			setCombined(true);
	}
	public Num(char c) {
		value = Double.valueOf(c);
	}
	
	public Num() {
	}
	public double getValue() throws InterpreterSyatnxException {
		return value;
	}
	
	@Override
	public char getSign() {
		if(!isCombined())
		return (char)(value.doubleValue());
		throw new RuntimeException("该符号已不是一个字符");
	}
	
	public void setCombined(boolean combined) {
		this.combined = combined;
	}


	@Override
	public boolean isMe(char c) {
		return c >= 48 && c<= 58;
	}
	@Override
	public String toString() {
		try {
			return String.valueOf(getValue());
		} catch (InterpreterSyatnxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
