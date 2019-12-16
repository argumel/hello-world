package mx.prototype.beans;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.prototype.daos.ClienteDAO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

public class BuscaActualizaCliente implements Serializable{
	
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
//	private FileInputStream   photo;
	private String segment1;
	private String segment2;
	private int    segment3;
	private java.util.Date creationDate;
	private List<ClienteBeanTableModel> listClient;
	private int sAccountNumber;
	NuevoClienteBean nuevoClienteBean;
	ClienteDAO clienteDAO;
	ClienteBeanTableModel selectedClient;
	private List<ColumnModel> columns;
	private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("no_Cuenta", "nombre",
																		"apellidos", "telefono",
																		"email","direccion","fecha_creacion");
	FacesMessage message;
	private static org.apache.log4j.Logger logger = Logger.getLogger(BuscaActualizaCliente.class);
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();


	public BuscaActualizaCliente() {
		
		clienteDAO = new ClienteDAO();
		listClient = clienteDAO.getClientesTable();
		createDynamicColumns();
	}
	

	  private void createDynamicColumns() {
	       
	        columns = new ArrayList<ColumnModel>();   
	         
	        for(String columnKey : VALID_COLUMN_KEYS) {
	               columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
	        }
	    }
	  
	public void onRowSelect(SelectEvent event) {
		ClienteBeanTableModel clienteBeanTableModel = (ClienteBeanTableModel) event.getObject();
		
		this.accountNumber  = clienteBeanTableModel.getNo_Cuenta();
		//session.setAttribute("accountNumber", this.accountNumber);
		sAccountNumber      = clienteBeanTableModel.getNo_Cuenta();
		this.givenName 		= clienteBeanTableModel.getNombre();
		this.surnames  		= clienteBeanTableModel.getApellidos();
		this.phone     		= clienteBeanTableModel.getTelefono(); 
		this.email     		= clienteBeanTableModel.getEmail();
		this.address   		= clienteBeanTableModel.getDireccion();
		this.creationDate   = clienteBeanTableModel.getFecha_creacion();
		
//        FacesMessage msg = new FacesMessage("Car Selected", String.valueOf(((ClienteBeanTableModel) event.getObject()).getNo_Cuenta()));
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", String.valueOf(((ClienteBeanTableModel) event.getObject()).getNo_Cuenta()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void atualizaCliente(){
    	if(this.accountNumber == 0){
    		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debes seleccionar un cliente de la lista","Error" ); 
		    FacesContext.getCurrentInstance().addMessage(null, message);
    	}else{
    	boolean bandera = clienteDAO.actualizaCliente(this);
		if(bandera){
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico correctamente el cliente","Exito" ); 
			    FacesContext.getCurrentInstance().addMessage(null, message);
			    listClient = clienteDAO.getClientesTable();
			    
			}else{
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocurrio un error al modificar el cliente","Error" ); 
			    FacesContext.getCurrentInstance().addMessage(null, message);
			}
    	}
    	sAccountNumber = 0;
    }
    
	public void handleFileUpload(FileUploadEvent event) {
		
			FileInputStream finput;
			FacesMessage message;
			
			try {
//				if(session.getAttribute("accountNumber") != null){
				if(this.getAccountNumber() > 0){
					finput = (FileInputStream) event.getFile().getInputstream();
					//event.getFile().getFileName().
					String extension = StringUtils.substringAfter(event.getFile().getFileName(), ".");
					logger.info(extension);
					
					boolean bandera = clienteDAO.subeFoto(finput, this.getAccountNumber(),extension);
					if(bandera){
						message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Succesful", event.getFile().getFileName() + " Se cargo correctamente.");  
				        FacesContext.getCurrentInstance().addMessage(null, message);
					}else{
						message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", event.getFile().getFileName() + " Error al cargar la imagen.");  
				        FacesContext.getCurrentInstance().addMessage(null, message);
					}
				}else{
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","Debes seleccionar un cliente de la lista" ); 
				    FacesContext.getCurrentInstance().addMessage(null, message);
				}
				
		} catch (IOException e) {
			logger.error(e);
		} 

        
    } 
    
    
	static public class ColumnModel implements Serializable {
    	 
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String header;
        private String property;
 
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
 
        public String getHeader() {
            return header;
        }
 
        public String getProperty() {
            return property;
        }
    }

	public List<ClienteBeanTableModel> getListClient() {
		return listClient;
	}

	public void setListClient(List<ClienteBeanTableModel> listClient) {
		this.listClient = listClient;
	}

	public ClienteBeanTableModel getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClienteBeanTableModel selectedClient) {
		this.selectedClient = selectedClient;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
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

/*	public FileInputStream  getPhoto() {
		return photo;
	}

	public void setPhoto(FileInputStream  photo) {
		this.photo = photo;
	}
*/
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

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	

}
