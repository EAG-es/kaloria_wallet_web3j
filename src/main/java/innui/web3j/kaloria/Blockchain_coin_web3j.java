package innui.web3j.kaloria;

import innui.web3j.Erc20_bases_web3j;
import innui.web3j.web3js;
import static innui.web3j.web3js.k_tiempo_maximo_esperando_milisegundos;
import innui.modelos.errores.oks;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

/**
 *
 * @author emilio
 */
public class Blockchain_coin_web3j extends Erc20_bases_web3j {

    /**
     * Obtener el símbolo
     * @param direccion Dirección de la que leer el balance
     * @param balance_stringBuilder Texto con la coma situada en el lugar correcto (puede ser null).
     * @param ok
     * @param extras_array
     * @return El valor como un número sin decimales
     * @throws Exception 
     */
    @Override
    public BigInteger leer_balance(String direccion, StringBuilder balance_stringBuilder, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        BigInteger retorno = null;
        try {
            // send asynchronous requests to get balance
            EthGetBalance ethGetBalance = web3j.web3j.ethGetBalance(direccion
              , DefaultBlockParameterName.LATEST).send();
            BigInteger wei = ethGetBalance.getBalance();
            retorno = wei;
            if (balance_stringBuilder != null) {
                String texto = poner_decimales_a_numero(retorno, ok);
                if (ok.es == false) { return null; }
                balance_stringBuilder.replace(0, balance_stringBuilder.length(), texto);
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }    
    /**
     * Obtener el símbolo
     * @param balance_stringBuilder Texto con la coma situada en el lugar correcto.
     * @param ok
     * @param extras_array
     * @return El valor como un número sin decimales
     * @throws Exception 
     */
    @Override
    public BigInteger leer_balance(StringBuilder balance_stringBuilder, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return null; }
        BigInteger retorno = null;
        try {
            // send asynchronous requests to get balance
            EthGetBalance ethGetBalance = web3j.web3j.ethGetBalance(web3j.credentials.getAddress()
              , DefaultBlockParameterName.LATEST).send();
            BigInteger wei = ethGetBalance.getBalance();
            retorno = wei;
            balance_stringBuilder.delete(0, balance_stringBuilder.length());
            balance_stringBuilder.append(web3js.poner_decimales_a_numero(wei, decimales, ok, extras_array));
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
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
        return nombre;
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
        return simbolo;
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
        return decimales;
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
        if (ok.es == false) { return null; }
        TransactionReceipt retorno = null;
        try {
            BigDecimal bigDecimal = new BigDecimal(cantidad);
            RemoteCall<TransactionReceipt> remoteCall = Transfer.sendFundsEIP1559(web3j.web3j
              , web3j.credentials, direccion
              , bigDecimal, Convert.Unit.WEI //unit
              , gas_aceptable
              , web3j.gas.gasLimitEIP1559 //maxPriorityFeePerGas (max fee per gas transaction willing to give to miners)
              , web3j.gas.gasLimitEIP1559);
            TransactionReceipt transactionReceipt = remoteCall.send();
            transactionReceipt = web3j.comprobar_y_esperar_recibo(transactionReceipt
                  , k_tiempo_maximo_esperando_milisegundos, ok, extras_array);
            if (web3j.ser_recibo_vacio(transactionReceipt, ok) == false) {
                if (ok.es == false) { return null; }
                web3j.restar_gas(transactionReceipt.getGasUsed(), ok);
            }
            if (ok.es == false) { return null; }
            retorno = transactionReceipt;
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
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    @Override
    public boolean enviar_asincrono(BigInteger gas_aceptable
      , String direccion, BigInteger cantidad
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            BigDecimal bigDecimal = new BigDecimal(cantidad);
            RemoteCall<TransactionReceipt> remoteCall = Transfer.sendFundsEIP1559(web3j.web3j
              , web3j.credentials, direccion
              , bigDecimal, Convert.Unit.WEI //unit
              , gas_aceptable
              , web3j.gas.gasLimitEIP1559 // maxPriorityFeePerGas (max fee per gas transaction willing to give to miners)
              , web3j.gas.gasLimitEIP1559);
            CompletableFuture<TransactionReceipt> completable = remoteCall.sendAsync();
            poner_escuchador_por_defecto_con_gas(completable, datos_mapa, ok, extras_array);
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return ok.es;
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
        if (ok.es == false) { return null; }
        BigInteger retorno = null;
        try {
            Transaction transaction = Transaction.createEthCallTransaction(web3j.credentials.getAddress(),direccion
              , "", cantidad);
            EthEstimateGas ethEstimateGas = web3j.web3j.ethEstimateGas(transaction).send();
            retorno = ethEstimateGas.getAmountUsed();
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return retorno;
    }
    /**
     * Establece un comportamiento por defecto para las transacciones
     * @param completable
     * @param datos_mapa
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public CompletableFuture<TransactionReceipt> poner_escuchador_por_defecto_con_gas(CompletableFuture<TransactionReceipt> completable
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception {
        CompletableFuture<TransactionReceipt> completableFuture = null;
        try {
            if (ok.es == false) { return null; }
            completableFuture = completable.handleAsync(new BiFunction<TransactionReceipt, Throwable, TransactionReceipt>() {
                @Override
                public TransactionReceipt apply(TransactionReceipt transactionReceipt, Throwable throwable) {
                    oks ok = new oks();
                    try {
                        if (transactionReceipt != null) {
                            web3j.i_notificacion_asincrona.procesar_transaccion_asincrona(transactionReceipt
                              , datos_mapa, ok);
                            if (ok.es == false) {
                                web3j.i_notificacion_asincrona.poner_error_asincrono(false, ok.getTxt()
                                  ,datos_mapa, ok.iniciar());
                            }
                        } else if (throwable != null) {
                            Exception exception = new Exception(throwable);
                            ok.setTxt(exception);
                            try {
                                web3j.i_notificacion_asincrona.poner_error_asincrono(true, ok.getTxt()
                                  , datos_mapa, ok.iniciar());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (Exception e) {
                        ok.setTxt(e);
                        try {
                            web3j.i_notificacion_asincrona.poner_error_asincrono(throwable != null, ok.getTxt()
                              , datos_mapa, ok.iniciar());
                        } catch (Exception es) {
                            throw new RuntimeException(e);
                        }
                    }
                    return transactionReceipt;
                }
            });
        } catch (Exception e) {
            ok.setTxt(e); 
        }
        return completableFuture;
    }
    
}
