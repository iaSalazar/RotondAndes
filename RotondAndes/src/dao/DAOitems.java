package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingredientes;
import vos.Items;



public class DAOitems {

	
	private ArrayList<Object> recursos;
	
	
	private Connection conn;
	
	public DAOitems()
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
    
	public ArrayList<Items> darItems() throws SQLException, Exception
	{
		ArrayList<Items> items = new ArrayList<Items>();

		String sql = "SELECT * FROM ITEMS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long Rid = rs.getLong("ID_RESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			Long precio = rs.getLong("PRECIO");
			String nombreen =rs.getString("NOMBREINGLES");
			Long tiempop = rs.getLong("TIEMPO_PREPARACION");
			Long costop = rs.getLong("COSTO_PRODU");
			int cant = rs.getInt("CANTIDAD");
			items.add(new Items(id, Rid, name,tipo,precio,nombreen,tiempop,costop,cant));
		}
		return items;
		
	}
	public Items darItem(Long id) throws SQLException, Exception
	{

		Items it = null;
		String sql = "SELECT * FROM ITEMS WHERE ID="+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
        if(rs.next())
        {
			Long ids = rs.getLong("ID");
			Long Rid = rs.getLong("ID_RESTAURANTE");
			String name = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			Long precio = rs.getLong("PRECIO");
			String nombreen =rs.getString("NOMBREINGLES");
			Long tiempop = rs.getLong("TIEMPO_PREPARACION");
			Long costop = rs.getLong("COSTO_PRODU");
			int cant = rs.getInt("CANTIDAD");
	        it= new Items(id, Rid, name,tipo,precio,nombreen,tiempop,costop,cant);
		}
		return it;
		
	}
	public void addItems(Items item) throws SQLException, Exception
	{
		
		String sql = "INSERT INTO ITEMS (ID_RESTAURANTE,NOMBRE,TIPO,PRECIO,NOMBREINGLES,TIEMPO_PREPARACION,COSTO_PRODU) VALUES (";
		sql += item.getRid() + ",'";
		sql += item.getNombre() + "','";
		sql += item.getTipo() + "',";
		sql += item.getPrecio() +",'";
		sql += item.getNombreEN() + "',";
		sql += item.getTiempopreparacion() + ",";
		sql += item.getCantidad() + ",";
		sql += item.getCostoproducion() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
	}
	

	
	public void updateItem(Items item) throws SQLException, Exception {

		String sql = "UPDATE ITEMS SET ";
		sql += "ID=" + item.getId() + ",";
		sql += "ID_RESTAURANTE=" + item.getRid() + ",";
		sql += "NOMBRE='" +  item.getNombre() +"',";
		sql += "TIPO='" +  item.getTipo() +"',";
		sql += "PRECIO=" +  item.getPrecio() +", " ;
		sql += "NOMBREINGLES='" +  item.getNombreEN() +"'," ;
		sql += "TIEMPO_PREPARACION=" +  item.getTiempopreparacion() +"," ;
		sql += "COSTO_PRODU=" +  item.getTiempopreparacion() +" " ;
		sql += " WHERE ID = " + item.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteItems(Long id) throws SQLException, Exception {

		String sql = "DELETE FROM ITEMS";
		sql += " WHERE ID = " + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	//////////////////////
	///ingredientes//////
	////////////////////
	
	public void addIngrediente(Long id ,Ingredientes nom) throws SQLException
	{
		String sql = "INSERT INTO INGREDIENTE_ITEM (ITEMS_ID,INGRE_ID) VALUES (";
		sql += id + ",'";
		sql += nom.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();	
	}
	
	public ArrayList<String> darItemsIngrediente(Long id) throws SQLException, Exception
	{
		ArrayList<String> in = new ArrayList<String>();
		ArrayList<Ingredientes> items = new ArrayList<Ingredientes>();
        DAOIngredientes ingredi = new DAOIngredientes();
        ingredi.setConn(conn);
		String sql = "SELECT INGRE_ID AS NOM FROM INGREDIENTE_ITEM WHERE ITEMS_ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
          String nombre = rs.getString("NOM");
          in.add(nombre);
		 }	
 
		return in;
		
	}
}
