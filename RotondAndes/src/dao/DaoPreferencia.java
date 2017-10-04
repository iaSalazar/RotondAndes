package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.PersonaVos;
import vos.Preferencia;

public class DaoPreferencia {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DaoPreferencia() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
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

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	public void addPreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "INSERT INTO PREFERENCIAS VALUES (";
		sql += preferencia.getId_preferencia() + ",'";
		sql +=preferencia.getTipo()+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public Preferencia buscarPreferencias(Long id, Long idp) throws SQLException {
		
		Preferencia preferencia = null;
		String sql = "SELECT * FROM PREFERENCIAS_PERSONAS s INNER JOIN PREFERENCIAS p ON s.ID_PREFERENCIA=p.ID_PREFERENCIA WHERE ID_PERSONA ="+id+"AND s.ID_PREFERENCIA =" +idp;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
        if (rs.next()) {
			
//			Long idusuario = rs.getLong("ID_PERSONA");
			Long idPreferencia =rs.getLong("ID_PREFERENCIA");
//			String rol = rs.getString("ROL");
			String tipo = rs.getString("TIPO");
		
			preferencia=new Preferencia(idPreferencia, tipo);
        }
		return preferencia;
		
	}

}
