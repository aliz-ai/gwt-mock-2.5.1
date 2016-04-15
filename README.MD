Mock classes for in-JVM gwt testing

##Motivation
Testing GWT code is cumbersome. You have the following options basically:
* Follow a clean MVP architecture, as described in http://www.gwtproject.org/articles/mvp-architecture.html for example. This is your best option. But you sometimes fail to do so (because sometimes it can be really hard), or you inherit a codebase that's not clean.
* GWTTestCase - This actually compiles your code and runs it with HtmlUnit. You can assert various useful things. However your test development is slow, the test executions are slow and you cannot debug your tests.
* Selenium - Developing Selenium tests is even more cumbersome than GWTTestCases, though you can assert even more useful stuff.

After all, if the code is not clean MVP, you cannot assert things like 'given this and that a data, that save button there will be disabled'. You can of course start refactoring to clean MVP, but it's not always safe to do so, as the code is not covered with tests.

With gwt-mock, I wanted to find a way to develop and run tests fast for existing GWT code.

##The solution in general

I wanted to make the application code written using GWT run in the JVM.

### A failed attempt with instrumentation
At first I tried to do so by instrumenting class loading of GWT classes (which allows me to mock native methods), but I soon found that the class hierarchy of GWT is not consistently used. There are classes in the ```com.google.gwt.dom.client``` package to reflect the DOM, extending ```JavaScriptObject```: ```Node```, ```Element``` and then ```HeadElement```, ```FrameElement``` and so on for most standard html elements. However there's an ```com.google.gwt.user.client.Element``` class that simpl extends the ```Element``` above, it's 'An opaque handle to a native DOM Element.' according to the JavaDoc. However, when elements are created, they are often typecast between ```DivElement``` and ```com.google.gwt.user.client.Element``` for example, but those types are not consistent. I haven't investigated why GWT actually needs the ```user.client.Element``` type, but this causes most GWT code to fail to run in the JVM.

### What finally worked
I finally created a maven module, copied the code from the gwt-user module to it and then started making it compile and work. I removed many stuff that are not necessary
* some code required for the hostedmode to work
* "rebinding" code of many packages - that are code generators to run with GWT.create
* server-side code of many packages
* RPC related code
* the css and other resource generation code, including gss
* validation code
* and some other stuff, you can check this commit: https://github.com/Doctusoft/gwt-mock-2.5.1/commit/b7a632d00b6df702d1420704dda2c42359558f21

And I also removed many stuff that could have been saved, but they are GWT features that we were not using:
* The UiBinder implementation entirely (though NativeVerticalScrollbar and NativeHorizontalScrollbar widgets internally rely on it, but I could work that out)
* The Bean Editor implementation entirely

And most importantly: I removed the ```com.google.gwt.user.client.Element``` type entirely, changing all of its usages to ```com.google.gwt.dom.client.Element```.

## GWT.create
The magic ```GWT.create``` method is widely used of course. Sometimes it just picks the most appropriate browser specific implementation of an interface or abstract class, it also chooses locale-specific stuff like messages or formatters, it gives you image or style resources, and sometimes it gives you magic classes with generated source (for RPC, UiBinder and other stuff).

For the most common classes, I just wired up the most trivial options and dummy implementations, see https://github.com/Doctusoft/gwt-mock-2.5.1/blob/master/src/main/java/com/google/gwt/core/shared/GWT.java

For other stuff that might be specific to the application itself, I left it to the application to give a supplier using the ```addCustomSupplier``` method (and as this is static, you are advised to clean up suppliers between tests using ```cleanCustomSuppliers``` so that tests don't depend on each other).

## Working with the DOM
Our basic principle was to only change the GWT code as much as we need it for our tests to run. Thus there's a basic DOM implementation, but only as much as it was needed for the tests to run. You can although use ```Document.Instance.getBody().getInnerHTML()``` to see what's there, and you can also use getInnerHTML() on any element to assert text content.

Unlike with Selenium or PhantomJS, we don't work with the DOM directly, we work with the Widgets.

## Working with scheduled tasks
Your application might use the Scheduler, ```scheduleDeferred``` most often. To support this, I modified the SchedulerImpl class to have an ```executeScheduledCommands()``` and ```clearQueues()``` method, so that you tests can control when deferred commands are executed.

## Mocking messages
Most of the time when GWT uses code generation in GWT.ceate, you can use reflection and proxies to do the same in the JVM. For example for messages we have:
```
	public static <T extends Messages> T proxyMessages(final Class<T> msgClass) {
		return (T) Proxy.newProxyInstance(msgClass.getClassLoader(), new Class [] { msgClass }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getDeclaringClass().equals(msgClass)) {
					return method.getAnnotation(DefaultMessage.class).value();
				}
				if (method.getDeclaringClass().equals(Object.class)) {
					return method.invoke(proxy, args);
				}
				throw new UnsupportedOperationException(method.getName());
			}
		});
	}
```
Which simply gets the default message from the annotation. If your testing needs to test multiple languages, you can change this implementation to read the actual messages from the properties file.

We use the same technique to make [EventBinder](https://github.com/google/gwteventbinder) work.

## RPC
A great power of this kind of integration testing is that we can mock RPC. We also have a class that uses Java proxies to provide the ansync interfaces and forwards the calls to synchronous Mockito spies. If you need to, you can also keep mocking the async interfaces and you can control exactly when specific RPC responses arrive, thus you can test more complex parallel scenarios.

## Why only GWT 2.5.1?
Well, that's what we had. GWT 2.6 and 2.7 wouldn't bring much value to us, 2.8.0 wasn't even in beta. You can however try to use this gwt-mock for your application code written with a later GWT version. It might work if you don't rely on specific changes. But if you need to, you can try to do the same changes for a later GWT version, you'll be able to reuse some code and most experiences I had.

## Is it production ready?
Well, 'production' has a different meaning in testing, but yes, [AODocs](https://www.aodocs.com/), a popular enterprise document management system for Google Drive uses this testing technique.

## Is it ready?
Far from it. We only modified what needed to be modified to run our test scenarios. You can stumble upon a part anytime where we didn't change native methods, or something works a bit different than your application expects. But you are free and encouraged to ask for help and to contribute!

## How does a test look like?
```
@Test
public void testSomething() {
    MyEntryPoint myEntryPoint = new MyEntryPoint();
    myEntryPoint.onModuleLoad();
    myEntryPoint.getAnyPanel().getSomeButton().getElemet().fireEvent(ClickEvent.getType());
    Assert.assertTrue(myEntryPoint.getOtherPanel().getInnerHTML().contains('clicked'));
}
```
