package innui.web3j.generated.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.8.
 */
@SuppressWarnings("rawtypes")
public class Minibases extends Contract {
    public static final String BINARY = "60806040526001805460ff1916811790553480156200001d57600080fd5b507fa3c796c1157c3cb9fb6f34e43d73431e67f443a38176c32c7dd3b91e150091676040516200007e9060208082526018908201527f6d696e696261736573202d3e20636f6e7374727563746f720000000000000000604082015260600190565b60405180910390a16040805160808101825260606020808301828152600084860152828401929092523383528351808501855260048152631c9bdbdd60e21b918101919091529052905169726f6f745f636c61766560b01b8152600290600a01602060405180830381855afa158015620000fc573d6000803e3d6000fd5b5050506040513d601f19601f82011682018060405250810190620001219190620002a7565b6040828101919091528051600180825281830190925290602080830190803683375050506060820181905280516d7065726d69736f5f6d6178696d6f60901b9190600090620001745762000174620002d7565b60209081029190910181019190915260008054600181018255908052825160049091027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563810180546001600160a01b039093166001600160a01b03199093169290921782559183015183927f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e56401906200020e90826200037c565b5060408201516002820155606082015180516200023691600384019160209091019062000240565b5050505062000448565b8280548282559060005260206000209081019282156200027e579160200282015b828111156200027e57825182559160200191906001019062000261565b506200028c92915062000290565b5090565b5b808211156200028c576000815560010162000291565b600060208284031215620002ba57600080fd5b5051919050565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b600181811c908216806200030257607f821691505b6020821081036200032357634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200037757600081815260208120601f850160051c81016020861015620003525750805b601f850160051c820191505b8181101562000373578281556001016200035e565b5050505b505050565b81516001600160401b03811115620003985762000398620002c1565b620003b081620003a98454620002ed565b8462000329565b602080601f831160018114620003e85760008415620003cf5750858301515b600019600386901b1c1916600185901b17855562000373565b600085815260208120601f198616915b828110156200041957888601518255948401946001909101908401620003f8565b5085821015620004385787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6114aa80620004586000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c8063a9a4079c1161008c578063c49c3d0511610066578063c49c3d0514610199578063e3dbc944146101a1578063f5a57136146101b4578063f9d06c4d146101bc57600080fd5b8063a9a4079c14610176578063b2be353c14610189578063b776a7a21461019157600080fd5b80631d032d01146100d45780632dd47cac146100fa5780634dc4643714610104578063688e3467146101275780637b318b6e146101415780638e3ee7ba14610154575b600080fd5b6100e76100e2366004610e76565b6101cf565b6040519081526020015b60405180910390f35b6101026102ba565b005b610117610112366004610e76565b6102f5565b60405190151581526020016100f1565b6001546101349060ff1681565b6040516100f19190610eae565b61010261014f366004610f79565b61035d565b610167610162366004610ffe565b61053b565b6040516100f19392919061103b565b610102610184366004611085565b610603565b61010261082c565b610102610987565b6101026109bd565b6101026101af3660046110f9565b6109f5565b6100e7610b26565b6101026101ca366004610e76565b610b94565b60008060015460ff1660028111156101e9576101e9610e98565b1461023b5760405162461bcd60e51b815260206004820152601c60248201527f456c20636f6e747261746f206e6f20657374c3a12061637469766f2e0000000060448201526064015b60405180910390fd5b6000805b60005481101561029b576000818154811061025c5761025c61116d565b60009182526020909120600490910201546001600160a01b0390811690851603610289576001915061029b565b610294816001611199565b905061023f565b81156102aa5791506102b59050565b506000199392505050565b919050565b6102c3336102f5565b6102df5760405162461bcd60e51b8152600401610232906111b2565b600180546000919060ff191681835b0217905550565b600080805b60005481101561035657600081815481106103175761031761116d565b60009182526020909120600490910201546001600160a01b03908116908516036103445760019150610356565b61034f816001611199565b90506102fa565b5092915050565b610366336102f5565b6103825760405162461bcd60e51b8152600401610232906111b2565b6000546002106103fa5760405162461bcd60e51b815260206004820152603e60248201527f536f6c6f207365207065726d6974652061637475616c697a617220736920686160448201527f79206d617320646520646f732061646d696e6973747261646f7265732e2000006064820152608401610232565b6000610405856101cf565b905060008112156104285760405162461bcd60e51b8152600401610232906111b2565b8351156104625783600082815481106104435761044361116d565b906000526020600020906004020160010190816104609190611283565b505b8251156104e2576002836040516104799190611343565b602060405180830381855afa158015610496573d6000803e3d6000fd5b5050506040513d601f19601f820116820180604052508101906104b9919061135f565b600082815481106104cc576104cc61116d565b9060005260206000209060040201600201819055505b6001600160a01b038216156105345781600082815481106105055761050561116d565b6000918252602090912060049091020180546001600160a01b0319166001600160a01b03929092169190911790555b5050505050565b6000818154811061054b57600080fd5b6000918252602090912060049091020180546001820180546001600160a01b0390921693509061057a906111fa565b80601f01602080910402602001604051908101604052809291908181526020018280546105a6906111fa565b80156105f35780601f106105c8576101008083540402835291602001916105f3565b820191906000526020600020905b8154815290600101906020018083116105d657829003601f168201915b5050505050908060020154905083565b61060c336102f5565b6106285760405162461bcd60e51b8152600401610232906111b2565b6000805b60005481101561068857600081815481106106495761064961116d565b60009182526020909120600490910201546001600160a01b03908116908616036106765760019150610688565b610681816001611199565b905061062c565b81156106f45760405162461bcd60e51b815260206004820152603560248201527f4573612064697265636369c3b36e2064652061646d696e6973747261646f722060448201527403cb09032b9ba61d0903932b3b4b9ba3930b230971605d1b6064820152608401610232565b604080516080810182526000818301526060808201526001600160a01b038716815260208101869052905160029061072d908690611343565b602060405180830381855afa15801561074a573d6000803e3d6000fd5b5050506040513d601f19601f8201168201806040525081019061076d919061135f565b60408201526000805460018101825590805281517f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563600490920291820180546001600160a01b0319166001600160a01b03909216919091178155602083015183927f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e56401906107fb9082611283565b506040820151600282015560608201518051610821916003840191602090910190610d68565b505050505050505050565b610835336102f5565b6108515760405162461bcd60e51b8152600401610232906111b2565b600061085c336101cf565b9050600081121561087f5760405162461bcd60e51b8152600401610232906111b2565b6000805461088f90600190611378565b8154811061089f5761089f61116d565b9060005260206000209060040201600082815481106108c0576108c061116d565b60009182526020909120825460049092020180546001600160a01b0319166001600160a01b039092169190911781556001808201906109019084018261138b565b50600282015481600201556003820181600301908054610922929190610db3565b5090505060008054806109375761093761145e565b60008281526020812060046000199093019283020180546001600160a01b0319168155906109686001830182610df3565b60028201600090556003820160006109809190610e30565b5050905550565b610990336102f5565b6109ac5760405162461bcd60e51b8152600401610232906111b2565b60018054819060ff191681806102ee565b6109c6336102f5565b6109e25760405162461bcd60e51b8152600401610232906111b2565b600180546002919060ff191681836102ee565b6109fe336102f5565b610a1a5760405162461bcd60e51b8152600401610232906111b2565b336000610a26826101cf565b90506000811215610a495760405162461bcd60e51b8152600401610232906111b2565b845115610a83578460008281548110610a6457610a6461116d565b90600052602060002090600402016001019081610a819190611283565b505b835115610b0357600284604051610a9a9190611343565b602060405180830381855afa158015610ab7573d6000803e3d6000fd5b5050506040513d601f19601f82011682018060405250810190610ada919061135f565b60008281548110610aed57610aed61116d565b9060005260206000209060040201600201819055505b6001600160a01b038316156105345782600082815481106105055761050561116d565b60008060015460ff166002811115610b4057610b40610e98565b14610b8d5760405162461bcd60e51b815260206004820152601c60248201527f456c20636f6e747261746f206e6f20657374c3a12061637469766f2e000000006044820152606401610232565b5060005490565b610b9d336102f5565b610bb95760405162461bcd60e51b8152600401610232906111b2565b600054600210610c315760405162461bcd60e51b815260206004820152603a60248201527f536f6c6f207365207065726d69746520626f7272617220736920686179206d6160448201527f7320646520646f732061646d696e6973747261646f7265732e200000000000006064820152608401610232565b6000610c3c826101cf565b90506000811215610c5f5760405162461bcd60e51b8152600401610232906111b2565b60008054610c6f90600190611378565b81548110610c7f57610c7f61116d565b906000526020600020906004020160008281548110610ca057610ca061116d565b60009182526020909120825460049092020180546001600160a01b0319166001600160a01b03909216919091178155600180820190610ce19084018261138b565b50600282015481600201556003820181600301908054610d02929190610db3565b509050506000805480610d1757610d1761145e565b60008281526020812060046000199093019283020180546001600160a01b031916815590610d486001830182610df3565b6002820160009055600382016000610d609190610e30565b505090555050565b828054828255906000526020600020908101928215610da3579160200282015b82811115610da3578251825591602001919060010190610d88565b50610daf929150610e4a565b5090565b828054828255906000526020600020908101928215610da35760005260206000209182015b82811115610da3578254825591600101919060010190610dd8565b508054610dff906111fa565b6000825580601f10610e0f575050565b601f016020900490600052602060002090810190610e2d9190610e4a565b50565b5080546000825590600052602060002090810190610e2d91905b5b80821115610daf5760008155600101610e4b565b80356001600160a01b03811681146102b557600080fd5b600060208284031215610e8857600080fd5b610e9182610e5f565b9392505050565b634e487b7160e01b600052602160045260246000fd5b6020810160038310610ed057634e487b7160e01b600052602160045260246000fd5b91905290565b634e487b7160e01b600052604160045260246000fd5b600082601f830112610efd57600080fd5b813567ffffffffffffffff80821115610f1857610f18610ed6565b604051601f8301601f19908116603f01168101908282118183101715610f4057610f40610ed6565b81604052838152866020858801011115610f5957600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060008060808587031215610f8f57600080fd5b610f9885610e5f565b9350602085013567ffffffffffffffff80821115610fb557600080fd5b610fc188838901610eec565b94506040870135915080821115610fd757600080fd5b50610fe487828801610eec565b925050610ff360608601610e5f565b905092959194509250565b60006020828403121561101057600080fd5b5035919050565b60005b8381101561103257818101518382015260200161101a565b50506000910152565b60018060a01b03841681526060602082015260008351806060840152611068816080850160208801611017565b604083019390935250601f91909101601f19160160800192915050565b60008060006060848603121561109a57600080fd5b6110a384610e5f565b9250602084013567ffffffffffffffff808211156110c057600080fd5b6110cc87838801610eec565b935060408601359150808211156110e257600080fd5b506110ef86828701610eec565b9150509250925092565b60008060006060848603121561110e57600080fd5b833567ffffffffffffffff8082111561112657600080fd5b61113287838801610eec565b9450602086013591508082111561114857600080fd5b5061115586828701610eec565b92505061116460408501610e5f565b90509250925092565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b808201808211156111ac576111ac611183565b92915050565b60208082526028908201527f4c6120646972656363696f6e206e6f20657320646520756e2061646d696e697360408201526703a3930b237b917160c51b606082015260800190565b600181811c9082168061120e57607f821691505b60208210810361122e57634e487b7160e01b600052602260045260246000fd5b50919050565b601f82111561127e57600081815260208120601f850160051c8101602086101561125b5750805b601f850160051c820191505b8181101561127a57828155600101611267565b5050505b505050565b815167ffffffffffffffff81111561129d5761129d610ed6565b6112b1816112ab84546111fa565b84611234565b602080601f8311600181146112e657600084156112ce5750858301515b600019600386901b1c1916600185901b17855561127a565b600085815260208120601f198616915b82811015611315578886015182559484019460019091019084016112f6565b50858210156113335787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b60008251611355818460208701611017565b9190910192915050565b60006020828403121561137157600080fd5b5051919050565b818103818111156111ac576111ac611183565b818103611396575050565b6113a082546111fa565b67ffffffffffffffff8111156113b8576113b8610ed6565b6113c6816112ab84546111fa565b6000601f8211600181146113fa57600083156113e25750848201545b600019600385901b1c1916600184901b178455610534565b600085815260209020601f19841690600086815260209020845b838110156114345782860154825560019586019590910190602001611414565b50858310156113335793015460001960f8600387901b161c19169092555050600190811b01905550565b634e487b7160e01b600052603160045260246000fdfea2646970667358221220a8dfe2e2c80e812c2b3964dcf59e6477134867020b52fdb7bc53c17c45a89c6464736f6c63430008110033";

