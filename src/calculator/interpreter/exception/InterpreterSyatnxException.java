package calculator.interpreter.exception;

public class InterpreterSyatnxException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterpreterSyatnxException() {
		super("解析语法异常,请检查表达式");
	}

	public InterpreterSyatnxException(String message) {
		super(message);
	}
	
}
