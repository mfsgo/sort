package sort;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
//改良后的插入排序，在读取输入流的同时进行排序操作
public class insertsort {

    static ArrayList<String> arr = new ArrayList<>();

    public static void readFromTxt(String path) throws IOException{

        File file = new File(path);

        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));

        BufferedReader br = new BufferedReader(reader);

        String line ="";
        arr.add(0, br.readLine());
        line = br.readLine();
        int i = 0;

        int index = 1;
        while (line != null){
            int j = index - 1;
            while(j >= 0 && arr.get(j).compareTo(line) > 0){
                j--;
            }
            arr.add(j + 1,line);
            index++;
            line = br.readLine();
        }
        String str ="";
        arr.add(str);

    }

    public static void insertSort(){
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

    public static void writeSortedTxt(String path){

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