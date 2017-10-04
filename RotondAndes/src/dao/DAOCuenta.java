package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.CuentaVos;



public class DAOCuenta {

	
	private ArrayList<Object> recursos;
	
	
	private Connection conn;
	
	public DAOCuenta()
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
    
	public ArrayList<CuentaVos> darCuentas() throws SQLException, Exception
	{
		ArrayList<CuentaVos> cuentas = new ArrayList<CuentaVos>();

		String sql = "SELECT * FROM CUENTA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			boolean pag;
			Long id = rs.getLong("ID");
			Long oid = rs.getLong("ORDEN_ID");
			Long total = rs.getLong("TOTAL");
			String resb = rs.getString("PAGADO");
			if(resb.equals("y"))
			{
				pag = true;
			}
			else
				pag =false;
			cuentas.add(new CuentaVos(id, oid, total, pag));
		}
		return cuentas;
		
	}
}
