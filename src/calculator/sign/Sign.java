package calculator.sign;
/***
 * @author zhyu
 * @createdtime 2018年2月12日下午8:20:58
 */
public interface Sign {
	
	/***
	 * 对传入char 确认是否 是 本字符char
	 * @param c
	 * @return
	 */
	boolean isMe(char c);
	
	/**s
	 * 获取本字符的char
	 * @return
	 */
	char getSign();
	
	@Override
	String toString();
}
