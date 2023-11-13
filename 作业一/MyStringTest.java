import java.util.Arrays;
import java.util.Random;

public class MyStringTest {
    private static void Test_correctness(Random random){
        // Only has '0', '1', 'null'
        char [] values = new char[3];
        values[0] = '0';
        values[1] = '1';

        // String 1
        int str1Len = random.nextInt(40);
        char [] chars1 = new char[str1Len];
        for (int i = 0; i < str1Len; ++i){
            chars1[i] = values[random.nextInt(3)];
        }
        String str1 = new String(chars1);
        MyString my_str1 = new MyString(chars1);

        // String 2
        int str2Len = random.nextInt(10);
        char [] chars2 = new char[str2Len];
        for (int i = 0; i < str2Len; ++i){
            chars2[i] = values[random.nextInt(3)];
        }
        String str2 = new String(chars2);
        MyString my_str2 = new MyString(chars2);

        // String 3
        int str3Len = random.nextInt(10);
        char [] chars3 = new char[str3Len];
        for (int i = 0; i < str3Len; ++i){
            chars3[i] = values[random.nextInt(3)];
        }
        String str3 = new String(chars3);
        MyString my_str3 = new MyString(chars3);

        // Test three interfaces, throw exception if any error
        if(str1.indexOf(str2) != my_str1.indexOf(chars2)){
            System.out.println("indexOf wrong! str1 is " + str1 + "str2 is" + str2);
            throw new RuntimeException();
        }
        if(!str1.concat(str2).equals(new String(my_str1.concat(chars2).getValue()))){
            System.out.println("concat wrong! str1 is " + str1 + "str2 is" + str2);
            throw new RuntimeException();
        }
        if(!str1.replace(str2, str3).equals(new String(my_str1.replace(chars2, chars3).getValue()))){
            System.out.println("replace wrong! str1 is " + str1 + "str2 is" + str2);
            throw new RuntimeException();
        }
    }
    private static void PerformanceTest(){
        String stringOrig = "1234567812345678123456781234567812345678123456781234567812345679";
        char [] chars = stringOrig.toCharArray();

        String stringToBeFound = "123456789";
        char [] dest = stringToBeFound.toCharArray();

        String stringToBeReplaced = "1234567";
        char [] toBeReplaced = stringToBeReplaced.toCharArray();

        long startTime;
        long endTime;
        long TIMES1 = 1000000;

        MyString a = new MyString(chars);
        startTime = System.currentTimeMillis(); //Acquire current time
        for(int i = 0; i < TIMES1; i++) {
            a.indexOf(dest);
            //System.out.println("ret = " + a.indexOf(dest));
        }
        endTime = System.currentTimeMillis();
        System.out.println("MyString Time for indexOf: "+(endTime-startTime)+"ms");

        startTime = System.currentTimeMillis(); //Acquire current time
        for(int i = 0; i < TIMES1; i++) {
            int d = stringOrig.indexOf(stringToBeFound);
        }
        endTime = System.currentTimeMillis();
        System.out.println("String Time for indexOf: "+(endTime-startTime)+"ms");
        System.out.print("Test indexOf correctness: ");
        System.out.println(stringOrig.indexOf(stringToBeFound) == a.indexOf(dest));
        System.out.println();

        startTime = System.currentTimeMillis(); //Acquire current time
        for(int i = 0; i < TIMES1; i++) {
            a.concat(dest);
            //System.out.println("ret = " + a.indexOf(dest));
        }
        endTime = System.currentTimeMillis();
        System.out.println("MyString Time for concat: "+(endTime-startTime)+"ms");

        startTime = System.currentTimeMillis(); //Acquire current time
        for(int i = 0; i < TIMES1; i++) {
            stringOrig.concat(stringToBeFound);
        }
        endTime = System.currentTimeMillis();
        System.out.println("String Time for concat: "+(endTime-startTime)+"ms");
        System.out.print("Test concat correctness: ");
        System.out.println(stringOrig.concat(stringToBeFound).equals(new String(a.concat(dest).getValue())));
        System.out.println();

        startTime = System.currentTimeMillis(); //Acquire current time
        for(int i = 0; i < TIMES1; i++) {
            a.replace(toBeReplaced, dest);
            //System.out.println("ret = " + a.indexOf(dest));
        }
        endTime = System.currentTimeMillis();
        System.out.println("MyString Time for replace: "+(endTime-startTime)+"ms");

        startTime = System.currentTimeMillis(); //Acquire current time
        for(int i = 0; i < TIMES1; i++) {
            stringOrig.replace(stringToBeReplaced, stringToBeFound);
        }
        endTime = System.currentTimeMillis();
        System.out.println("String Time for replace: "+(endTime-startTime)+"ms");
        System.out.print("Test replace correctness: ");
        System.out.println(stringOrig.replace(stringToBeReplaced, stringToBeFound).equals(new String(a.replace(toBeReplaced, dest).getValue())));
        System.out.println();
    }
    public static void main(String[] args) {
        System.out.println("Begin random tests ...");
        Random random = new Random();
        for (int i = 0; i < 50000; ++i){
            Test_correctness(random);
        }
        System.out.println("Pass all random tests!");
        System.out.println();

        System.out.println("Begin performance tests ...");
        PerformanceTest();
        System.out.println("End performance tests.");
    }
}

/**  Efficiency Result (1,000,000 times for example, check graph for statistic of more times):
 *
 * MyString Time for indexOf: 110ms
 * String Time for indexOf: 25ms
 * Test indexOf correctness: true
 *
 * MyString Time for concat: 110ms
 * String Time for concat: 25ms
 * Test concat correctness: true
 *
 * MyString Time for replace: 390ms
 * String Time for replace: 170ms
 * Test replace correctness: true
 *
 */
