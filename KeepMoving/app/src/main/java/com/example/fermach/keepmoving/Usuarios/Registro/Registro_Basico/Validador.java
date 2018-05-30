package com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fermach on 30/05/2018.
 */

public class Validador {

    // Email Regex java
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String FECHA_REGEX= "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    // static Pattern object, since pattern is fixed
    private static Pattern pattern_email;
    private static Pattern pattern_fecha;

    // non-static Matcher object because it's created from the input String
    private Matcher matcher;

    public Validador() {
        // initialize the Pattern object
        pattern_email = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        pattern_fecha = Pattern.compile(FECHA_REGEX, Pattern.CASE_INSENSITIVE);
    }

    /**
     * This method validates the input email address with EMAIL_REGEX pattern
     *
     * @param email
     * @return boolean
     */
    public boolean validateEmail(String email) {
        matcher = pattern_email.matcher(email);
        return matcher.matches();
    }

    public boolean validateFecha(String fecha) {
        matcher = pattern_fecha.matcher(fecha);
        return matcher.matches();
    }
}
