package uicode.listener;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import uicode.Controller;
import uicode.listener.common.ButtonSimpleClickListener;
import uicode.view.CalculatorView;
import uicode.view.UiCodeView;

public class CalculatorListener extends ButtonSimpleClickListener{

	@Override
	public void service(Display display, Shell shell, MouseEvent e) {
		UiCodeView view = Controller.getCurrentView();
		if(view != null ) {
			view.closeView();
			view = null;
		}
		if( view == null) {
			view = new CalculatorView();
			Controller.setCurrentView(view);
		}
		view.show(display, shell);
	}
	
}
