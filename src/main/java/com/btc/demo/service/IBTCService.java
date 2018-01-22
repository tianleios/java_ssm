package com.btc.demo.service;

import com.btc.demo.domain.BTCDefaultUTXO;
import com.btc.demo.domain.BTCWithdrawUTXO;

import java.util.List;

/**
 * Created by tianlei on 2018/一月/22.
 */
public interface IBTCService {

    /*
      生成地址，isTest yes为test,no 不是test
    */
    String address(boolean isTest);

    void storeDefaultUTXO(List<BTCDefaultUTXO> utxoList);

    void storeWithdrawUTXO(List<BTCWithdrawUTXO> utxoList);

}
