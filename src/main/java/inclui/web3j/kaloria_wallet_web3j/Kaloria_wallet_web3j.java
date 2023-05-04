package inclui.web3j.kaloria_wallet_web3j;

import com.fasterxml.jackson.databind.ObjectMapper;
import inclui.formularios.clui_formularios;
import inclui.formularios.control_entradas;
import static inclui.formularios.control_entradas.k_entradas_tipo_numero;
import static inclui.formularios.control_entradas.k_entradas_tipo_password;
import static inclui.formularios.control_entradas.k_entradas_tipo_submit;
import static inclui.formularios.control_entradas.k_entradas_tipo_texto;
import inclui.formularios.control_selecciones;
import static inclui.formularios.control_selecciones.k_control_selecciones_letras_por_linea_num;
import static inclui.formularios.control_selecciones.k_control_selecciones_opciones_mapa;
import inclui.formularios.control_tablas;
import static inclui.formularios.control_tablas.k_control_tablas_letras_por_linea_num;
import static inclui.formularios.control_tablas.k_control_tablas_opciones_mapa_lista;
import inclui.web3j.web3js;
import innui.formularios.controles;
import innui.modelos.configuraciones.ResourceBundles;
import innui.modelos.configuraciones.Resources;
import innui.modelos.configuraciones.iniciales;
import innui.modelos.errores.oks;
import innui.modelos.internacionalizacion.tr;
import innui.modelos.modelos;
import java.io.File;
import java.io.Serializable;
import static java.lang.System.exit;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 *
 * @author emilio
 */
