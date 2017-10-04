package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ReservaVos;



public class DAOReservas {

	
	private ArrayList<Object> recursos;
	
	
	private Connection conn;
	
	
	public DAOReservas()
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
	
	public ArrayList<ReservaVos> darReservas() throws SQLException, Exception
	{
		ArrayList<ReservaVos> reservas = new ArrayList<ReservaVos>();

		String sql = "SELECT * FROM RESERVAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID_RESERVA");
			Date fecha = rs.getDate("FECHA");
			Long zid = rs.getLong("ZONA_ID");
			reservas.add(new ReservaVos(id, zid,fecha));
		}
		return reservas;
		
	}
}
