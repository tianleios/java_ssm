package com.tianlei.valid;

/**
 * Created by tianlei on 2017/十一月/19.
 */
public class ValidationInterceptor {
//
//    @Aspect
//    public class ValidationInterceptor {
//
//        @Inject
//        private Validator validator;
//
//        //Match any public methods in a class annotated with @AutoValidating
//        @Around("execution(public * *(..)) && @within(de.gmorling.methodvalidation.spring.AutoValidating)")
//        public Object validateMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
//            Object result;
//            MethodSignature signature = (MethodSignature) pjp.getSignature();
//            MethodValidator methodValidator = validator.unwrap( MethodValidator.class );
//
//            Set<MethodConstraintViolation<Object>> parametersViolations = methodValidator.validateParameters(
//                    pjp.getTarget(), signature.getMethod(), pjp.getArgs()
//            );
//            if ( !parametersViolations.isEmpty() ) {
//                throw new MethodConstraintViolationException( parametersViolations );
//            }
//
//            result =  pjp.proceed(); //Execute the method
//
//            Set<MethodConstraintViolation<Object>> returnValueViolations = methodValidator.validateReturnValue(
//                    pjp.getTarget(), signature.getMethod(), result
//            );
//            if ( !returnValueViolations.isEmpty() ) {
//                throw new MethodConstraintViolationException( returnValueViolations );
//            }
//
//            return result;
//        }
//
//    }
}
