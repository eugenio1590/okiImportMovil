package modelo;

import java.io.Serializable;

import java.util.List;


/**
 * The persistent class for the ciudad database table.
 * 
 */
public class Ciudad implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idCiudad;

	private String nombre;

	//bi-directional many-to-one association to Estado
	private Estado estado;

	//bi-directional one-to-many association to Persona
	private List<Persona> personas;

	public Ciudad() {
	}
	
	public Ciudad(String nombre, Estado estado){
		this.nombre = nombre;
		this.estado = estado;
	}

	public Integer getIdCiudad() {
		return this.idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public Persona addPersona(Persona persona) {
		getPersonas().add(persona);
		persona.setCiudad(this);

		return persona;
	}

	public Persona removePersona(Persona persona) {
		getPersonas().remove(persona);
		persona.setCiudad(null);

		return persona;
	}
}