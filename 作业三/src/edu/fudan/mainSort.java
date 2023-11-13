package edu.fudan;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public interface mainSort {
    int[] sort(int [] input);
}

class Main{
    public static void main(String[] args){
        Map<String , String> config = new HashMap<>();
        // 1. Read configuration.
        try {
            read_config("./sort.conf", config);
        }catch (IOException e){
            System.out.println("Can not read configuration file!");
            e.printStackTrace();
        }

        //2. Load plugins and run.
        try {
            run_plugin(config);
        }catch (Exception e){
            System.out.println("Fail to run plugin!");
            e.printStackTrace();
        }

    }
    private static void read_config(String filename, Map<String, String> config) throws IOException {
        FileReader fr = new FileReader(filename);
        Properties props = new Properties();
        props.load(fr);
        assert props.containsKey("classname");
        assert props.containsKey("package_name");
        config.put("classname", (String) props.get("classname"));
        config.put("package_name", (String) props.get("package_name"));
    }
    private static void run_plugin(Map<String, String> config) throws MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        // Randomly generate 20 number in [0,99]
        Random random = new Random();
        int len = random.nextInt(20) + 1;
        int[] num = new int[len];
        System.out.print("The numbers to be sorted: ");
        for (int i = 0; i < len; ++i){
            num[i] = random.nextInt(100);
            System.out.print(num[i] + " ");
        }
        System.out.println('\n');

        num = load_and_run(config, num);

        System.out.print("\nThe numbers after sorting: ");
        for (int i = 0; i < len; ++i){
            System.out.print(num[i] + " ");
        }
    }
    private static int[] load_and_run(Map<String, String> config, int[] num) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String classname = config.get("classname");
        String package_name = config.get("package_name");
        URL[] urls = new URL[]{new URL("file:" + classname + ".jar")};
        URLClassLoader loader = new URLClassLoader(urls);
        Class<?> clazz = loader.loadClass(package_name + classname);
        mainSort sort_obj = (mainSort) clazz.getDeclaredConstructor().newInstance();
        // Parameters
        Class<?>[] params = new Class[]{int[].class};
        Method sort_method = clazz.getDeclaredMethod("sort", params);
        num = (int[]) sort_method.invoke(sort_obj, (Object) num);
        return num;
    }

}
