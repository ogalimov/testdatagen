package de.galimov.datagen.recording;

import static java.lang.reflect.Modifier.isFinal;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class RecordingProxyCreator {
    private RecordingProxyCreator() {
    }

    public static <T> T createRecordingProxy(Class<T> clazz) {
        Enhancer e = new Enhancer();
        e.setSuperclass(clazz);
        e.setCallback(new RecordingMethodInterceptor());
        return (T) e.create();
    }

    private static class RecordingMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Class<?> returnType = method.getReturnType();
            OngoingRecordingHolder.setCurrentMethod(method);
            OngoingRecordingHolder.storeInvocation();

            if (!isFinal(returnType.getModifiers()) && !returnType.isPrimitive()) {
                return createRecordingProxy(returnType);
            } else {
                OngoingRecordingHolder.storeInvocationChain();
                return null;
            }
        }
    }
}
