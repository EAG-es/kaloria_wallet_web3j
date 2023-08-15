package innui.web3j;

import java.math.BigInteger;

/**
 *
 * @author emilio
 */
public class gas_precios {
    public static String k_in_ruta = "in/innui/web3j/in";  //NOI18N
    public BigInteger ultimo_precio_gas_gwei = BigInteger.ZERO;
    public BigInteger gasPrice;
    public BigInteger gasLimit;
    public BigInteger gasLimitEIP1559;
}
