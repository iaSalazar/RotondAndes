package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Items;
import vos.OrdenVos;
import vos.Zona;

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
			Date ff = rs.getDate("FECHA");
			ordenes.add(new OrdenVos(id, pid, total,ff));
		}
		return ordenes;
		
	}
	
	public void addOrden(Long id) throws SQLException, Exception {

		String sql = "INSERT INTO ORDEN (ID_PERSONA) VALUES (";	
		sql += id +")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	public void addOrdenItem(Long id,Long Iid) throws SQLException, Exception {

		String sql = "INSERT INTO ITEMS_ORDEN (ORDEN_ID,ITEMS_ID) VALUES (";	
		sql += id +" ," + Iid+ ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	public void cancelarOrden(Long idRestaurante, Long ordenId) throws Exception {
		
		
		surtirItemsIdOrden(idRestaurante,ordenId);
		
		String sql = "DELETE FROM ITEMS_ORDEN WHERE ID = " +ordenId;
		String sql2 = "DELETE FROM ORDEN WHERE ID = " +ordenId;
				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt.executeQuery();
	}
	


	public ArrayList<Long> surtirItemsIdOrden(Long idRestaurante, Long ordenId) throws SQLException, Exception
	{
		ArrayList<Long> items = new ArrayList<Long>();
		
		DAOitems  item= new DAOitems();

			
		

		String sql = "SELECT * FROM ITEMS_ORDEN WHERE ORDEN_ID = "+ordenId;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			
			Long id = rs.getLong("ORDEN_ID");
			Long itemsId = rs.getLong("ITEMS_ID");
			item.surtirRestaurante(idRestaurante, itemsId, new Long(1));
			
		}
		return items;
		
	}
}
