package uicode.view.table;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * ���������,��Ҫ�Ƕ�List�����е����ݼ�¼����ɸѡ��ת��.
 * ��"������"������.
 * ����������Ҫ�Ƕ�setInput()��������ݼ�����(��������ָ������PeopleFactory�з�װ�õ�List����)������.
 * ����ת������һ�����鷵��.
 * 
 * ʵ�ֶ�Ӧ�Ľӿ�IStructuredContentProvider,Ȼ��ʵ�����еķ���.
 * @author kongxiaohan
 */
public class TableViewerContentProvider implements IStructuredContentProvider {

    @Override
    public void dispose() {
        
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        
    }
    
    //ʵ��IStructuredContentProvider�ӿ�֮��,��Ҫ��д�ľ������getElements()����.
    @Override
    public Object[] getElements(Object inputElement) {//setInput����������һ������
        if(inputElement instanceof List){//����������������͵��ж�.
            return ((List<?>)inputElement).toArray();//��setInput��������List���һ���������
        }else{
            return new Object[0];
        }
    }
}