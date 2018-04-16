package uicode.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.CodeDao;
import uicode.Controller;
import uicode.listener.common.ButtonSimpleClickListener;
import uicode.view.SelectView;
import uicode.view.UiCodeView;

public class SelectButtonMouseListener extends  ButtonSimpleClickListener{
	private Text selectText;
	private CodeDao codeDao;
	public SelectButtonMouseListener( Text selectText,CodeDao codeDao) {
		this.selectText = selectText;
		this.codeDao = codeDao;
	}
	@Override
	public void service(Display display, Shell shell, MouseEvent e) {
		String label = selectText.getText().trim();
		if(label.equals("")) {
			MessageBox messageBox = new MessageBox(shell,SWT.APPLICATION_MODAL | SWT.OK);
			messageBox.setText("错误提示");
			messageBox.setMessage("请输入内容后在搜索");
			if(messageBox.open() == SWT.OK) {
				return;
			}
		}
		UiCodeView view = Controller.getCurrentView();
		if(view != null ) {
			view.closeView();
			view = null;
		}
		if( view == null) {
			view = new SelectView(codeDao, selectText);
			Controller.setCurrentView(view);
		}
		view.show(display, shell);
	}
	
}
