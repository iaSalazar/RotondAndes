package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.OrdenVos;

public class DAOOrden {
	
	private ArrayList<Object> recursos;
	
	
	private Connection conn;
	
	public DAOOrden()
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
	
	public ArrayList<OrdenVos> darOrdenes() throws SQLException, Exception
	{
		ArrayList<OrdenVos> ordenes = new ArrayList<OrdenVos>();

		String sql = "SELECT * FROM ORDEN";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long pid = rs.getLong("ID_PERSONA");
			Long total = rs.getLong("TOTAL");
			ordenes.add(new OrdenVos(id, pid, total));
		}
		return ordenes;
		
	}
	
	
}
