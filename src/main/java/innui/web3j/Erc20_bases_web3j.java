package innui.web3j;

import innui.bases;
import innui.modelos.errores.oks;
import innui.web3j.generated.contracts.I_erc20_bases;
import java.math.BigInteger;
import java.util.Map;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public abstract class Erc20_bases_web3j extends bases {
    public web3js web3j;
    public I_erc20_bases i_erc20_base;
    public String nombre;
    public String simbolo;
    public Integer decimales;
    /**
     * Obtener el nombre
     * @param ok
     * @param extras_array
     * @return 
     * @throws Exception 
     */
    abstract public String leer_nombre(oks ok, Object ... extras_array) throws Exception;
    /**
     * Obtener el símbolo
     * @param ok
     * @param extras_array
     * @return 
     * @throws Exception 
     */
    abstract public String leer_simbolo(oks ok, Object ... extras_array) throws Exception;
    /**
     * Obtener el número de decimales
     * @param ok
     * @param extras_array
     * @return 
     * @throws Exception 
     */
    abstract public Integer leer_decimales(oks ok, Object ... extras_array) throws Exception;
    /**
     * Leer el balance
     * @param balance_stringBuilder Texto con la coma situada en el lugar correcto.
     * @param ok
     * @param extras_array
     * @return El valor como un número sin decimales
     * @throws Exception 
     */
    abstract public BigInteger leer_balance(StringBuilder balance_stringBuilder, oks ok, Object ... extras_array) throws Exception;

    /**
     * Leer el balance
     * @param direccion
     * @param balance_stringBuilder Texto con la coma situada en el lugar correcto.
     * @param ok
     * @param extras_array
     * @return El valor como un número sin decimales
     * @throws Exception 
     */
    abstract public BigInteger leer_balance(String direccion, StringBuilder balance_stringBuilder, oks ok, Object ... extras_array) throws Exception;
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
    abstract public TransactionReceipt enviar(BigInteger gas_aceptable, String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception;
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
    abstract public boolean enviar_asincrono(BigInteger gas_aceptable
      , String direccion, BigInteger cantidad
      , Map<String, Object> datos_mapa, oks ok, Object ... extras_array) throws Exception;
    /**
     * Estima el gas necesario para enviar una cantidad a una dirección
     * @param direccion
     * @param cantidad
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    abstract public BigInteger estimar_gas_enviar(String direccion, BigInteger cantidad, oks ok, Object ... extras_array) throws Exception;
    /**
     * Pone los decimales al número entero que se maneja internamente
     * @param numero
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String poner_decimales_a_numero(BigInteger numero, oks ok, Object ... extras_array) throws Exception {
        return web3js.poner_decimales_a_numero(numero, decimales, ok, extras_array);
    }
    /**
     * Avanza el separador decimal multiplicando por 10
     * @param numero
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public BigInteger avanzar_separador_decimal(Double numero, oks ok, Object ... extras_array) throws Exception {
        return web3js.avanzar_separador_decimal(numero, decimales, ok, extras_array);
    }
    /**
     * Retrocede el separador decimal dividiendo por 10
     * @param numero
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public Double retroceder_separador_decimal(BigInteger numero, oks ok, Object ... extras_array) throws Exception {
        return web3js.retroceder_separador_decimal(numero, decimales, ok, extras_array);
    }
    
    public web3js getWeb3j() {
        return web3j;
    }

    public void setWeb3j(web3js web3j) {
        this.web3j = web3j;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Integer getDecimales() {
        return decimales;
    }

    public void setDecimales(Integer decimales) {
        this.decimales = decimales;
    }    
}

