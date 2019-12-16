package mx.prototype.beans;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.StreamedContent;

import mx.prototype.daos.ClienteDAO;

public class CarouselClient implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<NuevoClienteBean> listClient;
	ClienteDAO clienteDAO;
	NuevoClienteBean selectedClient;
	StreamedContent miFoto;
	
	public CarouselClient() {
		clienteDAO = new ClienteDAO();
		listClient = clienteDAO.getClientes();

	}

	public StreamedContent encontrarFoto(int accountNumber){
		return clienteDAO.bajaFoto(accountNumber);
	}
	public List<NuevoClienteBean> getListClient() {
		return listClient;
	}

	public void setListClient(List<NuevoClienteBean> listClient) {
		this.listClient = listClient;
	}

	public NuevoClienteBean getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(NuevoClienteBean selectedClient) {
		this.selectedClient = selectedClient;
	}

	public StreamedContent getMiFoto() {
		return miFoto;
	}

	public void setMiFoto(StreamedContent miFoto) {
		this.miFoto = miFoto;
	}
	
	

}
