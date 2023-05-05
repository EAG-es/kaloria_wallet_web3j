package inclui.web3j.kaloria_wallet_web3j;

import inclui.web3j.web3js;
import static inclui.web3j.web3js.k_tiempo_maximo_esperando_milisegundos;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.errores.oks;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ResourceBundle;
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
public class Blockchain_coin_web3j extends I_erc20_padre_web3j {
    public static String k_in_ruta = "in/inclui/web3j/kaloria_wallet_web3j/in";  //NOI18N
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
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
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
              , web3j.defaultGasProvider.getGasLimit() //maxPriorityFeePerGas (max fee per gas transaction willing to give to miners)
              , web3j.defaultGasProvider.getGasLimit());
            TransactionReceipt transactionReceipt = remoteCall.send();
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
    
}
