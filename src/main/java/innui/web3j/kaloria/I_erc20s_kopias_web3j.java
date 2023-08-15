package innui.web3j.kaloria;

import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.I_erc20s_kopias;
import innui.web3j.generated.contracts.I_erc20s_kopias.OkEventResponse;
import static innui.web3j.generated.contracts.I_erc20s_kopias.getOkEvents;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public class I_erc20s_kopias_web3j extends I_erc20_web3j {
    public I_erc20s_kopias i_erc20s_kopia;
    /**
     * Carga los datos generales que utilizar con las llamadas a las funciones del contrato en Solidity
     * @param web3_direccion_contrato
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    @Override
    public boolean cargar_contrato(String web3_direccion_contrato, oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return false; }
            super.cargar_contrato(web3_direccion_contrato, ok, extras_array);
            if (ok.es == false) { return false; }
            i_erc20s_kopia = I_erc20s_kopias.load(web3_direccion_contrato, web3j.web3j, web3j.transactionManager, web3j.defaultGasProvider);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param direccion Direccion de quien envolver
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt envolver(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas_y_coin(i_erc20s_kopia.envolver(direccion, cantidad)
                   , gas_aceptable, cantidad, null, ok, extras_array);
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param direccion Direccion de quien envolver
     * @param cantidad
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean envolver_asincrono(BigInteger gas_aceptable
      , String direccion, BigInteger cantidad
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            web3j.firmar_y_llamar_asincrono_funcion_con_gas_y_coin(i_erc20s_kopia.envolver(direccion, cantidad)
                   , gas_aceptable, cantidad, null, datos_mapa, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estimar el gas de envolver la moneda de pago de la blockchain
     * @param direccion Direccion de quien envolver
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_envolver(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20s_kopia.envolver(direccion, cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt envolver(BigInteger gas_aceptable, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas_y_coin(i_erc20s_kopia.envolver(cantidad)
                   , gas_aceptable, cantidad, null, ok, extras_array);
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param cantidad
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean envolver_asincrono(BigInteger gas_aceptable
      , BigInteger cantidad, Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            web3j.firmar_y_llamar_asincrono_funcion_con_gas_y_coin(i_erc20s_kopia.envolver(cantidad)
                   , gas_aceptable, cantidad, null, datos_mapa, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estimar el gas de envolver la moneda de pago de la blockchain
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_envolver(BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20s_kopia.envolver(cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param direccion Direccion de quien envolver
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt desenvolver(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(i_erc20s_kopia.desenvolver(direccion, cantidad), gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
            List<OkEventResponse> oks_lista = getOkEvents(transactionReceipt);
            for (OkEventResponse okEventResponse: oks_lista) {
                if (okEventResponse.es == false) {
                    ok.es = okEventResponse.es;
                    ok.setTxt(okEventResponse.mensaje);
                    break;
                }
            }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param direccion Direccion de quien envolver
     * @param cantidad
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean desenvolver_asincrono(BigInteger gas_aceptable
      , String direccion, BigInteger cantidad
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            web3j.firmar_y_llamar_asincrono_funcion_con_gas(i_erc20s_kopia.desenvolver(direccion, cantidad)
              , gas_aceptable, null, datos_mapa, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estimar el gas de envolver la moneda de pago de la blockchain
     * @param direccion Direccion de quien envolver
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_desenvolver(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20s_kopia.desenvolver(direccion, cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt desenvolver(BigInteger gas_aceptable, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(i_erc20s_kopia.desenvolver(cantidad), gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Envolver la moneda de pago de la blockchain
     * @param gas_aceptable
     * @param cantidad
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean desenvolver_asincrono(BigInteger gas_aceptable, BigInteger cantidad
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            web3j.firmar_y_llamar_asincrono_funcion_con_gas(i_erc20s_kopia.desenvolver(cantidad)
              , gas_aceptable, null, datos_mapa, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estimar el gas de envolver la moneda de pago de la blockchain
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_desenvolver(BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20s_kopia.desenvolver(cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
}
