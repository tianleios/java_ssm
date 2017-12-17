package com.tianlei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import rx.Observable;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//import org.web3j.protocol.infura.InfuraHttpService;
//import org.web3j.protocol.parity.Parity;
//import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;

/**
 * Created by tianlei on 2017/十月/15.
 */
@Controller
@RequestMapping("/eth")
public class BlockchainController {

    static private  Web3j web3j;
    static {
        // 别人提供的节点
//        web3j = Web3j.build(new InfuraHttpService("https://morden.infura.io/your-token"));

        //
        web3j = Web3j.build(new HttpService("http://localhost:8545"));
        //

    }

    @ResponseBody
    @GetMapping("/balance/{accountAddress}")
    public Object test(@PathVariable("accountAddress") String accountAddress) throws ExecutionException, InterruptedException, IOException {

        //
        Web3ClientVersion clientVersion =
                web3j.web3ClientVersion().sendAsync().get();

      //
      Request<?, EthGetBalance> ethGetBalanceRequest = web3j.ethGetBalance(accountAddress, DefaultBlockParameterName.LATEST);
      BigInteger balance = ethGetBalanceRequest.send().getBalance();

      //
      Map banlanceMap = new HashMap();
      banlanceMap.put("banlance", balance.toString());

      return banlanceMap;

    }

    @ResponseBody
    @PostMapping("/tx")
    public void tx(String from , String to, BigInteger amount) throws  Exception {

//        String mineAddress = "0x53663d7126cfdae77165ad3edaf7e680814b2aec";
//        String otherAddress = "0x7132067c3447f65a6cf8c67ea2fca85c0e4f7593"
        if (from == null || to == null || amount == null) {
            throw  new Exception("参数不能为空");
        }

        String mineAddress = from;
        String otherAddress = to;
        //
        String mineAddressPasssword = "q4121585";
//        Parity parity = Parity.build(new HttpService());
//        PersonalUnlockAccount personalUnlockAccount = parity.personalUnlockAccount(mineAddress, mineAddressPasssword).sendAsync().get();
//        //
//        if (!personalUnlockAccount.accountUnlocked()) {
//            //账户处于锁定状态
//            return;
//        }


        //获得 nonce
//        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(mineAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
//
//            BigInteger nonce = transactionCount.getTransactionCount();
//            BigInteger gasPrice = BigInteger.valueOf(2);
//            BigInteger gaslimit = BigInteger.valueOf(30000);
//
//            BigInteger jinLv = new BigInteger("1000000000000000000");
//            BigInteger value = amount.multiply(jinLv);
//
//            //1.创建交易
//            org.web3j.protocol.core.methods.request.Transaction transaction = org.web3j.protocol.core.methods.request.Transaction.createEtherTransaction(mineAddress, nonce, gasPrice, gaslimit, otherAddress, value);
//
//            //2.发起交易
//            EthSendTransaction ethSendTransactionResp = parity.ethSendTransaction(transaction).sendAsync().get();
//
//            //结果哈希
//            String transactionHash = ethSendTransactionResp.getTransactionHash();
//
//            int a = 10;


    }


    public  void tt() throws Exception {

        Observable blocksObservable = web3j.replayBlocksObservable(new DefaultBlockParameterNumber(1), new DefaultBlockParameterNumber(2), true);
        blocksObservable.subscribe(block -> {
            System.out.print(block);
        });
        //获取范围内的交易
//       web3j.replayTransactionsObservable(new DefaultBlockParameterNumber(1),new DefaultBlockParameterNumber(10))
//        .subscribe(tx -> {
//
//
//        });

        //
        web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.EARLIEST).filter(tx ->

                tx.getFrom().equals("0x53663d7126cfdae77165ad3edaf7e680814b2aec")

        ).subscribe(tx -> {

//            System.out.print(tx.getValue());
//            System.out.print("\n");

        });

        //订阅所有 block
        web3j.blockObservable(false).subscribe(block -> {


        });

        //订阅所有 事务
        web3j.transactionObservable().subscribe(tx -> {

        });

        //所有挂起的交易
        web3j.pendingTransactionObservable().subscribe(tx -> {
            //
            System.out.print("pending \n");
            System.out.print(tx.getValue());
            System.out.print(" \n");
            //
        });

        //
//        Transaction transaction = new Transaction()
        String mineAddress = "0x53663d7126cfdae77165ad3edaf7e680814b2aec";
        String otherAddress = "0x7132067c3447f65a6cf8c67ea2fca85c0e4f7593";
        String mineAddressPasssword = "q4121585";

//        Parity parity = Parity.build(new HttpService());
//        PersonalUnlockAccount personalUnlockAccount = parity.personalUnlockAccount(mineAddress, mineAddressPasssword).sendAsync().get();
//
//        //
//        if (personalUnlockAccount.accountUnlocked()) {

//            org.web3j.protocol.core.methods.request.Transaction.createContractTransaction(mineAddress,new BigInteger(1),new BigInteger(1),"122");
//        }


//            EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(mineAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
//
//            BigInteger nonce = transactionCount.getTransactionCount();
//            BigInteger gasPrice = BigInteger.valueOf(2);
//            BigInteger gaslimit = BigInteger.valueOf(30000);
//            BigInteger value = BigInteger.valueOf(10);
//
//            org.web3j.protocol.core.methods.request.Transaction transaction = org.web3j.protocol.core.methods.request.Transaction.createEtherTransaction(mineAddress, nonce, gasPrice, gaslimit, otherAddress, value);
//
//            EthSendTransaction ethSendTransactionResp = parity.ethSendTransaction(transaction).sendAsync().get();
//            String transactionHash = ethSendTransactionResp.getTransactionHash();
//
//            int a = 10;

//        }
    }

    @ResponseBody
    @GetMapping("/contract")
    public void contract() {

    }


}


