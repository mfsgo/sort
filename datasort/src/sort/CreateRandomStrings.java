package sort;
import java.io.*;
import java.util.Random;

//生成随机待排序txt文件

public class CreateRandomStrings {

    public static boolean createrandomstrings() throws IOException {
        boolean flag = false;
        String path = "F:/bigdata/sort/src/randomstrings12.txt";     //文件路径
        File file = new File(path);
        BufferedWriter bufw = new BufferedWriter(new FileWriter(file,true));

        try{
            if(!file.exists()){
                file.createNewFile();
            }
            for(int i = 0; i < 21000000;i++){     //对文件写入随机字符串
                bufw.write(getRandomString());
            }
            bufw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return flag;
    }

    public static String getRandomString(){     //生成一个随机字符串序列
        String str ="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";       //字符串中可能包括的字符
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 50; i++){        //字符串长度为50
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        sb.append("\n");
        return sb.toString();
    }

}
