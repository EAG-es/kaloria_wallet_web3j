package innui.web3j;

import innui.bases;
import innui.modelos.concurrencias.Threads;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.configuraciones.Resources;
import innui.modelos.errores.oks;
import innui.modelos.internacionalizacion.tr;
import innui.utiles.bigdecimals.BigDecimals;
import innui.web3j.generated.contracts.I_erc20;
import static innui.web3j.generated.contracts.I_erc20.getOkEvents;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthLog.LogResult;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.EmptyTransactionReceipt;
import org.web3j.utils.Numeric;
import org.web3j.utils.TxHashVerifier;

/**
 *
 * @author emilio
 */
public class web3js extends bases {
    public static interface i_notificaciones_asincronas {
        /**
         * Método llamado por los escuchadores de transacciones por defecto
         * @param transactionReceipt
         * @param datos_mapa
         * @param ok
         * @param extras_array
         * @return 
         * @throws java.lang.Exception 
         */
        public boolean procesar_transaccion_asincrona(TransactionReceipt transactionReceipt
          , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception;
        /**
         * Método llamado por los escuchadores de llamadas (sin gas) por defecto
         * @param ethCall
         * @param datos_mapa
         * @param ok
         * @param extras_array
         * @return
         * @throws Exception 
         */
        public boolean procesar_llamada_asincrono(EthCall ethCall
          , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception;
        /**
         * Método llamado por los escuchadores por defecto en caso de error
         * @param mensaje
         * @param datos_mapa
         * @param ok
         * @param extras_array
         * @return 
         */
        public boolean poner_error_asincrono(Boolean es_transaccion_fallida, String mensaje, Map<String, Object> datos_mapa, oks ok, Object ... extras_array);
    }
    public i_notificaciones_asincronas i_notificacion_asincrona = new i_notificaciones_asincronas() {
        @Override
        public boolean procesar_transaccion_asincrona(TransactionReceipt transactionReceipt
          , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
            return web3js.this.procesar_transaccion_asincrona_por_defecto(transactionReceipt, datos_mapa, ok, extras_array);
        }
        @Override
        public boolean procesar_llamada_asincrono(EthCall ethCall
          , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
            return web3js.this.procesar_llamada_asincrona_por_defecto(ethCall, datos_mapa, ok, extras_array);
        }
        @Override
        public boolean poner_error_asincrono(Boolean es_transaccion_fallida, String mensaje, Map<String, Object> datos_mapa, oks ok, Object ... extras_array) {
            escribir_linea_error(mensaje, ok, extras_array);
            return ok.es;
        }
    };
    public static String k_in_ruta = "in/innui/web3j/in";  //NOI18N
    public static String k_comprobar_y_esperar_recibo_tiempo_excedido = "comprobar_y_esperar_recibo_tiempo_excedido";
    public static Long k_tiempo_durmiendo_milisegundos = 10000L;
    public static Long k_tiempo_maximo_esperando_milisegundos = 30*60*1000L; // 1/2 hora
    public String web3_endpoint_https;      
    public String web3_endpoint_https_nombre;
    public String web3_endpoint_https_simbolo;
    public Integer web3_endpoint_https_decimales;
    public String web3_endpoint_https_gas_limite;
    public String web3_archivo_EAO;
    public String web3_clave_EAO;
    public String web3_direccion_contrato;
    public BigInteger web3_gas_disponible_total;
    public BigInteger web3_gas_aceptable_por_transaccion;
    public Web3j web3j;
    public Credentials credentials;
    public TransactionManager transactionManager;
    public DefaultGasProvider defaultGasProvider;
    public gas_precios gas = new gas_precios();

    public web3js() {
        defaultGasProvider = new DefaultGasProvider();
        gas.gasPrice = defaultGasProvider.getGasPrice();
        gas.gasLimit = defaultGasProvider.getGasLimit();
        gas.gasLimitEIP1559 = defaultGasProvider.getGasLimit();
    }
    
    public web3js(web3js origen) {
        web3_endpoint_https = origen.web3_endpoint_https;
        web3_endpoint_https_nombre = origen.web3_endpoint_https_nombre;
        web3_endpoint_https_simbolo = origen.web3_endpoint_https_simbolo;
        web3_endpoint_https_decimales = origen.web3_endpoint_https_decimales;
        web3_archivo_EAO = origen.web3_archivo_EAO;
        web3_clave_EAO = origen.web3_clave_EAO;
        web3_direccion_contrato = origen.web3_direccion_contrato;
        web3_gas_disponible_total = origen.web3_gas_disponible_total;
        web3_gas_aceptable_por_transaccion = origen.web3_gas_aceptable_por_transaccion;
        web3j = origen.web3j;
        credentials = origen.credentials;
        transactionManager = origen.transactionManager;
        defaultGasProvider = origen.defaultGasProvider;
        gas.gasPrice = origen.gas.gasPrice;
        gas.gasLimit = origen.gas.gasLimit;
        gas = origen.gas;
        i_notificacion_asincrona = origen.i_notificacion_asincrona;
    }
    /**
     * Realiza las operaciones de inicio de la clase
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean iniciar(oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return false; }
            ResourceBundle in;
            File file;
            in = ResourceBundles.getBundle(k_in_ruta);
            file = new File(web3_archivo_EAO);
            String web3_archivo = file.getPath();
            if (file.exists() == false) {
                URL url = Resources.getResource(this.getClass(), web3_archivo, ok, extras_array);
                if (ok.es == false) {
                    ok.setTxt(tr.in(in, "No se ha encontrado el archivo de credenciales de la wallet web3. "));
                    return false;
                } else {
                    file = new File(url.toURI());
                }
            }
            return iniciar(file, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.es;
    }
    /**
     * Realiza las operaciones de inicio de la clase
     * @param wallet_file
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean iniciar(File wallet_file, oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return false; }
            ResourceBundle in;
            in = ResourceBundles.getBundle(k_in_ruta);
            escribir_linea(tr.in(in, "Cargando credenciales... Espere por favor (puede llevar bastante tiempo). "), ok, extras_array);
            if (ok.es == false) { return false; }
            web3j = Web3j.build(new HttpService(web3_endpoint_https));
            credentials = WalletUtils.loadCredentials(web3_clave_EAO, wallet_file);
            escribir_linea(tr.in(in, "Credenciales cargadas. "), ok, extras_array);
            if (ok.es == false) { return false; }
            String blockchain_id = web3j.netVersion().send().getNetVersion();
            Long blockchain_id_long = Long.valueOf(blockchain_id);
//            transactionManager = new RawTransactionManager(web3j, credentials, blockchain_id_long);
            int tiempo_de_espera = 15 * 1000;
            int intentos = 8; // DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
            transactionManager = new RawTransactionManager(web3j, credentials, blockchain_id_long
              , intentos, tiempo_de_espera);
            gas.gasPrice = estimar_coste_gas(BigInteger.ONE, ok);
            if (ok.es == false) { return false; }
            if (web3_endpoint_https_gas_limite != null
             && web3_endpoint_https_gas_limite.trim().isEmpty() == false
             && web3_endpoint_https_gas_limite.trim().equals("0") == false) {
                gas.gasLimitEIP1559 = gas.gasPrice.multiply(BigInteger.TWO);
                BigInteger bigInteger = new BigInteger(web3_endpoint_https_gas_limite);
                if (bigInteger.compareTo(gas.gasLimitEIP1559) > 0) {
                    gas.gasLimitEIP1559 = bigInteger;
                }
            }
        } catch (Exception e) {
            web3j = null;
            credentials = null;
            transactionManager = null;
            ok.setTxt(e);            
        }
        return ok.es;
    }
    /**
     * Obtiene el número nonce necesario como parámetro de las llamadas y las transacciones con gas_precios
     * @param direccion
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger obtener_nonce(String direccion, oks ok, Object ... extras_array) throws Exception {
        BigInteger nonce = null;
        try {
            if (ok.es == false) { return null; }
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(direccion
              , DefaultBlockParameterName.LATEST).sendAsync().get();
            nonce = ethGetTransactionCount.getTransactionCount();
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return nonce;
    }
    /**
     * Firma una RawTransaction con las credenciales
     * @param rawTransaction
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String firmar(RawTransaction rawTransaction, oks ok, Object ... extras_array) throws Exception {
        byte[] signedMessage = null;
        try {
            String blockchain_id = web3j.netVersion().send().getNetVersion();
            Long blockchain_id_long = Long.valueOf(blockchain_id);
            if (blockchain_id_long >= 0) {
                signedMessage = TransactionEncoder.signMessage(rawTransaction, blockchain_id_long, credentials);
            } else {
                signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            }
            return Numeric.toHexString(signedMessage);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return null;
    }    
    /**
     * Llama a una función remota que consume gas_precios (firmándola) utilizando transacciones raw
     * @param remoteFunctionCall
     * @param resultado
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt _firmar_y_llamar_funcion_con_gas(RemoteFunctionCall<?> remoteFunctionCall, StringBuilder resultado
      , oks ok, Object ... extras_array) throws Exception {
        String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
        return _firmar_y_llamar_funcion_con_gas(encodedFunction_tex, resultado, ok, extras_array);
    }
    /**
     * Llama a una función remota que consume gas_precios (firmándola) utilizando transacciones raw
     * @param remoteFunctionCall
     * @param resultado
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> _firmar_y_llamar_asincrono_funcion_con_gas(RemoteFunctionCall<?> remoteFunctionCall, StringBuilder resultado
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
        return _firmar_y_llamar_asincrono_funcion_con_gas(encodedFunction_tex, resultado, datos_mapa, ok, extras_array);
    }
    /**
     * Llama a una función remota que consume gas_precios (firmándola) utilizando transacciones raw
     * @param encodedFunction_tex
     * @param resultado
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> _firmar_y_llamar_asincrono_funcion_con_gas(String encodedFunction_tex, StringBuilder resultado
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthSendTransaction> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            BigInteger nonce;
            RawTransaction rawTransaction;
            String transaccion_firmada;
            nonce = obtener_nonce(credentials.getAddress(), ok);
            if (ok.es == false) { return null; }
            rawTransaction = RawTransaction.createTransaction(nonce
            , gas.gasPrice, gas.gasLimit, web3_direccion_contrato, encodedFunction_tex);
            transaccion_firmada = firmar(rawTransaction, ok);
            if (ok.es == false) { return null; }
            completableFuture = web3j.ethSendRawTransaction(transaccion_firmada).sendAsync();
            poner_escuchador_por_defecto_con_gas(completableFuture, datos_mapa, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Llama a una función remota que consume gas_precios (firmándola) utilizando transacciones raw
     * @param encodedFunction_tex
     * @param resultado
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt _firmar_y_llamar_funcion_con_gas(String encodedFunction_tex, StringBuilder resultado
      , oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt transactionReceipt = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            if (ok.es == false) { return null; }
            BigInteger nonce;
            RawTransaction rawTransaction;
            String transaccion_firmada;
            EthSendTransaction ethSendTransaction = null;
            String txHashLocal = null;
            String txHashRemoto = null;
            nonce = obtener_nonce(credentials.getAddress(), ok);
            if (ok.es == false) { return null; }
            rawTransaction = RawTransaction.createTransaction(nonce
            , gas.gasPrice, gas.gasLimit, web3_direccion_contrato, encodedFunction_tex);
            transaccion_firmada = firmar(rawTransaction, ok);
            if (ok.es == false) { return null; }
            ethSendTransaction = web3j.ethSendRawTransaction(transaccion_firmada).send();
            if (ethSendTransaction.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + rawTransaction.getTo() + " " 
                        + tr.in(in, "con error: ")
                        + ethSendTransaction.getError().getMessage(), ok, extras_array);
            } else {
                txHashLocal = Hash.sha3(transaccion_firmada);
                txHashRemoto = ethSendTransaction.getTransactionHash();
                TxHashVerifier txHashVerifier = new TxHashVerifier();
                if (!txHashVerifier.verify(txHashLocal, txHashRemoto)) {
                    ok.setTxt(tr.in(in, "El hash de la transacción discrepa del hash local. "));
                }                
            }
            if (ok.es == false) { return null; }
            if (resultado != null) {
                resultado.replace(0, resultado.length(), ethSendTransaction.getResult());
            }
            transactionReceipt = obtener_recibo_de_transaccion(txHashRemoto, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Llama a una función remota que consume gas_precios y envía blockchain-coins (firmándola) utilizando transacciones raw
     * @param encodedFunction_tex
     * @param valor Unidades de coin que enviar
     * @param resultado
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> _firmar_y_llamar_asincrono_funcion_con_gas_y_coin(String encodedFunction_tex
      , BigInteger valor, StringBuilder resultado
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthSendTransaction> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            BigInteger nonce;
            RawTransaction rawTransaction;
            String transaccion_firmada;
            nonce = obtener_nonce(credentials.getAddress(), ok);
            if (ok.es == false) { return null; }
            rawTransaction = RawTransaction.createTransaction(nonce
            , gas.gasPrice, gas.gasLimit, web3_direccion_contrato, valor, encodedFunction_tex);
            transaccion_firmada = firmar(rawTransaction, ok);
            if (ok.es == false) { return null; }
            completableFuture = web3j.ethSendRawTransaction(transaccion_firmada).sendAsync();
            poner_escuchador_por_defecto_con_gas(completableFuture, datos_mapa, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Llama a una función remota que consume gas_precios y envía blockchain-coins (firmándola) utilizando transacciones raw
     * @param encodedFunction_tex
     * @param valor Unidades de coin que enviar
     * @param resultado
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt _firmar_y_llamar_funcion_con_gas_y_coin(String encodedFunction_tex
      , BigInteger valor, StringBuilder resultado
      , oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt transactionReceipt = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            if (ok.es == false) { return null; }
            BigInteger nonce;
            RawTransaction rawTransaction;
            String transaccion_firmada;
            EthSendTransaction ethSendTransaction = null;
            String txHashLocal = null;
            String txHashRemoto = null;
            nonce = obtener_nonce(credentials.getAddress(), ok);
            if (ok.es == false) { return null; }
            rawTransaction = RawTransaction.createTransaction(nonce
            , gas.gasPrice, gas.gasLimit, web3_direccion_contrato, valor, encodedFunction_tex);
            transaccion_firmada = firmar(rawTransaction, ok);
            if (ok.es == false) { return null; }
            ethSendTransaction = web3j.ethSendRawTransaction(transaccion_firmada).send();
            if (ethSendTransaction.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + rawTransaction.getTo() + " " 
                        + tr.in(in, "con error: ")
                        + ethSendTransaction.getError().getMessage(), ok, extras_array);
            } else {
                txHashLocal = Hash.sha3(transaccion_firmada);
                txHashRemoto = ethSendTransaction.getTransactionHash();
                TxHashVerifier txHashVerifier = new TxHashVerifier();
                if (!txHashVerifier.verify(txHashLocal, txHashRemoto)) {
                    ok.setTxt(tr.in(in, "El hash de la transacción discrepa del hash local. "));
                }
            }
            if (ok.es == false) { return null; }
            if (resultado != null) {
                resultado.replace(0, resultado.length(), ethSendTransaction.getResult());
            }
            transactionReceipt = obtener_recibo_de_transaccion(txHashRemoto, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Llama a una función remota que consume gas_precios (firmándola) utilizando transacciones raw
     * @param remoteFunctionCall
     * @param valor Unidades de coin que enviar
     * @param resultado
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> _firmar_y_llamar_asincrono_funcion_con_gas_y_coin(RemoteFunctionCall<?> remoteFunctionCall
      , BigInteger valor, StringBuilder resultado
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
        return web3js.this._firmar_y_llamar_asincrono_funcion_con_gas_y_coin(encodedFunction_tex
          , valor, resultado, datos_mapa, ok, extras_array);
    }
    /**
     * Llama a una función remota que consume gas_precios (firmándola) utilizando transacciones raw
     * @param remoteFunctionCall
     * @param valor Unidades de coin que enviar
     * @param resultado
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt _firmar_y_llamar_funcion_con_gas_y_coin(RemoteFunctionCall<?> remoteFunctionCall
      , BigInteger valor, StringBuilder resultado
      , oks ok, Object ... extras_array) throws Exception {
        String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
        return _firmar_y_llamar_funcion_con_gas_y_coin(encodedFunction_tex, valor, resultado, ok, extras_array);
    }
    /**
     * Obtiene el recibo de una transaccion, o genera uno vacío.
     * @param transaccion_hash
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt obtener_recibo_de_transaccion(String transaccion_hash, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt transactionReceipt = null;
        EthGetTransactionReceipt ethGetTransactionReceipt = null;
        try {
            if (ok.es == false) { return null; }
            ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(transaccion_hash).send();
            transactionReceipt = ethGetTransactionReceipt.getResult();
            if (transactionReceipt == null) {
                Optional<TransactionReceipt> optional = ethGetTransactionReceipt.getTransactionReceipt();
                if (optional.isPresent()) {
                    transactionReceipt = optional.get();
                } else {
                    transactionReceipt = new EmptyTransactionReceipt(transaccion_hash);
                }
            }
        } catch (Exception e) {
            throw e; 
        }
        return transactionReceipt;
    }
    /**
     * Llama a una función remota que no consume gas_precios utilizando createEthCallTransaction
     * @param remoteFunctionCall
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthCall> llamar_funcion_asincrono_sin_gas(RemoteFunctionCall<?> remoteFunctionCall
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
        return llamar_funcion_asincrono_sin_gas(encodedFunction_tex, datos_mapa, ok, extras_array);
    }
    /**
     * Llama a una función remota que no consume gas_precios utilizando createEthCallTransaction
     * @param remoteFunctionCall
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public EthCall llamar_funcion_sin_gas(RemoteFunctionCall<?> remoteFunctionCall, oks ok, Object ... extras_array) throws Exception {
        String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
        return llamar_funcion_sin_gas(encodedFunction_tex,ok, extras_array);
    }
    /**
     * Llama a una función remota que no consume gas_precios utilizando createEthCallTransaction
     * @param encodedFunction_tex
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthCall> llamar_funcion_asincrono_sin_gas(String encodedFunction_tex
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthCall> completableFuture = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            if (ok.es == false) { return null; }
            Transaction transaction = Transaction.createEthCallTransaction(credentials.getAddress()
              , web3_direccion_contrato, encodedFunction_tex);            
            completableFuture = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync();
            poner_escuchador_por_defecto_sin_gas(completableFuture, datos_mapa, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }    
    /**
     * Llama a una función remota que no consume gas_precios utilizando createEthCallTransaction
     * @param encodedFunction_tex
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public EthCall llamar_funcion_sin_gas(String encodedFunction_tex, oks ok, Object ... extras_array) throws Exception {
        EthCall ethCall = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            if (ok.es == false) { return null; }
            Transaction transaction = Transaction.createEthCallTransaction(credentials.getAddress()
              , web3_direccion_contrato, encodedFunction_tex);            
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            if (ethCall.isReverted()) {                
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + transaction.getTo() + " " 
                        + tr.in(in, "revertida: ")
                        + ethCall.getRevertReason(), ok, extras_array);
            }
            if (ok.es == false) { return null; }
            if (ethCall.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + transaction.getTo() + " " 
                        + tr.in(in, "con error: ")
                        + ethCall.getError().getMessage(), ok, extras_array);
            }
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ethCall;
    }
    /**
     * Valida el gas aceptable según el que se estima que es necesario para pagar una transacción con gas.Si el gas_aceptable es menor de 0 no se compara con el gas estimado, ni con el gas disponible.
     * Si el gas disponible es menor de 0 no se utiliza para las comprobaciones. 
     * @param gas_aceptable 
     * @param remoteFunctionCall
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean estimar_gas(BigInteger gas_aceptable, RemoteFunctionCall<?> remoteFunctionCall, oks ok, Object ... extras_array) throws Exception {
        ResourceBundle in;
        try {
            if (ok.es == false) { return false; }
            if (gas_aceptable.compareTo(BigInteger.ZERO) >= 0) {
                String encodedFunction_tex = remoteFunctionCall.encodeFunctionCall();
                BigInteger gas_estimado = estimar_gas(encodedFunction_tex, ok, extras_array);
                if (ok.es == false) { return false; }
                if (gas_estimado.compareTo(gas_aceptable) > 0) {
                    in = ResourceBundles.getBundle(k_in_ruta);
                    ok.setTxt(tr.in(in, "El coste estimado de gas es mayor que el máximo aceptable configurado: ") 
                      + gas_estimado + " > " + gas_aceptable.toString(), extras_array);
                } else if (web3_gas_disponible_total != null
                        && gas_estimado.compareTo(web3_gas_disponible_total) > 0) {
                    if (web3_gas_disponible_total.compareTo(BigInteger.ZERO) >= 0) {
                        in = ResourceBundles.getBundle(k_in_ruta);
                        ok.setTxt(tr.in(in, "El coste estimado de gas es mayor que el gas disponible configurado: ") 
                          + gas_estimado.toString() + " > "
                          + web3_gas_disponible_total.toString(), extras_array);
                    }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estima el gas_precios necesario para pagar una transacción con gas_precios.
     * @param encodedFunction
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_gas(String encodedFunction, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        BigInteger retorno = null;
        ResourceBundle in;
        try {
            EthEstimateGas ethEstimateGas = null;
            Transaction transaction = Transaction.createEthCallTransaction(credentials.getAddress()
              , web3_direccion_contrato, encodedFunction);
            ethEstimateGas = web3j.ethEstimateGas(transaction).sendAsync().get();
            if (ethEstimateGas.hasError()) {
                in = ResourceBundles.getBundle(k_in_ruta);
                ok.setTxt(tr.in(in, "Error estimando el gas (error en la función). "), ethEstimateGas.getError().getMessage());
                return null;
            } else {
                retorno = ethEstimateGas.getAmountUsed();
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Resta el gas_precios usado (Si web3_gas_disponible_total es menor de 0 no se resta.)
     * @param gas_usado 
     * @param ok
     * @param extras_array
     * @return El gas_precios restante
     * @throws Exception 
     */
    public BigInteger restar_gas(BigInteger gas_usado, oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return null; }
            if (web3_gas_disponible_total != null
             && web3_gas_disponible_total.compareTo(BigInteger.ZERO) >= 0) {
                web3_gas_disponible_total = web3_gas_disponible_total.subtract(gas_usado);
            }
        } catch (Exception e) {
            throw e;
        }
        return web3_gas_disponible_total;
    }
    /**
     * Llama a la función con gasto de gas_precios de un contrato inteligente
     * @param remoteFunctionCall
     * @param gas_aceptable
     * @param resultado
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> firmar_y_llamar_asincrono_funcion_con_gas(RemoteFunctionCall<?> remoteFunctionCall
      , BigInteger gas_aceptable, StringBuilder resultado
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthSendTransaction> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            estimar_gas(gas_aceptable, remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            completableFuture = web3js.this._firmar_y_llamar_asincrono_funcion_con_gas(remoteFunctionCall
              , resultado, datos_mapa, ok); // Forma con más código
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Llama a la función con gasto de gas_precios de un contrato inteligente
     * @param remoteFunctionCall
     * @param gas_aceptable
     * @param resultado
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public TransactionReceipt firmar_y_llamar_funcion_con_gas(RemoteFunctionCall<?> remoteFunctionCall
      , BigInteger gas_aceptable, StringBuilder resultado, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt transactionReceipt = null;
        try {
            if (ok.es == false) { return null; }
            estimar_gas(gas_aceptable, remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            transactionReceipt = _firmar_y_llamar_funcion_con_gas(remoteFunctionCall, resultado, ok); // Forma con más código
            if (ok.es == false) { return null; }
            transactionReceipt = comprobar_y_esperar_recibo(transactionReceipt
                  , k_tiempo_maximo_esperando_milisegundos, ok, extras_array);
            if (ser_recibo_vacio(transactionReceipt, ok) == false) {
                if (ok.es == false) { return null; }
                restar_gas(transactionReceipt.getGasUsed(), ok);
            }
            if (ok.es == false) { return null; }
            poner_ultimo_precio_gas(transactionReceipt, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Llama a la función con gasto de gas_precios de un contrato inteligente
     * @param remoteFunctionCall
     * @param gas_aceptable
     * @param valor
     * @param resultado
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> firmar_y_llamar_asincrono_funcion_con_gas_y_coin(RemoteFunctionCall<?> remoteFunctionCall
      , BigInteger gas_aceptable, BigInteger valor, StringBuilder resultado
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthSendTransaction> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            estimar_gas(gas_aceptable, remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            completableFuture = _firmar_y_llamar_asincrono_funcion_con_gas_y_coin(remoteFunctionCall
              , valor, resultado, datos_mapa, ok); // Forma con más código
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Llama a la función con gasto de gas_precios de un contrato inteligente
     * @param remoteFunctionCall
     * @param gas_aceptable
     * @param valor
     * @param resultado
     * @param ok
     * @param extras_array
     * @return El hash de la trasaccion
     * @throws Exception 
     */
    public TransactionReceipt firmar_y_llamar_funcion_con_gas_y_coin(RemoteFunctionCall<?> remoteFunctionCall
      , BigInteger gas_aceptable, BigInteger valor, StringBuilder resultado, oks ok, Object ... extras_array) throws Exception {
        TransactionReceipt transactionReceipt = null;
        try {
            if (ok.es == false) { return null; }
            estimar_gas(gas_aceptable, remoteFunctionCall, ok, extras_array);
            if (ok.es == false) { return null; }
            transactionReceipt = _firmar_y_llamar_funcion_con_gas_y_coin(remoteFunctionCall, valor, resultado, ok); // Forma con más código
            if (ok.es == false) { return null; }
            transactionReceipt = comprobar_y_esperar_recibo(transactionReceipt
                  , k_tiempo_maximo_esperando_milisegundos, ok, extras_array);
            if (ser_recibo_vacio(transactionReceipt, ok) == false) {
                if (ok.es == false) { return null; }
                restar_gas(transactionReceipt.getGasUsed(), ok);
            }
            if (ok.es == false) { return null; }
            poner_ultimo_precio_gas(transactionReceipt, ok, extras_array);
            if (ok.es == false) { return null; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Establece un comportamiento por defecto para las transacciones
     * @param completable
     * @param datos_a_guardar_mapa Datos que guardar de la llamada asíncrona.
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> poner_escuchador_por_defecto_con_gas(CompletableFuture<EthSendTransaction> completable
      , Map<String, Object> datos_a_guardar_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthSendTransaction> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            completableFuture = completable.handleAsync(new BiFunction<EthSendTransaction, Throwable, EthSendTransaction>() {
                public Map<String, Object> datos_mapa;
                // Constructor anónimo
                {
                    if (datos_a_guardar_mapa != null) {
                        this.datos_mapa = new HashMap<>(datos_a_guardar_mapa);
                    }
                }

                @Override
                public EthSendTransaction apply(EthSendTransaction ethSendTransaction, Throwable throwable) {
                    oks ok = new oks();
                    try {
                        if (ethSendTransaction != null) {
                            String transactionHash = ethSendTransaction.getTransactionHash();
                            TransactionReceipt transactionReceipt = obtener_recibo_de_transaccion(transactionHash, ok, extras_array);
                            transactionReceipt = comprobar_y_esperar_recibo(transactionReceipt
                              , k_tiempo_maximo_esperando_milisegundos, ok, extras_array);
                            if (ok.es == false) { 
                                i_notificacion_asincrona.poner_error_asincrono(false, ok.getTxt(), datos_mapa, ok.iniciar());
                            }
                            i_notificacion_asincrona.procesar_transaccion_asincrona(transactionReceipt, datos_mapa, ok, extras_array);
                            if (ok.es == false) {
                                i_notificacion_asincrona.poner_error_asincrono(false, ok.getTxt(), datos_mapa, ok.iniciar());
                            }
                        } else if (throwable != null) {
                            Exception exception = new Exception(throwable);
                            ok.setTxt(exception);
                            try {
                                i_notificacion_asincrona.poner_error_asincrono(true, ok.getTxt(), datos_mapa, ok.iniciar());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (Exception e) {
                        ok.setTxt(e);
                        try {
                            i_notificacion_asincrona.poner_error_asincrono((throwable != null), ok.getTxt(), datos_mapa, ok.iniciar());
                        } catch (Exception es) {
                            throw new RuntimeException(e);
                        }
                    }
                    return ethSendTransaction;
                }
            });
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Establece un comportamiento por defecto para las llamadas
     * @param completable
     * @param datos_a_guardar_mapa Datos que guardar de la llamada asíncrona.
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthCall> poner_escuchador_por_defecto_sin_gas(CompletableFuture<EthCall> completable
      , Map<String, Object> datos_a_guardar_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthCall> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            completableFuture = completable.handleAsync(new BiFunction<EthCall, Throwable, EthCall>() {
                public Map<String, Object> datos_mapa;
                // Constructor anónimo
                {
                    this.datos_mapa = new HashMap<>(datos_a_guardar_mapa);
                }

                @Override
                public EthCall apply(EthCall ethCall, Throwable throwable) {
                    oks ok = new oks();
                    try {
                        if (ethCall != null) {
                            i_notificacion_asincrona.procesar_llamada_asincrono(ethCall, datos_mapa, ok);
                            if (ok.es == false) {
                                i_notificacion_asincrona.poner_error_asincrono(false, ok.getTxt(), datos_mapa, ok.iniciar());
                            }

                        } else if (throwable != null) {
                            Exception exception = new Exception(throwable);
                            ok.setTxt(exception);
                            try {
                                i_notificacion_asincrona.poner_error_asincrono(true, ok.getTxt(), datos_mapa, ok.iniciar());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (Exception e) {
                        ok.setTxt(e);
                        try {
                            i_notificacion_asincrona.poner_error_asincrono(throwable != null, ok.getTxt(), datos_mapa, ok.iniciar());
                        } catch (Exception es) {
                            throw new RuntimeException(e);
                        }
                    }
                    return ethCall;
                }
            });
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Pone un escuchador en lugar del escucahdor por defecto
     * @param completable
     * @param escuchador
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthSendTransaction> poner_escuchador_con_gas(CompletableFuture<EthSendTransaction> completable
      , BiFunction<EthSendTransaction, Throwable, EthSendTransaction> escuchador, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthSendTransaction> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            completableFuture = completable.handleAsync(escuchador);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    /**
     * Pone un escuchador en lugar del escucahdor por defecto
     * @param completable
     * @param escuchador
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<EthCall> poner_escuchador_sin_gas(CompletableFuture<EthCall> completable
      , BiFunction<EthCall, Throwable, EthCall> escuchador, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<EthCall> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            completableFuture = completable.handleAsync(escuchador);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }    
    /**
     * Informa si el recibo de una transaccón es vacío
     * @param transactionReceipt
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean ser_recibo_vacio(TransactionReceipt transactionReceipt, oks ok, Object ... extras_array) throws Exception {
        return (transactionReceipt instanceof EmptyTransactionReceipt);
    }
    /**
     * Comprueba que la transaccion no está vacía y la vuelve a pedir, en caso de que esté vacía.
     * @param transactionReceipt
     * @param milisegundos
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public TransactionReceipt comprobar_y_esperar_recibo(TransactionReceipt transactionReceipt
      , Long milisegundos, oks ok, Object ... extras_array) throws Exception {
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            if (ok.es == false) { return null; }
            if (ser_recibo_vacio(transactionReceipt, ok)) {
                if (ok.es == false) { return null; }
                Date date = new Date();
                Long tiempo_actual = date.getTime();
                Long tiempo_final = tiempo_actual + milisegundos;
                while (true) {
                    if (tiempo_actual >= tiempo_final) {
                        ok.id = k_comprobar_y_esperar_recibo_tiempo_excedido;
                        ok.setTxt(tr.in(in, "No se ha podido recuperar el recibo en el tiempo establecido. "));
                        break;
                    }
                    transactionReceipt = obtener_recibo_de_transaccion(transactionReceipt.getTransactionHash(), ok, extras_array);
                    if (ser_recibo_vacio(transactionReceipt, ok)) {
                        if (ok.es == false) { return null; }
                        Threads.sleep(k_tiempo_durmiendo_milisegundos, ok);
                        if (ok.es == false) { return null; }
                    } else {
                        if (ok.es == false) { return null; }
                        try {
                            // Testear transaccion
                            transactionReceipt.getFrom();
                            break;
                        } catch (Exception e) {}
                        if (transactionReceipt.isStatusOK()) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Pone decimales a un número
     * @param numero
     * @param decimales_num
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public static String poner_decimales_a_numero(BigInteger numero, Integer decimales_num, oks ok, Object ... extras_array) throws Exception {
        String retorno = null;
        try {
            if (ok.es == false) { return null; }
            BigDecimal bigDecimal = new BigDecimal(numero);
            int i = 0;
            while (true) {
                if (i >= decimales_num) {
                    break;
                }
                bigDecimal = BigDecimals.divide_0(bigDecimal, BigDecimal.valueOf(10L), ok, extras_array);
                if (ok.es == false) { return null; }
                i = i + 1;
            }
            retorno = String.format("%,." + decimales_num + "f", bigDecimal);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Avanza el separador decimal multiplicando por 10
     * @param numero
     * @param decimales_num
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public static BigInteger avanzar_separador_decimal(Double numero, Integer decimales_num, oks ok, Object ... extras_array) throws Exception {
        BigInteger retorno = null;
        try {
            if (ok.es == false) { return null; }
            Double multiplicador = 1.0;
            int i = 0;
            while (true) {
                if (i >= decimales_num) {
                    break;
                }
                multiplicador = multiplicador * 10.0;
                i = i + 1;
            }
            numero = numero * multiplicador;
            retorno = BigInteger.valueOf(numero.longValue());
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Retrocede el separador decimal dividiendo por 10
     * @param numero
     * @param decimales_num
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public static Double retroceder_separador_decimal(BigInteger numero, Integer decimales_num, oks ok, Object ... extras_array) throws Exception {
        Double retorno = null;
        try {
            if (ok.es == false) { return null; }
            Double divisor = 1.0;
            int i = 0;
            while (true) {
                if (i >= decimales_num) {
                    break;
                }
                divisor = divisor / 10.0;
                i = i + 1;
            }
            retorno = numero.doubleValue() / divisor;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Extrae todos los logs de la última llamada de un contrato
     * @param contrato_direccion
     * @param bloques_num
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public List<Log> extraer_ultimos_logs(String contrato_direccion, BigInteger bloques_num
      , oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        List<Log> logs_lista =  null;
        try {
            final List<Log> final_logs_lista = new LinkedList<>();
            EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
            BigInteger inicio_busqueda_bigInteger = ethBlockNumber.getBlockNumber();
            // Filter
            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST
              , DefaultBlockParameter.valueOf(inicio_busqueda_bigInteger.subtract(bloques_num))
              , contrato_direccion);
            Disposable disposable = web3j.ethLogFlowable(filter).subscribe(log -> {
                try {
                    final_logs_lista.add(log);
                } catch (Exception e) {
                    ok.setTxt(e); 
                }
            });
            if (ok.es) {
                logs_lista = final_logs_lista;
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return logs_lista;
    }
    /**
     * Selecciona los logs de un evento en concreto
     * @param evento_decode Se puede conocer el valor usando: EventEncoder.encode(XX_EVENT)
     * @param logs_lista
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public List<Log> extraer_logs(String evento_decode
      , List<Log> logs_lista
      , oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        List<Log> retorno_lista =  null;
        try {
            retorno_lista = new LinkedList<>(); 
            for (Log log: logs_lista) {
                if (log.getTopics().get(0).equals(evento_decode)) {
                    retorno_lista.add(log);
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno_lista;
    }
    /**
     * Pone el último precio de gas_precios a partir del recibo de la transaccion
     * @param transactionReceipt
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean poner_ultimo_precio_gas(TransactionReceipt transactionReceipt, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            String gas_tex = transactionReceipt.getEffectiveGasPrice();
            if (gas_tex != null) {
                gas_tex = gas_tex.trim().substring(2); // Quitar 0x
                Long gas_largo = Long.parseLong(gas_tex, 16);
                BigInteger bigInteger = BigInteger.valueOf(gas_largo);
//                EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
//                BigInteger actual_gasPrice = ethGasPrice.getGasPrice();
                gas.ultimo_precio_gas_gwei = bigInteger;
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estima el coste del necesario para enviar una cantidad a una dirección
     * @param gas_a_valorar
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_coste_gas(BigInteger gas_a_valorar, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        BigInteger retorno = null;
        try {
            if (gas.ultimo_precio_gas_gwei == null 
             || gas.ultimo_precio_gas_gwei.compareTo(BigInteger.ZERO) == 0) {
                EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
                BigInteger actual_gasPrice = ethGasPrice.getGasPrice();
                gas.ultimo_precio_gas_gwei = actual_gasPrice;
            }
            retorno = gas_a_valorar.multiply(gas.ultimo_precio_gas_gwei);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Busca los logs de una operación de puente por su identificador
     * @param id
     * @param contrato_direccion
     * @param evento_encoded
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public Log buscar_logs_de_puente_por_id(BigInteger id, String contrato_direccion
      , String evento_encoded, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        Log retorno = null;
        try {
            EthFilter ethFilter;
            EthBlockNumber ethBlockNumber;
            BigInteger inicio_busqueda_bigInteger;
            DefaultBlockParameter fin_defaultBlockParameter; 
            DefaultBlockParameter inicio_defaultBlockParameter; 
            BigInteger cien = BigInteger.valueOf(100L);
            ethBlockNumber = web3j.ethBlockNumber().send();
            inicio_busqueda_bigInteger = ethBlockNumber.getBlockNumber();
            fin_defaultBlockParameter = DefaultBlockParameter.valueOf(inicio_busqueda_bigInteger);
            boolean es_salir = false;
            int comparacion_num;
            BigInteger resultado;
            EthLog ethLog;
            List<LogResult> logResult_list;
            LinkedList<LogResult> logResult_linkedlist;
            Iterator<LogResult> desdending_iterator;
            LogResult<?> logResult;
            Log log;
            while (true) {
                if (inicio_busqueda_bigInteger.compareTo(BigInteger.ZERO) <= 0) {
                    ok.setTxt(tr.in(in, "No se ha encontrado el identificador buscado de operación de puente. "));
                    break;
                }
                if (inicio_busqueda_bigInteger.compareTo(cien) < 0) {
                    inicio_busqueda_bigInteger = BigInteger.ZERO;
                } else {
                    inicio_busqueda_bigInteger = inicio_busqueda_bigInteger.subtract(cien);
                }
                inicio_defaultBlockParameter = DefaultBlockParameter.valueOf(inicio_busqueda_bigInteger);
                ethFilter = new EthFilter(inicio_defaultBlockParameter, fin_defaultBlockParameter, contrato_direccion); 
                ethFilter.addSingleTopic(evento_encoded);
                ethLog = web3j.ethGetLogs(ethFilter).send();
                logResult_list = ethLog.getLogs();
                logResult_linkedlist = new LinkedList<>(); // Vacio la lista anterior
                logResult_linkedlist.addAll(logResult_list);
                desdending_iterator = logResult_linkedlist.descendingIterator();
                while (true) {
                    if (desdending_iterator.hasNext() == false) {
                        break;
                    }
                    logResult = desdending_iterator.next();
                    log = (Log) logResult.get();
                    resultado = ((Uint) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(3), new TypeReference<Uint>() {})).getValue();
                    comparacion_num = resultado.compareTo(id);
                    if (comparacion_num == 0) {
                        retorno = log;
                        es_salir = true;
                        break;
                    } else if (comparacion_num < 0) {
                        ok.setTxt(tr.in(in, "No se ha encontrado el identificador buscado de operación de puente. "));
                        es_salir = true;
                        break;
                    }
                }
                if (es_salir) {
                    break;
                } else {
                    fin_defaultBlockParameter = inicio_defaultBlockParameter;
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Método llamado por los escuchadores de transacciones por defecto
     * @param transactionReceipt
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return 
     * @throws java.lang.Exception 
     */
    public boolean procesar_transaccion_asincrona_por_defecto(TransactionReceipt transactionReceipt
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            if (ser_recibo_vacio(transactionReceipt, ok) == false) {
                if (ok.es == false) { return false; }
                restar_gas(transactionReceipt.getGasUsed(), ok);
            }
            if (ok.es == false) { return false; }
            try {
                List<Log> logs_lista = transactionReceipt.getLogs();
                if (logs_lista != null && logs_lista.isEmpty() == false) {
                    List<I_erc20.OkEventResponse> oks_lista = getOkEvents(transactionReceipt);
                    if (oks_lista != null) {
                        for (I_erc20.OkEventResponse okEventResponse: oks_lista) {
                            if (okEventResponse.es == false) {
                                ok.es = okEventResponse.es;
                                ok.setTxt(okEventResponse.mensaje);
                                break;
                            }
                        }
                    }
                }
                poner_ultimo_precio_gas(transactionReceipt, ok, extras_array);
                if (ok.es == false) { return false; }
            } catch (Exception e_ignorada) {
                // Si no se pueden leer los logs, se ignoran
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Método llamado por los escuchadores de llamadas (sin gas) por defecto
     * @param ethCall
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean procesar_llamada_asincrona_por_defecto(EthCall ethCall
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            oks ok_extra = new oks();
            if (ethCall.isReverted()) {                
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + ethCall.getId()
                        + tr.in(in, "revertida: ")
                        + ethCall.getRevertReason(), ok, extras_array);
                i_notificacion_asincrona.poner_error_asincrono(true, ok.getTxt(), datos_mapa, ok_extra);
            }
            if (ok.es == false) { return false; }
            if (ethCall.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + ethCall.getId()
                        + tr.in(in, "con error: ")
                        + ethCall.getError().getMessage(), ok, extras_array);
                i_notificacion_asincrona.poner_error_asincrono(true, ok.getTxt(), datos_mapa, ok_extra);
            }
            if (ok_extra.es == false) {
                ok.setTxt(ok.getTxt(), ok_extra.getTxt(), extras_array);
            }
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }

}
