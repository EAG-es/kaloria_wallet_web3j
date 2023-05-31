package innui.web3j.kaloria;

import innui.web3j.web3js;
import static innui.web3j.web3js.k_tiempo_maximo_esperando_milisegundos;
import innui.bases;
import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.Kalorias;
import java.math.BigInteger;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public class Kalorias_web3j extends bases {
    public web3js web3j;
    public Kalorias kaloria;

    /**
     * Carga los datos generales que utilizar con las llamadas a las funciones del contrato en Solidity
     * @param web3_direccion_contrato
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean cargar_contrato(String web3_direccion_contrato, oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return false; }
            kaloria = Kalorias.load(web3_direccion_contrato, web3j.web3j, web3j.transactionManager, web3j.defaultGasProvider);
            if (ok.es == false) { return false; }
            web3j.web3_direccion_contrato = web3_direccion_contrato;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Regala una cantidad (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param direccion
     * @param cantidad
     * @param id
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt regalar(BigInteger gas_aceptable, String direccion
        , BigInteger cantidad, BigInteger id
        , oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria.regalar(direccion, cantidad, id), gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Estima el gas necesario para regalar una cantidad a una dirección
     * @param direccion
     * @param cantidad
     * @param id
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_regalar(String direccion, BigInteger cantidad
      , BigInteger id, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria.regalar(direccion, cantidad, id);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    
}
