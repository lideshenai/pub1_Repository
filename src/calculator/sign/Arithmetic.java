package calculator.sign;

import calculator.interpreter.exception.InterpreterSyatnxException;
import calculator.sign.impl.Num;

public interface Arithmetic {
	
	public Double compute(Num bef, Num lat) throws InterpreterSyatnxException;
}
