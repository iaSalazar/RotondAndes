package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrdenVos {
	@JsonProperty(value="id") 
	private Long id;
	@JsonProperty(value="pid") 
	private Long pid;
	@JsonProperty(value="total") 
	private Long total;
	
	public OrdenVos(@JsonProperty(value="id") Long id,@JsonProperty(value="pid") Long pid,@JsonProperty(value="total") Long total )
	{
		this.id = id;
		this.pid = pid;
		this.total = total;
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
	 * @return the pid
	 */
	public Long getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(Long pid) {
		this.pid = pid;
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

}
