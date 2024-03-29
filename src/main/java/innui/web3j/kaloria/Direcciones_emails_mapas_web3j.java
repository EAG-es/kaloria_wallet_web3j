package innui.web3j.kaloria;

import innui.web3j.web3js;
import innui.bases;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.errores.oks;
import innui.modelos.internacionalizacion.tr;
import innui.web3j.generated.contracts.Direcciones_emails_mapas;
import java.io.File;
import java.math.BigInteger;
import java.util.List;
import java.util.ResourceBundle;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public class Direcciones_emails_mapas_web3j extends bases {
    public static String k_in_ruta;
    static {
        String paquete_tex = Direcciones_emails_mapas_web3j.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_ruta = "in/" + paquete_tex + "/in";
    }
    public static String k_email_no_encontrado = "Email no encontrado. ";
    public static String k_direccion_no_encontrada = "Direccion no encontrada. ";
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
            if (ok.es == false) { return false; }
            RemoteFunctionCall<Boolean> remoteFunctionCall = direcciones_emails_mapa.estar_email(email);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return false; }
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
        Boolean retorno = false;
        try {
            if (ok.es == false) { return false; }
            RemoteFunctionCall<Boolean> remoteFunctionCall = direcciones_emails_mapa.estar_direccion(direccion);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return false; }
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
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);        
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<String> remoteFunctionCall = direcciones_emails_mapa.leer_direccion(email);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { 
                ok.id = k_direccion_no_encontrada;
                ok.setTxt(tr.in(in, k_direccion_no_encontrada), extras_array);
                return null;
            }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (String) types_list.get(0).getValue();
            }
            if (retorno.equals("0x0000000000000000000000000000000000000000")) {
                ok.id = k_direccion_no_encontrada;
                ok.setTxt(tr.in(in, k_direccion_no_encontrada), extras_array);
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
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);        
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<String> remoteFunctionCall = direcciones_emails_mapa.leer_email(direccion);
            EthCall ethCall = web3j.llamar_funcion_sin_gas(remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { 
                ok.id = k_email_no_encontrado;
                ok.setTxt(tr.in(in, k_email_no_encontrado), extras_array);
                return null;
            }
            List<Type> types_list = remoteFunctionCall.decodeFunctionResponse(ethCall.getValue());
            if (types_list != null) {
                retorno = (String) types_list.get(0).getValue();
            }
            if (retorno.equals("0x0000000000000000000000000000000000000000")) {
                ok.id = k_email_no_encontrado;
                ok.setTxt(tr.in(in, k_email_no_encontrado), extras_array);
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
     * @param coste
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt auto_crear(BigInteger gas_aceptable, String email, BigInteger coste, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            if (email == null || email.isBlank()) {
                ok.setTxt("No se puede poner un valor nulo o vacío. ", extras_array);
            }
            if (ok.es == false) { return null; }
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.auto_crear(email, coste);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas_y_coin(remoteFunctionCall, gas_aceptable, coste, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Crear un nuevo registro
     * @param gas_aceptable
     * @param email
     * @param coste
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean auto_crear_asincrono(BigInteger gas_aceptable, String email, BigInteger coste, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            if (email == null || email.isBlank()) {
                ok.setTxt("No se puede poner un valor nulo o vacío. ", extras_array);
            }
            if (ok.es == false) { return false; }
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.auto_crear(email, coste);
            web3j.firmar_y_llamar_asincrono_funcion_con_gas_y_coin(remoteFunctionCall, gas_aceptable, coste, null, null, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param email
     * @param coste
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_auto_crear(String email, BigInteger coste, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.auto_crear(email, coste);
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
     * @param coste
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt auto_borrar(BigInteger gas_aceptable, BigInteger coste, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        TransactionReceipt transactionReceipt = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.auto_borrar(coste);
            transactionReceipt = web3j.firmar_y_llamar_funcion_con_gas_y_coin(remoteFunctionCall, gas_aceptable, coste, null, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Borrar un registro utilizando la dirección
     * @param gas_aceptable
     * @param coste
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean auto_borrar_asincrono(BigInteger gas_aceptable, BigInteger coste, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.auto_borrar(coste);
            web3j.firmar_y_llamar_asincrono_funcion_con_gas_y_coin(remoteFunctionCall, gas_aceptable, coste, null, null, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param coste
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_auto_borrar(BigInteger coste, oks ok, Object ... extras_array) throws Exception {
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = direcciones_emails_mapa.auto_borrar(coste);
        String encodedFunction = remoteFunctionCall.encodeFunctionCall();
        return web3j.estimar_gas(encodedFunction, ok, extras_array);
    }
    /**
     * Leer el coste de auto_crear y de auto_borrar
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger leer_coste(oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = direcciones_emails_mapa.leer_coste();
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
