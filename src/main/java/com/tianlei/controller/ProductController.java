package com.tianlei.controller;

import com.tianlei.common.Response;
import com.tianlei.pojo.Product;
import com.tianlei.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Created by tianlei on 2017/9/8.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl iProductService;

    //商品增删改查
    @PostMapping("")
    @ResponseBody
    public Response addProduct(String name, Integer stock, BigDecimal price) {

        Product product = new Product();
        product.setName(name);
        product.setStock(stock);
        product.setPrice(price);


        return this.iProductService.insert(product);

    }

    @PostMapping("/validation")
    @ResponseBody
    public Response validation(@Valid Product product, BindingResult bindingResult) {

//        Product product = new Product();
//        product.setName(name);
//        product.setStock(stock);
//        product.setPrice(price);

        return this.iProductService.insert(product);

    }


}
