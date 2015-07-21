package modelo;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
public class Cliente extends Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Boolean juridico;
	
	//bi-directional one-to-many association to Proveedor
	private List<Requerimiento> requerimientos;

	public Cliente() {
	}
	/*
	public Cliente(Persona persona) {
		super(persona);
	}
	*/
	public Cliente(String cedula){
		super.cedula = cedula;
	}
	
	public Boolean getJuridico() {
		return juridico;
	}

	public void setJuridico(Boolean juridico) {
		this.juridico = juridico;
	}

	public List<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(List<Requerimiento> requerimientos) {
		this.requerimientos = requerimientos;
	}
	
	public Requerimiento addRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().add(requerimiento);
		requerimiento.setCliente(this);

		return requerimiento;
	}

	public Requerimiento removeRequerimiento(Requerimiento requerimiento) {
		getRequerimientos().remove(requerimiento);
		requerimiento.setCliente(null);

		return requerimiento;
	}

	/**METODOS OVERRIDE*/
	@Override
	public Integer getTipoMenu() {
		// TODO Auto-generated method stub
		return null;
	}

    /**METODOS PROPIOS DE LA CLASE*/
    public String cedulaCompleta(){
        return this.cedula.substring(0, 1)+"-"+this.cedula.substring(1, this.cedula.length());
    }

}