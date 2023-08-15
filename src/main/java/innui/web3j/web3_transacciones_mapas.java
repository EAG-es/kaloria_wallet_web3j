package innui.web3j;

import innui.modelos.errores.oks;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 *
 * @author emilio
 */
public class web3_transacciones_mapas implements Serializable {
    public static class filas implements Serializable {
        public static String k_filas_destino_direccion = "filas_destino_direccion";
        public static String k_filas_cantidad = "filas_cantidad";
        public static String k_filas_simbolo = "filas_simbolo";
        public static String k_filas_transaccion_hash = "filas_transaccion_hash";
        public static String k_filas_precio_gas = "filas_precio_gas";
        public static String k_filas_gas_usado = "filas_gas_usado";
        public String destino_direccion;
        public String cantidad;
        public String simbolo;
        public String transaccion_hash;
        public String precio_gas;
        public BigInteger gas_usado;
    }
    public TreeMap<Long, filas> o = new TreeMap<>();

    /**
     * Forma un mensaje informativo de transaccion
     * @param milisegundos
     * @param fila
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String formar_mensaje_transaccion(Long milisegundos, web3_transacciones_mapas.filas fila
      , oks ok, Object... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        String texto = null;
        try {
            Date date = new Date(milisegundos);
            String fecha_tex = DateFormat.getInstance().format(date);
            if (fila.cantidad == null) {
                fila.cantidad = "";
            }
            if (fila.simbolo == null) {
                fila.simbolo = "";
            }
            String destino = "-";
            String gas = "-";
            String precio_gas = "-";
            String cantidad = "";
            String simbolo = "";
            if (fila.destino_direccion != null) {
                destino = fila.destino_direccion;
            }
            if (fila.gas_usado != null) {
                gas = fila.gas_usado.toString();
            }
            if (fila.precio_gas != null) {
                precio_gas = fila.precio_gas;
            }
            if (fila.cantidad != null) {
                cantidad = fila.cantidad;
            }
            if (fila.simbolo != null) {
                simbolo = fila.simbolo;
            }
            texto = fecha_tex 
              + " " + fila.transaccion_hash
              + " " + destino
              + " " + gas
              + " " + precio_gas
              + " " + cantidad
              + " " + simbolo;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return texto;
    }
}
