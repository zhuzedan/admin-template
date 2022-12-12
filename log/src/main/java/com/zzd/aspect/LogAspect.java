package com.zzd.aspect;

import com.alibaba.fastjson.JSON;

import com.zzd.annotation.Log;
import com.zzd.domain.SystemOperationLog;
import com.zzd.service.SystemOperationLogService;
import com.zzd.utils.HttpContextUtils;
import com.zzd.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private SystemOperationLogService systemOperationLogService;
    //切点
    @Pointcut("@annotation(com.zzd.annotation.Log)")
    public void logAspect() {

    }
    //定义通知类，标识切点，环绕通知
    @Around("logAspect()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(joinPoint,time);
        return result;

    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 获取request 设置ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //请求方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = methodSignature.getName();
        // 请求参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        Log logAnnotation = method.getAnnotation(Log.class);
        log.info("===============================请求开始=============================");
        log.info("[请求标题]:{}",logAnnotation.title());
        log.info("[业务类型]:{}",logAnnotation.businessType());
        log.info("[地址]:{}",IpUtil.getIpAddress(request));
        log.info("[请求类名]:{},[请求方法名]:{}",className,methodName);
        log.info(params);
        log.info(String.valueOf(time));
        // 将操作日志记录到数据库
        SystemOperationLog systemOperationLog = new SystemOperationLog();
        systemOperationLog.setTitle(logAnnotation.title());  //标题
        systemOperationLog.setBusinessType(String.valueOf(logAnnotation.businessType()));   //业务类型
        systemOperationLog.setOperationIp(IpUtil.getIpAddress(request));     //地址
        systemOperationLog.setOperationUrl(request.getRequestURI());         //url
        systemOperationLog.setStatus(1);                                     //状态
        systemOperationLog.setMethod(className + "." + methodName + "()");   //方法名
        systemOperationLog.setRequestMethod(request.getMethod());            // 设置请求方式
        systemOperationLog.setOperationTime(time+ "ms");                     //操作时长
        systemOperationLog.setOperationParam(params);
        // 是否需要保存request，参数和值
        // if (logAnnotation.isSaveRequestData()) {
        //     // 获取参数的信息，传入到数据库中。
        //     setRequestValue(joinPoint, systemOperationLog);
        // }
        systemOperationLogService.saveSysLog(systemOperationLog);

    }

    @AfterReturning(returning = "ret", pointcut = "logAspect()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("===============================返回内容=============================");
        log.info("RESPONSE:{}",JSON.toJSONString(ret));
        log.info("===============================请求结束=============================");
        SystemOperationLog systemOperationLog = new SystemOperationLog();
        systemOperationLog.setJsonResult(JSON.toJSONString(ret));
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SystemOperationLog operLog, Object jsonResult) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().name());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().name());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && !StringUtils.isEmpty(jsonResult)) {
            operLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param systemOperationLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SystemOperationLog systemOperationLog) throws Exception {
        String requestMethod = systemOperationLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            systemOperationLog.setOperationParam(params);
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
