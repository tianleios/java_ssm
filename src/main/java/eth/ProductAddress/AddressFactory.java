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
public class AddressFactory {

    public static void main(String[] args) {

//        String seed = "1234545dfsfsdf56";
//        Account account = new Account();
//        account.init(ecKey);

        //1.23ds
//        ECKey ecKey = ECKey.fromPrivate(HashUtil.sha3(seed.getBytes()));
//
//        //地址
//        byte[] address = ecKey.getAddress();
//        String addressHex = toJsonHex(address);
//
//        //获取私钥
//        BigInteger privateKey = ecKey.getPrivKey();
//        String privateKeyStr = privateKey.toString(16);
//
//        //获取公钥
//        byte[] publicKey = ecKey.getPubKey();
//        String pubKeyHexStr = toJsonHex(publicKey);
//
//        try {
//            ECKeyPair ecKeyPair =  ECKeyPair.create(privateKey);
//            WalletFile walletFile = Wallet.createLight("123456",ecKeyPair);
//            int a = 10;
//
//        } catch (Exception e) {
//
//        }

        product();



    }

    //
    private static void product() {
        ECKey key = new ECKey();

        byte[] addr = key.getAddress();
        byte[] priv = key.getPrivKeyBytes();
        byte[] pub =  key.getPubKey();

        String addrBase16 = Hex.toHexString(addr);
        String privBase16 = Hex.toHexString(priv);
        String pubBase16 = Hex.toHexString(pub);



        System.out.println("Address     : " + addrBase16);
        System.out.println("Private Key : " + privBase16);

        //
        try {

            ECKeyPair ecKeyPair =  ECKeyPair.create(key.getPrivKey());
            WalletFile walletFile = Wallet.createStandard("password",ecKeyPair);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(walletFile);
            String filename = "UTC--" + getISO8601(new Date()) + "--" + addrBase16;
            saveFile(jsonStr,filename);

        } catch (Exception e) {

            System.out.print("创建WalletFile失败");

        }


    }

    private static void saveFile(String content,String fileName)  {
        //
        try {

            String path = "/Users/tianlei/Desktop/keystore" + "/" + fileName;
            File file =  new File(path);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.print(content);

        } catch (Exception e) {

            System.out.print("创建失败");
        }
    }

    public static String getISO8601(Date date){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }


    public static String toJsonHex(byte[] x) {
        return "0x"+ Hex.toHexString(x);
    }
}


