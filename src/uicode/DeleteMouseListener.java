package uicode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import bean.Code;
import dao.CodeDao;
import uicode.listener.common.ButtonSimpleClickListener;
import uicode.view.SelectView;
import uicode.view.UiCodeView;

public class DeleteMouseListener extends  ButtonSimpleClickListener {
	private CodeDao codeDao;
	public DeleteMouseListener(CodeDao codeDao) {
		this.codeDao = codeDao;
	}
	@Override
	public void service(Display display, Shell shell, MouseEvent e) {
		Code code = (Code)display.getData("selected");
		display.setData("selected", null);
		if(code == null) {
			MessageBox messageBox = new MessageBox(shell,SWT.APPLICATION_MODAL | SWT.OK);
			messageBox.setText("错误提示");
			messageBox.setMessage("请输入内容后在搜索");
			if(messageBox.open() == SWT.OK) {
				return;
			}
		}
		codeDao.delete(code);
		String currentSelected = Controller.getCurrentSelected();
		UiCodeView view = Controller.getCurrentView();
		if(view != null ) {
			view.closeView();
			view = null;
		}
		if( view == null) {
			view = new SelectView(codeDao, currentSelected);
			Controller.setCurrentView(view);
		}
		view.show(display, shell);
	}

}
