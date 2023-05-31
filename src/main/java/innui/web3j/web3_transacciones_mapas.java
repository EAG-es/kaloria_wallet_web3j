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
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public String formar_mensaje_transaccion(Long milisegundos, web3_transacciones_mapas.filas fila
      , oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String texto = null;
        try {
            Date date = new Date(milisegundos);
            String fecha_tex = DateFormat.getInstance().format(date);
            if (fila.cantidad == null) {
                fila.cantidad = "--";
            }
            if (fila.simbolo == null) {
                fila.simbolo = "--";
            }
            texto = fecha_tex 
              + ", " + fila.destino_direccion
              + ", " + fila.cantidad
              + ", " + fila.simbolo
              + ", " + fila.transaccion_hash
              + ", " + fila.gas_usado.toString()
              + ", " + fila.precio_gas;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return texto;
    }
}
