package innui.web3j;

import innui.bases;
import innui.modelos.errores.oks;

/**
 *
 * @author emilio
 */
public class Bytes__ extends bases {
    
    public static byte [] ajustar(byte [] bytes_array, int tam, oks ok, Object ... extras_array) throws Exception {
        byte [] bytes___array = null;
        try {
            if (ok.es == false) { return null; }
            bytes___array = new byte [tam];
            int i = 0;
            while (true) {
                if (i >= tam) {
                    break;
                }
                bytes___array[i] = 0;
                i = i + 1;
            }
            i = 0;
            int tam_actual = bytes_array.length;
            while (true) {
                if (i >= tam || i >= tam_actual) {
                    break;
                }
                bytes___array[i] = bytes_array[i];
                i = i + 1;
            }
        } catch (Exception e) {
            throw e;
        }
        return bytes___array;
    }        
    
}
