package calculator.data;

public class DoubleUtil {
	public static String getValidValue(double d) {
		String dd = String.valueOf(d);
		char[] cc = dd.toCharArray();
		int index = 0;
		for (int i = cc.length - 1; i >= 0; i--) {
			char c = cc[i];
			if(c == '0') {
				index++;
			} else {
				break;
			}
		}
		String string = dd.substring(0, dd.length() - index);
		if(string.endsWith(".")) {
			string = string.replaceFirst("\\.", "");
		}
		return string;
		
	}
}
