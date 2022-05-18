package emented.lab8FX.server.util;

import emented.lab8FX.common.util.requests.CommandRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


@Aspect
public class AspectsForLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsForLogging.class);

    @Before("execution(void startServerWorker())")
    public void startServerAdvise(JoinPoint joinPoint) {
        LOGGER.info(joinPoint.getSignature().getName() + " method executed");
    }

    @AfterReturning(pointcut = "execution(public static String validateClass(..))", returning = "res")
    public void validationAdvise(Object res) {
        if (res == null) {
            LOGGER.info("Successfully validated data from file");
        } else {
            LOGGER.error((String) res);
        }
    }

    @AfterReturning(pointcut = "execution(* listenForRequest())", returning = "res")
    public void listenForRequestAdvise(RequestWithAddress res) {
        if (res != null) {
            LOGGER.info("Accepted request: " + res.getRequest().toString());
        }
    }

    @After("execution(void sendResponse(..))")
    public void sendResponseAdvice(JoinPoint joinPoint) {
        LOGGER.info("Sent response: " + (joinPoint.getArgs()[0]).toString());
    }

    @AfterReturning(pointcut = "execution(String readCommand())", returning = "res")
    public void readCommandFromConsoleAdvise(Object res) {
        LOGGER.info("Read command from console: " + res.toString());
    }

    @After("execution(* emented.lab7.server.util.CommandManager.executeServerCommand(..))")
    public void executeServerCommandAdvise(JoinPoint joinPoint) {
        LOGGER.info("Executing server command: " + joinPoint.getArgs()[0]);
    }

    @After("execution(* emented.lab7.server.util.CommandManager.executeClientCommand(..))")
    public void executeClientCommandAdvise(JoinPoint joinPoint) {
        LOGGER.info("Executing client command: " + ((CommandRequest) joinPoint.getArgs()[0]).getCommandName());
    }

    @After("execution(* emented.lab7.server.db.DBManager.*(..))")
    public void dbQueryAdvice(JoinPoint joinPoint) {
        LOGGER.info("Executing DB query: " + joinPoint.getSignature().getName());
    }

    @After("execution(void connectSSH())")
    public void sshAdvise() {
        LOGGER.info("Successfully started SSH session!");
    }

    @After("execution(void initializeDB())")
    public void initializeAdvise() {
        LOGGER.info("Successfully initialized DB!");
    }

    @AfterThrowing(pointcut = "execution(* *(..))", throwing = "ex")
    public void anyExceptionAdvise(Throwable ex) {
        LOGGER.error(Arrays.toString(ex.getStackTrace()));
    }

}
