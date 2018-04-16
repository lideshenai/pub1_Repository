package calculator.sign;

/***
 * 
 * @author zhyu
 * @createdtime 2018年2月12日下午8:20:46
 */
public abstract class AbstractUnterminalSign implements UnTerminalSign {
	
	protected char sign;
	
	public abstract char getSign();
	public boolean isMe(char c) {
		if(getSign() == c)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getSign());
	}
}
