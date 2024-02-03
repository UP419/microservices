package com.example.Product.Service.interceptor;


import com.example.Product.Service.dto.ProductRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ProductInterceptor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.Product.Service.controller.ProductController.saveProduct(..)) && args(productRequest, ..)")
    public void beforeProductSave(ProductRequest productRequest) {
        log.info("Starting to save product {}", productRequest);
    }

    @AfterReturning(pointcut = "execution(* com.example.Product.Service.controller.ProductController.saveProduct(..))", returning = "result")
    public void afterProductSave(Object result) {
        log.info("Product saved successfully + ");
    }


}
