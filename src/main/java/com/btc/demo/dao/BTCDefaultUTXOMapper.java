package com.btc.demo.dao;

import com.btc.demo.domain.BTCDefaultUTXO;

import java.util.List;

public interface BTCDefaultUTXOMapper {

    int insertBatch(List<BTCDefaultUTXO> defaultUTXOList);

//    int insert(BTCDefaultUTXO record);
//
//    int insertSelective(BTCDefaultUTXO record);
//
//
//    int updateByPrimaryKeySelective(BTCDefaultUTXO record);
//
//    int updateByPrimaryKeyWithBLOBs(BTCDefaultUTXO record);
//
//    int updateByPrimaryKey(BTCDefaultUTXO record);
}