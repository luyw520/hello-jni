
package com.lu.aoplib.annotation;

import android.util.Log;

import com.lu.aoplib.LogUtil;
import com.lu.aoplib.StopWatch;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 跟踪被DebugTrace注解标记的方法和构造函数
 */
@Aspect
public class DebugTraceTraceAspect {

  private static final String POINTCUT_METHOD =
      "execution(@com.lu.aoplib.annotation.DebugTrace * *(..))";

  private static final String POINTCUT_CONSTRUCTOR =
      "execution(@com.lu.aoplib.annotation.DebugTrace *.new(..))";

  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotatedWithDebugTrace() {}

  @Pointcut(POINTCUT_CONSTRUCTOR)
  public void constructorAnnotatedDebugTrace() {}

  @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {


    Signature signature=joinPoint.getSignature();
    String className = signature.getDeclaringType().getSimpleName();
    String methodName = signature.getName();

    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Object result = joinPoint.proceed();
    stopWatch.stop();
    StringBuilder stringBuilder=new StringBuilder();
//    stringBuilder.append(signature.getDeclaringType().getName());
//    stringBuilder.append(".");
//    stringBuilder.append(methodName);
    Object[] args=joinPoint.getArgs();
    if (args!=null&&args.length>0){
      stringBuilder.append("[ param:");
      for (Object a:args){
        stringBuilder.append(a);
        stringBuilder.append(",");
      }

    }

    if (result instanceof Void){

    }else{
      stringBuilder.append("-->result:"+result);
    }
    stringBuilder.append("]");
    stringBuilder.append("["+stopWatch.getTotalTimeMillis()+"ms]");
    Log.d(getClass().getSimpleName(),stringBuilder.toString());
    LogUtil.d2(stringBuilder.toString());
    return result;
  }

  private static String buildLogMessage(String methodName, long methodDuration) {
    StringBuilder message = new StringBuilder();
    message.append("Gintonic --> ");
    message.append(methodName);
    message.append(" --> ");
    message.append("[");
    message.append(methodDuration);
    message.append("ms");
    message.append("]");

    return message.toString();
  }
}
