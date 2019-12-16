package mx.prototype.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncionesGrales {
	
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public FuncionesGrales() {

	}
	
    /**
     * Valida un correo electronico.
     * 
     * @param email a validar
     * @return true si es correcto de otra manera falso
     */
    public static boolean validateEmail(String email) {
 

        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }

}