public class Kaloria_wallet_web3j extends iniciales {
    /** 
     * Ruta de los recursos de traducción para este paquete
     */
    public static String k_in_ruta = "in/inclui/web3j/kaloria_wallet_web3j/in";  //NOI18N
    public static String k_wallet_ruta = "/re";  
    public static String k_web3_endpoint_https_pos = "web3_endpoint_https_pos";
    public static String k_web3_endpoint_https_datos_lista = "web3_endpoint_https_datos_lista";
    public static String k_web3_archivo_EAO = "web3_archivo_EAO";
    public static String k_web3_clave_EAO = "web3_clave_EAO";
    public static String k_web3_direcciones_kaloria_datos_lista = "web3_direcciones_kaloria_datos_lista";
    public static String k_web3_direcciones_criptos_datos_lista = "web3_direcciones_criptos_datos_lista";
    public static String k_kaloria_wallet_columnas_cabecera_tex = "kaloria_wallet.columnas_cabecera_tex";
    public static String kaloria_wallet_letras_por_linea = "kaloria_wallet.letras_por_linea";
    public static String k_crear_wallet_submit = "crear_wallet_submit";
    public static String k_crear_wallet_contraseña_password = "contraseña_password";
    public static String k_crear_wallet_contraseña_repetida_password = "contraseña_repetida_password";
    public static String k_wallet_tabla = "wallet_tabla";
    public static String k_wallet_seleccion = "wallet_seleccion";
    public static String k_cambiar_de_blockchain_seleccion = "cambiar_de_blockchain_seleccion";
    public static String k_seleccionar_entrada = "seleccionar_entrada";
    public static String k_añadir_entrada = "añadir_entrada";
    public static String k_quitar_submit = "quitar_submit";
    public static String k_enviar_direccion_entrada = "enviar_direccion_entrada";
    public static String k_enviar_cantidad_entrada = "enviar_cantidad_entrada";
    public static String k_criptomoneda_seleccion = "criptomoneda_seleccion";
    public static String k_simbolo = "Símbolo";
    public static String k_ballance = "Balance";
    public static String k_contrato_no_valido = "contrato_no_valido";
    public static final String k_seleccionar = "selecionar";
    public static final String k_añadir = "añadir";
    public static final String k_cambiar_de_blockchain = "cambiar_de_blockchain";
    public static final String k_enviar = "enviar";
    public static final String k_transacciones = "transacciones";
    public static final String k_quitar = "quitar";
    public String columnas_cabecera_tex;
    public Integer letras_por_linea;
    public Integer blockchain_pos;
    public String web3_direccion_contrato_kaloria;
    public List<String> criptos_lista;
    public List<Erc20_web3j> criptos_i_erc20_lista;
    public web3js web3j = new web3js();
    public I_erc20_token_web3j kaloria_i_erc20_web3j;
    public Blockchain_coin_web3j blockchain_coin_web3j;
    public LinkedList<LinkedHashMap<String, Object>> wallet_lista = new LinkedList<>();
    public control_tablas wallet_control_tabla = new control_tablas();
    public control_selecciones wallet_control_seleccion = new control_selecciones();
    public clui_formularios wallet_clui_formulario = new clui_formularios();
    /**
     * Lista de blockchain-endpoints y sus datos asociados.
     */
    public static class web3_endpoint_https_datos_listas implements Serializable {
        public static class filas implements Serializable {
            public String pos;
            public String nombre;
            public String endpoint_https;
            public String simbolo;
            public Integer decimales;
            public String explorador_bloques_https;
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
                filas fila = new filas();
                fila.pos = "0";
                fila.nombre = "Sepolia Ethereum Testnet";
                fila.endpoint_https = "https://ethereum-sepolia-rpc.allthatnode.com/kXaA68KYCVYKwjT6ka6h5bVLx5PL3SdN";
                fila.simbolo = "SepoliaETH";
                fila.decimales = 18;
                fila.explorador_bloques_https = "https://sepolia.etherscan.io";
                o.add(fila);
                fila = new filas();
                fila.pos = "1";
                fila.nombre = "Mumbai Polygon Testnet";
                fila.endpoint_https = "https://polygon-testnet-rpc.allthatnode.com:8545/kXaA68KYCVYKwjT6ka6h5bVLx5PL3SdN";
                fila.simbolo = "MumbaiMatic";
                fila.decimales = 18;
                fila.explorador_bloques_https = "https://mumbai.polygonscan.com";
                o.add(fila);
                fila = new filas();
                fila.pos = "2";
                fila.nombre = "Fantom Testnet";
                fila.endpoint_https = "https://ftm.getblock.io/f43043bf-f5ea-4669-84bf-d753b0ad57b9/testnet/";
                fila.simbolo = "TestnetFTM";
                fila.decimales = 18;
                fila.explorador_bloques_https = "https://testnet.ftmscan.com";
                o.add(fila);
                fila = new filas();
                fila.pos = "3";
                fila.nombre = "Fuji Avax Testnet";
                fila.endpoint_https = "https://avax.getblock.io/f43043bf-f5ea-4669-84bf-d753b0ad57b9/testnet/ext/bc/C/rpc";
                fila.simbolo = "FujiAvax";
                fila.decimales = 18;
                fila.explorador_bloques_https = "https://testnet.snowtrace.io";
                o.add(fila);
            } catch (Exception e) {
                ok.setTxt(e);            
            }
            return ok.es;
        }
    }
    /**
     * Lista de tokens en diferentes blockchain (distintos de kaloria)
     */
    public static class web3_direcciones_criptos_datos_listas implements Serializable {
        public static class filas implements Serializable {
            public String pos;
            public LinkedList<String> criptos_lista;
        }
        public LinkedList<filas> o = new LinkedList<>();
        
        public boolean iniciar(oks ok, Object ... extras_array) throws Exception {
            try {
                if (ok.es == false) { return ok.es; }
                filas fila = new filas();
                fila.pos = "0";
                fila.criptos_lista = new LinkedList<>() {
                    {
                        add("0x4c5e14aD98bED70bA3A868e27118b6C5F6A0eAD5");
                    }
                };
                o.add(fila);
            } catch (Exception e) {
                ok.setTxt(e);            
            }
            return ok.es;
        }    }
    /**
     * Lista con las direcciones de kaloria en diferentes blockchains
     */
    public static class web3_direcciones_kaloria_datos_listas implements Serializable {
        public static class filas implements Serializable {
            public String pos;
            public String direccion_cripto;
            public String gas_aceptable_por_transaccion;
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
                fila.direccion_cripto = "0xb5431a7D2Abbb4C2190D607d19Dce250D808957a";
                fila.gas_aceptable_por_transaccion = "150000";
                o.add(fila);
            } catch (Exception e) {
                ok.setTxt(e);            
            }
            return ok.es;
        }
    }
    public static class web3_transacciones_mapas implements Serializable {
        public static class filas implements Serializable {
            public String destino_direccion;
            public String cantidad;
            public String simbolo;
            public String transaccion_hash;
            public String precio_gas;
            public BigInteger gas_usado;
        }
        public TreeMap<Long, filas> o = new TreeMap<>();
    }
    web3_endpoint_https_datos_listas web3_endpoint_https_datos_lista; 
    web3_direcciones_criptos_datos_listas web3_direcciones_criptos_datos_lista;
    web3_direcciones_kaloria_datos_listas web3_direcciones_kaloria_datos_lista;
    web3_transacciones_mapas web3_transacciones_mapa = new web3_transacciones_mapas();
    /**
     * Inicio de la aplicación
     * @param args 
     */
    public static void main(String[] args) {
        oks ok = new oks();
        Kaloria_wallet_web3j kaloria_wallet_web3j = null;
        try {
            kaloria_wallet_web3j = new Kaloria_wallet_web3j();
            kaloria_wallet_web3j.run(ok);
        } catch (Exception e) {
            ok.setTxt(e);
        }
        if (ok.es == false) {
            System.err.println(ok.txt);
            exit(1);
        } else {
            exit(0);
        }
    }    
    /**
     * Inicio de la aplicación desde un objeto instanciado
     * @param ok Comunicar resultados
     * @param extras_array Opción de añadir parámetros en el futuro.
     * @return true si todo va bien
     * @throws Exception Opción de notificar errores de excepción
     */
    public boolean run(oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return ok.es; }
            ResourceBundle in = null;
            iniciar(ok);
            if (ok.es) {
                in = ResourceBundles.getBundle(k_in_ruta);
                boolean es_llenar_lista_cripto = true;
                boolean es_wallet_salir;
                // Inicio del código propio de la aplicación
                conectar_web3(ok);
                if (ok.es == false) { return false; }
                while (true) {
                    ok.iniciar();
                    es_wallet_salir = false;
                    crear_formulario_wallet(ok);
                    if (ok.es == false) { break; }
                    while (true) {
                        if (es_llenar_lista_cripto) {
                            llenar_lista_criptos(ok, extras_array);
                            es_llenar_lista_cripto = false;
                            if (ok.es == false) { 
                                if (ok.id.equals(k_contrato_no_valido)) {
                                    oks ok_extra = new oks();
                                    escribir_linea_error(ok.getTxt(), ok_extra, extras_array);
                                    if (ok_extra.es == false) {
                                        ok.iniciar(ok_extra);
                                        break;
                                    }
                                    ok.iniciar();
                                } else {
                                    break; 
                                }
                            }
                        }
                        llenar_wallet_lista(ok, extras_array);
                        if (ok.es == false) { 
                            es_wallet_salir = true;
                            break; 
                        }
                        poner_cabecera(wallet_lista, ok);
                        if (ok.es == false) { 
                            es_wallet_salir = true;
                            break; 
                        }
                        wallet_control_tabla.cargar_tabla(wallet_lista, ok);
                        if (ok.es == false) { 
                            es_wallet_salir = true;
                            break; 
                        }
                        escribir_linea(web3j.web3_endpoint_https_nombre, ok);
                        if (ok.es == false) { 
                            es_wallet_salir = true;
                            break; 
                        }
                        wallet_clui_formulario.procesar(ok);
                        if (ok.es == false) { break; }
                        if (wallet_clui_formulario._es_cancelar) {
                            es_wallet_salir = true;
                            break;
                        }
                        String valor_tex = (String) wallet_control_seleccion.leer_seleccion(ok);
                        if (ok.es == false) { break; }
                        switch (valor_tex) {
                        case k_seleccionar -> {
                            Integer seleccion = procesar_formulario_de_seleccion(ok);
                            if (ok.es == false) { break; }
                            String seleccion_tex = procesar_formulario_de_criptomoneda(seleccion, ok);
                            if (ok.es == false) { break; }
                            if (seleccion_tex == null) {
                                break;
                            }
                            switch (seleccion_tex) {
                            case k_enviar -> {
                                procesar_formulario_de_enviar(seleccion, ok, extras_array);
                                if (ok.es == false) { break; }
                            }
                            case k_transacciones -> {
                                escribir_informacion_transacciones(seleccion, ok);
                                if (ok.es == false) { break; }
                            }
                            case k_quitar -> {
                                procesar_formulario_de_quitar(seleccion, ok);
                                if (ok.es == false) { break; }
                            }
                            default -> {
                                escribir_linea_error(tr.in(in, "Opción no válida. "), ok, extras_array);
                                if (ok.es == false) { break; }
                            }
                            }
                        }
                        case k_añadir -> {
                            procesar_formulario_de_añadir(ok);
                            if (ok.es == false) { break; }
                            es_llenar_lista_cripto = true;
                        }
                        case k_cambiar_de_blockchain -> {
                            procesar_formulario_de_cambio_de_blockchain(ok);
                            if (ok.es == false) { break; }
                            es_llenar_lista_cripto = true;
                        }
                        case k_transacciones -> {
                            escribir_informacion_transacciones(null, ok);
                            if (ok.es == false) { break; }
                        }
                        default -> {
                            escribir_linea_error(tr.in(in, "Opción no válida. "), ok, extras_array);
                            if (ok.es == false) { break; }
                        }
                        }
                    }
                    if (ok.es == false) {
                        escribir_linea_error(ok.getTxt(), ok, extras_array);
                        ok.iniciar();
                    }
                    if (es_wallet_salir) {
                        break;
                    }
                }
                // Fin del código propio de la aplicación
                oks ok_fin = new oks();
                terminar(ok_fin);
                if (ok_fin.es == false) {
                    ok.setTxt(ok.getTxt(), ok_fin.getTxt());
                }
            }
            return ok.es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean iniciar(oks ok, Object... extra_array) throws Exception {
        // Iniciar clase principal de la librería
        if (ok.es == false) { return ok.es; }
        _iniciar_desde_clase(modelos.class, ok);
        if (ok.es == false) { return ok.es; }
        _iniciar_desde_clase(this.getClass(), ok);
        return ok.es;
    }
    
    @Override
    public boolean terminar(oks ok, Object... extra_array) throws Exception {
        // Terminar clase principal de la librería
        if (ok.es == false) { return ok.es; }
        _terminar_desde_clase(modelos.class, ok);
        if (ok.es == false) { return ok.es; }
        _terminar_desde_clase(this.getClass(), ok);
        return ok.es;
    }
    /**
     * Conecta con el punto de acceso HTTPS e inicia los objetos de manejo de los contratos inteligentes
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean conectar_web3(oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.es == false) { return false; }
            ResourceBundle in;
            in = ResourceBundles.getBundle(k_in_ruta);
            String texto;
            ObjectMapper objectMapper = new ObjectMapper();
            columnas_cabecera_tex = properties.getProperty(k_kaloria_wallet_columnas_cabecera_tex);
            texto = properties.getProperty(kaloria_wallet_letras_por_linea);
            letras_por_linea = Integer.valueOf(texto);
            web3j.web3_archivo_EAO = properties.getProperty(k_web3_archivo_EAO);
            web3j.web3_clave_EAO = properties.getProperty(k_web3_clave_EAO);
            texto = properties.getProperty(k_web3_endpoint_https_pos);
            blockchain_pos = Integer.valueOf(texto);
            texto = properties.getProperty(k_web3_endpoint_https_datos_lista);
            if (texto.isBlank() == false) {
                web3_endpoint_https_datos_lista = objectMapper.readValue(texto
                  , web3_endpoint_https_datos_listas.class);	
            } else {
                web3_endpoint_https_datos_lista = new web3_endpoint_https_datos_listas();
                web3_endpoint_https_datos_lista.iniciar(ok, extras_array);
                if (ok.es == false) { return false; }
                texto = objectMapper.writeValueAsString(web3_endpoint_https_datos_lista);
                properties.setProperty(k_web3_endpoint_https_datos_lista, texto);
                terminar(ok);
                if (ok.es == false) { return false; }
            }
            web3j.web3_endpoint_https = web3_endpoint_https_datos_lista.o.get(blockchain_pos).endpoint_https;
            web3j.web3_endpoint_https_nombre  = web3_endpoint_https_datos_lista.o.get(blockchain_pos).nombre;
            web3j.web3_endpoint_https_simbolo  = web3_endpoint_https_datos_lista.o.get(blockchain_pos).simbolo;
            web3j.web3_endpoint_https_decimales =  web3_endpoint_https_datos_lista.o.get(blockchain_pos).decimales;
            texto = properties.getProperty(k_web3_direcciones_criptos_datos_lista);
            if (texto.isBlank() == false) {
                web3_direcciones_criptos_datos_lista = objectMapper.readValue(texto
                  , web3_direcciones_criptos_datos_listas.class);
                if (web3_direcciones_criptos_datos_lista != null
                  && blockchain_pos < web3_direcciones_criptos_datos_lista.o.size()) {
                    criptos_lista = web3_direcciones_criptos_datos_lista.o.get(blockchain_pos).criptos_lista;
                } else {
                    criptos_lista = null;
                }
            } else {
                web3_direcciones_criptos_datos_lista = new web3_direcciones_criptos_datos_listas();
                web3_direcciones_criptos_datos_lista.iniciar(ok, extras_array);
                if (ok.es == false) { return false; }
                texto = objectMapper.writeValueAsString(web3_direcciones_criptos_datos_lista);
                properties.setProperty(k_web3_direcciones_criptos_datos_lista, texto);
                terminar(ok);
                if (ok.es == false) { return false; }
            }
            texto = properties.getProperty(k_web3_direcciones_kaloria_datos_lista);
            if (texto.isBlank() == false) {
                web3_direcciones_kaloria_datos_lista = objectMapper.readValue(texto
                  , web3_direcciones_kaloria_datos_listas.class);
                if (web3_direcciones_kaloria_datos_lista != null
                  && blockchain_pos < web3_direcciones_kaloria_datos_lista.o.size()) {
                    web3_direccion_contrato_kaloria = web3_direcciones_kaloria_datos_lista.o.get(blockchain_pos).direccion_cripto;
                } else {
                    web3_direccion_contrato_kaloria = null;
                }
            } else {
                web3_direcciones_kaloria_datos_lista = new web3_direcciones_kaloria_datos_listas();
                web3_direcciones_kaloria_datos_lista.iniciar(ok, extras_array);
                if (ok.es == false) { return false; }
                texto = objectMapper.writeValueAsString(web3_direcciones_kaloria_datos_lista);
                properties.setProperty(k_web3_direcciones_kaloria_datos_lista, texto);
                terminar(ok);
                if (ok.es == false) { return false; }
            }
            if (web3j.web3_archivo_EAO.isBlank()) {
                String contraseña = procesar_formulario_para_crear_wallet(ok);
                if (ok.es == false) { return false; }
                if (contraseña.isBlank() == false) {
                    File archivo_file = crear_wallet(contraseña, ok);
                    if (ok.es == false) { return false; }
                    escribir_linea(tr.in(in, "Se ha creado la wallet. Su archivo de credenciales es: ")
                     + archivo_file.getCanonicalPath(), ok, extras_array);
                    if (ok.es == false) { return false; }
                    web3j.web3_archivo_EAO = archivo_file.getName();
                    web3j.web3_clave_EAO = contraseña;
                    properties.setProperty(k_web3_archivo_EAO, web3j.web3_archivo_EAO);
                    properties.setProperty(k_web3_clave_EAO, web3j.web3_clave_EAO);
                    terminar(ok);
                    if (ok.es == false) { return false; }
                }
            }
            web3j.iniciar(k_wallet_ruta, ok, extras_array);
            if (ok.es == false) { return false; }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.es;
    }

    public File crear_wallet(String contraseña, oks ok, Object ... extras_array) throws Exception {
        File retorno = null;
        try {
            if (ok.es == false) { return null; }
            URL url = Resources.getResource(this.getClass(), k_wallet_ruta);
            File file = new File(url.toURI());
            String nombre_de_archivo = WalletUtils.generateNewWalletFile(contraseña
              , file);
            retorno = new File(file, nombre_de_archivo);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }   
    /**
     * Crea y procesa el formulario de creacion de wallet
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public String procesar_formulario_para_crear_wallet(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas crear_wallet_control_entrada = new control_entradas();
            crear_wallet_control_entrada.iniciar(k_entradas_tipo_submit, ok);
            if (ok.es == false) { return null; }
            crear_wallet_control_entrada.poner_en_formulario(clui_formulario, k_crear_wallet_submit
              , null, tr.in(in, "Archivo json de wallet no encontrado. ¿Desea crear una nueva wallet? "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                if (crear_wallet_control_entrada.valor.toString().equals("1")) {
                    retorno = _procesar_formulario_para_obtener_contraseña(ok);
                    if (ok.es == false) { return null; }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }    
    /**
     * Crea el formulario de obtención de contraseña de la wallet
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public String _procesar_formulario_para_obtener_contraseña(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            final StringBuilder contraseña_stringbuilder = new StringBuilder();
            clui_formularios clui_formulario = new clui_formularios() {
                @Override
                public boolean _terminar_formulario(String modo_operacion, oks ok, Object ... extras_array) throws Exception {
                    try {
                        if (ok.es == false) { return false; }
                        if (_es_cancelar) {
                            return ok.es;
                        }
                        ResourceBundle in;
                        String password = "";
                        String password_repetida = "";
                        for (controles control : _controles_lista) {
                            if (control.clave.equals(k_crear_wallet_contraseña_password)) {
                                password = (String) control.valor;
                                contraseña_stringbuilder.delete(0, contraseña_stringbuilder.length());
                                contraseña_stringbuilder.append(password);
                            } else if (control.clave.equals(k_crear_wallet_contraseña_repetida_password)) {
                                password_repetida = (String) control.valor;
                            }
                        }
                        if (password.equals(password_repetida) == false) {
                            in = ResourceBundle.getBundle(k_in_ruta);
                            escribir_linea_error(tr.in(in, "La contraseña y la contraseña repetida no coinciden. "), ok);
                            if (ok.es == false) { return ok.es; }
                            repetir(ok);
                            if (ok.es == false) { return false; }
                            ok.iniciar();
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                    return ok.es;
                }
            };
            control_entradas contraseña_control_entrada = new control_entradas();
            contraseña_control_entrada.iniciar(k_entradas_tipo_password, ok);
            if (ok.es == false) { return null; }
            contraseña_control_entrada.poner_en_formulario(clui_formulario, k_crear_wallet_contraseña_password
              , null, tr.in(in, "Escriba la contraseña para su nueva wallet. "), null, ok);
            if (ok.es == false) { return null; }
            control_entradas contraseña_repetida_control_entrada = new control_entradas();
            contraseña_repetida_control_entrada.iniciar(k_entradas_tipo_password, ok);
            if (ok.es == false) { return null; }
            contraseña_repetida_control_entrada.poner_en_formulario(clui_formulario, k_crear_wallet_contraseña_repetida_password
              , null, tr.in(in, "Repita la contraseña para su nueva wallet. "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok); // criptoy???
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                retorno = contraseña_stringbuilder.toString();
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }    
    /**
     * Llena la lista con los criptos indicadas en l archivo de configuración
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean llenar_lista_criptos(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            escribir_linea(tr.in(in, "Cargando datos de conexión con el contrato... Espera por favor (puede llevar bastante tiempo). "), ok, extra_array);
            I_erc20_token_web3j i_erc20_web3j;
            oks ok_ignorado = new oks();
            String error_tex = "";
            criptos_i_erc20_lista = new ArrayList<>();
            blockchain_coin_web3j = new Blockchain_coin_web3j();
            blockchain_coin_web3j.web3j = new web3js(web3j);
            blockchain_coin_web3j.nombre = web3j.web3_endpoint_https_nombre;
            blockchain_coin_web3j.simbolo = web3j.web3_endpoint_https_simbolo;
            blockchain_coin_web3j.decimales = web3j.web3_endpoint_https_decimales;
            criptos_i_erc20_lista.add(blockchain_coin_web3j);
            if (web3_direccion_contrato_kaloria != null) {
                kaloria_i_erc20_web3j = new I_erc20_token_web3j();
                kaloria_i_erc20_web3j.web3j = new web3js(web3j);
                kaloria_i_erc20_web3j.cargar_contrato(web3_direccion_contrato_kaloria, ok, extra_array);
                criptos_i_erc20_lista.add(kaloria_i_erc20_web3j);
            }
            if (criptos_lista != null) {
                for(String contrato: criptos_lista) {
                    i_erc20_web3j = new I_erc20_token_web3j();
                    i_erc20_web3j.web3j = new web3js(web3j);
                    i_erc20_web3j.cargar_contrato(contrato, ok_ignorado, extra_array);
                    if (ok_ignorado.es == false) {
                        i_erc20_web3j.web3j = null;
                        i_erc20_web3j.simbolo = contrato + " (" + (criptos_i_erc20_lista.size() + 1) + ") ";
                        error_tex = error_tex + tr.in(in, "Contrato no válido para: ") 
                          + i_erc20_web3j.simbolo;
                        ok_ignorado.iniciar();
                    }
                    criptos_i_erc20_lista.add(i_erc20_web3j);
                }
            }
            if (error_tex.isBlank() == false) {
                ok.id = k_contrato_no_valido;
                ok.setTxt(error_tex);
            }
            escribir_linea(tr.in(in, "Datos de conexión con el contrato cargados. "), ok, extra_array);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.es;
    }    
    /**
     * Llena la lista que representa la wallet con los datos de cada criptomoneda
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean llenar_wallet_lista(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            String texto;
            StringBuilder stringBuilder = new StringBuilder();
            wallet_lista.clear();
            LinkedHashMap<String, Object> fila = new LinkedHashMap<>();
            for(Erc20_web3j i_erc20_web3j : criptos_i_erc20_lista) {
                fila = new LinkedHashMap<>();
                if (i_erc20_web3j.getWeb3j() == null) {
                    fila.put(k_simbolo, i_erc20_web3j.getSimbolo());
                    fila.put(k_ballance, "--");
                } else {
                    if (i_erc20_web3j.getSimbolo() == null) {
                        texto = i_erc20_web3j.leer_simbolo(ok);
                        if (ok.es == false) { return false; }
                    } else {
                        texto = i_erc20_web3j.getSimbolo();
                    }
                    fila.put(k_simbolo, texto);
                    if (i_erc20_web3j.getDecimales() == null) {
                        i_erc20_web3j.leer_decimales(ok);
                        if (ok.es == false) { return false; }
                    }
                    i_erc20_web3j.leer_balance(stringBuilder, ok);
                    if (ok.es == false) { return false; }
                    fila.put(k_ballance, stringBuilder.toString());
                }
                wallet_lista.add(fila);
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return ok.es;
    }    

    /**
     * Crea el formulario de la wallet.
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean crear_formulario_wallet(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return ok.es; }
        ResourceBundle in = null;
        try {
            in = ResourceBundles.getBundle(k_in_ruta);
            wallet_control_tabla.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return ok.es; }
            Map<String, Object> opciones_mapa = new HashMap<>();
            opciones_mapa.put(k_control_tablas_opciones_mapa_lista, wallet_control_tabla.crear_tabla_vacia(ok, extra_array));
            if (ok.es == false) { return ok.es; }
            opciones_mapa.put(k_control_tablas_letras_por_linea_num, letras_por_linea);
            wallet_control_tabla.poner_en_formulario(wallet_clui_formulario, k_wallet_tabla, null, tr.in(in, "Wallet de criptomonedas "), opciones_mapa, ok);
            if (ok.es == false) { return ok.es; }
            wallet_control_seleccion.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return ok.es; }
            opciones_mapa = new HashMap<>();
            opciones_mapa.put(k_control_selecciones_opciones_mapa, new LinkedHashMap<String, Object>() {{
                ResourceBundle in = null;
                in = ResourceBundles.getBundle(k_in_ruta);
                put(k_seleccionar, tr.in(in,"Seleccionar"));
                put(k_añadir, tr.in(in,"Añadir"));
                put(k_cambiar_de_blockchain, tr.in(in,"Cambiar de blockchain"));
                put(k_transacciones, tr.in(in, "Ver transacciones"));
            }});
            opciones_mapa.put(k_control_selecciones_letras_por_linea_num, letras_por_linea);
            wallet_control_seleccion.poner_en_formulario(wallet_clui_formulario, k_wallet_seleccion, null, tr.in(in, "Seleccione una opción. "), opciones_mapa, ok);
            if (ok.es == false) { return ok.es; }
        } catch (Exception e) {
            throw e;
        }
        return ok.es;
    }
    
    public boolean poner_cabecera(LinkedList<LinkedHashMap<String, Object>> filas_lista, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        try {
            LinkedHashMap<String, Object> cabecera = new LinkedHashMap<>();
            String [] columnas_array;
            columnas_array = columnas_cabecera_tex.split(",");
            int i = 0;
            for (String columna: columnas_array) {
                columna = columna.trim();
                cabecera.put(columna, columna);
            }
            wallet_lista.addFirst(cabecera); 
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    
    /**
     * Crea y procesa el formulario de cambio de blockchain
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean procesar_formulario_de_cambio_de_blockchain(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_selecciones control_seleccion = new control_selecciones();
            control_seleccion.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return ok.es; }
            Map<String, Object> opciones_mapa = new HashMap<>();
            LinkedHashMap<String, Object> lista_opciones = new LinkedHashMap<>();
            int i = 0;
            for (web3_endpoint_https_datos_listas.filas fila : web3_endpoint_https_datos_lista.o) {
                lista_opciones.put(String.valueOf(i), " " + fila.nombre);
                i = i + 1;
            }
            opciones_mapa.put(k_control_selecciones_opciones_mapa, lista_opciones);
            opciones_mapa.put(k_control_selecciones_letras_por_linea_num, letras_por_linea);
            control_seleccion.poner_en_formulario(clui_formulario, k_cambiar_de_blockchain_seleccion
              , null, tr.in(in, "Seleccione una opción. "), opciones_mapa, ok);
            if (ok.es == false) { return ok.es; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return ok.es; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                String valor_tex = (String) control_seleccion.leer_seleccion(ok);
                int pos = Integer.parseInt(valor_tex);
                properties.setProperty(k_web3_endpoint_https_pos, valor_tex);
                terminar(ok);
                if (ok.es == false) { return ok.es; }
                web3j.web3_endpoint_https = web3_endpoint_https_datos_lista.o.get(pos).endpoint_https;
                web3j.web3_endpoint_https_nombre = web3_endpoint_https_datos_lista.o.get(pos).nombre;
                web3j.web3_endpoint_https_simbolo = web3_endpoint_https_datos_lista.o.get(pos).simbolo;
                web3j.web3_endpoint_https_decimales = web3_endpoint_https_datos_lista.o.get(pos).decimales;
                if (web3_direcciones_criptos_datos_lista != null
                  && pos < web3_direcciones_criptos_datos_lista.o.size()) {
                    criptos_lista = web3_direcciones_criptos_datos_lista.o.get(pos).criptos_lista;
                } else {
                    criptos_lista = null;
                }
                if (web3_direcciones_kaloria_datos_lista != null
                  && pos < web3_direcciones_kaloria_datos_lista.o.size()) {
                    web3_direccion_contrato_kaloria = web3_direcciones_kaloria_datos_lista.o.get(pos).direccion_cripto;
                } else {
                    web3_direccion_contrato_kaloria = null;
                }
                web3j.iniciar(k_wallet_ruta, ok);
                if (ok.es == false) { return false; }
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
    public Integer procesar_formulario_de_seleccion(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        Integer retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas seleccion_control_entrada = new control_entradas();
            seleccion_control_entrada.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return null; }
            seleccion_control_entrada.poner_en_formulario(clui_formulario, k_seleccionar_entrada
              , null, tr.in(in, "Introduzca el número de la criptomoneda a seleccionar. "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                Double doble = (Double) seleccion_control_entrada.valor;
                retorno = doble.intValue() - 1;
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Crea y procesa el formulario de añadir de criptomoneda
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public Integer procesar_formulario_de_añadir(oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        Integer retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas seleccion_control_entrada = new control_entradas();
            seleccion_control_entrada.iniciar(k_entradas_tipo_texto, ok);
            if (ok.es == false) { return null; }
            seleccion_control_entrada.poner_en_formulario(clui_formulario, k_añadir_entrada
              , null, tr.in(in, "Introduzca la direccion de la criptomoneda de la blockchain en curso. "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                if (ok.es == false) { return null; }
                String texto = seleccion_control_entrada.valor.toString();
                texto = texto.trim();
                if (texto.equals(web3_direccion_contrato_kaloria)) {
                    escribir_linea_error(tr.in(in, "Esa dirección ya está añadida. "), ok);
                    if (ok.es == false) { return null; }
                } else {
                    if (web3_direcciones_criptos_datos_lista == null) {
                        web3_direcciones_criptos_datos_lista = new web3_direcciones_criptos_datos_listas();
                        web3_direcciones_criptos_datos_lista.o = new LinkedList<>();
                        web3_direcciones_criptos_datos_listas.filas fila = new web3_direcciones_criptos_datos_listas.filas();
                        fila.pos = "0";
                        fila.criptos_lista = new LinkedList<>();
                        web3_direcciones_criptos_datos_lista.o.add(fila);
                    }
                    while (true) {
                        if (blockchain_pos < web3_direcciones_criptos_datos_lista.o.size()) {
                            break;
                        }
                        web3_direcciones_criptos_datos_listas.filas fila = new web3_direcciones_criptos_datos_listas.filas();
                        fila.pos = String.valueOf(web3_direcciones_criptos_datos_lista.o.size());
                        fila.criptos_lista = new LinkedList<>();
                        web3_direcciones_criptos_datos_lista.o.add(fila);
                    }
                    if (web3_direcciones_criptos_datos_lista.o.get(blockchain_pos).criptos_lista.indexOf(texto) < 0) {
                        web3_direcciones_criptos_datos_lista.o.get(blockchain_pos).criptos_lista.add(texto);
                        criptos_lista = web3_direcciones_criptos_datos_lista.o.get(blockchain_pos).criptos_lista;
                        ObjectMapper objectMapper = new ObjectMapper();
                        texto = objectMapper.writeValueAsString(web3_direcciones_criptos_datos_lista);
                        properties.setProperty(k_web3_direcciones_criptos_datos_lista, texto);
                        terminar(ok);
                        if (ok.es == false) { return null; }
                    } else {
                        escribir_linea_error(tr.in(in, "Esa dirección ya está añadida. "), ok);
                    }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Crea y procesa el formulario de quitar de criptomoneda
     * @param pos Posición en wallet_lista que quitar
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public Integer procesar_formulario_de_quitar(Integer pos, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        Integer retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas seleccion_control_entrada = new control_entradas();
            seleccion_control_entrada.iniciar(k_entradas_tipo_submit, ok);
            if (ok.es == false) { return null; }
            seleccion_control_entrada.poner_en_formulario(clui_formulario, k_quitar_submit
              , null, tr.in(in, "¿Está seguro de eliminar la criptomoneda del wallet? "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                String texto = seleccion_control_entrada.valor.toString();
                if (texto.equals("1")) {
                    if (pos < 2) {
                        escribir_linea_error(tr.in(in, "Las dos primeras criptomomedas no se pueden quitar. "), ok);
                        if (ok.es == false) { return null; }
                    } else {
                        web3_direcciones_criptos_datos_lista.o.get(blockchain_pos).criptos_lista.remove(pos - 2);
                        criptos_lista = web3_direcciones_criptos_datos_lista.o.get(blockchain_pos).criptos_lista;
                        criptos_i_erc20_lista.remove(pos.intValue());
                        ObjectMapper objectMapper = new ObjectMapper();
                        texto = objectMapper.writeValueAsString(web3_direcciones_criptos_datos_lista);
                        properties.setProperty(k_web3_direcciones_criptos_datos_lista, texto);
                        terminar(ok);
                        if (ok.es == false) { return null; }
                    }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Procesa el formulario de una criptomoneda
     * @param pos Posición en wallet_lista
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public String procesar_formulario_de_criptomoneda(Integer pos, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_selecciones criptomoneda_control_seleccion = new control_selecciones();
            criptomoneda_control_seleccion.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return null; }
            LinkedHashMap<String, Object> columnas_mapa = wallet_lista.get(pos + 1);
            for (Entry<String, Object> entry: columnas_mapa.entrySet()) {
                escribir_linea(entry.getKey() + ": " + entry.getValue(), ok, extra_array);
            }
            Map<String, Object> opciones_mapa = new HashMap<>();
            final Integer posicion = pos;
            opciones_mapa.put(k_control_selecciones_opciones_mapa, new LinkedHashMap<String, Object>() {{
                ResourceBundle in = null;
                in = ResourceBundles.getBundle(k_in_ruta);
                put(k_enviar, tr.in(in, "Enviar"));
                put(k_transacciones, tr.in(in, "Ver transacciones"));
                if (posicion >= 2) {
                    put(k_quitar, tr.in(in, "Quitar"));
                }
            }});
            opciones_mapa.put(k_control_selecciones_letras_por_linea_num, letras_por_linea);
            criptomoneda_control_seleccion.poner_en_formulario(clui_formulario, k_criptomoneda_seleccion
              , null, tr.in(in, "Seleccione una opción. "), opciones_mapa, ok);
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                retorno = criptomoneda_control_seleccion.leer_seleccion(ok, extra_array).toString();
                if (ok.es == false) { return null; }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
    public boolean escribir_informacion_transacciones(Integer pos, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            String simbolo = null;
            if (pos != null && pos >= 0) {
                simbolo = criptos_i_erc20_lista.get(pos).simbolo;
            }
            String texto = tr.in(in, "Pueden consultar las transacciones indicando la dirección de su wallet (<direccion_wallet>) dentro de la pagina web: <pagina_web>. ");
            texto = texto.replace("<direccion_wallet>", web3j.credentials.getAddress());
            texto = texto.replace("<pagina_web>", web3_endpoint_https_datos_lista.o.get(blockchain_pos).explorador_bloques_https);
            escribir_linea(texto, ok);
            web3_transacciones_mapas.filas fila;
            for (Entry<Long, web3_transacciones_mapas.filas> entry: web3_transacciones_mapa.o.descendingMap().entrySet()) {
                fila = entry.getValue();
                if (simbolo == null 
                 || fila.simbolo.equals(simbolo)) {
                    texto = formar_mensaje_transaccion(entry.getKey(), entry.getValue(), ok);
                    if (ok.es == false) { return false; }                            
                    escribir_linea(texto, ok);
                    if (ok.es == false) { return false; }                            
                }                
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.es;
    }
    /**
     * Forma un mensaje informativo de transaccion
     * @param milisegundos
     * @param fila
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public String formar_mensaje_transaccion(Long milisegundos, web3_transacciones_mapas.filas fila
      , oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        String texto = null;
        try {
            Date date = new Date(milisegundos);
            String fecha_tex = formar_fecha("", date, ok);
            if (ok.es == false) { return null; }
            texto = fecha_tex 
              + ", " + fila.destino_direccion
              + ", " + fila.cantidad
              + ", " + fila.simbolo
              + ", " + fila.transaccion_hash
              + ", " + fila.gas_usado.toString()
              + ", " + fila.precio_gas;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return texto;
    }
    /**
     * Crea y procesa el formulario de envío de criptomoneda
     * @param pos Posición en la wallet (sin cabecera)
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public Integer procesar_formulario_de_enviar(Integer pos, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return null; }
        Integer retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas enviar_direccion_control_entrada = new control_entradas();
            enviar_direccion_control_entrada.iniciar(k_entradas_tipo_texto, ok);
            if (ok.es == false) { return null; }
            enviar_direccion_control_entrada.poner_en_formulario(clui_formulario, k_enviar_direccion_entrada
              , null, tr.in(in, "Introduzca la direccion destinataria del envío. "), null, ok);
            if (ok.es == false) { return null; }
            control_entradas enviar_cantidad_control_entrada = new control_entradas();
            enviar_cantidad_control_entrada.iniciar(k_entradas_tipo_numero, ok);
            if (ok.es == false) { return null; }
            enviar_cantidad_control_entrada.poner_en_formulario(clui_formulario, k_enviar_cantidad_entrada
              , null, tr.in(in, "Introduzca la cantidad que enviar. "), null, ok);
            if (ok.es == false) { return null; }
            clui_formulario.procesar(ok);
            if (ok.es == false) { return null; }
            if (clui_formulario.ser_cancelar(ok) == false) {
                String direccion = enviar_direccion_control_entrada.valor.toString();
                Double doble = (Double) enviar_cantidad_control_entrada.valor;
                Erc20_web3j i_erc20_web3j = criptos_i_erc20_lista.get(pos);
                Integer decimales = i_erc20_web3j.getDecimales();
                int i = 0;
                while (true) {
                    if (i >= decimales) {
                        break;
                    }
                    doble = doble * 10.0;
                    i = i + 1;
                }
                BigInteger cantidad = BigInteger.valueOf(doble.longValue());
                BigInteger gas_estimado = i_erc20_web3j.estimar_gas_enviar(direccion, cantidad, ok);
                BigInteger precio_gas = i_erc20_web3j.web3j.estimar_coste_gas(gas_estimado, ok);
                if (_procesar_formulario_de_envio_aceptar_gas(gas_estimado, precio_gas, ok)) {
                    if (ok.es == false) { return null; }
                    escribir_linea(tr.in(in,"Envío en curso... Espere por favor. "), ok);
                    TransactionReceipt transactionReceipt = i_erc20_web3j.enviar(gas_estimado, direccion, cantidad, ok);
                    if (ok.es == false) { return null; }
                    web3_transacciones_mapas.filas fila = new web3_transacciones_mapas.filas();
                    fila.destino_direccion = direccion;
                    fila.cantidad = i_erc20_web3j.poner_decimales_a_numero(cantidad, ok, extra_array);
                    fila.simbolo = i_erc20_web3j.getSimbolo();
                    fila.transaccion_hash = transactionReceipt.getTransactionHash();
                    fila.gas_usado = transactionReceipt.getGasUsed();
                    BigInteger bigInteger = i_erc20_web3j.web3j.estimar_coste_gas(fila.gas_usado, ok);
                    if (ok.es == false) { return null; }
                    fila.precio_gas = blockchain_coin_web3j.poner_decimales_a_numero(bigInteger, ok);
                    if (ok.es == false) { return null; }
                    Long milisegundos = Instant.now().toEpochMilli();
                    web3_transacciones_mapa.o.put(milisegundos, fila);
                    String texto = formar_mensaje_transaccion(milisegundos, fila, ok);
                    if (ok.es == false) { return null; }
                    escribir_linea(tr.in(in, "Envío realizado. "), ok, extra_array);
                    if (ok.es == false) { return null; }
                    escribir_linea(texto, ok, extra_array);
                    if (ok.es == false) { return null; }
                }
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
    public boolean _procesar_formulario_de_envio_aceptar_gas(BigInteger cantidad_gas
      , BigInteger precio_gas, oks ok, Object... extra_array) throws Exception {
        if (ok.es == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_ruta);
        try {
            clui_formularios clui_formulario = new clui_formularios();
            control_entradas seleccion_control_entrada = new control_entradas();
            seleccion_control_entrada.iniciar(k_entradas_tipo_submit, ok);
            if (ok.es == false) { return false; }
            seleccion_control_entrada.poner_en_formulario(clui_formulario, k_quitar_submit
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
            escribir_linea(tr.in(in, "Estimación del precio por ese gas (al alza): ") + precio, ok, extra_array);
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
}
