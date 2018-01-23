package com.btc.service.impl;

import com.alibaba.fastjson.JSON;
import com.btc.Expection.BizExpection;
import com.btc.Expection.EBizError;
import com.btc.dao.BTCAddressMapper;
import com.btc.dao.BTCDefaultUTXOMapper;
import com.btc.dao.BTCWithdrawUTXOMapper;
import com.btc.domain.BTCAddress;
import com.btc.domain.BTCDefaultUTXO;
import com.btc.domain.BTCInfo.*;
import com.btc.domain.BTCUTXO;
import com.btc.domain.BTCWithdrawUTXO;
import com.btc.enumeration.EAddressType;
import com.btc.enumeration.EDefaultUTXOStatus;
import com.btc.enumeration.EWithdrawUTXOStatus;
import com.btc.service.IBTCService;
import com.btc.util.BitcoinOfflineRawTxBuilder;
import com.btc.util.OfflineTxInput;
import com.btc.util.OfflineTxOutput;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by tianlei on 2018/一月/22.
 */
@Service("iBTCService")
public class BTCService implements IBTCService {

    @Autowired
    BTCDefaultUTXOMapper defaultUTXOMapper;

    @Autowired
    BTCWithdrawUTXOMapper withdrawUTXOMapper;

    @Autowired
    BTCAddressMapper addressMapper;

