package mx.prototype.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import mx.prototype.model.ConectionWrapper;

import org.apache.log4j.Logger;

public class LoginBean {
	private static org.apache.log4j.Logger logger = Logger.getLogger(LoginBean.class);
	
	private String user;
	private String password;
	ConectionWrapper conectionWrapper;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	private String correo;
	Connection con;
	
	CuentasBean cuentasBean;
	
	public LoginBean() {
		
		conectionWrapper = new ConectionWrapper();
	}
	

	
	public String passUsuario(){
		String paNoPasa = "NoPasa";
		try {
			con = conectionWrapper.getConexion();
			String sql = "SELECT login_id, \n"+
						 "       username, \n"+
					     "       password, \n"+
					     "       creation_date, \n"+
					     "       end_date,     \n"+ 
					     "       last_login,    \n"+
					     "       count(*)    \n"+
					     "FROM login \n" + 
						 " WHERE USERNAME = ? \n" + 
						 "AND password = MD5(?)";
			
			PreparedStatement psmt =  con.prepareStatement(sql);
			psmt.setString(1,user);
			psmt.setString(2,password);
			ResultSet rs = psmt.executeQuery();
			int count = 0;
			while(rs.next()){
				cuentasBean = new CuentasBean();
				cuentasBean.setLogin_id(rs.getInt(1));
				cuentasBean.setUserName(rs.getString(2));
				cuentasBean.setPassword(rs.getString(3));
				cuentasBean.setCreationDate(rs.getDate(4));
				cuentasBean.setEndDate(rs.getDate(5));
				cuentasBean.setLastLogin(rs.getDate(6));
				count = rs.getInt(7);
			}
			if(count > 0){
				HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
				session.setAttribute("cuentasBean", cuentasBean);
				paNoPasa = "success";
				
			}else{
				 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario y/o contraseña incorrectos", "Por favor vuelve a intentarlo"));
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			        FacesMessage.SEVERITY_FATAL, e.getClass().getName(), e.getMessage()));
			logger.error(e);
			
		}finally{
			try {
				con.close();
				
			} catch (SQLException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				        FacesMessage.SEVERITY_FATAL, e.getClass().getName(), e.getMessage()));
				 
				logger.error(e);
			}
		}
		
		logger.debug("paNoPasa "+paNoPasa);
		return paNoPasa;
	}
	
	
	
	public String salir(){
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		CuentasBean cuentasBean = (CuentasBean)session.getAttribute("cuentasBean");
		logger.debug("Session de "+cuentasBean.getUserName() + " Cerrada!!");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "salir";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	

}
