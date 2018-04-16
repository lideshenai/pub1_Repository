package uicode.view.table;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import bean.Code;

/**
 * ���������Ҫ����Ϊ"��ǩ��"���������õ�.
 * "��ǩ��"��һ����ʵ�������ֶα����ֱ�ȡ��Ȼ��ϵ������TableViewer�ĸ�������.
 * ʵ�ֶ�Ӧ�Ľӿ�
 * @author kongxiaohan
 */
public class TableViewerLabelProvider implements ITableLabelProvider{
    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;//�����getColumnText()��ͬ����,����������Է���һ��nullֵ.
    }

    /**
     * �ɴ˷����������ݼ�¼�ڱ���ÿһ������ʾʲô����
     * @param element ʵ�������
     * @param columnIndex �кţ�0�ǵ�һ��
     * @return ����ֵһ��Ҫ����NULLֵ,�������
     */
    @Override
    public String getColumnText(Object element, int columnIndex) {
        Code code = (Code) element;
        if(columnIndex == 0){
            return code.getId().toString();
        }
        
        if(columnIndex == 1){
            return code.getLabel();
       }
        
        if(columnIndex == 2){
            return code.getRecode();
        }
        
        if(columnIndex == 3){
            return new SimpleDateFormat("yy-MM-dd hh:mm").format(code.getCreatedtime());
            
        }
        
        if(columnIndex == 4){
            return code.getCode();
        }
        
        //���������ϵ�ʱ�򷵻�һ�����ַ���
        return "";
    }
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>����˵�����⼸�������ô�����,��ʵ�־Ϳ�����>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void addListener(ILabelProviderListener listener) {
        
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }
    
    @Override
    public void removeListener(ILabelProviderListener listener) {
        
    }
}