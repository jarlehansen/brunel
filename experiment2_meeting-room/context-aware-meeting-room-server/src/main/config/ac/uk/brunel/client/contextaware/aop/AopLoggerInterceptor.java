package ac.uk.brunel.client.contextaware.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 5:19:04 PM
 */
public class AopLoggerInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger("");

    public Logger getLogger() {
        return logger;
    }

    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        final Object returnObj = methodInvocation.proceed();

        if (logger.isInfoEnabled()) {
            final String classAndMethodName = methodInvocation.getClass().getName() + "." + methodInvocation.getMethod().getName();
            final Object[] methodArgs = methodInvocation.getArguments();

            logInputArgs(classAndMethodName, methodArgs);
            logReturnValue(classAndMethodName, returnObj);
        }

        return returnObj;
    }

    private void logInputArgs(final String classAndMethodName, final Object[] methodArgs) {
        final StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(classAndMethodName);

        if (methodArgs.length > 0) {
            inputBuilder.append(" - Input arguments: ");

            for (Object obj : methodArgs) {
                inputBuilder.append(obj.toString()).append(" - ");
            }
        } else {
            inputBuilder.append(" - No input arguments");
        }
        logger.info(inputBuilder.toString());
    }

    private void logReturnValue(final String classAndMethodName, final Object returnValue) {
        final StringBuilder returnBuilder = new StringBuilder();
        returnBuilder.append(classAndMethodName);

        if (returnValue != null) {
            returnBuilder.append(" - Return value: ").append(returnValue.toString());
        } else {
            returnBuilder.append(" - No return value");
        }

        logger.info(returnBuilder.toString());
    }
}
