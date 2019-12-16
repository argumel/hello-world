package mx.prototype.daos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import mx.prototype.beans.BuscaActualizaCliente;
import mx.prototype.beans.ClienteBeanTableModel;
import mx.prototype.beans.NuevoClienteBean;
import mx.prototype.model.ConectionWrapper;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class ClienteDAO {
	
	Connection con;
	ConectionWrapper conectionWrapper;
	private static org.apache.log4j.Logger logger = Logger.getLogger(ClienteDAO.class);
	NuevoClienteBean nuevoClienteBean;
	ClienteBeanTableModel clienteBeanTableModel;
	List<NuevoClienteBean> listClient;
	List<ClienteBeanTableModel> listClientTable;
	private StreamedContent file; 
	
	public ClienteDAO(){
		
	}
	
	public boolean insertaCliente(NuevoClienteBean nuevoClienteBean){
		boolean bandera = false;
		conectionWrapper = new ConectionWrapper();
		String sql = "INSERT INTO customers \n"+
					 "						(given_names,\n"+
					 "						surnames,\n"+
					 "						phone,\n"+
					 "						email,\n"+
					 "						address,\n"+
					 "						photo,\n"+
					 "						segment1,\n"+
					 "						segment2,\n"+
					 "						segment3,\n"+
					 "						creation_date)\n"+
					 "						VALUES (?,?,?,?,?,?,?,?,?,sysdate())";
		try {
			con = conectionWrapper.getConexion();
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, nuevoClienteBean.getGivenName());
			psmt.setString(2, nuevoClienteBean.getSurnames());
			psmt.setString(3, nuevoClienteBean.getPhone());
			psmt.setString(4, nuevoClienteBean.getEmail());
			psmt.setString(5, nuevoClienteBean.getAddress());
			psmt.setBlob(6, nuevoClienteBean.getPhoto());
			psmt.setString(7, nuevoClienteBean.getSegment1());
			psmt.setString(8, nuevoClienteBean.getSegment2());
			psmt.setInt(9, nuevoClienteBean.getSegment3());
			psmt.executeUpdate();
			bandera = true;
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
	
	public boolean actualizaCliente(BuscaActualizaCliente buscaActualizaCliente){
		boolean bandera = false;
		conectionWrapper = new ConectionWrapper();
		String sql = "UPDATE customers SET \n"+
					 "						given_names = ?,\n"+
					 "						surnames = ?,\n"+
					 "						phone = ?,\n"+
					 "						email = ?,\n"+
					 "						address = ?,\n"+
					 "						segment2 = ?,\n"+
					 "						segment3 = ?,\n"+
					 "						creation_date = ?\n"+
					 "						WHERE account_number = ?";
		try {
			//FileInputStream finput = (FileInputStream) buscaActualizaCliente.getPhoto();
			con = conectionWrapper.getConexion();
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, buscaActualizaCliente.getGivenName());
			psmt.setString(2, buscaActualizaCliente.getSurnames());
			psmt.setString(3, buscaActualizaCliente.getPhone());
			psmt.setString(4, buscaActualizaCliente.getEmail());
			psmt.setString(5, buscaActualizaCliente.getAddress());
			psmt.setString(6, buscaActualizaCliente.getSegment2());
			psmt.setInt(7,    buscaActualizaCliente.getSegment3());
			psmt.setDate(8,  new java.sql.Date(buscaActualizaCliente.getCreationDate().getTime()));
			psmt.setInt(9,   buscaActualizaCliente.getAccountNumber());
			psmt.executeUpdate();
			bandera = true;
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
	
	public List<NuevoClienteBean> getClientes(){
		
		conectionWrapper = new ConectionWrapper();
		String sql = "  SELECT account_number AS No_Cuenta, \n"+
					 "         given_names AS Nombre,\n"+
					 "		    surnames AS Apellidos,\n"+
					 "		    phone AS Telefono,\n"+
					 "		    email AS mail,\n"+
					 "		    address AS Direccion,\n"+
					 "		    photo,\n"+
					 "		    segment1,\n"+
					 "		    segment2,\n"+
					 "		    segment3,\n"+
					 "		    creation_date AS Fecha_creacion\n"+
					 "		FROM customers";
		try {
			con = conectionWrapper.getConexion();
			 PreparedStatement psmt = con.prepareStatement(sql);
			 ResultSet rs =  psmt.executeQuery();
			 listClient = new ArrayList<>();
			 while(rs.next()){
				 nuevoClienteBean = new NuevoClienteBean();
				 nuevoClienteBean.setAccountNumber(rs.getInt(1));
				 nuevoClienteBean.setGivenName(rs.getString(2));
				 nuevoClienteBean.setSurnames(rs.getString(3));
				 nuevoClienteBean.setPhone(rs.getString(4));
				 nuevoClienteBean.setEmail(rs.getString(5));
				 nuevoClienteBean.setAddress(rs.getString(6));
				 nuevoClienteBean.setPhoto(rs.getBinaryStream(7));
				 nuevoClienteBean.setSegment1(rs.getString(8));
				 nuevoClienteBean.setSegment2(rs.getString(9));
				 nuevoClienteBean.setSegment3(rs.getInt(10));
				 nuevoClienteBean.setCreationDate(rs.getDate(11));
				 listClient.add(nuevoClienteBean);
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
		
		return listClient;
	}

public List<ClienteBeanTableModel> getClientesTable(){
		
		conectionWrapper = new ConectionWrapper();
		String sql = "  SELECT account_number AS No_Cuenta, \n"+
					 "         given_names AS Nombre,\n"+
					 "		    surnames AS Apellidos,\n"+
					 "		    phone AS Telefono,\n"+
					 "		    email AS mail,\n"+
					 "		    address AS Direccion,\n"+
					 "		    photo,\n"+
					 "		    segment1,\n"+
					 "		    segment2,\n"+
					 "		    segment3,\n"+
					 "		    creation_date AS Fecha_creacion\n"+
					 "		FROM customers";
		try {
			con = conectionWrapper.getConexion();
			 PreparedStatement psmt = con.prepareStatement(sql);
			 ResultSet rs =  psmt.executeQuery();
			 listClientTable = new ArrayList<>();
			 while(rs.next()){
				 clienteBeanTableModel = new ClienteBeanTableModel();
				 clienteBeanTableModel.setNo_Cuenta(rs.getInt(1));
				 clienteBeanTableModel.setNombre(rs.getString(2));
				 clienteBeanTableModel.setApellidos((rs.getString(3)));
				 clienteBeanTableModel.setTelefono((rs.getString(4)));
				 clienteBeanTableModel.setEmail(rs.getString(5));
				 clienteBeanTableModel.setDireccion(rs.getString(6));
				 //clienteBeanTableModel.setFoto(rs.getBinaryStream(7));
				 clienteBeanTableModel.setSegment1(rs.getString(8));
				 clienteBeanTableModel.setSegment2(rs.getString(9));
				 clienteBeanTableModel.setSegment3(rs.getInt(10));
				 clienteBeanTableModel.setFecha_creacion(rs.getDate(11));
				 listClientTable.add(clienteBeanTableModel);
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
		
		return listClientTable;
	}

public boolean subeFoto(FileInputStream finput,int accountNumber,String extension){
	boolean bandera = false;
	conectionWrapper = new ConectionWrapper();
	String sql = "UPDATE customers \n"+
				 "	SET photo = ? , segment1 = ?\n"+
				 "	WHERE account_number = ?";
	
	try {
		con = conectionWrapper.getConexion();
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setBinaryStream(1,finput);
		psmt.setString(2, extension);
		psmt.setInt(3,accountNumber);
		psmt.executeUpdate();
		bandera = true;
		
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


	

	public StreamedContent bajaFoto(int accountNumber) {
		
		InputStream inStream = null;
		try {
			conectionWrapper = new ConectionWrapper();
			con = conectionWrapper.getConexion();
			String sql = "SELECT  photo \n"+
						 " FROM customers \n"+
						 " WHERE account_number = ?";
			
		PreparedStatement psmt =  con.prepareStatement(sql);
		
		psmt.setInt(1,accountNumber);
		ResultSet rs = psmt.executeQuery();
		
		while(rs.next()){
			inStream = rs.getBinaryStream(1);
		}
			
		} catch (NamingException | SQLException e) {
			logger.error(e);
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	  file = new DefaultStreamedContent(inStream, "image/png");
	  return file;
	
	}



}
