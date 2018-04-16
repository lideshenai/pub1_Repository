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

//�̳�ActionGroup
public class MyActionGroup extends ActionGroup{
  private TableViewer tableViewer;
  
  /**
   * ����Ҽ��в˵�,����Ҫ
   * ���ɲ˵�Menu,��������Action����
   */
  public void fillContextMenu(IMenuManager mgr){//I��ͷ��һ���ǽӿڵ���˼.
      //��������Action���󵽲˵���������
      MenuManager menuManager = (MenuManager) mgr; //��Ϊ�������һ���ӿ�,��������ط�Ҫת��һ������.
      menuManager.add(new OpenAction());
      menuManager.add(new RefreshAction());

 
      //����Menu�����ر��table��,menu��table��������Ϊ�Է��Ĳ���.
      Table table = tableViewer.getTable();
      Menu menu = menuManager.createContextMenu(table);
      table.setMenu(menu);
      
  }
  
  private CodeDao codeDao;
  private Text selectText;
  /**
   * ��������TableViewer����Ĺ��캯����
   * ��Ϊ��Action��Ҫʹ�õ�TableViewer���� ����һ��Ҫ��TableViewer��������
   */
  public MyActionGroup(TableViewer tableViewer, CodeDao codeDao, Text selectText) {
      this.tableViewer = tableViewer;
      this.codeDao = codeDao;
      this.selectText = selectText;
  }
  
  /**
   * "��"��Action��
   */
  private class OpenAction extends Action{
      public OpenAction(){
          setText("�༭");
      }
      /**
       * �̳���Action�ķ���,��������д�ڴ˷�����.
       */
      public void run(){
          IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
          Code code = (Code) (selection.getFirstElement());
          if (code == null) {
              MessageDialog.openInformation(null, null, "��ѡ���¼");
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
   * ˢ�µ�Action��
   */
  private final class RefreshAction extends Action{
      public RefreshAction(){
          setText("ˢ��");
      }
      
      public void run(){
          tableViewer.refresh();//���ñ���ˢ�·���.
      }
  }
}