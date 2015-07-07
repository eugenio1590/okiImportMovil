package modelo;

import java.io.Serializable;

//import com.okiimport.app.mvvm.AbstractViewModel;


/**
 * The persistent class for the detalle_requerimiento database table.
 * 
 */
public class DetalleRequerimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idDetalleRequerimiento;

	private Long cantidad;

	private String codigoOem;

	private String estatus;

	//private byte[] foto;
	
	private String descripcion;

	//bi-directional many-to-one association to Requerimiento
	private Requerimiento requerimiento;

	public DetalleRequerimiento() {
	}

	public Integer getIdDetalleRequerimiento() {
		return this.idDetalleRequerimiento;
	}

	public void setIdDetalleRequerimiento(Integer idDetalleRequerimiento) {
		this.idDetalleRequerimiento = idDetalleRequerimiento;
	}

	public Long getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigoOem() {
		return this.codigoOem;
	}

	public void setCodigoOem(String codigoOem) {
		this.codigoOem = codigoOem;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/*public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}*/

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	/**METODOS PROPIOS DE LA CLASE*/
	/*public String getFoto64(){
		return AbstractViewModel.decodificarImagen(foto);
	}*/
}