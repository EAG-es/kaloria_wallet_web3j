package inclui.web3j.kaloria_wallet_web3j;

import inclui.web3j.kaloria.Kalorias_faucets_web3j;
import inclui.web3j.kaloria.I_erc20_web3j;
import inclui.formularios.clui_formularios;
import inclui.formularios.control_entradas;
import static inclui.formularios.control_entradas.k_entradas_tipo_numero;
import inclui.formularios.control_selecciones;
import static inclui.formularios.control_selecciones.k_control_selecciones_letras_por_linea_num;
import static inclui.formularios.control_selecciones.k_control_selecciones_opciones_mapa;
import inclui.web3j.web3_transacciones_mapas;
import innui.bases;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.errores.oks;
import innui.modelos.internacionalizacion.tr;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class kalorias_operaciones extends bases {
    /** 
     * Ruta de los recursos de traducción para este paquete
     */
    public static String k_in_ruta = "in/inclui/web3j/kaloria_wallet_web3j/in";  //NOI18N
    public static final String k_pedir_kalorias_gratis_o_en_prestamo = "pedir_kalorias_gratis_o_en_prestamo";
    public static final String k_pedir_kalorias_informacion_regalo_y_prestamo="pedir_kalorias_informacion_regalo_y_prestamo";
    public static final String k_devolver_kalorias_prestadas = "devolver_kalorias_prestadas";
    public static final String k_poner_kalorias_a_regalar = "poner_kalorias_a_regalar";
    public static String k_seleccion = "seleccion";
    public control_selecciones operar_kalorias_control_seleccion = new control_selecciones();
    public clui_formularios operar_kalorias_clui_formulario = new clui_formularios();
    public Integer letras_por_linea;
    public I_erc20_web3j kaloria_i_erc20_web3j;
    public Kalorias_faucets_web3j kaloria_faucect_web3j;
    public web3_transacciones_mapas web3_transacciones_mapa;
    public String web3_direccion_contrato_kaloria;
    public String web3_direccion_contrato_kaloria_faucet;
    public direcciones_emails_operaciones direcciones_emails_operacion;
    /**
     * Lista con las direcciones de kalorias_faucets en diferentes blockchains
     */
    public static class web3_direcciones_kalorias_faucets_listas implements Serializable {
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
                //  = constructor(, )
                fila.direccion_cripto = "0x7dA658DD7bc77745Ae7C43d79669e667acf2319A";
                o.add(fila);
            } catch (Exception e) {
                ok.setTxt(e);            
            }
            return ok.es;
        }
    }    
    public web3_direcciones_kalorias_faucets_listas web3_direcciones_kalorias_faucets_lista;
    /**
     * Establece los datos de inicio de la clase.
     * @param _kaloria_i_erc20_web3j
     * @param _kaloria_faucect_web3j
     * @param _web3_transacciones_mapa
     * @param _web3_direccion_contrato_kaloria
     * @param _web3_direccion_contrato_kaloria_faucet
     * @param _direcciones_emails_operacion
     * @param _web3_direcciones_kalorias_faucets_lista
     * @param _letras_por_linea
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean iniciar(I_erc20_web3j _kaloria_i_erc20_web3j
      , Kalorias_faucets_web3j _kaloria_faucect_web3j
      , web3_transacciones_mapas _web3_transacciones_mapa
      , String _web3_direccion_contrato_kaloria
      , String _web3_direccion_contrato_kaloria_faucet
      , direcciones_emails_operaciones _direcciones_emails_operacion
      , web3_direcciones_kalorias_faucets_listas _web3_direcciones_kalorias_faucets_lista
      , Integer _letras_por_linea
      , oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }            
        try {
            kaloria_i_erc20_web3j = _kaloria_i_erc20_web3j;
            kaloria_faucect_web3j = _kaloria_faucect_web3j;
            web3_transacciones_mapa = _web3_transacciones_mapa;
            web3_direccion_contrato_kaloria = _web3_direccion_contrato_kaloria;
            web3_direccion_contrato_kaloria_faucet = _web3_direccion_contrato_kaloria_faucet;
            direcciones_emails_operacion = _direcciones_emails_operacion;
            web3_direcciones_kalorias_faucets_lista = _web3_direcciones_kalorias_faucets_lista;
            letras_por_linea = _letras_por_linea;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Procesar el menu de operar kalorias
     * @param ok Comunicar resultados
     * @param extras_array Opción de añadir parámetros en el futuro.
     * @return true si todo va bien
     * @throws Exception Opción de notificar errores de excepción
     */
    public boolean procesar_operar_kalorias(oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return ok.es; }
            ResourceBundle in = null;
            in = ResourceBundles.getBundle(k_in_ruta);
            while (true) {
                ok.iniciar();
                operar_kalorias_clui_formulario.procesar(ok);
                if (ok.es == false) { break; }
                if (operar_kalorias_clui_formulario.ser_cancelar(ok, extras_array)) {
                    break;
                }
                if (ok.es == false) { break; }
                String valor_tex = (String) operar_kalorias_control_seleccion.leer_seleccion(ok);
                if (ok.es == false) { break; }
                switch (valor_tex) {
                case k_pedir_kalorias_gratis_o_en_prestamo -> {
                    procesar_formulario_de_pedir_kalorias_regaladas(ok);
                    if (ok.es == false) { break; }
                }
                case k_pedir_kalorias_informacion_regalo_y_prestamo -> {
                    procesar_formulario_de_informacion_kalorias_regaladas_y_prestadas(ok);
                    if (ok.es == false) { break; }
                }
                case k_devolver_kalorias_prestadas -> {
                    procesar_formulario_devolver_prestamo(ok, extras_array);
                    if (ok.es == false) { break; }
                }
                case k_poner_kalorias_a_regalar -> {
                    procesar_formulario_poner_para_regalo(ok, extras_array);
                    if (ok.es == false) { break; }
                }
                default -> {
                    escribir_linea_error(tr.in(in, "Opción no válida. "), ok, extras_array);
                    if (ok.es == false) { break; }
                }
                }
                if (ok.es == false) { break; }
            }
            return ok.es;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * Crea el formulario de la wallet.
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean crear_formulario_operar_kalorias(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return ok.es; }
        ResourceBundle in = null;
        try {
            in = ResourceBundles.getBundle(k_in_ruta);
            Map<String, Object> opciones_mapa;
            operar_kalorias_control_seleccion.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return ok.es; }
            opciones_mapa = new HashMap<>();
            opciones_mapa.put(k_control_selecciones_opciones_mapa, new LinkedHashMap<String, Object>() {{
                ResourceBundle in = null;
                in = ResourceBundles.getBundle(k_in_ruta);
                put(k_pedir_kalorias_gratis_o_en_prestamo, tr.in(in,"Pedir kalorias gratis o en préstamo"));
                put(k_pedir_kalorias_informacion_regalo_y_prestamo, tr.in(in,"Conocer la cantidad de kalorias en regalo y en prestamo"));
                put(k_devolver_kalorias_prestadas, tr.in(in,"Devolver kalorias prestadas"));
                put(k_poner_kalorias_a_regalar, tr.in(in,"Poner kalorias a regalar (quemar)"));
            }});
            opciones_mapa.put(k_control_selecciones_letras_por_linea_num, letras_por_linea);
            operar_kalorias_control_seleccion.poner_en_formulario(operar_kalorias_clui_formulario, k_seleccion, null, tr.in(in, "Seleccione una opción. "), opciones_mapa, ok);
            if (ok.es == false) { return ok.es; }
        } catch (Exception e) {
            throw e;
        }
        return ok.es;
    }    
    /**
     * Crea y procesa el formulario de seleccion de criptomoneda
     * @param ok
     * @param extra_array
     * @return La linea del elemento seleccionado en wallet (menos la linea de cabecera)
     * @throws Exception 
     */
    public boolean procesar_formulario_de_pedir_kalorias_regaladas(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        String k_regalo_cantidad_entrada = "regalo_cantidad_entrada";
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            BigInteger precio_gas;
            TransactionReceipt transactionReceipt = null;
            BigInteger cantidad_concedida_acumulada;
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas regalo_cantidad_control_entrada = new control_entradas();
            regalo_cantidad_control_entrada.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return false; }
            escribir_linea(tr.in(in, "Cada año se reinicia el contador de regalos, y se pueden pedir más. "), ok);
            if (ok.es == false) { return false; }
            regalo_cantidad_control_entrada.poner_en_formulario(clui_formulario, k_regalo_cantidad_entrada
              , null, tr.in(in, "Introduzca la cantidad de criptomonedas que le gustaría que le regalaran. "), null, ok);
            if (ok.es == false) { return false; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return false; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                Double doble = (Double) regalo_cantidad_control_entrada.valor;
                Integer decimales = kaloria_i_erc20_web3j.leer_decimales(ok, extra_array);
                BigInteger cantidad = kaloria_i_erc20_web3j.avanzar_separador_decimal(doble, decimales, ok);
                String direccion = direcciones_emails_operacion.web3j.credentials.getAddress();
                if (direcciones_emails_operacion.direcciones_emails_mapas_web3j.estar_direccion(direccion, ok, extra_array) == false) {
                    if (ok.es == false) { return false; }
                    escribir_linea(tr.in(in, "Para obtener regalo o pedir préstamo debe estar registrado. "), ok);
                    if (ok.es == false) { return false; }
                    direcciones_emails_operacion.procesar_formulario_registro(ok);
                    if (ok.es == false) { return false; }
                }
                if (ok.es == false) { return false; }
                BigInteger gas_estimado = kaloria_faucect_web3j.estimar_gas_pedir_regalo(cantidad, ok, extra_array);
                if (ok.es) { 
                    precio_gas = kaloria_faucect_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                    if (ok.es == false) { return false; }
                    if (direcciones_emails_operacion._procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in,"Regalo en curso... Espere por favor. "), ok);
                        if (ok.es == false) { return false; }
                        transactionReceipt = kaloria_faucect_web3j.pedir_regalo(gas_estimado, cantidad, ok, extra_array);
                        if (ok.es == false) { return false; }
                        cantidad_concedida_acumulada = kaloria_faucect_web3j.leer_cantidad_regalo(ok, extra_array);
                        if (ok.es == false) { return false; }
                        web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                        fila.destino_direccion = direccion;
                        fila.cantidad = kaloria_i_erc20_web3j.poner_decimales_a_numero(cantidad_concedida_acumulada, ok, extra_array);
                        if (ok.es == false) { return false; }
                        fila.transaccion_hash = transactionReceipt.getTransactionHash();
                        fila.gas_usado = transactionReceipt.getGasUsed();
                        BigInteger bigInteger = kaloria_faucect_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                        if (ok.es == false) { return false; }
                        fila.precio_gas = direcciones_emails_operacion.blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                        if (ok.es == false) { return false; }
                        Long milisegundos = Instant.now().toEpochMilli();
                        web3_transacciones_mapa.o.put(milisegundos, fila);
                        String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Regalo realizado. Total regalado acumulado: ") + fila.cantidad, ok, extra_array);
                        if (ok.es == false) { return false; }
                        escribir_linea(texto, ok, extra_array);
                        if (ok.es == false) { return false; }
                    }
                    if (ok.es == false) { return false; }
                } else {
                    ok.iniciar();
                    escribir_linea(tr.in(in, "Regalo denegado. ") + ok.getTxt(), ok, extra_array);
                    if (ok.es == false) { return false; }
                    BigInteger prestamo_puntos_interes_a_retener = kaloria_faucect_web3j.leer_prestamo_puntos_interes_a_retener(ok);
                    if (ok.es == false) { return false; }
                    Double prestamo_interes_a_retener = (double) prestamo_puntos_interes_a_retener.longValue();
                    prestamo_interes_a_retener = prestamo_interes_a_retener / 100.00;
                    String por_ciento_tex = formar_numero("", prestamo_interes_a_retener, ok);
                    if (ok.es == false) { return false; }
                    por_ciento_tex = por_ciento_tex + " % ";
                    escribir_linea(tr.in(in, "Puede pedir un préstamo.\\nNo hay interés anual. Pero debe devolver el préstamo para recibir nuevos regalos (reinicio anual).\\nSe le retiene un % de interés del préstamo: ")
                      + por_ciento_tex, ok);
                    if (ok.es == false) { return false; }
                    if (direcciones_emails_operacion._procesar_formulario_si_o_no(tr.in(in, "¿Desea continuar?"), ok) == true) {
                        if (ok.es == false) { return false; }
                        gas_estimado = kaloria_faucect_web3j.estimar_gas_pedir_prestamo(cantidad, ok, extra_array);
                        if (ok.es == false) { return false; }
                        precio_gas = kaloria_faucect_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                        if (ok.es == false) { return false; }
                        if (direcciones_emails_operacion._procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                            if (ok.es == false) { return false; }
                            escribir_linea(tr.in(in,"Operación en curso... Espere por favor. "), ok);
                            if (ok.es == false) { return false; }
                            transactionReceipt = kaloria_faucect_web3j.pedir_prestamo(gas_estimado, cantidad, ok, extra_array);
                            if (ok.es == false) { return false; }
                            cantidad_concedida_acumulada = kaloria_faucect_web3j.leer_cantidad_prestamo(ok, extra_array);
                            if (ok.es == false) { return false; }
                            web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                            fila.destino_direccion = direccion;
                            fila.cantidad = kaloria_i_erc20_web3j.poner_decimales_a_numero(cantidad_concedida_acumulada, ok, extra_array);
                            if (ok.es == false) { return false; }
                            fila.transaccion_hash = transactionReceipt.getTransactionHash();
                            fila.gas_usado = transactionReceipt.getGasUsed();
                            BigInteger bigInteger = kaloria_faucect_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                            if (ok.es == false) { return false; }
                            fila.precio_gas = direcciones_emails_operacion.blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                            if (ok.es == false) { return false; }
                            Long milisegundos = Instant.now().toEpochMilli();
                            web3_transacciones_mapa.o.put(milisegundos, fila);
                            String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                            if (ok.es == false) { return false; }
                            escribir_linea(tr.in(in, "Préstamo realizado. Total prestado acumulado: ") + fila.cantidad , ok, extra_array);
                            if (ok.es == false) { return false; }
                            escribir_linea(texto, ok, extra_array);
                            if (ok.es == false) { return false; }
                        }
                        if (ok.es == false) { return false; }
                    }
                    if (ok.es == false) { return false; }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Crea y procesa el formulario de seleccion de criptomoneda
     * @param ok
     * @param extra_array
     * @return La linea del elemento seleccionado en wallet (menos la linea de cabecera)
     * @throws Exception 
     */
    public boolean procesar_formulario_de_informacion_kalorias_regaladas_y_prestadas(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            String cantidad_tex;
            BigInteger cantidad_concedida_acumulada;
            if (ok.es == false) { return false; }
            String direccion = direcciones_emails_operacion.web3j.credentials.getAddress();
            if (ok.es == false) { return false; }
            if (direcciones_emails_operacion.direcciones_emails_mapas_web3j.estar_direccion(direccion, ok, extra_array) == false) {
                direcciones_emails_operacion.procesar_formulario_registro(ok);
                if (ok.es == false) { return false; }
            }
            cantidad_concedida_acumulada = kaloria_faucect_web3j.leer_cantidad_regalo(ok, extra_array);
            if (ok.es == false) { return false; }
            cantidad_tex = kaloria_i_erc20_web3j.poner_decimales_a_numero(cantidad_concedida_acumulada, ok, extra_array);
            if (ok.es == false) { return false; }
            escribir_linea(tr.in(in, "Total regalado acumulado: ") + cantidad_tex, ok, extra_array);
            if (ok.es == false) { return false; }
            cantidad_concedida_acumulada = kaloria_faucect_web3j.leer_cantidad_prestamo(ok, extra_array);
            if (ok.es == false) { return false; }
            cantidad_tex = kaloria_i_erc20_web3j.poner_decimales_a_numero(cantidad_concedida_acumulada, ok, extra_array);
            if (ok.es == false) { return false; }
            escribir_linea(tr.in(in, "Total prestado acumulado: ") + cantidad_tex, ok, extra_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Crea y procesa el formulario de devolución de cantidades prestadas
     * @param ok
     * @param extra_array
     * @return La linea del elemento seleccionado en wallet (menos la linea de cabecera)
     * @throws Exception 
     */
    public boolean procesar_formulario_devolver_prestamo(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        String k_devolucion_cantidad_entrada = "devolucion_cantidad_entrada";
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas devolucion_control_entrada = new control_entradas();
            devolucion_control_entrada.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return false; }
            devolucion_control_entrada.poner_en_formulario(clui_formulario, k_devolucion_cantidad_entrada
              , null, tr.in(in, "Introduzca la cantidad a devolver. "), null, ok);
            if (ok.es == false) { return false; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return false; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                if (ok.es == false) { return false; }
                Double doble = (Double) devolucion_control_entrada.valor;
                Integer decimales = kaloria_i_erc20_web3j.leer_decimales(ok, extra_array);
                String direccion = web3_direccion_contrato_kaloria_faucet;
                BigInteger cantidad = kaloria_i_erc20_web3j.avanzar_separador_decimal(doble, decimales, ok);
                if (procesar_formulario_aprobar_gasto(direccion, cantidad, ok)) {
                    if (ok.es == false) { return false; }
                    BigInteger gas_estimado = kaloria_faucect_web3j.estimar_gas_devolver_prestamo(cantidad, ok, extra_array);
                    if (ok.es == false) { return false; }
                    BigInteger precio_gas = kaloria_faucect_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                    if (ok.es == false) { return false; }
                    if (direcciones_emails_operacion._procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Operación en curso... Espere por favor. "), ok, extra_array);
                        if (ok.es == false) { return false; }
                        TransactionReceipt transactionReceipt = kaloria_faucect_web3j.devolver_prestamo(gas_estimado, cantidad, ok, extra_array);
                        if (ok.es == false) { return false; }
                        web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                        fila.destino_direccion = kaloria_faucect_web3j.web3j.credentials.getAddress();
                        fila.transaccion_hash = transactionReceipt.getTransactionHash();
                        fila.gas_usado = transactionReceipt.getGasUsed();
                        BigInteger bigInteger = direcciones_emails_operacion.direcciones_emails_mapas_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                        if (ok.es == false) { return false; }
                        fila.precio_gas = direcciones_emails_operacion.blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                        if (ok.es == false) { return false; }
                        Long milisegundos = Instant.now().toEpochMilli();
                        web3_transacciones_mapa.o.put(milisegundos, fila);
                        String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Devolución realizada. "), ok, extra_array);
                        if (ok.es == false) { return false; }
                        escribir_linea(texto, ok, extra_array);
                        if (ok.es == false) { return false; }
                    }
                    if (ok.es == false) { return false; }
                }
                if (ok.es == false) { return false; }
            }
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Crea y procesa el formulario de puesta de cantidades para que sean regaladas
     * @param ok
     * @param extra_array
     * @return La linea del elemento seleccionado en wallet (menos la linea de cabecera)
     * @throws Exception 
     */
    public boolean procesar_formulario_poner_para_regalo(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        String k_parar_regalo_cantidad_entrada = "parar_regalo_cantidad_entrada";
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas para_regalo_control_entrada = new control_entradas();
            para_regalo_control_entrada.iniciar(k_entradas_tipo_numero, ok);
            para_regalo_control_entrada.poner_en_formulario(clui_formulario, k_parar_regalo_cantidad_entrada
              , null, tr.in(in, "Introduzca la cantidad a poner para regalar. "), null, ok);
            if (ok.es == false) { return false; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return false; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                if (ok.es == false) { return false; }
                Double doble = (Double) para_regalo_control_entrada.valor;
                Integer decimales = kaloria_i_erc20_web3j.leer_decimales(ok, extra_array);
                BigInteger cantidad = kaloria_i_erc20_web3j.avanzar_separador_decimal(doble, decimales, ok, extra_array);
                String direccion = web3_direccion_contrato_kaloria_faucet;
                if (procesar_formulario_aprobar_gasto(direccion, cantidad, ok)) {
                    BigInteger gas_estimado = kaloria_faucect_web3j.estimar_gas_poner_para_regalo(cantidad, ok, extra_array);
                    if (ok.es == false) { return false; }
                    BigInteger precio_gas = kaloria_faucect_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                    if (ok.es == false) { return false; }
                    if (direcciones_emails_operacion._procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Operación en curso... Espere por favor. "), ok, extra_array);
                        if (ok.es == false) { return false; }
                        TransactionReceipt transactionReceipt = kaloria_faucect_web3j.poner_para_regalo(gas_estimado, cantidad, ok, extra_array);
                        if (ok.es == false) { return false; }
                        web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                        fila.destino_direccion = kaloria_faucect_web3j.web3j.credentials.getAddress();
                        fila.transaccion_hash = transactionReceipt.getTransactionHash();
                        fila.gas_usado = transactionReceipt.getGasUsed();
                        BigInteger bigInteger = direcciones_emails_operacion.direcciones_emails_mapas_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                        if (ok.es == false) { return false; }
                        fila.precio_gas = direcciones_emails_operacion.blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                        if (ok.es == false) { return false; }
                        Long milisegundos = Instant.now().toEpochMilli();
                        web3_transacciones_mapa.o.put(milisegundos, fila);
                        String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                        if (ok.es == false) { return false; }
                        escribir_linea(tr.in(in, "Operación realizada. "), ok, extra_array);
                        if (ok.es == false) { return false; }
                        escribir_linea(texto, ok, extra_array);
                        if (ok.es == false) { return false; }
                    }
                    if (ok.es == false) { return false; }
                }
                if (ok.es == false) { return false; }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Crea y procesa el formulario de aprobar gasto
     * @param destino_dir
     * @param cantidad
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean procesar_formulario_aprobar_gasto(String destino_dir, BigInteger cantidad
      , oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            TransactionReceipt transactionReceipt = null;
            BigInteger gas_estimado = kaloria_i_erc20_web3j.estimar_gas_aprobar(destino_dir, cantidad, ok, extra_array);
            if (ok.es == false) { return false; }
            BigInteger precio_gas = kaloria_i_erc20_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
            if (ok.es == false) { return false; }
            if (direcciones_emails_operacion._procesar_formulario_de_aceptar_gas(gas_estimado, precio_gas, ok)) {
                if (ok.es == false) { return false; }
                escribir_linea(tr.in(in,"Aprobación en curso... Espere por favor. "), ok);
                if (ok.es == false) { return false; }
                transactionReceipt = kaloria_i_erc20_web3j.aprobar(gas_estimado, destino_dir, cantidad, ok, extra_array);
                if (ok.es == false) { return false; }
                web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                fila.destino_direccion = destino_dir;
                fila.cantidad = kaloria_i_erc20_web3j.poner_decimales_a_numero(cantidad, ok, extra_array);
                if (ok.es == false) { return false; }
                fila.transaccion_hash = transactionReceipt.getTransactionHash();
                fila.gas_usado = transactionReceipt.getGasUsed();
                BigInteger bigInteger = kaloria_i_erc20_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                if (ok.es == false) { return false; }
                fila.precio_gas = direcciones_emails_operacion.blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                if (ok.es == false) { return false; }
                Long milisegundos = Instant.now().toEpochMilli();
                web3_transacciones_mapa.o.put(milisegundos, fila);
                String texto = web3_transacciones_mapa.formar_mensaje_transaccion(milisegundos, fila, ok);
                if (ok.es == false) { return false; }
                escribir_linea(tr.in(in, "Aprobación realizada. "), ok);
                if (ok.es == false) { return false; }
                escribir_linea(texto, ok, extra_array);
                if (ok.es == false) { return false; }
            } else {
                ok.setTxt(tr.in(in, "Cancelado "));
            }
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.es;
    }
    
}
