package calculator.words;
/***
 * 
 * @author zhyu
 * @createdtime 2018年2月13日下午4:41:27
 */

import calculator.data.MathList;
import calculator.interpreter.SignInterpreter;
import calculator.sign.Sign;
public class Word {
	private String UnInterpreterWords;
	private MathList<Sign> signList;
	private boolean isInterpreter;
	private SignInterpreter signInterpreter;
	

	public void signInterpreter() {
		signList = signInterpreter.interpreter(this);
		if(signList == null)
			isInterpreter = true;
	}
	
	public SignInterpreter getSignInterpreter() {
		return signInterpreter;
	}
	public void setSignInterpreter(SignInterpreter signInterpreter) {
		this.signInterpreter = signInterpreter;
	}
	public Word(String unInterpreterWords) {
		UnInterpreterWords = unInterpreterWords;
	}
	public String getUnInterpreterWords() {
		return UnInterpreterWords;
	}
	public MathList<Sign> getSignList() {
		return signList;
	}
	public boolean isInterpreter() {
		return isInterpreter;
	}
	
	
}