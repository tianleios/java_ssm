package com.tianlei.service.impl;

import com.tianlei.common.Response;
import com.tianlei.dao.ProductMapper;
import com.tianlei.pojo.Product;
import com.tianlei.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by tianlei on 2017/9/11.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    public Response insert( Product product) {

        //先验证
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Product>> set = validator.validate(product);

        if (set.size() > 0) {

            Iterator iterator = set.iterator();
            if (iterator.hasNext()) {
             ConstraintViolation constraintViolation = (ConstraintViolation) iterator.next();
             constraintViolation.getMessage();
             Path path = constraintViolation.getPropertyPath();

            }
            return Response.failure(1,"参数不符合要求");

        }
        //
        int count = this.productMapper.insert(product);
        if (count > 0 ) {
            return Response.success("添加成功");
        }

        return Response.failure(1,"添加失败");

    }
}