    @Override
    public String address(boolean isTest) {

//        ECKey ecKey = new ECKey();
//        //私钥
//        String privateKey = ecKey.getPrivateKeyAsHex();
//        //公钥
//        String publicKey = ecKey.getPublicKeyAsHex();
//        NetworkParameters networkParameters = isTest ? TestNet3Params.get() : MainNetParams.get();
//        String address = ecKey.toAddress(networkParameters).toString();
//        if (address == null || address.length() <= 0) {
//            throw new BizExpection(EBizError.ADDRESS_CREATE_ERROR);
//        }
//        return address;
        return "123";
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

        String requestUrl = this.blockUrl(currentBlockHeight);
        Request request = new Request.Builder()
                .get()
                .url(this.blockUrl(currentBlockHeight))
                .build();
        Call call = okHttpClient.newCall(request);
        try {

            Response response = call.execute();
            String jsonStr = response.body().string();
            BTCBlock btcBlock = JSON.parseObject(jsonStr, BTCBlock.class);

            //用于存储 UTXO
            List<BTCDefaultUTXO> defaultBtcUTXOList = new ArrayList<>();

            //存储提现地址的 utxo
            List<BTCWithdrawUTXO> withdrawBtcUTXOList = new ArrayList<>();

            //用于存储已花费
            List<BTCDefaultUTXO> defaultBtcSpendedTXOList = new ArrayList<>();

            //存储提现地址的 utxo
            List<BTCWithdrawUTXO> withdrawBtcSpendedUTXOList = new ArrayList<>();


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

                            BTCUTXO btcutxo = new BTCUTXO();
                            btcutxo.setTxid(btcTx.getHash());
                            btcutxo.setVout(preOut.getN());
                            if (addressType.equals(EAddressType.DEFAULT.getCode())) {

                                defaultBtcSpendedTXOList.add((BTCDefaultUTXO) btcutxo);

                            } else if (addressType.equals(EAddressType.WITHDRAW.getCode())) {

                                withdrawBtcSpendedUTXOList.add((BTCWithdrawUTXO) btcutxo);

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

            //变更输入状态 为
            if (defaultBtcSpendedTXOList.size() > 0) {
                defaultBtcSpendedTXOList.forEach(utxo -> {

                    this.defaultUTXOMapper.updateUTXOStatus(utxo.getTxid(), utxo.getVout(), EDefaultUTXOStatus.COLLECTION_ED.getCode());

                });
            }

            if (withdrawBtcSpendedUTXOList.size() > 0) {

                defaultBtcSpendedTXOList.forEach(utxo -> {

                    this.defaultUTXOMapper.updateUTXOStatus(utxo.getTxid(), utxo.getVout(), EWithdrawUTXOStatus.USE_ED.getCode());

                });
            }

            //存储输入，和输出
            if (defaultBtcUTXOList.size() > 0) {
                this.defaultUTXOMapper.insertBatch(defaultBtcUTXOList);
            }

            if (withdrawBtcUTXOList.size() > 0) {
                this.withdrawUTXOMapper.insertBatch(withdrawBtcUTXOList);
            }

        } catch (Exception e) {

            //todo
            // 此处应该有log
        }

    }

    @Scheduled(cron = "*/30 * * * * ?")
    synchronized public void collection() {

        BigDecimal min = BigDecimal.valueOf(5 * Math.pow(10, 7));
        String collectionAddress = "";
        String statusCode = EDefaultUTXOStatus.UN_COLLECTION.getCode();
        BigDecimal count = this.defaultUTXOMapper.selectCoinCount(statusCode);

        if (count.compareTo(min) < 0) {
            //不符合归集条件
            return;
        }

        //先取最多20条
        List<BTCDefaultUTXO> UTXOList = this.defaultUTXOMapper.selectList(statusCode, 0, 20);

        BitcoinOfflineRawTxBuilder rawTxBuilder = new BitcoinOfflineRawTxBuilder();

        BigDecimal totalCount = BigDecimal.valueOf(0);
        //构造输入
        UTXOList.forEach(utxo -> {

            String txid = utxo.getTxid();
            Integer vout = utxo.getVout();

            //统计总额
            totalCount.add(utxo.getCount());

            BTCAddress btcAddress = this.addressMapper.selectByPrimaryKey(utxo.getAddress());
            OfflineTxInput offlineTxInput = new OfflineTxInput(txid, vout, utxo.getScriptPubKey(), btcAddress.getPrivatekey());
            rawTxBuilder.in(offlineTxInput);

            //同时更新状态为归集中
            this.defaultUTXOMapper.updateUTXOStatus(txid, vout, EDefaultUTXOStatus.COLLECTION_ING.getCode());

        });

        //如何估算手续费，先预先给一个size,然后那这个size进行签名。
        //对签名的数据进行解码，拿到真实大小，然后进行旷工费的修正
        int preSize = BitcoinOfflineRawTxBuilder.calculateSize(rawTxBuilder.getInputs().size(), rawTxBuilder.getOutputs().size());

        int prePerByte = 200;

        //预估的手续费 sat
        int preFee = preSize * prePerByte;


        BigDecimal preReturnCount = totalCount.subtract(BigDecimal.valueOf(preFee));

        //要进行单位转换
        OfflineTxOutput offlineTxOutput = new OfflineTxOutput(collectionAddress, this.convert(preReturnCount));

        try {

            String signResult = rawTxBuilder.offlineSign();
            //应该先处理我们的数据库，在进行广播。广播失败就回滚
            //拿到结果进行解码，获取真实Size
            Long trueSize = rawTxBuilder.decodeOfflineRawTxThenGetSize(signResult);

            //开始修正输入手续费
            Integer feePerByte = this.getFee();
            BigDecimal trueFee = BigDecimal.valueOf(trueSize).multiply(BigDecimal.valueOf(feePerByte));

            //重新构造交易, 转换为 1.3232 这种格式
            offlineTxOutput.setAmount(this.convert(totalCount.subtract(trueFee)));
            rawTxBuilder.reloadOut(offlineTxOutput);

            //得到最终结果
            String lastSignResult = rawTxBuilder.offlineSign();

            //2.进行广播
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder().add("rawtx", lastSignResult).build();
            Request request = new Request.Builder()
                    .post(formBody)
                    .url(this.broadcastRawTxUrl())
                    .build();
            //
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            String jsonStr = response.body().string();
            Map map = JSON.parseObject(jsonStr, HashMap.class);
            String txid = (String) map.get("txid");
            if (txid == null && txid.length() <= 0) {

                throw new BizExpection(EBizError.UTXO_COLLECTION_ERROR);

            }

        } catch (Exception e) {

            throw new BizExpection(EBizError.UTXO_COLLECTION_ERROR);

        }

    }

    /* 100000000sta 转换为 1btc*/
    private BigDecimal convert(BigDecimal orgNum) {

        return orgNum.divide(BigDecimal.TEN.pow(8));

    }

    private Integer getFee() {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request feeReq = new Request.Builder().get().url(this.feeUrl()).build();

        try {

            Call call = okHttpClient.newCall(feeReq);
            Response response = call.execute();
            String jsonStr = response.body().string();
            BTCFee fee = JSON.parseObject(jsonStr, BTCFee.class);

            // 应该读取一个配置值，获取手续费如果大于这个值，就取这个是
            Integer maxFeePerByte = 300;
            Integer fastFee = fee.getFastestFee();

            return fastFee > maxFeePerByte ? fastFee : maxFeePerByte;

        } catch (Exception e) {

            throw new BizExpection(EBizError.TX_FEE_ERROR);

        }

    }

    private String blockUrl(Long blockHeight) {
        String requestUrl = this.baseUrl() + "/block-height/" + blockHeight + "?format=json";
        return requestUrl;
    }

    private String broadcastRawTxUrl() {

//        return "https://testnet.blockexplorer.com/api/tx/send";
        return "https://blockexplorer.com/api/tx/send";

    }

    private String feeUrl() {
        //        {"fastestFee":250,"halfHourFee":240,"hourFee":130}
        //
        return "https://bitcoinfees.earn.com/api/v1/fees/recommended";
    }

    private String baseUrl() {
        return "https://blockchain.info";
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


}
