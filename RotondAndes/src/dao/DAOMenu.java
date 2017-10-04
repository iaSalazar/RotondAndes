package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MenuVos;



public class DAOMenu {
	
	private ArrayList<Object> recursos;
	
	
	private Connection conn;
	
	public DAOMenu()
	{
		recursos = new ArrayList<Object>();
	}
	
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	
	public void setConn(Connection con){
		this.conn = con;
	}
    
	public ArrayList<MenuVos> darMenus() throws SQLException, Exception
	{
		ArrayList<MenuVos> menu = new ArrayList<MenuVos>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long rid = rs.getLong("RESTAURANTE_ID");
			Long precio = rs.getLong("PRECIO");
			menu.add(new MenuVos(id,rid,precio));
		}
		return menu;
		
	}

}
