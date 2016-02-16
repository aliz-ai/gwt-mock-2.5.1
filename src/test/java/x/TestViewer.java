package x;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.altirnao.aodocs.common.shared.UserInfo;
import com.altirnao.aodocs.common.shared.entity.Domain;
import com.altirnao.aodocs.common.shared.service.AuthRemoteService;
import com.altirnao.aodocs.view.client.view.DocumentViewer;
import com.doctusoft.gwtmock.Document;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.core.shared.GWT.CustomGWTCreateSupplier;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.web.bindery.event.shared.binder.EventBinder;

//@RunWith(GwtMockTestRunner.class)
public class TestViewer {
	
	static <T extends CssResource> T proxyCssResource(final Class<T> cls) {
		return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class [] { cls }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (CssResource.class.equals(method.getDeclaringClass())) {
					if (method.getName().equals("ensureInjected")) {
						return true;
					}
				}
				if (method.getDeclaringClass().equals(cls)) {
					return method.getName();
				}
				if (method.getDeclaringClass().equals(Object.class)) {
					return method.invoke(proxy, args);
				}
				throw new UnsupportedOperationException();
			}
		});
	}
	
	@Before
	public void setup() {
		Document.Instance.getBody().setId("content");
		GWT.addCustomSupplier(new CustomGWTCreateSupplier() {
			@Override
			public Object create(Class<?> classLiteral) {
				if (EventBinder.class.isAssignableFrom(classLiteral)) {
					return Proxy.newProxyInstance(classLiteral.getClassLoader(), new Class [] { classLiteral }, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (method.getName().equals("bindEventHandlers")) {
								return new com.google.gwt.event.shared.HandlerRegistration() {
									@Override
									public void removeHandler() {
										// no-op
									}
								};
							}
							if (method.getDeclaringClass().equals(Object.class)) {
								return method.invoke(proxy, args);
							}
							throw new UnsupportedOperationException();
						}
					});
				}
				return null;
			}
		});
		GWT.addCustomSupplier(new CustomGWTCreateSupplier() {
			@Override
			public Object create(Class<?> classLiteral) {
				if (ClientBundle.class.isAssignableFrom(classLiteral)) {
					return Proxy.newProxyInstance(classLiteral.getClassLoader(), new Class [] { classLiteral }, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (CssResource.class.isAssignableFrom(method.getReturnType())) {
								return proxyCssResource((Class)method.getReturnType());
							}
							if (method.getDeclaringClass().equals(Object.class)) {
								return method.invoke(proxy, args);
							}
							throw new UnsupportedOperationException(method.getName());
						}
					});
				}
				return null;
			}
		});
		GWT.addCustomSupplier(new CustomGWTCreateSupplier() {
			@Override
			public Object create(Class<?> classLiteral) {
				if (RemoteService.class.isAssignableFrom(classLiteral)) {
					return RemoteServiceProxy.getOrCreateRemoteServiceProxy((Class)classLiteral).getProxy();
				}
				return null;
			}
		});
	}
	
	@Test
	public void testViewer() throws Exception {
//		Mockito.when(RemoteServiceProxy.getOrCreateRemoteServiceProxy(XsrfTokenService.class).getSpy().getNewXsrfToken()).thenReturn(new XsrfToken("xx"));
		AuthRemoteService authService = RemoteServiceProxy.getOrCreateRemoteServiceProxy(AuthRemoteService.class).getSpy();
		UserInfo userInfo = new UserInfo();
		userInfo.setLocale("en_US");
		userInfo.setEmail("admin@rapps-test.revevol.eu");
		userInfo.setDomain(new Domain("rapps-test.revevol.eu"));
		Mockito.when(authService.checkDomainAccess(Mockito.anyString())).thenReturn(userInfo);
		new DocumentViewer().onModuleLoad();
		Document.Instance.printFormatted(new PrintWriter(System.out));
	}

}
