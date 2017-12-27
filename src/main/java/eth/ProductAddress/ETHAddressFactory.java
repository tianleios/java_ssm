package eth.ProductAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class ETHAddressFactory {


    public static void main(String[] args) {

        String addr = productAddress("password");


    }

    private static String productAddress(String password) {

        ECKey key = new ECKey();

        byte[] addr = key.getAddress();
        byte[] priv = key.getPrivKeyBytes();
        byte[] pub = key.getPubKey();

        String addrBase16 = Hex.toHexString(addr);
        String privBase16 = Hex.toHexString(priv);
        String pubBase16 = Hex.toHexString(pub);

        try {

            ECKeyPair ecKeyPair = ECKeyPair.create(key.getPrivKey());
            WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(walletFile);
            String filename = "UTC--" + getISO8601(new Date()) + "--" + addrBase16;
            String filePath = "/Users/tianlei/Desktop/keystore" + "/" + filename;

            //
            saveFile(jsonStr, filePath);

            return "0x" + addrBase16;

        } catch (Exception e) {

            System.out.print("创建WalletFile失败");
            return null;

        }


    }

    private static void saveFile(String content,final String filePath) {
        //
        try {

            File file = new File(filePath);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.print(content);

        } catch (Exception e) {

            throw new RuntimeException("失败");
        }
    }

    public static String getISO8601(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }
}
