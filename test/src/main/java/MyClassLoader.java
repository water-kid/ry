import com.cj.User;
import jdk.nashorn.internal.ir.CallNode;

import java.io.FileInputStream;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {
    // classLoader加载路径
    private String classPath;

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                if (name.startsWith("com.cj")){
                    // com.cj包 调用自己的类加载器
                    c = findClass(name);
                }else{
                    // 其他使用双亲委派
                    c = super.loadClass(name,resolve);
                }

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        // 加载data
        byte[] data = new byte[0];
        try {
            String path = name.replaceAll("\\.", "/").concat(".class");
            FileInputStream fis = new FileInputStream(classPath + "/" + path);
            // 返回有多少字节
            int len = fis.available();

            data = new byte[len];

            fis.read(data);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // defineClass() 将一个字节数组转换为Class对象
        return defineClass(name,data,0,data.length);

    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        Class<?> clazz = classLoader.loadClass("com.cj.User");
        Object obj = clazz.newInstance();
        System.out.println(clazz.getClassLoader().getClass().getName());
        System.out.println(User.class.getClassLoader().getClass().getName());
    }
}