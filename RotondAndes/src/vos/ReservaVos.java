package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaVos {
	
	@JsonProperty(value="id") 
	private Long id;
	
	@JsonProperty(value="zid")
	private Long zid;
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	public ReservaVos(	@JsonProperty(value="id") Long id,	@JsonProperty(value="zid") Long zid,	@JsonProperty(value="fecha") Date fecha )
	{
		this.id=id;
		this.zid = zid;
		this.fecha = fecha;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the zid
	 */
	public Long getZid() {
		return zid;
	}

	/**
	 * @param zid the zid to set
	 */
	public void setZid(Long zid) {
		this.zid = zid;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

}
