package modelo;

import java.io.Serializable;

import java.util.List;



public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idEstado;

	private String nombre;

	//private List<Ciudad> ciudads;

	public Estado() {
	}
	
	public Estado(String nombre){
		this.nombre = nombre;
	}

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    /*
	public List<Ciudad> getCiudads() {
		return this.ciudads;
	}

	public void setCiudads(List<Ciudad> ciudads) {
		this.ciudads = ciudads;
	}

	public Ciudad addCiudad(Ciudad ciudad) {
		getCiudads().add(ciudad);
		ciudad.setEstado(this);

		return ciudad;
	}

	public Ciudad removeCiudad(Ciudad ciudad) {
		getCiudads().remove(ciudad);
		ciudad.setEstado(null);

		return ciudad;
	}
    */
}