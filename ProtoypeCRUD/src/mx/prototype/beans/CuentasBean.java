package mx.prototype.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.prototype.daos.CuentasDAO;

public class CuentasBean implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int login_id;
	private String userName;
	private String password;
	private java.sql.Date creationDate;
	private java.sql.Date endDate;
	private java.sql.Date lastLogin;
	CuentasDAO cuentasDAO;
	
	
	public void guardaContrasena(){
		cuentasDAO = new CuentasDAO();
		String bandera = cuentasDAO.insertaCuenta(this);
		if(bandera.equals("true")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Correcto", "Cuenta creada"));
		}else if(bandera.equals("false")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Fallo al crear la cuenta"));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"El nombre de ususario ya existe, ingrese otra cuenta", "Error"));
		}
	}
	
	public int getLogin_id() {
		return login_id;
	}
	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public java.sql.Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(java.sql.Date creationDate) {
		this.creationDate = creationDate;
	}
	public java.sql.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}
	public java.sql.Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(java.sql.Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	
}
