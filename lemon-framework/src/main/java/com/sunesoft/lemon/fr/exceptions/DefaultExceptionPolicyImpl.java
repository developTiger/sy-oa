package com.sunesoft.lemon.fr.exceptions;


import com.sunesoft.lemon.fr.loggers.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Created by zhouz on 2016/5/11.
 */
@Service("exceptionPolicy")
public class DefaultExceptionPolicyImpl implements ExceptionPolicy {


    @Autowired
    private Logger logger;

    @Override
    public Boolean HandleException(Object sender, Exception exception) {

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        if (isFatal(exception)) {
            //TODO  异常处理
            return false;
        }
        if (exception instanceof SeeraFatalException) {
            //TODO  异常处理
            return false;
        }
        //TODO  log记录
        logger.info(exception.getMessage());
        RaiseNotification(exception);
        return true;
    }

    public static Boolean isFatal(Exception ex) {
        return ex instanceof NullPointerException ||
                ex instanceof NoSuchMethodException;//……

    }

    private void RaiseNotification(Exception exception) {

        if (exception instanceof SeeraException) {

            //TODO　DoSomething
        }
    }
}
