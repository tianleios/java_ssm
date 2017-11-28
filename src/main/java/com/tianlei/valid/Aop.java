package com.tianlei.valid;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by tianlei on 2017/十一月/19.
 */

@Component
@Aspect
public class Aop {

    @Around("@annotation(com.tianlei.valid.AutoValidating)")
    public void pointCut(ProceedingJoinPoint pjp) throws Exception {

        Object result;
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        Method method = signature.getMethod();
//            MethodSignature signature = (MethodSignature) pjp.getSignature();
//            MethodValidator methodValidator = validator.unwrap( MethodValidator.class );


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator executableValidator = factory.getValidator().forExecutables();
        Set set = executableValidator.validateParameters(pjp.getTarget(),method,pjp.getArgs());
        int a = 1;
        if (set.size() > 0) {

            throw new Exception("参数错误");
        }
        System.out.print("121312312 2312 1231");
//        throw new
    }

//    @Before("@annotation(com.tianlei.valid.AutoValidating)")
//    public void pointCut1() throws Exception {
//
//        int a = 1;
//        System.out.print("121312312 2312 1231");
//        throw new Exception("参数错误");
//    }


    // 切点表达式
//    @Pointcut("execution(* com.tianlei.service.impl.UserServiceImpl.*(..))")
//    public void dataAccessOperation() {
//        System.out.print("121312312 2312 1231 qweasd ");
//    } // 切点前面
//
//    @Before("execution(* com.tianlei.service.impl.UserServiceImpl.*(..))")
//    public void dataAccessOperation1() {
//
//        System.out.print("121312312 2312 1231 qweasd ");
//    } // 切点前面


//    @Around("@annotation(com.tianlei.valid.AutoValidating)")
//    public Object validateMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
//        Object result;
//
//
//
//
//        return "";
//    }

}
