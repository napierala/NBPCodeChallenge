package pl.napierala.nbpcodechallenge.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class Profiler {

    private final static Logger logger = LoggerFactory.getLogger(Profiler.class);

    @Pointcut("execution(* pl.napierala.nbpcodechallenge.controller.UserController*.*(..))")
    public void userMethods() {
    }

    @Pointcut("execution(* pl.napierala.nbpcodechallenge.controller.ExchangeRateController*.*(..))")
    public void exchangeMethods() {
    }

    @Around("userMethods() || exchangeMethods()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        logger.info("Method[" + method.getName() + "] execution time: " + elapsedTime + " milliseconds.");
        return output;
    }
}