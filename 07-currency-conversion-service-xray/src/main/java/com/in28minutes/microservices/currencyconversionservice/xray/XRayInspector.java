package com.in28minutes.microservices.currencyconversionservice.xray;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import com.amazonaws.xray.spring.aop.XRayInterceptorUtils;
import com.in28minutes.microservices.currencyconversionservice.resource.CurrencyConversionController;

@Aspect
@Component
public class XRayInspector extends AbstractXRayInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XRayInspector.class);
	@Override
	protected Map<String, Map<String, Object>> generateMetadata(ProceedingJoinPoint proceedingJoinPoint,
			Subsegment subsegment) {
		return super.generateMetadata(proceedingJoinPoint, subsegment);
	}

	@Override
	@Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && bean(*)")
	public void xrayEnabledClasses() {
	}

//	@SuppressWarnings("deprecation")
//	@Override
//	protected Object processXRayTrace(ProceedingJoinPoint pjp) throws Throwable {
//		try {
//            Subsegment subsegment = AWSXRay.beginSubsegment(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
//            LOGGER.trace("Begin aws xray subsegment");
//            
//            Optional.ofNullable(subsegment)
//            		.ifPresent(s->s.setMetadata(generateMetadata(pjp, subsegment)));
//            
//            Object result = XRayInterceptorUtils.conditionalProceed(pjp);         
//            Optional.ofNullable(result)
//            		.ifPresent(r->{
//            			Map<String, Object> resultMeta = new HashMap<String, Object>();
//        	            resultMeta.put(result.getClass().getCanonicalName(), result);
//        	            subsegment.getMetadata().put("Result", resultMeta);
//            		});
//            
//            return result;
//        } catch (Exception e) {
//            AWSXRay.getCurrentSegment().addException(e);
//            throw e;
//        } finally {
//        	LOGGER.trace("Ending aws xray subsegment");
//            AWSXRay.endSubsegment();
//        }
//	}
}