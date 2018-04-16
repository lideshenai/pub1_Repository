/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Code;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface CodeDao {
       public List<Code> getList(String label);
       public boolean add(Code code);
       public boolean delete(Code code);
       public boolean update(Code code);
}
