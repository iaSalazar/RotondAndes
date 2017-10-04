package tm;

import java.io.File;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOCuenta;
import dao.DAOIngredientes;
import dao.DAOMenu;
import dao.DAOOrden;
import dao.DAOReservas;
import dao.DAOitems;
import dao.DaoPersonas;
import dao.DaoPreferencia;
import dao.DaoRestaurante;
import dao.DaoZona;
import vos.CuentaVos;
import vos.Ingredientes;
import vos.Items;
import vos.MenuVos;
import vos.OrdenVos;
import vos.PersonaVos;
import vos.Preferencia;
import vos.ReservaVos;
import vos.Restaurante;
import vos.Zona;




public class RotondAndesTm {
	
	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTm(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			System.out.println(url);
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}
	/**
	 * da las personas de todo el sistema
	 * @return
	 * @throws Exception
	 */

	public List<PersonaVos> darPersonas() throws Exception {
		List<PersonaVos> personas;
		DaoPersonas daoPersona = new DaoPersonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			personas = daoPersona.darPersonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}
	/**
	 * 
	 * @param persona
	 * @throws Exception
	 */
	public void addPersona(PersonaVos persona) throws Exception {
		DaoPersonas daoPersonas = new DaoPersonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPersonas.setConn(conn);
			daoPersonas.addPersona(persona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public void addCliente(PersonaVos persona) throws Exception {
		DaoPersonas daoPersonas = new DaoPersonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPersonas.setConn(conn);
			daoPersonas.addCliente(persona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * retorna una persona con su ID.
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public PersonaVos BuscarPersonaPorId(Long id) throws SQLException, Exception {
		PersonaVos persona;
		DaoPersonas daoPersona = new DaoPersonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			persona = daoPersona.buscarPersonaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return persona;	}

	/**
	 * retorna los admins del sistema.
	 * @return
	 * @throws SQLException,Exception 
	 */
	public List<PersonaVos> darAdmins() throws SQLException,Exception {
		List<PersonaVos> admins;
		DaoPersonas daoPersona = new DaoPersonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPersona.setConn(conn);
			admins = daoPersona.darAdmins();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return admins;
	}

	public void addRestaurante(Restaurante restaurante) throws SQLException,Exception {
		DaoRestaurante daoRestaurante = new DaoRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			daoRestaurante.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}
	
	public void addZona(Zona zona) throws SQLException,Exception {
		DaoZona daoZona = new DaoZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoZona.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}

	public void addPreferencia(Preferencia preferencia) throws SQLException,Exception {
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.addPreferencia(preferencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ArrayList<Preferencia> BuscarPreferenciaPorId(Long id,Long idp) throws SQLException, Exception {
		ArrayList<Preferencia> preferencia;
		DaoPreferencia daoPreferencia = new DaoPreferencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			preferencia = daoPreferencia.buscarPreferencias(id,idp);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencia;	}

	//////////////////////////////////////////
	/////////////////ITEMS////////////////////
	/////////////////////////////////////////
	/**
	 * metodo que retorna los items de la base de datos
	 * @return lista de items
	 * @throws Exception
	 */
	public  List<Items> darItems() throws Exception 
	{
		List<Items> items = null;
		DAOitems it = new DAOitems() ;
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			items = it.darItems();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return items;
	}
	/**
	 * retorna un idtem con un id
	 * @param id ide del item
	 * @return el item
	 * @throws Exception
	 */
	public Items darItem(Long id) throws Exception
	{
		Items item = null;
		DAOitems it = new DAOitems() ;
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			item = it.darItem(id);
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return item;
	}
	/**
	 * crea un nuevo objeto
	 * @param items
	 * @throws Exception
	 */
	public void addItem(Items items) throws Exception
	{
		DAOitems it = new DAOitems() ;
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			it.addItems(items);
			conn.commit();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
		
	}
	/**
	 * actuliza un item
	 * @param items
	 * @throws Exception
	 */
	public void UpdateItem(Items items) throws Exception
	{
		DAOitems it = new DAOitems() ;
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			it.updateItem(items);
		
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
		
	}
	/***
	 * borra un item
	 * @param id
	 * @throws Exception
	 */
	public void DeleteItem(Long id) throws Exception
	{
		DAOitems it = new DAOitems() ;
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			it.deleteItems(id);;
		
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
		
	}
	////////////////////////////////
	////ingredienteproducto///////
	//////////////////////////////
	public void addItemigrendeinte(Long ids,Ingredientes ingre) throws Exception
	{
		DAOitems it = new DAOitems() ;
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			it.addIngrediente(ids, ingre);
			conn.commit();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	}
	public  List<Ingredientes> darItemingredientes(Long id ) throws Exception 
	{   
		List<String> nom = null;
		ArrayList<Ingredientes>items = new ArrayList<Ingredientes>();
		DAOitems it = new DAOitems() ;
		DAOIngredientes ingre = new DAOIngredientes();
		try
		{
			this.conn = darConexion();
			it.setConn(conn);
			ingre.setConn(conn);
			nom = it.darItemsIngrediente(id);
			for(int i=0;i<nom.size();i++)
			{
				String nom1 = nom.get(i);
				items.add(ingre.darIgrediente(nom1));
			}  
			
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					it.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return items;
	}
	
	//////////////////////////////////////////
	////////////ingredientes//////////////////
	/////////////////////////////////////////
	
	public  List<Ingredientes> darIngredientes() throws Exception 
	{
		List<Ingredientes> ingre = null;
		DAOIngredientes dao = new DAOIngredientes() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ingre = dao.darIngredientes();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return ingre;
	}
	public Ingredientes darIngrediente(String id) throws Exception
	{
		Ingredientes ingre = null;
		DAOIngredientes dao = new DAOIngredientes() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ingre = dao.darIgrediente(id);
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return ingre;
	}
	public void addIngrediente(Ingredientes ingre) throws Exception
	{
		DAOIngredientes dao = new DAOIngredientes() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			dao.addIngrediente(ingre);
			conn.commit();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}	
	}
	public void UpdateIngrediente(Ingredientes ingre) throws Exception
	{
		DAOIngredientes dao = new DAOIngredientes() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			dao.updateIngredientes(ingre);;
		
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
		
	}
	//////////////////////////////////////////
	////////////////Reservas//////////////////
	/////////////////////////////////////////
	
	public  List<ReservaVos> darReservas() throws Exception 
	{
		List<ReservaVos> reservas = null;
		DAOReservas dao = new DAOReservas() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			reservas = dao.darReservas();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return reservas;
	}
	
	//////////////////////////////////////////
	/////////////////menus///////////////////
	/////////////////////////////////////////
	public  List<MenuVos> darMenus() throws Exception 
	{
		List<MenuVos> menus = null;
		DAOMenu dao = new DAOMenu() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			menus = dao.darMenus();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return menus;
	}
	
	//////////////////////////////////////////
	/////////////////ordenes//////////////////
	/////////////////////////////////////////
	
	public  List<OrdenVos> darOrdenes() throws Exception 
	{
		List<OrdenVos> ordenes = null;
		DAOOrden dao = new DAOOrden() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ordenes = dao.darOrdenes();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return ordenes;
	}
	//////////////////////////////////////////
	/////////////////Cuentas//////////////////
	/////////////////////////////////////////
	public  List<CuentaVos> darCuentas() throws Exception 
	{
		List<CuentaVos> cuentas = null;
		DAOCuenta dao = new DAOCuenta() ;
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);
			cuentas = dao.darCuentas();
		}
		 catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
	
			return cuentas;
	}
}
