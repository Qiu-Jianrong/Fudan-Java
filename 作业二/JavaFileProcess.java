import java.io.*;
import java.util.Objects;

/**
 * A new type of Exception indicating there is no match.
 */
class TODONotFoundException extends Exception{
    public TODONotFoundException(String msg){super(msg);}
}
public class JavaFileProcess {
    public JavaFileProcess(){super();}

    /**
     * @description Search the file for "//todo:"
     * print and return immediately once finding.
     * If no such match, throw TODONotFoundException with message
     *
     * @param file
     * @throws Exception Not only TODONotFoundException
     */
    public void processJavaFile(File file) throws Exception{
        assert file.isFile();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = bf.readLine();
        while (line != null){
            if (line.contains("//todo:")){
                bf.close();
                System.out.println("There is \"//todo:\" in " + file.getCanonicalPath());
                return;
            }
            line = bf.readLine();
        }
        throw new TODONotFoundException("todo not found");
    }

    /**
     * @description Traverse given path, and recursively call findJavaFiles()
     * for all "*.java" files. Besides, handle the exceptions findJavaFiles() throws.
     * @param path
     */
    public void findJavaFiles(String path) throws Exception {
        File path_file = new File(path);
        if(path_file.isFile()){
            if (path_file.getName().endsWith(".java")){
                try {
                    processJavaFile(path_file);
                }
                catch (TODONotFoundException e){
                    System.out.println(e.getMessage() + " in " + path_file.getCanonicalPath());
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        } else if (path_file.isDirectory()) {
            for (File subfile: Objects.requireNonNull(path_file.listFiles())){
                findJavaFiles(subfile.toString());
            }
        }
        else {
            throw new Exception("No such file or directory!");
        }
    }
}
