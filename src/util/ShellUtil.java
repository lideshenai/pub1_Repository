package util;

import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class ShellUtil {

	private static Text[] getText(List<Control> controls) {
		Text[] texts = new Text[controls.size()];
		for(int i = 0; i < controls.size(); i++) {
			if(controls.get(i) instanceof Text) {
				texts[i] = (Text)controls.get(i);
			}
		}
		return texts;
	}
   public  static boolean confirmAllNotNull(List<Control> controls) {
        Text[] texts = getText(controls);
        for(int i = 0 ;i < texts.length; i++) {
        	if(texts[i] != null) {
        		String string = texts[i].getText().trim();
        		if(string.equals(""))
        			return false;
        	}
        	
        }
        return true;
    }
   public  static boolean confirmAllIsNull(List<Control> controls) {
	   Text[] texts = getText(controls);
	   for(int i = 0 ;i < texts.length; i++) {
		   if(texts[i] != null) {
			   String string = texts[i].getText().trim();
			   if(!string.equals(""))
				   return false;
		   }
		   
	   }
	   return true;
   }

}
