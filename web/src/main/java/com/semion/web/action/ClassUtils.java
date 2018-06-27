package com.semion.web.action;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by heshuanxu on 2018/6/27.
 */
public class ClassUtils {

    /**
     * @Author: heshuanxu
     * @Date: 11:39 2018/6/26
     * @Desc: 获取制定接口的所有实现类
     */
    public static List<Class> getAllCalssesByInteface(Class clazz){

        List<Class>  resultList = new ArrayList<Class>(16);

        if(clazz.isInterface()){
            String packName = clazz.getPackage().getName();
            ArrayList<Class> allClass = getAllClass(packName);
            for (int i = 0; i < allClass.size(); i++) {
                /**
                 * 判断是不是同一个接口
                 */
                // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                if (clazz.isAssignableFrom(allClass.get(i))) {
                    if (!clazz.equals(allClass.get(i))) {
                        // 自身并不加进去
                        resultList.add(allClass.get(i));
                    }
                }
            }

        }
        return resultList;
    }


    private static ArrayList<Class> getAllClass(String packagename) {
        ArrayList<Class> list = new ArrayList<Class>();
        // 返回对当前正在执行的线程对象的引用。
        // 返回该线程的上下文 ClassLoader。
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagename.replace('.', '/');
        try {
            ArrayList<File> fileList = new ArrayList<File>();
            /**
             * 这里面的路径使用的是相对路径 如果大家在测试的时候获取不到，请理清目前工程所在的路径 使用相对路径更加稳定！
             * 另外，路径中切不可包含空格、特殊字符等！
             */
            // getResources:查找所有给定名称的资源
            // 获取jar包中的实现类:Enumeration<URL> enumeration =
            // classLoader.getResources(path);
            Enumeration<URL> enumeration = classLoader.getResources(path);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                // 获取此 URL 的文件名
                fileList.add(new File(url.getFile()));
            }
            for (int i = 0; i < fileList.size(); i++) {
                list.addAll(findClass(fileList.get(i), packagename));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    private static ArrayList<Class> findClass(File file, String packagename) {
        ArrayList<Class> list = new ArrayList<Class>();
        if (!file.exists()) {
            return list;
        }
        // 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                if (!file2.getName().contains(".")) {
                    ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
                    list.addAll(arrayList);
                }
            } else if (file2.getName().endsWith(".class")) {
                try {
                    // 保存的类文件不需要后缀.class
                    list.add(Class.forName(packagename + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }



}
