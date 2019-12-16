package mx.prototype.controler;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import mx.prototype.model.ConectionWrapper;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

public class FileUploadController {

	public FileUploadController() {
	}
	
	private static org.apache.log4j.Logger logger = Logger.getLogger(FileUploadController.class);
	ConectionWrapper conectionWrapper;
	Connection con;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	
	public void handleFileUpload(FileUploadEvent event) {
		String topicId = (String)session.getAttribute("topicId");
		try {
			conectionWrapper = new ConectionWrapper();
			FileInputStream finput = (FileInputStream) event.getFile().getInputstream();
			con = conectionWrapper.getConexion();
		   String sql = "UPDATE content_topics SET lamina = ? \n"+
				   		"WHERE topic_id = ?";
		PreparedStatement psmt =  con.prepareStatement(sql);
		logger.debug("UPDATE "+sql);
		psmt.setBinaryStream(1,finput);
		//psmt.setInt(2,Integer.parseInt(topicId));
		psmt.setInt(2,2);
		psmt.executeUpdate();
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " Se cargo correctamente.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
			
		} catch (NamingException | SQLException | IOException e) {
			logger.error(e);
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		
        
    } 

}
