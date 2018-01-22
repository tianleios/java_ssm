package com.btc.demo.dao;

import com.btc.demo.domain.BTCWithdrawUTXO;

import java.util.List;

/**
 * Created by tianlei on 2018/一月/22.
 */
public interface BTCWithdrawUTXOMapper {

    int insertBatch(List<BTCWithdrawUTXO> defaultUTXOList);

}
