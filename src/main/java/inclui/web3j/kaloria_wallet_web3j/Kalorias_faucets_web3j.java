package inclui.web3j.kaloria_wallet_web3j;

import inclui.web3j.web3js;
import static inclui.web3j.web3js.k_tiempo_maximo_esperando_milisegundos;
import innui.bases;
import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.Kalorias_faucets;
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
public class Kalorias_faucets_web3j extends bases {
    public static String k_in_ruta = "in/inclui/web3j/kaloria_wallet_web3j/in";  //NOI18N
    public web3js web3j;
    public Kalorias_faucets kaloria_faucet;

    /**
     * Carga los datos generales que utilizar con las llamadas a las funciones del contrato Hola_mundos
     * @param web3_direccion_contrato
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean cargar_contrato(String web3_direccion_contrato, oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return false; }
            kaloria_faucet = Kalorias_faucets.load(web3_direccion_contrato, web3j.web3j, web3j.transactionManager, web3j.defaultGasProvider);
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
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt pedir_regalo(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.pedir_regalo(direccion, cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para pedir_regalo una cantidad a una dirección
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_pedir_regalo(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.pedir_regalo(direccion, cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Pedir prestada una cantidad (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt pedir_prestamo(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.pedir_prestamo(direccion, cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para pedir_regalo una cantidad a una dirección
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_pedir_prestamo(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.pedir_prestamo(direccion, cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Regala una cantidad (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt pedir_regalo(BigInteger gas_aceptable, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.pedir_regalo(cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para pedir_regalo una cantidad a una dirección
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_pedir_regalo(BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.pedir_regalo(cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Pedir prestada una cantidad (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt pedir_prestamo(BigInteger gas_aceptable, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.pedir_prestamo(cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para pedir_regalo una cantidad a una dirección
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_pedir_prestamo(BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.pedir_prestamo(cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Devolver una cantidad prestada (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt devolver_prestamo(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.devolver_prestamo(direccion, cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para devolver_regalo una cantidad a una dirección
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_devolver_prestamo(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.devolver_prestamo(direccion, cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Devolver una cantidad prestada (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt devolver_prestamo(BigInteger gas_aceptable, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.devolver_prestamo(cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para devolver_regalo una cantidad a una dirección
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_devolver_prestamo(BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.devolver_prestamo(cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Obtener información del estado
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger leer_prestamo_puntos_interes_a_retener(oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = kaloria_faucet.leer_prestamo_puntos_interes_a_retener();
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
    /**
     * Obtener información del estado
     * @param direccion
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger leer_cantidad_regalo(String direccion, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = kaloria_faucet.leer_cantidad_regalo(direccion);
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
    /**
     * Obtener información del estado
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger leer_cantidad_regalo(oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = kaloria_faucet.leer_cantidad_regalo();
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
    /**
     * Obtener información del estado
     * @param direccion
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger leer_cantidad_prestamo(String direccion, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = kaloria_faucet.leer_cantidad_prestamo(direccion);
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
    /**
     * Obtener información del estado
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public BigInteger leer_cantidad_prestamo(oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            RemoteFunctionCall<BigInteger> remoteFunctionCall = kaloria_faucet.leer_cantidad_prestamo();
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
    /**
     * Devolver una cantidad prestada (sin decimales) a una dirección, aceptando un gasto de gas
     * @param gas_aceptable
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt poner_para_regalo(BigInteger gas_aceptable, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt retorno = null;
        try {
            TransactionReceipt transactionReceipt 
               = web3j.firmar_y_llamar_funcion_con_gas(kaloria_faucet.poner_para_regalo(cantidad), gas_aceptable, null, ok, extras_array);
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
     * Estima el gas necesario para devolver_regalo una cantidad a una dirección
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas_poner_para_regalo(BigInteger cantidad, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = kaloria_faucet.poner_para_regalo(cantidad);
            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
            retorno = web3j.estimar_gas(encodedFunction, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
}
