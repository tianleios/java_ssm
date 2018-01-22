package com.btc.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.btc.demo.Expection.BizExpection;
import com.btc.demo.Expection.EBizError;
import com.btc.demo.dao.BTCAddressMapper;
import com.btc.demo.dao.BTCDefaultUTXOMapper;
import com.btc.demo.dao.BTCWithdrawUTXOMapper;
import com.btc.demo.domain.BTCAddress;
import com.btc.demo.domain.BTCDefaultUTXO;
import com.btc.demo.domain.BTCInfo.BTCBlock;
import com.btc.demo.domain.BTCInfo.BTCInput;
import com.btc.demo.domain.BTCInfo.BTCOut;
import com.btc.demo.domain.BTCInfo.BTCTx;
import com.btc.demo.domain.BTCUTXO;
import com.btc.demo.domain.BTCWithdrawUTXO;
import com.btc.demo.enumeration.EAddressType;
import com.btc.demo.enumeration.EDefaultUTXOStatus;
import com.btc.demo.enumeration.EWithdrawUTXOStatus;
import com.btc.demo.service.IBTCService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tianlei on 2018/一月/22.
 */
public class BTCService implements IBTCService {

    @Autowired
    BTCDefaultUTXOMapper defaultUTXOMapper;

    @Autowired
    BTCWithdrawUTXOMapper withdrawUTXOMapper;

    @Autowired
    BTCAddressMapper addressMapper;

    @Override
    public String address(boolean isTest) {

        ECKey ecKey = new ECKey();
        //私钥
        String privateKey = ecKey.getPrivateKeyAsHex();
        //公钥
        String publicKey = ecKey.getPublicKeyAsHex();
        NetworkParameters networkParameters = isTest ? TestNet3Params.get() : MainNetParams.get();
        String address = ecKey.toAddress(networkParameters).toString();
        if (address == null || address.length() <= 0) {
            throw new BizExpection(EBizError.ADDRESS_CREATE_ERROR);
        }
        return address;
    }

    @Override
    public void storeDefaultUTXO(List<BTCDefaultUTXO> utxoList) {

        int count = this.defaultUTXOMapper.insertBatch(utxoList);
        if (count != utxoList.size()) {
            throw new BizExpection(EBizError.UTXO_INSERT_ERROR);
        }

    }

    @Override
    public void storeWithdrawUTXO(List<BTCWithdrawUTXO> utxoList) {


    }

    @Transactional
    @Scheduled(cron = "*/30 * * * * ?")
    public void exploreBlockChain() {

        OkHttpClient okHttpClient = new OkHttpClient();

        //读取区块高度

        Long currentBlockHeight = Long.valueOf(1000);

        String requestUrl = "https://blockchain.info/block-height/" + currentBlockHeight + "?format=json";
        Request request = new Request.Builder().get().url("").build();
        Call call = okHttpClient.newCall(request);
        try {

            Response response = call.execute();
            String jsonStr = response.body().string();
            BTCBlock btcBlock = JSON.parseObject(jsonStr, BTCBlock.class);

            //用于存储 UTXO
            List<BTCDefaultUTXO> defaultBtcUTXOList = new ArrayList<>();

            //存储提现地址的 utxo
            List<BTCWithdrawUTXO> withdrawBtcUTXOList = new ArrayList<>();

//            用于存储
//            List<BTCDefaultUTXO> defaultBtcUTXOList = new ArrayList<>();
//
//            //存储提现地址的 utxo
//            List<BTCWithdrawUTXO> withdrawBtcUTXOList = new ArrayList<>();

            //存储花费
            List<>

            //遍历交易
            btcBlock.getTx().forEach((BTCTx btcTx) -> {

                //遍历输入
                btcTx.getInputs().forEach((BTCInput btcInput) -> {

                    BTCOut preOut = btcInput.getPrev_out();
                    if (preOut != null) {
                        String outAddress = preOut.getAddr();
                        BTCAddress address = this.addressMapper.selectByPrimaryKey(outAddress);
                        if (address != null) {
                            //
                            String addressType = address.getType();
                            if (addressType.equals(EAddressType.DEFAULT.getCode())) {


                            } else if (addressType.equals(EAddressType.WITHDRAW.getCode())) {


                            }

                        }
                    }

                });

                //遍历输出
                btcTx.getOut().forEach(btcOut -> {

                    String outToAddress = btcOut.getAddr();

                    BTCAddress address = this.addressMapper.selectByPrimaryKey(outToAddress);
                    if (address != null) {

                        String addressType = address.getType();
                        if (addressType.equals(EAddressType.DEFAULT.getCode())) {
//普通utxo收集
                            BTCUTXO btcutxo = this.convertOut(btcOut, btcTx.getHash(), btcBlock.getHeight(), EDefaultUTXOStatus.UN_COLLECTION.getCode());
                            defaultBtcUTXOList.add((BTCDefaultUTXO) btcutxo);

                        } else if (addressType.equals(EAddressType.WITHDRAW.getCode())) {
//提现utxo收集
                            BTCUTXO btcutxo = this.convertOut(btcOut, btcTx.getHash(), btcBlock.getHeight(), EWithdrawUTXOStatus.UN_USE.getCode());
                            withdrawBtcUTXOList.add((BTCWithdrawUTXO) btcutxo);

                        }

                    }

                });
            });

            //存储输入，和输出
            if (defaultBtcUTXOList.size() > 0) {
                this.defaultUTXOMapper.insertBatch(defaultBtcUTXOList);
            }

            if (withdrawBtcUTXOList.size() > 0) {
                this.withdrawUTXOMapper.insertBatch(withdrawBtcUTXOList);
            }


        } catch (Exception e) {

        }
    }

    private BTCUTXO convertOut(BTCOut btcOut, String txid, Long blockHeight, String status) {

        BTCUTXO btcutxo = new BTCUTXO();
        btcutxo.setAddress(btcOut.getAddr());
        btcutxo.setCount(btcOut.getValue());
        btcutxo.setScriptPubKey(btcOut.getScript());
        btcutxo.setTxid(txid);
        btcutxo.setVout(btcOut.getN());
        btcutxo.setSyncTime(new Date());
        btcutxo.setBlockHeight(blockHeight);
        btcutxo.setStatus(status);
        return btcutxo;
    }


    private void handleIn(BTCTx btcTx) {

        btcTx.getInputs().forEach(btcInput -> {


        });

    }

    private void handleOut(BTCTx btcTx) {


    }
}
