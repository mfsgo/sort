package sort;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
//归并排序

public class Merge {

    static ArrayList<String> arr = new ArrayList<>();

    public static void readFromTxt(String path) throws IOException {    //读取文件


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

    }

    public void quartion(ArrayList<String> arr, int low, int high){ //实现“分治”

        if (low >= high) return;

        int mid = low + (high - low) / 2;
        quartion(arr,low,mid);
        quartion(arr,mid + 1,high);
        merge(arr, low, mid ,high);
    }

    public void merge(ArrayList<String> arr,int low, int mid, int high){
        ArrayList<String> temp = new ArrayList();
        int i = low, j = mid + 1;

        for (int z = 0; z < arr.size(); z++){
            if (i <= mid && (j > high || arr.get(i).compareTo(arr.get(j)) <= 0)){
                temp.add(z,arr.get(i++));
            }else if (j <= high && (i > mid || arr.get(j).compareTo(arr.get(i)) <= 0 )){
                temp.add(z,arr.get(j++));
            }
        }
        for (int z = 0 ; z < temp.size();z++){
            arr.set(low + z,temp.get(z));
        }
        arr.add("");
    }

    public static void writeSortedTxt(String path){     //写入输出文件

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