package com.tianlei.dao;

import com.tianlei.pojo.Product;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by tianlei on 2017/9/8.
 */
public interface ProductMapper {

    public int insert(Product product);
}
