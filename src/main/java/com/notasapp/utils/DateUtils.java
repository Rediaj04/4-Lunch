package com.notasapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de utilidad para el manejo y formateo de fechas.
 * Proporciona métodos para operaciones comunes con fechas.
 */
public class DateUtils {
    /**
     * Formatea una fecha al formato dd/MM/yyyy HH:mm:ss.
     * @param date La fecha a formatear
     * @return La fecha formateada como cadena de texto
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
    };
};