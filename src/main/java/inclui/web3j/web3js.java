package inclui.web3j;

import innui.bases;
import innui.modelos.concurrencias.Threads;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.configuraciones.Resources;
import innui.modelos.errores.oks;
import innui.modelos.internacionalizacion.tr;
import innui.utiles.bigdecimals.BigDecimals;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
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
    public static String k_in_ruta = "in/inclui/web3j/in";  //NOI18N
    public static String k_comprobar_y_esperar_recibo_tiempo_excedido = "comprobar_y_esperar_recibo_tiempo_excedido";
    public static Long k_tiempo_durmiendo_milisegundos = 10000L;
    public static Long k_tiempo_maximo_esperando_milisegundos = 30*60*1000L; // 1/2 hora
    public String web3_endpoint_https;      
    public String web3_endpoint_https_nombre;
    public String web3_endpoint_https_simbolo;
    public Integer web3_endpoint_https_decimales;
    public String web3_archivo_EAO;
    public String web3_clave_EAO;
    public String web3_direccion_contrato;
    public BigInteger web3_gas_disponible_total;
    public BigInteger web3_gas_aceptable_por_transaccion;
    public Web3j web3j;
    public Credentials credentials;
    public TransactionManager transactionManager;
    public DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
    public BigInteger gasPrice = defaultGasProvider.getGasPrice();
    public BigInteger gasLimit = defaultGasProvider.getGasLimit();
    public BigInteger ultimo_precio_gas_gwei = BigInteger.ZERO;

    public web3js() {
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
        gasPrice = origen.gasPrice;
        gasLimit = origen.gasLimit;
        ultimo_precio_gas_gwei = origen.ultimo_precio_gas_gwei;
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
                    ok.setTxt(tr.in(in, "No se ha encontrado el archivo de credenciales de la wallet web3. Puede crear uno a partir de las claves privadas con el jar: wallet_a_file_web3j. "));
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
            transactionManager = new RawTransactionManager(web3j, credentials, blockchain_id_long);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.es;
    }
    /**
     * Obtiene el número nonce necesario como parámetro de las llamadas y las transacciones con gas
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
     * Llama a una función remota que consume gas (firmándola) utilizando transacciones raw
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
     * Llama a una función remota que consume gas (firmándola) utilizando transacciones raw
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
            , gasPrice, gasLimit, web3_direccion_contrato, encodedFunction_tex);
            transaccion_firmada = firmar(rawTransaction, ok);
            if (ok.es == false) { return null; }
            ethSendTransaction = web3j.ethSendRawTransaction(transaccion_firmada).send();
            if (ethSendTransaction.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + encodedFunction_tex + " " 
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
     * Llama a una función remota que consume gas y envía blockchain-coins (firmándola) utilizando transacciones raw
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
            , gasPrice, gasLimit, web3_direccion_contrato, valor, encodedFunction_tex);
            transaccion_firmada = firmar(rawTransaction, ok);
            if (ok.es == false) { return null; }
            ethSendTransaction = web3j.ethSendRawTransaction(transaccion_firmada).send();
            if (ethSendTransaction.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + encodedFunction_tex + " " 
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
     * Llama a una función remota que consume gas (firmándola) utilizando transacciones raw
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
     * Llama a una función remota que no consume gas utilizando createEthCallTransaction
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
     * Llama a una función remota que no consume gas utilizando createEthCallTransaction
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
            BigInteger nonce = obtener_nonce(credentials.getAddress(), ok);
            if (ok.es == false) { return null; }
            Transaction transaction = Transaction.createEthCallTransaction(credentials.getAddress()
              , web3_direccion_contrato, encodedFunction_tex);            
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            if (ethCall.isReverted()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + encodedFunction_tex + " " 
                        + tr.in(in, "revertida: ")
                        + ethCall.getRevertReason(), ok, extras_array);
            }
            if (ok.es == false) { return null; }
            if (ethCall.hasError()) {
                ok.setTxt(tr.in(in, "Llamada a función de contrato inteligente: ") 
                        + encodedFunction_tex + " " 
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
                        ok.setTxt(tr.in(in, "El coste estimado de gas es mayor que el gas disponible configurado: ") + web3_gas_disponible_total.toString(), extras_array);
                    }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    /**
     * Estima el gas necesario para pagar una transacción con gas.
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
     * Resta el gas usado (Si web3_gas_disponible_total es menor de 0 no se resta.)
     * @param gas_usado 
     * @param ok
     * @param extras_array
     * @return El gas restante
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
     * Llama a la función con gasto de gas de un contrato inteligente
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
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
    }
    /**
     * Llama a la función con gasto de gas de un contrato inteligente
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
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return transactionReceipt;
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
                        break;
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
     * Estima el coste del necesario para enviar una cantidad a una dirección
     * @param gas
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger estimar_coste_gas(BigInteger gas, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        BigInteger retorno = null;
        try {
            if (ultimo_precio_gas_gwei == null 
             || ultimo_precio_gas_gwei.compareTo(BigInteger.ZERO) == 0) {
                EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
                BigInteger actual_gasPrice = ethGasPrice.getGasPrice();
                ultimo_precio_gas_gwei = actual_gasPrice;
            }
            retorno = gas.multiply(ultimo_precio_gas_gwei);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Pone el último precio de gas a partir del recibo de la transaccion
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
            gas_tex = gas_tex.trim().substring(2); // Quitar 0x
            Long gas_largo = Long.parseLong(gas_tex, 16);
            BigInteger bigInteger = BigInteger.valueOf(gas_largo);
            ultimo_precio_gas_gwei = bigInteger;
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
    }
    
}
