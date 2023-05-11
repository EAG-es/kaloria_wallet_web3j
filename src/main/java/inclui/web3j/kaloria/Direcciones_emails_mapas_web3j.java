package inclui.web3j.kaloria;

import inclui.web3j.web3js;
import innui.bases;
import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.Direcciones_emails_mapas;
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
public class Direcciones_emails_mapas_web3j extends bases {
    public web3js web3j;
    public Direcciones_emails_mapas direcciones_emails_mapa;
    
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
            direcciones_emails_mapa = Direcciones_emails_mapas.load(web3_direccion_contrato, web3j.web3j, web3j.transactionManager, web3j.defaultGasProvider);
            if (ok.es == false) { return false; }
            web3j.web3_direccion_contrato = web3_direccion_contrato;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Conocer si un email esta registrado
     * @param email
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public Boolean estar_email(String email, oks ok, Object ... extras_array) throws Exception {
        Boolean retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<Boolean> remoteFunctionCall = direcciones_emails_mapa.estar_email(email);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (Boolean) types_list.get(0).getValue();
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Conocer si un email esta registrado
     * @param direccion
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public Boolean estar_direccion(String direccion, oks ok, Object ... extras_array) throws Exception {
        Boolean retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<Boolean> remoteFunctionCall = direcciones_emails_mapa.estar_direccion(direccion);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (Boolean) types_list.get(0).getValue();
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Leer la direccion de un email registrado
     * @param email
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public String leer_direccion(String email, oks ok, Object ... extras_array) throws Exception {
        String retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<String> remoteFunctionCall = direcciones_emails_mapa.leer_direccion(email);
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
     * Leer el email de una direccion registrada
     * @param direccion
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public String leer_email(String direccion, oks ok, Object ... extras_array) throws Exception {
        String retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<String> remoteFunctionCall = direcciones_emails_mapa.leer_email(direccion);
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
     * Crear un nuevo registro
     * @param gas_aceptable
     * @param direccion
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt crear(BigInteger gas_aceptable, String direccion, String email, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            if (email == null || email.isBlank()) {
                ok.setTxt("No se puede poner un valor nulo o vacío. ", extras_array);
            }
            if (direccion == null || direccion.isBlank()) {
                ok.setTxt(ok.getTxt(), "No se puede poner una direccion nula o vacía. ", extras_array);
            }
            if (ok.es == false) { return null; }
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.crear(direccion, email);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas(remoteFunctionCall, gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param direccion
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_crear(String direccion, String email, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.crear(direccion, email);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Crear un nuevo registro
     * @param gas_aceptable
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt crear(BigInteger gas_aceptable, String email, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            if (email == null || email.isBlank()) {
                ok.setTxt("No se puede poner un valor nulo o vacío. ", extras_array);
            }
            if (ok.es == false) { return null; }
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.crear(email);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas(remoteFunctionCall, gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_crear(String email, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.crear(email);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Actualiza un registro
     * @param gas_aceptable
     * @param direccion
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt actualizar(BigInteger gas_aceptable, String direccion, String email, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            if (email == null || email.isBlank()) {
                ok.setTxt("No se puede poner un valor nulo o vacío. ", extras_array);
            }
            if (direccion == null || direccion.isBlank()) {
                ok.setTxt(ok.getTxt(), "No se puede poner una direccion nula o vacía. ", extras_array);
            }
            if (ok.es == false) { return null; }
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.actualizar(direccion, email);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas(remoteFunctionCall, gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param direccion
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_actualizar(String direccion, String email, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.actualizar(direccion, email);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Borrar un registro utilizando la dirección
     * @param gas_aceptable
     * @param direccion
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt borrar_direccion(BigInteger gas_aceptable, String direccion, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.borrar_direccion(direccion);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas(remoteFunctionCall, gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param direccion
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_borrar_direccion(String direccion, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.borrar_direccion(direccion);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Borrar un registro utilizando la dirección
     * @param gas_aceptable
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt borrar_email(BigInteger gas_aceptable, String email, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.borrar_email(email);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas(remoteFunctionCall, gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param email
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_borrar_email(String email, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.borrar_email(email);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Borrar un registro utilizando la dirección
     * @param gas_aceptable
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt borrar(BigInteger gas_aceptable, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.borrar();
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas(remoteFunctionCall, gas_aceptable, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_borrar(oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.borrar();
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
}
