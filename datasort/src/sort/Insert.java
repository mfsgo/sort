package sort;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

// 插入排序

public class Insert {

    static ArrayList<String> arr = new ArrayList<>();

    public static void readFromTxt(String path) throws IOException{     //从文件读取数据
        File file = new File(path);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader br = new BufferedReader(reader);
        String line ="";
        line = br.readLine();
        int i = 0;
        while(line != null){
            arr.add(line);
            line = br.readLine();
        }
        String str ="";
        arr.add(str);
    }

    public static void insertSort(){    //进行插入排序
        int i = 1;

        for(i = 1; i < arr.size();i++){
            String str = arr.remove(i);
            int flag = 1;
            int j = i - 1;
            while(j >= 0 && arr.get(j).compareTo(str) > 0){
                j--;
            }
            arr.add(j + 1,str);
        }
        String str ="";
        arr.add(str);
    }

    public static void writeSortedTxt(String path){     //将排序结果写入输出文件
        File file = new File(path);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            int i = 0;

            while(arr.get(i).length() != 0){
                FileWriter fw = new FileWriter(path,true);
                fw.write(arr.get(i));
                fw.write("\r\n");
                fw.close();
                i++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}

