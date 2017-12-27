package com.tianlei.keystore;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.Certificate;


public class Keystore {

//    //导出证书 base64格式
//    public static void exportCert(KeyStore keystore, String alias, String exportFile) throws Exception {
//        Certificate cert = keystore.getCertificate(alias);
//        BASE64Encoder encoder = new BASE64Encoder();
//        String encoded = encoder.encode(cert.getEncoded());
//        FileWriter fw = new FileWriter(exportFile);
//        fw.write("-----BEGIN CERTIFICATE-----\r\n");    //非必须
//        fw.write(encoded);
//        fw.write("\r\n-----END CERTIFICATE-----");  //非必须
//        fw.close();
//    }

    //得到KeyPair  
    public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) {
        try {
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    //导出私钥  
    public static void exportPrivateKey(PrivateKey privateKey,String exportFile) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(privateKey.getEncoded());
        FileWriter fw = new FileWriter(exportFile);
        fw.write("—–BEGIN PRIVATE KEY—–\r\n");  //非必须  
        fw.write(encoded);
        fw.write("\r\n—–END PRIVATE KEY—–");        //非必须  
        fw.close();
    }

    //导出公钥  
//    public static void exportPublicKey(PublicKey publicKey,String exportFile) throws Exception {
//        BASE64Encoder encoder = new BASE64Encoder();
//        String encoded = encoder.encode(publicKey.getEncoded());
//        FileWriter fw = new FileWriter(exportFile);
//        fw.write("—–BEGIN PUBLIC KEY—–\r\n");       //非必须
//        fw.write(encoded);
//        fw.write("\r\n—–END PUBLIC KEY—–");     //非必须
//        fw.close();
//    }

    public static void main(String args[]) throws Exception {

        String keyStoreType = "JKS";
        String keystoreFile = "/Users/tianlei/Documents/coin/cer/bcoin_keystore";
        String password = "bCoin2017_online";

        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        keystore.load(new FileInputStream(new File(keystoreFile)), password.toCharArray());

        String alias = "bcoin";
//        String exportCertFile = "C:/Documents and Settings/zhao/cms.cer";
        String exportPrivateFile = "/Users/tianlei/Documents/coin/cer/cmsPrivateKey.txt";
//        String exportPublicFile = "C:/Documents and Settings/zhao/cmsPublicKey.txt";

//        exportCert(keystore, alias, exportCertFile);
        KeyPair keyPair = getKeyPair(keystore, alias, password.toCharArray());
        exportPrivateKey(keyPair.getPrivate(), exportPrivateFile);
//        ExportCert.exportPublicKey(keyPair.getPublic(), exportPublicFile);

        System.out.println("OK");
    }
}  