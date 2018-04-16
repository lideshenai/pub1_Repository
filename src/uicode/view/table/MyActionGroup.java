package uicode.view.table;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ActionGroup;

import bean.Code;
import dao.CodeDao;
import uicode.Controller;
import uicode.view.EditView;
import uicode.view.UiCodeView;

//继承ActionGroup
public class MyActionGroup extends ActionGroup{
  private TableViewer tableViewer;
  
  /**
   * 鼠标右键有菜单,首先要
   * 生成菜单Menu,并将两个Action传入
   */
  public void fillContextMenu(IMenuManager mgr){//I开头的一般是接口的意思.
      //加入两个Action对象到菜单管理器中
      MenuManager menuManager = (MenuManager) mgr; //因为传入的是一个接口,所以这个地方要转换一下类名.
      menuManager.add(new OpenAction());
      menuManager.add(new RefreshAction());

 
      //生成Menu并挂载表格table上,menu和table两个对象互为对方的参数.
      Table table = tableViewer.getTable();
      Menu menu = menuManager.createContextMenu(table);
      table.setMenu(menu);
      
  }
  
  private CodeDao codeDao;
  private Text selectText;
  /**
   * 用来接受TableViewer对象的构造函数。
   * 因为在Action会要使用到TableViewer对象 所以一定要把TableViewer传进来。
   */
  public MyActionGroup(TableViewer tableViewer, CodeDao codeDao, Text selectText) {
      this.tableViewer = tableViewer;
      this.codeDao = codeDao;
      this.selectText = selectText;
  }
  
  /**
   * "打开"的Action类
   */
  private class OpenAction extends Action{
      public OpenAction(){
          setText("编辑");
      }
      /**
       * 继承自Action的方法,动作代码写在此方法中.
       */
      public void run(){
          IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
          Code code = (Code) (selection.getFirstElement());
          if (code == null) {
              MessageDialog.openInformation(null, null, "请选择记录");
          } else {
        	  Display.getCurrent().setData("update", code);
        	  UiCodeView view = Controller.getCurrentView();
      		if(view != null ) {
      			view.closeView();
      			Display.getCurrent().getActiveShell().redraw();
      			view = null;
      		}
      		if( view == null) {
      			view = new EditView(codeDao, "update",selectText);
      			Controller.setCurrentView(view);
      		}
        	  view.show(Display.getCurrent(), Display.getCurrent().getActiveShell());
          }            
      }
  }
  
  /**
   * 刷新的Action类
   */
  private final class RefreshAction extends Action{
      public RefreshAction(){
          setText("刷新");
      }
      
      public void run(){
          tableViewer.refresh();//调用表格的刷新方法.
      }
  }
}