package com.azazar;

import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import com.azazar.bitcoin.jsonrpcclient.BitcoinException;
import com.azazar.bitcoin.jsonrpcclient.BitcoinJSONRPCClient;
import com.azazar.bitcoin.jsonrpcclient.BitcoinRawTxBuilder;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.params.RegTestParams;

import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianlei on 2018/一月/12.
 */
public class Playground {

    static BitcoinJSONRPCClient bitcoin = new BitcoinJSONRPCClient(true);


    //
    public static void main(String[] args) {

//        String rawTx = rawTransaction();
//
//        //1.
//        String bitcoinSignStr = Playground.signUseBitcoinj(rawTx);
//
//        //2.
//        String sign2 = testSign(rawTx);
//
//        //
//        if (bitcoinSignStr.equals(sign2)) {
//            //正确
//            int a = 10;
//        } else {
//            //错误
//
//        }

    }



    //
    public void testGetBalance() {

        try {
            // 获取余额
//            Bitcoin bitcoin = new BitcoinJSONRPCClient("http://localhost:8332");
            Bitcoin bitcoin = new BitcoinJSONRPCClient(true);


            double balacne = bitcoin.getBalance();
            System.out.print(balacne);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //


    }

    public static String rawTransaction() {
        //

        try {
            BitcoinRawTxBuilder bitcoinRawTxBuilder = new BitcoinRawTxBuilder(bitcoin);

            //in
            bitcoinRawTxBuilder.in("095c24a057b774ed32839a93d712ffda916aca17268dcae92d498f9d4aff0341", 0);

//            bitcoinRawTxBuilder.in("095c24a057b774ed32839a93d712ffda916aca17268dcae92d498f9d4aff0341", 1);
//            bitcoinRawTxBuilder.in("095c24a057b774ed32839a93d712ffda916aca17268dcae92d498f9d4aff0342", 0);

            //out
            bitcoinRawTxBuilder.out("mqbnT8M6N2mFCK1qn31LNDZxr3eunxFq7K", 89);

            //创建交易结果
            String result = bitcoinRawTxBuilder.create();
            return result;

        } catch (Exception e) {

            return null;
        }

    }

    //
    public static String testSign(@NotNull String rawTransaction) {


        //
        try {
            //
            Set privateKeys = new HashSet();
            privateKeys.add("cPoPmrma4Ep6WtV2eU5QPyk1wFMweRjq6dwKF99DwPDFLc31Bstt");

            //然后拿到结果进行签名
            String signResult = signRawTransaction(rawTransaction, bitcoin, privateKeys);

            return signResult;
            //
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }


    public static String signUseBitcoinj(String rawHex) {

        rawHex = "15953935a135031bfe43432c37d36a9d662aea43e1deb0ea463d6932ac6e537cb3e81";
        //
        String wif = "cPoPmrma4Ep6WtV2eU5QPyk1wFMweRjq6dwKF99DwPDFLc31Bstt";
        DumpedPrivateKey dpk = DumpedPrivateKey.fromBase58(null, wif);
        ECKey key = dpk.getKey();

        // 检查object
        NetworkParameters regTestParams = RegTestParams.get();
        String check = key.getPrivateKeyAsWiF(regTestParams);
        System.out.println(wif.equals(check));  // true

        //
        Sha256Hash hash = Sha256Hash.wrap(rawHex);
        ECKey.ECDSASignature sig = key.sign(hash);
        byte[] res = sig.encodeToDER();
        // converting to hex
        String hex = DatatypeConverter.printHexBinary(res);
        return hex;

    }

    /*
    *  privateKeys_转出账户的私钥集合
    * */
    public static String signRawTransaction(String hex, BitcoinJSONRPCClient bitcoin, Set privateKeys) throws BitcoinException {

        Map result = (Map) bitcoin.query("signrawtransaction", hex, new ArrayList(), privateKeys);
        if ((Boolean) result.get("complete")) {
            return (String) result.get("hex");
        } else {
            throw new BitcoinException("Incomplete");
        }

    }

}
