package calculator.sign;
/***
 * @author zhyu
 * @createdtime 2018��2��12������8:20:58
 */
public interface Sign {
	
	/***
	 * �Դ���char ȷ���Ƿ� �� ���ַ�char
	 * @param c
	 * @return
	 */
	boolean isMe(char c);
	
	/**s
	 * ��ȡ���ַ���char
	 * @return
	 */
	char getSign();
	
	@Override
	String toString();
}
