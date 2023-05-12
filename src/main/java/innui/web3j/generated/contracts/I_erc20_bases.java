package innui.web3j.generated.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class I_erc20_bases extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final Event AD_AD_U_EVENT = new Event("Ad_ad_u", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event MENSAJE_EVENT = new Event("Mensaje", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event OK_EVENT = new Event("Ok", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected I_erc20_bases(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected I_erc20_bases(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected I_erc20_bases(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected I_erc20_bases(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<Ad_ad_uEventResponse> getAd_ad_uEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AD_AD_U_EVENT, transactionReceipt);
        ArrayList<Ad_ad_uEventResponse> responses = new ArrayList<Ad_ad_uEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Ad_ad_uEventResponse typedResponse = new Ad_ad_uEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.mensaje = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static Ad_ad_uEventResponse getAd_ad_uEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AD_AD_U_EVENT, log);
        Ad_ad_uEventResponse typedResponse = new Ad_ad_uEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.mensaje = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<Ad_ad_uEventResponse> ad_ad_uEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAd_ad_uEventFromLog(log));
    }

    public Flowable<Ad_ad_uEventResponse> ad_ad_uEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AD_AD_U_EVENT));
        return ad_ad_uEventFlowable(filter);
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

    public static List<OkEventResponse> getOkEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OK_EVENT, transactionReceipt);
        ArrayList<OkEventResponse> responses = new ArrayList<OkEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OkEventResponse typedResponse = new OkEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.es = (Boolean) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.mensaje = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OkEventResponse getOkEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OK_EVENT, log);
        OkEventResponse typedResponse = new OkEventResponse();
        typedResponse.log = log;
        typedResponse.es = (Boolean) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.mensaje = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OkEventResponse> okEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOkEventFromLog(log));
    }

    public Flowable<OkEventResponse> okEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OK_EVENT));
        return okEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String who) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static I_erc20_bases load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new I_erc20_bases(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static I_erc20_bases load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new I_erc20_bases(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static I_erc20_bases load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new I_erc20_bases(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static I_erc20_bases load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new I_erc20_bases(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<I_erc20_bases> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(I_erc20_bases.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<I_erc20_bases> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(I_erc20_bases.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<I_erc20_bases> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(I_erc20_bases.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<I_erc20_bases> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(I_erc20_bases.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Ad_ad_uEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;

        public String mensaje;
    }

    public static class MensajeEventResponse extends BaseEventResponse {
        public String mensaje;
    }

    public static class OkEventResponse extends BaseEventResponse {
        public Boolean es;

        public String mensaje;
    }
}
