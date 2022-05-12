package moondimemap.destinationalarm;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BlockchainBridge {
    private final Credentials credentials;
    private final MoondimeToken contract;

    public BlockchainBridge(Credentials credentials, MoondimeToken contract) {
        this.credentials = credentials;
        this.contract = contract;
    }

    // mint 1 MDT token
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
