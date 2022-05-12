package moondimemap.destinationalarm;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BlockchainBridge {
    private final Web3j web3;
    private final Credentials credentials;
    private final MoondimeToken contract;

    public BlockchainBridge(Web3j web3, Credentials credentials, MoondimeToken contract) {
        this.web3 = web3;
        this.credentials = credentials;
        this.contract = contract;
    }

    // mint 1 token
    public void mintToken() {
        CompletableFuture<TransactionReceipt> receipt = contract.mint(credentials.getAddress(), new BigInteger("1000000000000000000")).sendAsync();
        receipt.thenAccept(transactionReceipt -> {
            System.out.println("Minted 1 MDT");
        }).exceptionally(transactionReceipt -> {
            System.out.println(transactionReceipt.getMessage());
            return null;
        });
    }

    public BigInteger getWalletBalance() {
        BigInteger balance = new BigInteger("0");
        try {
            balance = contract.balanceOf(credentials.getAddress()).sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return balance.divide(new BigInteger("1000000000000000000"));
    }
}
