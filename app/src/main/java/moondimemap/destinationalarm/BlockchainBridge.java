package moondimemap.destinationalarm;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class BlockchainBridge {
    Web3j web3;
    Moondimetesttoken contract;
    Credentials credentials;

    public BlockchainBridge() {
        this.web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/b5bf9e9d22514ecfbe25f474f387774f"));
        this.credentials = Credentials.create("02381aa245d8a4772385db9abeb10909d661757f73fdb7f0cdb6ccd17396e920");
        System.out.println(credentials.getAddress());

        this.contract = Moondimetesttoken.load(
                "0x3dd8404CcFB923B3a65EFb5E0475ea853C2Db26C",
                web3,
                credentials,
                new DefaultGasProvider()
        );
    }

    public void mintToken() {
        // mint 1 token
        try {
            this.contract.mint(this.credentials.getAddress(), new BigInteger("1000000000000000000")).sendAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BigInteger getWalletBalance() {
        BigInteger balance = new BigInteger("0");
        try {
            balance = this.contract.balanceOf(this.credentials.getAddress()).sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return balance.divide(new BigInteger("1000000000000000000"));
    }
}
