package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CuentaVos {

	@JsonProperty(value="id") 
	private Long id;
	@JsonProperty(value="oid") 
	private Long oid;
	@JsonProperty(value="total") 
	private Long total;
	@JsonProperty(value="pagado") 
	private boolean pagado;
	
	public CuentaVos(@JsonProperty(value="id")  Long id,@JsonProperty(value="oid") Long oid ,@JsonProperty(value="total")  Long total,
			@JsonProperty(value="pagado") boolean pagado)   
		{
			this.id=id;
			this.oid = oid;
			this.total = total;
			this.pagado = pagado;
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
	 * @return the oid
	 */
	public Long getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(Long oid) {
		this.oid = oid;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the pagado
	 */
	public boolean isPagado() {
		return pagado;
	}

	/**
	 * @param pagado the pagado to set
	 */
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	
}
