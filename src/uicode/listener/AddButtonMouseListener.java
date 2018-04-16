package uicode.listener;


import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.CodeDao;
import uicode.Controller;
import uicode.listener.common.ButtonSimpleClickListener;
import uicode.view.EditView;
import uicode.view.UiCodeView;

public class AddButtonMouseListener extends  ButtonSimpleClickListener {
	private CodeDao codeDao;
	private Text selectText;
	public AddButtonMouseListener(CodeDao codeDao, Text selectText){
		this.codeDao = codeDao;
		this.selectText = selectText;
	}
	public void service(Display display, Shell shell,MouseEvent event) {
		UiCodeView view = Controller.getCurrentView();
		if(view != null) {
			view.closeView();
			view = null;
		}
		if(view == null) {
			view = new EditView(codeDao,"add", selectText);
			Controller.setCurrentView(view);
		}
		view.show(display, shell);
	}
}
