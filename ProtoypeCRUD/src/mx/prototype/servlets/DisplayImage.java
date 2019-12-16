package mx.prototype.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.prototype.daos.ClienteDAO;
import mx.prototype.model.ConectionWrapper;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;


@WebServlet("/DisplayImage")
public class DisplayImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ConectionWrapper conectionWrapper;
	private static org.apache.log4j.Logger logger = Logger.getLogger(DisplayImage.class);
       
 
    public DisplayImage() {
        super();
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response){
    	
    	InputStream inStream = null;
		try {
			conectionWrapper = new ConectionWrapper();
			con = conectionWrapper.getConexion();
			String sql = "SELECT  photo,segment1 \n"+
						 " FROM customers \n"+
						 " WHERE account_number = ?";
			
		PreparedStatement psmt =  con.prepareStatement(sql);
		
		psmt.setInt(1,Integer.parseInt(request.getParameter("accountNumber")));
		ResultSet rs = psmt.executeQuery();
		byte[] bytearray;
		String extension;
		while(rs.next()){
			bytearray = new byte[1048576];
			int size  = 0;
			inStream  = rs.getBinaryStream(1);
			extension = rs.getString(2);
			response.reset();
			if(extension.equals("jpg")){
				response.setContentType("image/jpeg");
			}else if(extension.equals("gif")){
				response.setContentType("image/gif");
			}else if(extension.equals("png")){
				response.setContentType("image/png");
			}
            
            while ((size = inStream.read(bytearray)) != -1) {
                response.getOutputStream().
                write(bytearray, 0, size);
            }
			
		}
			
		} catch (NamingException | SQLException |IOException e) {
			logger.error(e);
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
