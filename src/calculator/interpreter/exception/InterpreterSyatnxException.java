package calculator.interpreter.exception;

public class InterpreterSyatnxException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterpreterSyatnxException() {
		super("�����﷨�쳣,������ʽ");
	}

	public InterpreterSyatnxException(String message) {
		super(message);
	}
	
}
