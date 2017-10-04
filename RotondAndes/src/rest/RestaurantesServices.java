package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.Ingredientes;
import vos.Items;
import vos.MenuVos;


@Path("restaurantes")
public class RestaurantesServices {

	@Context
	private ServletContext context;
	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	@GET
	@PathParam("menus")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenus() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<MenuVos> menu;
		try {
			menu = tm.darMenus();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
	@POST
	@Path("{id: \\d+}/newMenu")

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(@PathParam("id")Long id,MenuVos menu) {
		RotondAndesTm tm = new RotondAndesTm(getPath());	
		menu.setRid(id);
		try {
			tm.addmenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
	@POST
	@Path("{id:\\d+}/menu/{id2:\\d+}/item/{id3:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemmenu(@PathParam("id")Long id,@PathParam("id2")Long id2,@PathParam("id3")Long id3) {
		RotondAndesTm tm = new RotondAndesTm(getPath());	
		Items item;
		try {
			 
			 item = tm.darItem(id3);
			 tm.addMenuItem(item, id2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}
	@POST
	@Path("{id:\\d+}/additem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(@PathParam("id")Long id ,Items item) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		item.setRid(id);
		try {
			tm.addItem(item);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(item).build();
	}
	@POST
	@Path( "{id:\\d+}/item/{id2:\\d+}/ingrediente" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemIngrediente(  @PathParam( "id" ) Long id,@PathParam( "id2" ) Long id2  ,Ingredientes ingre) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.addItemigrendeinte(id2, ingre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingre).build();
	}

}
