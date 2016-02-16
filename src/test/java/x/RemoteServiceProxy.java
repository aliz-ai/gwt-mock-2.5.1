package x;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

import org.mockito.Mockito;

import com.google.appengine.repackaged.com.google.api.client.util.Maps;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RemoteServiceProxy<T extends RemoteService> {

	public static Map<Class<? extends RemoteService>, RemoteServiceProxy> serviceProxyMap = Maps.newHashMap();
	private Class<T> remoteServiceClass;
	private T spy;
	private Object proxy;

	public RemoteServiceProxy(final Class<T> remoteServiceClass) {
		this.remoteServiceClass = remoteServiceClass;
		try {
			final Class<?> asyncClass = Class.forName(remoteServiceClass.getName() + "Async");
			spy = Mockito.spy(remoteServiceClass);
			proxy = Proxy.newProxyInstance(remoteServiceClass.getClassLoader(),
					new Class[] { asyncClass, ServiceDefTarget.class }, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (method.getDeclaringClass().equals(asyncClass)) {
								// transform the call to the synchronous spy (which is easier to mock for testing)
								AsyncCallback callback = (AsyncCallback) args[args.length - 1];
								Method syncMethod = remoteServiceClass.getMethod(method.getName(), Arrays.copyOf(method.getParameterTypes(), method.getParameterTypes().length -1));
								try {
									Object result = syncMethod.invoke(spy, Arrays.copyOf(args, args.length - 1));
									callback.onSuccess(result);
								} catch (Exception e) {
									callback.onFailure(e);
								}
								return null;
							}
							if (method.getDeclaringClass().equals(ServiceDefTarget.class)) {
								return null;
							}
							if (method.getDeclaringClass().equals(Object.class)) {
								return method.invoke(proxy, args);
							}
							throw new UnsupportedOperationException(method.getName());
						}
					});
		} catch (Exception e) {
			throw new RuntimeException("Could not create proxy for " + remoteServiceClass, e);
		}
	}

	public static <T extends RemoteService> RemoteServiceProxy<T> getOrCreateRemoteServiceProxy(Class<T> remoteServiceClass) {
		if (serviceProxyMap.containsKey(remoteServiceClass)) {
			return serviceProxyMap.get(remoteServiceClass);
		}
		RemoteServiceProxy<T> remoteServiceProxy = new RemoteServiceProxy<T>(remoteServiceClass);
		serviceProxyMap.put(remoteServiceClass, remoteServiceProxy);
		return remoteServiceProxy;
	}

	public T getSpy() {
		return spy;
	}
	
	public Object getProxy() {
		return proxy;
	}
}
