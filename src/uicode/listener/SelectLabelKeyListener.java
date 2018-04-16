package uicode.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dao.CodeDao;
import uicode.Controller;
import uicode.view.SelectView;
import uicode.view.UiCodeView;

public class SelectLabelKeyListener implements KeyListener{
	private Text selectText;
	private Shell shell;
	private CodeDao codeDao;
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.keyCode - 3 == java.awt.event.KeyEvent.VK_ENTER) {
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
			view.show(Display.getCurrent(), shell);
		}
	}
	
	public SelectLabelKeyListener(Text selectText, Shell shell, CodeDao codeDao) {
		super();
		this.selectText = selectText;
		this.shell = shell;
		this.codeDao = codeDao;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}
}
