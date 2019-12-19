package mx.prototype.adaj4jAzure;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microsoft.aad.adal4j.AuthenticationResult;

@ManagedBean(name="dtAcureAc")
@RequestScoped
public class AzureAccountBean {
	
	public String nombre;

	public AzureAccountBean() {
		System.out.println("Hola Azure");
		getDirectoryObjects();
	}
	
	  
	  public String getDirectoryObjects() {
		   ModelMap model = new ModelMap();
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	    	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	        AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
	        if (result == null) {
	            model.addAttribute("error", new Exception("AuthenticationResult not found in session."));
	            return "/error";
	        } else {
	            String data;
	            try {
	                String tenant = session.getServletContext().getInitParameter("tenant");
	                data = getUsernamesFromGraph(result.getAccessToken(), tenant);
	                model.addAttribute("tenant", tenant);
	                this.nombre = tenant;
	                model.addAttribute("users", data);
	                model.addAttribute("userInfo", result.getUserInfo());
	            } catch (Exception e) {
	                model.addAttribute("error", e);
	                return "/error";
	            }
	        }
	        return "/secure/aad.xhtml";
	    }

	    private String getUsernamesFromGraph(String accessToken, String tenant) throws Exception {


	        URL url = new URL("https://graph.microsoft.com/v1.0/users");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        conn.setRequestProperty("Accept","application/json");
	        int httpResponseCode = conn.getResponseCode();

	        String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
	        // logger.info("goodRespStr ->" + goodRespStr);
	        int responseCode = conn.getResponseCode();
	        JSONObject response = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);
	        JSONArray users;

	        users = JSONHelper.fetchDirectoryObjectJSONArray(response);

	        StringBuilder builder = new StringBuilder();
	        User user;
	        for (int i = 0; i < users.length(); i++) {
	            JSONObject thisUserJSONObject = users.optJSONObject(i);
	            user = new User();
	            JSONHelper.convertJSONObjectToDirectoryObject(thisUserJSONObject, user);
	            builder.append(user.getUserPrincipalName() + "<br/>");
	        }
	        return builder.toString();
	    }

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

	    
}
