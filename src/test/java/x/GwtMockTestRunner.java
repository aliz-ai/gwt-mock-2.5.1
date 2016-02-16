package x;

import java.lang.reflect.Field;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.Translator;

// many stuff copied from https://github.com/google/gwtmockito/blob/master/gwtmockito/src/main/java/com/google/gwtmockito/GwtMockitoTestRunner.java
public class GwtMockTestRunner extends BlockJUnit4ClassRunner {

	private GwtMockClassLoader classLoader;
	private ClassLoader parentClassLoader;

	public GwtMockTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
		parentClassLoader = Thread.currentThread().getContextClassLoader();
		// Build a fresh class pool with the system path and any user-specified
		// paths and use it to
		// create the custom classloader
		ClassPool classPool = new ClassPool();
		classPool.appendClassPath(new LoaderClassPath(parentClassLoader));

		classLoader = new GwtMockClassLoader(parentClassLoader, classPool);

		try {
			Thread.currentThread().setContextClassLoader(classLoader);
			// Reload the test class with our own custom class loader that does
			// things like remove
			// final modifiers, allowing GWT Elements to be mocked. Also load
			// GwtMockito itself so we can
			// invoke initMocks on it later.
			Class<?> customLoadedTestClass = classLoader.loadClass(klass.getName());

			// Overwrite the private "fTestClass" field in ParentRunner
			// (superclass of
			// BlockJUnit4ClassRunner). This refers to the test class being run,
			// so replace it with our
			// custom-loaded class. As of JUnit 4.12, "fTestClass" was renamed
			// to "testClass".
			Field testClassField;
			try {
				testClassField = ParentRunner.class.getDeclaredField("fTestClass");
			} catch (NoSuchFieldException e) {
				testClassField = ParentRunner.class.getDeclaredField("testClass");
			}
			testClassField.setAccessible(true);
			testClassField.set(this, new TestClass(customLoadedTestClass));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Thread.currentThread().setContextClassLoader(parentClassLoader);
		}
	}

	public static class GwtMockClassLoader extends Loader implements Translator {
		public GwtMockClassLoader(ClassLoader classLoader, ClassPool classPool) {
			super(classLoader, classPool);
			try {
				addTranslator(classPool, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected Class findClass(String name) throws ClassNotFoundException {
			System.out.println("findclass: " + name);
			if (name.startsWith("com.altirnao.aodocs")) {
				System.out.println("loading aodocs class");
				return super.findClass(name);
			}
			return null;
		}

		@Override
		public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
			System.out.println("loading class");
			CtClass clazz = pool.get(classname);
			for (CtMethod method : clazz.getDeclaredMethods()) {
				if ((method.getModifiers() & Modifier.NATIVE) != 0) {
					System.out.println("native method found");
					method.setModifiers(method.getModifiers() & ~Modifier.NATIVE);
					CtClass returnType = method.getReturnType();
					String arguments = "";
					for (int i = 0; i < method.getParameterTypes().length; i++) {
						if (i > 0) {
							arguments += ",";
						}
						arguments += "$" + (i + 1);
					}
					method.setBody("return " + clazz.getName() + "_Hacked." + method.getName() + "(" + arguments + ");");
				}
			}
		}

		@Override
		public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
			// nothing
		}
	}

	@Override
	public void run(RunNotifier arg0) {
		try {
			Thread.currentThread().setContextClassLoader(classLoader);
			super.run(arg0);
		} finally {
			Thread.currentThread().setContextClassLoader(parentClassLoader);
		}
	}
}