    public static final String FUNC_ACL_ARRAY = "acl_array";

    public static final String FUNC_ACTIVAR = "activar";

    public static final String FUNC_actualizar_administrador_usuario_clave_direccion = "actualizar_administrador_usuario_clave_direccion";

    public static final String FUNC_borrar_administrador = "borrar_administrador";

    public static final String FUNC_CREAR_ADMINISTRADOR = "crear_administrador";

    public static final String FUNC_ESTADO = "estado";

    public static final String FUNC_INACTIVAR = "inactivar";

    public static final String FUNC_LEER_ADMINISTRADOR_POS = "leer_administrador_pos";

    public static final String FUNC_LEER_ADMINISTRADORES_NUM = "leer_administradores_num";

    public static final String FUNC_LIMITAR = "limitar";

    public static final String FUNC_SER_ADMINISTRADOR = "ser_administrador";

    public static final Event MENSAJE_EVENT = new Event("Mensaje", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Minibases(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Minibases(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Minibases(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Minibases(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<MensajeEventResponse> getMensajeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MENSAJE_EVENT, transactionReceipt);
        ArrayList<MensajeEventResponse> responses = new ArrayList<MensajeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MensajeEventResponse typedResponse = new MensajeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.mensaje = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MensajeEventResponse getMensajeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MENSAJE_EVENT, log);
        MensajeEventResponse typedResponse = new MensajeEventResponse();
        typedResponse.log = log;
        typedResponse.mensaje = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MensajeEventResponse> mensajeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMensajeEventFromLog(log));
    }

    public Flowable<MensajeEventResponse> mensajeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MENSAJE_EVENT));
        return mensajeEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple3<String, String, byte[]>> acl_array(BigInteger param0) {
        final Function function = new Function(FUNC_ACL_ARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, byte[]>>(function,
                new Callable<Tuple3<String, String, byte[]>>() {
                    @Override
                    public Tuple3<String, String, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, byte[]>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> activar() {
        final Function function = new Function(
                FUNC_ACTIVAR, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> actualizar_administrador_usuario_clave_direccion(String direccion, String usuario, byte[] clave, String nueva_direccion) {
        final Function function = new Function(
                FUNC_actualizar_administrador_usuario_clave_direccion, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, direccion), 
                new org.web3j.abi.datatypes.Utf8String(usuario), 
                new org.web3j.abi.datatypes.DynamicBytes(clave), 
                new org.web3j.abi.datatypes.Address(160, nueva_direccion)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> actualizar_administrador_usuario_clave_direccion(String usuario, byte[] clave, String nueva_direccion) {
        final Function function = new Function(
                FUNC_actualizar_administrador_usuario_clave_direccion, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(usuario), 
                new org.web3j.abi.datatypes.DynamicBytes(clave), 
                new org.web3j.abi.datatypes.Address(160, nueva_direccion)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> borrar_administrador() {
        final Function function = new Function(
                FUNC_borrar_administrador, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> borrar_administrador(String direccion) {
        final Function function = new Function(
                FUNC_borrar_administrador, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, direccion)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crear_administrador(String direccion, String usuario, byte[] clave) {
        final Function function = new Function(
                FUNC_CREAR_ADMINISTRADOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, direccion), 
                new org.web3j.abi.datatypes.Utf8String(usuario), 
                new org.web3j.abi.datatypes.DynamicBytes(clave)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> estado() {
        final Function function = new Function(FUNC_ESTADO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> inactivar() {
        final Function function = new Function(
                FUNC_INACTIVAR, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> leer_administrador_pos(String direccion) {
        final Function function = new Function(FUNC_LEER_ADMINISTRADOR_POS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, direccion)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> leer_administradores_num() {
        final Function function = new Function(FUNC_LEER_ADMINISTRADORES_NUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> limitar() {
        final Function function = new Function(
                FUNC_LIMITAR, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> ser_administrador(String direccion) {
        final Function function = new Function(FUNC_SER_ADMINISTRADOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, direccion)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static Minibases load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Minibases(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Minibases load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Minibases(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Minibases load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Minibases(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Minibases load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Minibases(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Minibases> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Minibases.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Minibases> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Minibases.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Minibases> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Minibases.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Minibases> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Minibases.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class MensajeEventResponse extends BaseEventResponse {
        public String mensaje;
    }
}
