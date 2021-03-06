package modelo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the marca_vehiculo database table.
 * 
 */
public class MarcaVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idMarcaVehiculo;

	private String nombre;
	
	private String estatus;
	
	//bi-directional one-to-many association to Requerimiento
	private List<Requerimiento> requerimientos;
	
	public MarcaVehiculo(String nombre){
		this.nombre = nombre;
	}

	public Integer getIdMarcaVehiculo() {
		return this.idMarcaVehiculo;
	}

	public void setIdMarcaVehiculo(Integer idMarcaVehiculo) {
		this.idMarcaVehiculo = idMarcaVehiculo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Requerimiento> getRequerimientos() {
		return this.requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}

	public Requerimiento addRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().add(requerimiento);
		requerimiento.setMarcaVehiculo(this);

		return requerimiento;
	}

	public Requerimiento removeRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().remove(requerimiento);
		requerimiento.setMarcaVehiculo(null);

		return requerimiento;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
}