package eth.ProductAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.jce.ECKeyPairGenerator;
import org.ethereum.crypto.jce.SpongyCastleProvider;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;

//import org.web3j.crypto.eck

//import org.ethereum.crypto.ec

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class ETHAddressFactory {

    private static final SecureRandom secureRandom;

    static {

        secureRandom = new SecureRandom();
    }

    public static void main(String[] args) {

        try {

            String addr = productAddress("password");

        } catch (Exception e) {

        }


    }

    static private BigInteger privateKey() {

         KeyPairGenerator keyPairGen = ECKeyPairGenerator.getInstance(SpongyCastleProvider.getInstance(), secureRandom);
         KeyPair keyPair = keyPairGen.generateKeyPair();

         //
        PrivateKey privateKey = keyPair.getPrivate();

        BigInteger bigInteger = ((BCECPrivateKey) privateKey).getD();

        return bigInteger;
    }

    public static String productAddress(String password) throws Exception {



        //
        org.ethereum.crypto.ECKey key = new ECKey();
//
        byte[] addr = key.getAddress();
        byte[] priv = key.getPrivKeyBytes();
        byte[] pub = key.getPubKey();
//
        String addrBase16 = Hex.toHexString(addr);
        String privBase16 = Hex.toHexString(priv);
        String pubBase16 = Hex.toHexString(pub);

        BigInteger privateKey = key.getPrivKey();
//        BigInteger privateKey = privateKey();

//        try {

        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(walletFile);
        String filename = getFileName(addrBase16);
        String filePath = "/Users/tianlei/Desktop/keystore" + "/" + filename;

        //
        saveFile(jsonStr, filePath);

        return "0x" + addrBase16;

//        } catch (Exception e) {
//
//            System.out.print("创建WalletFile失败");
//            return null;
//
//        }


    }

    private static String getFileName(String address) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern(
                "'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.format(format) + address;
    }

    private static void saveFile(String content, final String filePath) {
        //
        try {
            //
            File file = new File(filePath);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.print(content);

        } catch (Exception e) {

            throw new RuntimeException("失败");

        }

    }


}
