package mx.prototype.beans;

import java.io.InputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.prototype.daos.ClienteDAO;
import mx.prototype.util.FuncionesGrales;

import org.apache.log4j.Logger;

public class NuevoClienteBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int    accountNumber;
	private String givenName;
	private String surnames;
	private String phone;
	private String email;
	private String address;
	private InputStream   photo;
	private String segment1;
	private String segment2;
	private int    segment3;
	private java.sql.Date creationDate;
	private static org.apache.log4j.Logger logger = Logger.getLogger(NuevoClienteBean.class);
	ClienteDAO clienteDAO;
	FacesMessage message;
	
	
	public void guardaCliente(){
		
		if(FuncionesGrales.validateEmail(this.email)){
			clienteDAO = new ClienteDAO();
			boolean bandera = clienteDAO.insertaCliente(this);
			if(bandera){
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se agrego correctamente el cliente","Exito" ); 
			    FacesContext.getCurrentInstance().addMessage(null, message);
			}else{
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocurrio un error al agregar el cliente","Error" ); 
			    FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}else{
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese un correo valido ej. samuel@gmail.com","Error" ); 
		    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	
		
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getSurnames() {
		return surnames;
	}
	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public InputStream getPhoto() {
		return photo;
	}
	public void setPhoto(InputStream photo) {
		this.photo = photo;
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
	public java.sql.Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(java.sql.Date creationDate) {
		this.creationDate = creationDate;
	}
	
	

}
