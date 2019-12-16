package mx.prototype.beans;

import java.io.InputStream;
import java.io.Serializable;

public class ClienteBeanTableModel implements Serializable{


	private static final long serialVersionUID = 1L;
	private int    No_Cuenta;
	private String Nombre;
	private String Apellidos;
	private String Telefono;
	private String Email;
	private String Direccion;
	private InputStream   Foto;
	private String segment1;
	private String segment2;
	private int    segment3;
	private java.sql.Date Fecha_creacion;
	
	public ClienteBeanTableModel() {

	}

	public int getNo_Cuenta() {
		return No_Cuenta;
	}

	public void setNo_Cuenta(int no_Cuenta) {
		No_Cuenta = no_Cuenta;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellidos() {
		return Apellidos;
	}

	public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public InputStream getFoto() {
		return Foto;
	}

	public void setFoto(InputStream foto) {
		Foto = foto;
	}

	public String getSegment1() {
		return segment1;
	}

	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}

	public String getSegment2() {
		return segment2;
	}

	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}

	public int getSegment3() {
		return segment3;
	}

	public void setSegment3(int segment3) {
		this.segment3 = segment3;
	}

	public java.sql.Date getFecha_creacion() {
		return Fecha_creacion;
	}

	public void setFecha_creacion(java.sql.Date fecha_creacion) {
		Fecha_creacion = fecha_creacion;
	}
	
	

}
