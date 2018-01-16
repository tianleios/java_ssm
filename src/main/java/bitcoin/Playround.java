package bitcoin;

import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;

import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by tianlei on 2018/一月/06.
 */
public class Playround {

    public static void string(@Nullable String a) {

    }
    public static void main(String[] args) {

productAddress();
//
//            NetworkParameters params = RegTestParams.get();
//            Peer peer = PeerGroup.connectToLocalHost();
//            try {
//                // Decode the private key from Satoshis Base58 variant. If 51 characters long then it's from Bitcoins
//                // dumpprivkey command and includes a version byte and checksum, or if 52 characters long then it has
//                // compressed pub key. Otherwise assume it's a raw key.
//                ECKey key;
//                if (args[0].length() == 51 || args[0].length() == 52) {
//                    DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, args[0]);
//                    key = dumpedPrivateKey.getKey();
//                } else {
//                    BigInteger privKey = Base58.decodeToBigInteger(args[0]);
//                    key = ECKey.fromPrivate(privKey);
//                }
//                System.out.println("Address from private key is: " + key.toAddress(params).toString());
//                // And the address ...
//                Address destination = Address.fromBase58(params, args[1]);
//                int a = 10;
////                // Import the private key to a fresh wallet.
////                Wallet wallet = new Wallet(params);
////                wallet.importKey(key);
////
////                // Find the transactions that involve those coins.
////                final MemoryBlockStore blockStore = new MemoryBlockStore(params);
////                BlockChain chain = new BlockChain(params, wallet, blockStore);
////
////                final PeerGroup peerGroup = new PeerGroup(params, chain);
////                peerGroup.addAddress(new PeerAddress(params, InetAddress.getLocalHost()));
////                peerGroup.startAsync();
////                peerGroup.downloadBlockChain();
////                peerGroup.stopAsync();
////
////                // And take them!
////                System.out.println("Claiming " + wallet.getBalance().toFriendlyString());
////                wallet.sendCoins(peerGroup, destination, wallet.getBalance());
////                // Wait a few seconds to let the packets flush out to the network (ugly).
////                Thread.sleep(5000);
////                System.exit(0);
//            } catch (ArrayIndexOutOfBoundsException e) {
//                System.out.println("First arg should be private key in Base58 format. Second argument should be address " +
//                        "to send to.");
//            }

    }

    private static void productAddress() {

       ECKey ecKey = new ECKey();
       //私钥
       String privateKey = ecKey.getPrivateKeyAsHex();

       //公钥
       String publicKey = ecKey.getPublicKeyAsHex();
       String address = ecKey.toAddress(MainNetParams.get()).toString();
//       Address
//       //地址, 长度为20位数组 20*8 个二进制位
//       byte[] addressByte = ecKey.getPubKeyHash();
// new byte[]{1};
//        //换算成 16进制字符串，长度为42位
//       String address =  Utils.HEX.encode(addressByte);
//       Utils.HEX.decode(address + "01")
//Address.f
//ecKey.getPrivateKeyAsWiF(MainNetParams.get())
//        Address
//ecKey.toAddress()
//Base58check
       int a = 10;
//       Address address1 = ecKey.toAddress()

    }

    private void tx() {

        // message (hash) to be signed with private key
        // 要签名的信息
        String msg = "15953935a135031bfec37d36a9d662aea43e1deb0ea463d6932ac6e537cb3e81";

        // an example of WiF for private key (taken from 'Mastering Bitcoin')
        String wif ="KxFC1jmwwCoACiCAWZ3eXa96mBM6tb3TYzGmf6YwgdGWZgawvrtJ";

        // creating a key object from WiF
        DumpedPrivateKey dpk = DumpedPrivateKey.fromBase58(null, wif);
        ECKey key = dpk.getKey();

        // checking our key object
        NetworkParameters main =  MainNetParams.get();
        String check = key.getPrivateKeyAsWiF(main);
        System.out.println(wif.equals(check));  // true

        // creating Sha object from string
        Sha256Hash hash = Sha256Hash.wrap(msg);

        // creating signature
        ECKey.ECDSASignature sig = key.sign(hash);

        // encoding
        byte[] res = sig.encodeToDER();

        // converting to hex
        String hex = DatatypeConverter.printHexBinary(res);

        System.out.println(hex);  // 304502210081B528....

    }

    private void linkLocal() {
//        PeerGroup peerGroup =  PeerGroup
    }
}
