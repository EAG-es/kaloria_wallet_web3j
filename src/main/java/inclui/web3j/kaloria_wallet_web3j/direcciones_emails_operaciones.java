package inclui.web3j.kaloria_wallet_web3j;

import inclui.web3j.kaloria.Direcciones_emails_mapas_web3j;
import inclui.web3j.kaloria.Blockchain_coin_web3j;
import inclui.formularios.clui_formularios;
import inclui.formularios.control_entradas;
import static inclui.formularios.control_entradas.k_entradas_tipo_email;
import static inclui.formularios.control_entradas.k_entradas_tipo_numero;
import static inclui.formularios.control_entradas.k_entradas_tipo_reset;
import static inclui.formularios.control_entradas.k_entradas_tipo_submit;
import static inclui.formularios.control_entradas.k_entradas_tipo_texto;
import static inclui.formularios.control_entradas.k_in_ruta;
import inclui.formularios.control_selecciones;
import static inclui.formularios.control_selecciones.k_control_selecciones_letras_por_linea_num;
import static inclui.formularios.control_selecciones.k_control_selecciones_opciones_mapa;
import inclui.web3j.web3_transacciones_mapas;
import inclui.web3j.web3js;
import innui.bases;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.errores.oks;
import innui.modelos.internacionalizacion.tr;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public class direcciones_emails_operaciones extends bases {
    /** 
     * Ruta de los recursos de traducción para este paquete
     */
    public static String k_in_ruta = "in/inclui/web3j/kaloria_wallet_web3j/in";  //NOI18N
    public static final String k_crear = "crear";
    public static final String k_leer = "leer";
    public static final String k_borrar = "borrar";
    public static String k_email_entrada = "email_entrada";
    public static String k_email_repetir_entrada = "email_repetir_entrada";
    public static String k_email_corregir_entrada = "email_corregir_entrada";
    public Blockchain_coin_web3j blockchain_coin_web3j;
    public Integer letras_por_linea;
    public Direcciones_emails_mapas_web3j direcciones_emails_mapas_web3j;
    public web3_transacciones_mapas web3_transacciones_mapa;
    public String web3_direccion_contrato_direccione_emails_mapa;
    public web3js web3j;
    /**
     * Lista con las direcciones de direcciones_emails_mapas en diferentes blockchains
     */
    public static class web3_direcciones_emails_mapas_listas implements Serializable {
        public static class filas implements Serializable {
            public String pos;
            public String direccion_cripto;
        }
        public LinkedList<filas> o = new LinkedList<>();
        /**
         * Poner valores por defecto
         * @param ok
         * @param extras_array
         * @return
         * @throws Exception 
         */
        public boolean iniciar(oks ok, Object ... extras_array) throws Exception {
            try {
                if (ok.es == false) { return ok.es; }
                o = new LinkedList<>();
                filas fila;
                fila = new filas();
                fila.pos = "0";
                fila.direccion_cripto = "0x7D942D665e14f904425380763B83303d82457488";
                o.add(fila);
            } catch (Exception e) {
                ok.setTxt(e);            
            }
            return ok.es;
        }
    }
    public web3_direcciones_emails_mapas_listas web3_direcciones_emails_mapas_lista;
    
    /**
     * Establece los datos de inicio de la clase.
     * @param _blockchain_coin_web3j
     * @param _letras_por_linea
     * @param _direcciones_emails_mapas_web3j
     * @param _web3_transacciones_mapa
     * @param _web3_direccion_contrato_direccione_emails_mapa
     * @param _web3j
     * @param _web3_direcciones_emails_mapas_lista
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean iniciar(Blockchain_coin_web3j _blockchain_coin_web3j
      , Integer _letras_por_linea
      , Direcciones_emails_mapas_web3j _direcciones_emails_mapas_web3j
      , web3_transacciones_mapas _web3_transacciones_mapa
      , String _web3_direccion_contrato_direccione_emails_mapa
      , web3js _web3j
      , web3_direcciones_emails_mapas_listas _web3_direcciones_emails_mapas_lista
      , oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            blockchain_coin_web3j = _blockchain_coin_web3j;
            letras_por_linea = _letras_por_linea;
            direcciones_emails_mapas_web3j = _direcciones_emails_mapas_web3j;
            web3_transacciones_mapa = _web3_transacciones_mapa;
            web3_direccion_contrato_direccione_emails_mapa = _web3_direccion_contrato_direccione_emails_mapa;
            web3j = _web3j;
            web3_direcciones_emails_mapas_lista = _web3_direcciones_emails_mapas_lista;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Procesa la operativa de altas y bajas en el registro de direcciones_emails
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean procesar_formulario_registro(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            String direccion;
            BigInteger gas_estimado;
            BigInteger precio_gas;
            String opcion = _procesar_formulario_registro_opciones(ok);
            if (ok.es == false) { return false; }
            switch (opcion) {
            case k_crear -> {
                control_entradas email_control_entrada = new control_entradas();
                control_entradas email_repetir_control_entrada = new control_entradas();
                control_entradas corregir_control_entrada = new control_entradas();
                clui_formularios clui_formulario = new clui_formularios() {
                    @Override
                    public boolean _terminar_formulario(String modo_operacion, oks ok, Object ... extras_array) throws Exception {
                        try {
                            if (ok.es == false) { return false; }
                            String email = email_control_entrada.valor.toString();
                            String email_repetido = email_repetir_control_entrada.valor.toString();
                            if (email.equals(email_repetido) == false) {
                                escribir_linea_error("El email no coincide con el email repetido. ", ok);
                                if (ok.es == false) { return false; }
                                repetir(ok);
                                if (ok.es == false) { return false; }
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                        return ok.es;
                    }
                };
                email_control_entrada.iniciar(k_entradas_tipo_email, ok);
                if (ok.es == false) { return false; }
                email_repetir_control_entrada.iniciar(k_entradas_tipo_email, ok);
                if (ok.es == false) { return false; }
                corregir_control_entrada.iniciar(k_entradas_tipo_reset, ok);
                if (ok.es == false) { return false; }
                email_control_entrada.poner_en_formulario(clui_formulario, k_email_entrada
                  , null, tr.in(in, "Introduzca el email asociado con la dirección de su wallet. "), null, ok);
                if (ok.es == false) { return false; }
                email_repetir_control_entrada.poner_en_formulario(clui_formulario, k_email_repetir_entrada
                  , null, tr.in(in, "Repita el email. "), null, ok);
                if (ok.es == false) { return false; }
                corregir_control_entrada.poner_en_formulario(clui_formulario, k_email_corregir_entrada
                  , null, tr.in(in, "¿Desea corregir? "), null, ok);
                if (ok.es == false) { return false; }
                clui_formulario.procesar(ok, extra_array);
                if (ok.es == false) { return false; }
                if (clui_formulario.ser_cancelar(ok, extra_array) == false) {
                    if (ok.es == false) { break; }
                    String email = email_control_entrada.valor.toString();
                    if (direcciones_emails_mapas_web3j.estar_email(email, ok)) {
                        if (ok.es == false) { break; }
                        escribir_linea(tr.in(in, "El email indicado ya está registrado. "), ok);
                        if (ok.es == false) { break; }
                    } else {
                        if (ok.es == false) { break; }
                        direccion = direcciones_emails_mapas_web3j.web3j.credentials.getAddress();
                        gas_estimado = direcciones_emails_mapas_web3j.estimar_gas_crear(email, ok, extra_array);
                        if (ok.es == false) { break; }
                        precio_gas = direcciones_emails_mapas_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                        if (ok.es == false) { break; }
                        if (_procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                            if (ok.es == false) { return false; }
                            escribir_linea(tr.in(in, "Operación en curso... Espere por favor. "), ok, extra_array);
                            if (ok.es == false) { return false; }
                            TransactionReceipt transactionReceipt = direcciones_emails_mapas_web3j.crear(gas_estimado, email, ok, extra_array);
                            if (ok.es == false) { break; }
                            web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                            fila.destino_direccion = direccion;
                            fila.transaccion_hash = transactionReceipt.getTransactionHash();
                            fila.gas_usado = transactionReceipt.getGasUsed();
                            BigInteger bigInteger = direcciones_emails_mapas_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                            if (ok.es == false) { return false; }
                            fila.precio_gas = blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                            if (ok.es == false) { return false; }
                            Long milisegundos = Instant.now().toEpochMilli();
                            web3_transacciones_mapa.o.put(milisegundos, fila);
                            String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                            if (ok.es == false) { return false; }
                            escribir_linea(tr.in(in, "Registro realizado. "), ok, extra_array);
                            if (ok.es == false) { return false; }
                            escribir_linea(texto, ok, extra_array);
                            if (ok.es == false) { return false; }
                        }
                        if (ok.es == false) { break; }
                    }
                }
                if (ok.es == false) { break; }
            }
            case k_leer -> {
                direccion = direcciones_emails_mapas_web3j.web3j.credentials.getAddress();
                if (direcciones_emails_mapas_web3j.estar_direccion(direccion, ok)) {
                    if (ok.es == false) { return false; }
                    escribir_linea(tr.in(in, "Operación en curso... Espere por favor. "), ok, extra_array);
                    if (ok.es == false) { return false; }
                    direccion = direcciones_emails_mapas_web3j.leer_email(direccion, ok);
                    escribir_linea(tr.in(in, "Su email asociado es: ") + direccion, ok);
                    if (ok.es == false) { break; }
                } else {
                    if (ok.es == false) { break; }
                    escribir_linea(tr.in(in, "Su dirección no está en el registro "), ok);
                    if (ok.es == false) { break; }
                }
                if (ok.es == false) { return false; }
            }
            case k_borrar -> {
                if (_procesar_formulario_si_o_no(tr.in(in, "¿Está seguro? "), ok, extra_array)) {
                    if (ok.es == false) { return false; }
                    gas_estimado = direcciones_emails_mapas_web3j.estimar_gas_borrar(ok, extra_array);
                    if (ok.es == false) { return false; }
                    precio_gas = direcciones_emails_mapas_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                    if (ok.es == false) { break; }
                    if (_procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Operación en curso... Espere por favor. "), ok, extra_array);
                        if (ok.es == false) { return false; }
                        TransactionReceipt transactionReceipt = direcciones_emails_mapas_web3j.borrar(gas_estimado, ok);
                        if (ok.es == false) { break; }
                        web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                        fila.destino_direccion = web3j.credentials.getAddress();
                        fila.transaccion_hash = transactionReceipt.getTransactionHash();
                        fila.gas_usado = transactionReceipt.getGasUsed();
                        BigInteger bigInteger = direcciones_emails_mapas_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                        if (ok.es == false) { return false; }
                        fila.precio_gas = blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                        if (ok.es == false) { return false; }
                        Long milisegundos = Instant.now().toEpochMilli();
                        web3_transacciones_mapa.o.put(milisegundos, fila);
                        String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Registro borrado. "), ok, extra_array);
                        if (ok.es == false) { return false; }
                        escribir_linea(texto, ok, extra_array);
                        if (ok.es == false) { return false; }
                    }
                    if (ok.es == false) { return false; }
                }
                if (ok.es == false) { return false; }
            }
            default -> {
                escribir_linea_error(tr.in(in, "Opción no válida. "), ok, extra_array);
                if (ok.es == false) { break; }
            }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Crea y procesa el formulario de registro de direcciones y emails
     * @param ok
     * @param extra_array
     * @return La linea del elemento seleccionado en wallet (menos la linea de cabecera)
     * @throws Exception 
     */
    public String _procesar_formulario_registro_opciones(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String k_seleccion = "seleccion";
        String retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_selecciones seleccion_control_seleccion = new control_selecciones();
            seleccion_control_seleccion.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return null; }
            Map<String, Object> opciones_mapa = new HashMap<>();
            opciones_mapa.put(k_control_selecciones_opciones_mapa, new LinkedHashMap<String, Object>() {{
                ResourceBundle in = null;
                in = ResourceBundles.getBundle(k_in_ruta);
                put(k_crear, tr.in(in,"Crear"));
                put(k_leer, tr.in(in,"Leer"));
                put(k_borrar, tr.in(in, "Borrar"));
            }});
            opciones_mapa.put(k_control_selecciones_letras_por_linea_num, letras_por_linea);
            seleccion_control_seleccion.poner_en_formulario(clui_formulario, k_seleccion, null, tr.in(in, "Seleccione una opción. "), opciones_mapa, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                retorno = seleccion_control_seleccion.leer_seleccion(ok, extra_array).toString();
                if (ok.es == false) { return null; }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Crea y procesa el formulario para la aceptación del gas necesario para el envío.
     * @param cantidad_gas Importe de gas por el que preguntar
     * @param precio_gas
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean _procesar_formulario_de_aceptar_gas(BigInteger cantidad_gas
      , BigInteger precio_gas, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        String k_aceptar_gas_submit = "aceptar_gas_submit";
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas seleccion_control_entrada = new control_entradas();
            seleccion_control_entrada.iniciar(k_entradas_tipo_submit, ok);
            if (ok.es == false) { return false; }
            seleccion_control_entrada.poner_en_formulario(clui_formulario, k_aceptar_gas_submit
              , null, tr.in(in, "¿Acepta pagar el gas indicado? "), null, ok);
            if (ok.es == false) { return false; }
            // Añadir un 10%
            cantidad_gas = cantidad_gas.multiply(BigInteger.valueOf(110L));
            cantidad_gas = cantidad_gas.divide(BigInteger.valueOf(100L));
            // Fin Añadir un 10%
            escribir_linea(tr.in(in, "Estimación de gas (al alza): ") + cantidad_gas.toString(), ok, extra_array);
            precio_gas = precio_gas.multiply(BigInteger.valueOf(110L));
            precio_gas = precio_gas.divide(BigInteger.valueOf(100L));
            String precio = blockchain_coin_web3j.poner_decimales_a_numero(precio_gas, ok, extra_array);
            escribir_linea(tr.in(in, "Estimación del precio por ese gas (a precio previo): ") + precio, ok, extra_array);
            clui_formulario.procesar(ok);
            if (ok.es == false) { return false; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                String texto = seleccion_control_entrada.valor.toString();
                if (texto.equals("1")) {
                    return true;
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return false;
    }
    /**
     * Crea y procesa el formulario de quitar de criptomoneda
     * @param mensaje
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public Boolean _procesar_formulario_si_o_no(String mensaje, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String k_si_o_no_submit = "si_o_no_submit";
        Boolean retorno = false;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas seleccion_control_entrada = new control_entradas();
            seleccion_control_entrada.iniciar(k_entradas_tipo_submit, ok);
            if (ok.es == false) { return null; }
            seleccion_control_entrada.poner_en_formulario(clui_formulario, k_si_o_no_submit
              , null, mensaje, null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                String texto = seleccion_control_entrada.valor.toString();
                if (texto.equals("1")) {
                    retorno = true;
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Crea y procesa el formulario de creación de registro de direcciones y emails
     * @param ok
     * @param extra_array
     * @return La linea del elemento seleccionado en wallet (menos la linea de cabecera)
     * @throws Exception 
     */
    public String _procesar_formulario_leer_direccion(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String k_direccion_entrada = "direccion_entrada";
        String retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas direccion_control_entrada = new control_entradas() {
                @Override
                public boolean _validar(String modo_operacion, Object objeto_a_validar, oks ok, Object ... extras_array) throws Exception {
                    ResourceBundle in;
                    try {
                        if (ok.es == false) { return false; }
                        String texto = objeto_a_validar.toString();
                        texto = texto.replace("0x", "");
                        int i = 0;
                        int tam = texto.length();
                        while (true) {
                            if (i >= tam) {
                                break;
                            }
                            if ("ABCDEFabcdef0123456789".contains(texto.substring(i, i+1)) == false) {
                                in = ResourceBundles.getBundle(k_in_ruta);
                                ok.setTxt(tr.in(in, "El dato no tiene el formato de una dirección válida. "));
                                break;
                            }
                            i = i + 1;
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                    return ok.es;
                }                
            };
            direccion_control_entrada.iniciar(k_entradas_tipo_texto, ok);
            if (ok.es == false) { return null; }
            direccion_control_entrada.poner_en_formulario(clui_formulario, k_direccion_entrada
              , null, tr.in(in, "Introduzca la dirección de su wallet. "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                retorno = direccion_control_entrada.valor.toString();
            } else {
                ok.setTxt(tr.in(in,"Cancelado "));
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
}
