package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {
	@JsonProperty(value="id_preferencia")
	private Long id_preferencia;
	
	@JsonProperty(value="tipo")
	private String tipo;

	
	public Preferencia(@JsonProperty(value="id_preferencia")Long id_preferencia,@JsonProperty(value="tipo") String tipo){
		
		this.id_preferencia=id_preferencia;
		this.tipo=tipo;
	}
	/**
	 * @return the id_preferencia
	 */
	public Long getId_preferencia() {
		return id_preferencia;
	}

	/**
	 * @param id_preferencia the id_preferencia to set
	 */
	public void setId_preferencia(Long id_preferencia) {
		this.id_preferencia = id_preferencia;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
