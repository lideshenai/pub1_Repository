/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Code;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ConnUtil;

/**
 *
 * @author Administrator
 */
public class CodeDaoImpl implements CodeDao {
	private Connection conn;

	public CodeDaoImpl() throws SQLException {
		conn = ConnUtil.getConnection();
	}

	@Override
	public List<Code> getList(String label) {
		List<Code> list = new ArrayList<>();
		String sql = "select id ,code ,label ,recode,createdtime from code "
				+ "where label like concat('%',?,'%') order by createdtime desc";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, label);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Code code = new Code();
				code.setId(rs.getInt(1));
				code.setCode(rs.getString(2));
				code.setLabel(rs.getString(3));
				code.setRecode(rs.getString(4));
				code.setCreatedtime(rs.getDate(5));
				list.add(code);
			}
		} catch (SQLException ex) {
			Logger.getLogger(CodeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

		return list;
	}

	@Override
	public boolean add(Code code) {
		// To change body of generated methods, choose Tools | Templates.
		String sql = "insert into code(label,code,createdtime,recode) " + "values(?,?,NOW(),?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code.getLabel());
			ps.setString(2, code.getCode());
			ps.setString(3, code.getRecode());
			int isOK = ps.executeUpdate();
			if (isOK > 0)
				return true;
		} catch (SQLException ex) {
			Logger.getLogger(CodeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	@Override
	public boolean delete(Code code) {
		String sql = "delete from code where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, code.getId());
			int count = ps.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(CodeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	@Override
	public boolean update(Code code) {
		Date now = new Date(System.currentTimeMillis());
		String sql = "update code set code = ? ,label = ? ,recode = ? ,createdtime = " + "? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code.getCode());
			ps.setString(2, code.getLabel());
			ps.setString(3, code.getRecode());
			ps.setDate(4, now);
			ps.setInt(5, code.getId());
			int count = ps.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(CodeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

}
