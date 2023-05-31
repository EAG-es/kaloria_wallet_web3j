package innui.web3j.kaloria;

import innui.web3j.Erc20_bases_web3j;
import static innui.web3j.web3js.k_tiempo_maximo_esperando_milisegundos;
import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.I_erc20_bases;
import innui.web3j.generated.contracts.I_erc20_bases.OkEventResponse;
import static innui.web3j.generated.contracts.I_erc20_bases.getOkEvents;
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
public class I_erc20_bases_web3j extends Erc20_bases_web3j {
    
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
            i_erc20_base = I_erc20_bases.load(web3_direccion_contrato, web3j.web3j, web3j.transactionManager, web3j.defaultGasProvider);
            if (ok.es == false) { return false; }
            web3j.web3_direccion_contrato = web3_direccion_contrato;
            leer_decimales(ok, extras_array);
            leer_simbolo(ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Obtener el nombre
     * @param ok
     * @param extras_array
     * @return 
     * @throws Exception 
     */
    @Override
    public String leer_nombre(oks ok, Object ... extras_array) throws Exception {
        String retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<String> remoteFunctionCall = i_erc20_base.name();
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (String) types_list.get(0).getValue();
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Obtener el símbolo
     * @param ok
     * @param extras_array
     * @return 
     * @throws Exception 
     */
    @Override
    public String leer_simbolo(oks ok, Object ... extras_array) throws Exception {
        String retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<String> remoteFunctionCall = i_erc20_base.symbol();
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (String) types_list.get(0).getValue();
                simbolo = retorno;
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Obtener el número de decimales
     * @param ok
     * @param extras_array
     * @return 
     * @throws Exception 
     */
    @Override
    public Integer leer_decimales(oks ok, Object ... extras_array) throws Exception {
        Integer retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = i_erc20_base.decimals();
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = ((BigInteger) types_list.get(0).getValue()).intValue();
                decimales = retorno;
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Leer el balance
     * @param direccion Dirección de la que leer el balance
     * @param balance_stringBuilder Texto con la coma situada en el lugar correcto (puede ser null).
     * @param ok
     * @param extras_array
     * @return El valor como un número sin decimales
     * @throws Exception 
     */
    @Override
    public BigInteger leer_balance(String direccion, StringBuilder balance_stringBuilder, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = i_erc20_base.balanceOf(direccion);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (BigInteger) types_list.get(0).getValue();
                if (balance_stringBuilder != null) {
                    String texto = poner_decimales_a_numero(retorno, ok);
                    if (ok.es == false) { return null; }
                    balance_stringBuilder.replace(0, balance_stringBuilder.length(), texto);
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Leer el balance
     * @param balance_stringBuilder Texto con la coma situada en el lugar correcto (puede ser null).
     * @param ok
     * @param extras_array
     * @return El valor como un número sin decimales
     * @throws Exception 
     */
    @Override
    public BigInteger leer_balance(StringBuilder balance_stringBuilder, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = i_erc20_base.balanceOf(web3j.credentials.getAddress());
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (BigInteger) types_list.get(0).getValue();
                if (balance_stringBuilder != null) {
                    String texto = poner_decimales_a_numero(retorno, ok);
                    if (ok.es == false) { return null; }
                    balance_stringBuilder.replace(0, balance_stringBuilder.length(), texto);
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Envía una cantidad (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    @Override
    public TransactionReceipt enviar(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(i_erc20_base.transfer(direccion, cantidad), gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
            List<OkEventResponse> oks_lista = getOkEvents(transactionReceipt);
            for (OkEventResponse okEventResponse: oks_lista) {
                if (okEventResponse.es == false) {
                    ok.es = okEventResponse.es;
                    ok.setTxt(okEventResponse.mensaje);
                    break;
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    @Override
    public BigInteger estimar_gas_enviar(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = i_erc20_base.transfer(direccion, cantidad);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }

}
