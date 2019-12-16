package mx.prototype.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import mx.prototype.beans.CuentasBean;
import mx.prototype.model.ConectionWrapper;

import org.apache.log4j.Logger;


public class CuentasDAO {

	Connection con;
	ConectionWrapper conectionWrapper;
	private static org.apache.log4j.Logger logger = Logger.getLogger(CuentasDAO.class);
	
	public CuentasDAO() {

	}
	
	public String insertaCuenta(CuentasBean cuentasBean){
		String bandera = "false";
		String sql2 = "SELECT count(*) FROM login WHERE username = ?";
		String sql = "INSERT INTO login \n"+
					 "	(username,\n"+
					 "	password,\n"+
					 "	creation_date)\n"+
					 "	VALUES(?,MD5(?),sysdate())";
		try {
			conectionWrapper = new ConectionWrapper();
			con = conectionWrapper.getConexion();
			PreparedStatement psmt2 = con.prepareStatement(sql2);
			psmt2.setString(1, cuentasBean.getUserName());
			ResultSet rs2 = psmt2.executeQuery();
			int count = 0;
			while(rs2.next()){
				count = rs2.getInt(1);
			}
			if(count == 0){
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, cuentasBean.getUserName());
				psmt.setString(2, cuentasBean.getPassword());
				psmt.executeUpdate();
				bandera = "true";
			}else{
				bandera = "existe";
			}
			
			
			
		} catch (NamingException | SQLException e) {
			logger.error(e);
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		return bandera;
	}

}
