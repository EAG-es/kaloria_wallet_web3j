package inclui.web3j.kaloria_wallet_web3j;

import static inclui.web3j.web3js.k_tiempo_maximo_esperando_milisegundos;
import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.I_erc20;
import java.math.BigInteger;
import java.util.List;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public class I_erc20_web3j extends I_erc20_bases_web3j {
    public static String k_in_ruta = "in/inclui/web3j/kaloria_wallet_web3j/in";  //NOI18N

    public I_erc20 i_erc20;
    /**
     * Carga los datos generales que utilizar con las llamadas a las funciones del contrato Hola_mundos
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
            i_erc20 = I_erc20.load(web3_direccion_contrato, web3j.web3j, web3j.transactionManager, web3j.defaultGasProvider);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Aprobar una transferencia de gasto, el envío de una cantidad de una dirección a otra
     * @param gas_aceptable
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt aprobar(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(i_erc20.approve(direccion, cantidad), gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
            transactionReceipt = web3j.comprobar_y_esperar_recibo(transactionReceipt
                  , k_tiempo_maximo_esperando_milisegundos, ok, extras_array);
            if (web3j.ser_recibo_vacio(transactionReceipt, ok) == false) {
                if (ok.es == false) { return null; }
                web3j.restar_gas(transactionReceipt.getGasUsed(), ok);
            }
            if (ok.es == false) { return null; }
            web3j.poner_ultimo_precio_gas(transactionReceipt, ok);
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Estima el gas necesario para aprovar una transferencia de gasto, el envío de una cantidad de una dirección a otra
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_aprobar(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20.approve(direccion, cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Transferir de una cantidad de una dirección a otra
     * @param gas_aceptable
     * @param direccion_origen
     * @param direccion_destino
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt transferir(BigInteger gas_aceptable, String direccion_origen, String direccion_destino
            , BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(i_erc20.transferFrom(direccion_origen, direccion_destino, cantidad), gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
            transactionReceipt = web3j.comprobar_y_esperar_recibo(transactionReceipt
                  , k_tiempo_maximo_esperando_milisegundos, ok, extras_array);
            if (web3j.ser_recibo_vacio(transactionReceipt, ok) == false) {
                if (ok.es == false) { return null; }
                web3j.restar_gas(transactionReceipt.getGasUsed(), ok);
            }
            if (ok.es == false) { return null; }
            web3j.poner_ultimo_precio_gas(transactionReceipt, ok);
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Estima el gas necesario para aprovar el gasto, el envío de una cantidad de una dirección a otra
     * @param direccion_origen
     * @param direccion_destino
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_transferir(String direccion_origen, String direccion_destino
            , BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20.transferFrom(direccion_origen
                , direccion_destino, cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    
    /**
     * Obtener información de la aceptación de gasto de un propietario sobre un gastador
     * @param propietario_direcccion
     * @param gastador_direccion
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger aceptacion(String propietario_direcccion, String gastador_direccion, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = i_erc20.allowance(propietario_direcccion, gastador_direccion);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (BigInteger) types_list.get(0).getValue();
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
}
