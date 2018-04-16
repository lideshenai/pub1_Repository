package uicode.listener.view.edit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import uicode.listener.common.ButtonSimpleClickListener;
import uicode.view.EditView;
import uicode.view.UiCodeView;
import util.ShellUtil;

public class EditViewCancelListener extends ButtonSimpleClickListener {
	private UiCodeView view;

	public EditViewCancelListener(UiCodeView view) {
		this.view = view;
	}

	@Override
	public void service(Display display, Shell shell,MouseEvent event) {
		if(view instanceof EditView) {
			EditView view = (EditView)this.view;
			display.setData("update", null);
			if (!ShellUtil.confirmAllIsNull(view.getControls())) {
				int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
				MessageBox messageBox = new MessageBox(shell, style);
				messageBox.setText("提示");
				messageBox.setMessage("还有为保存的信息,请确认放弃?");
				if(messageBox.open() != SWT.YES) {
					return;
				}
			}
		}
		view.closeView();
	}
}
