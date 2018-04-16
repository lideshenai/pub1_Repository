package calculator.interpreter;

import calculator.expression.Expression;
import calculator.interpreter.exception.InterpreterSyatnxException;

public interface ExpressionInterpreter {
	Double interpreter(Expression expression) throws InterpreterSyatnxException;

}
