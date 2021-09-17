package sort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//使用归并排序的多路归并外排序，结构与insertExternalSort.java基本相同，注释参考insertExternalSort

public class mergeExternalSort {
    static final int maxSize = 10000000;//内存每次最多放1000000条记录
    static final char[] maxKey = {255, 255, 255, 255, 255, 255, 255, 255, ','};


    public static void test(File inputFile, File outputFile, File tempFile) throws Exception {

        BufferedReader bufr = new BufferedReader(new FileReader(inputFile));

        ArrayList<String> sortarr = new ArrayList<>();
        String line = null;
        List<File> tempFiles = new ArrayList<>();
        int tempSize = 50000;

        while ((line = bufr.readLine()) != null){
            File newTempFile = File.createTempFile("tempFile", ".txt", tempFile);
            tempFiles.add(newTempFile);
            BufferedWriter bufw = new BufferedWriter(new FileWriter(newTempFile));
            int index = 0;
            String[] sortstr = new  String[tempSize];
            while (line  != null){
                sortstr[index++] = line;
                line = bufr.readLine();
                if(index == tempSize){
                    break;
                }
            }
            sort(sortstr,0, index - 1);

            for (int z = 0;z < index;z++){
                bufw.write(sortstr[z]);
                bufw.newLine();
            }
            sortstr = null;
            bufw.close();
        }

        System.gc();
        System.runFinalization();
        System.gc();

        multiWayMergeSort(tempFiles, outputFile);


        for (File file : tempFiles
        ) {
            file.delete();
        }
    }




    static void multiWayMergeSort(List<File> files, File outputFile) throws IOException {
        int ways = files.size();
        int length_per_run = maxSize / ways;
        Run[] runs = new Run[ways];
        for (int i = 0; i < ways; i++) {
            runs[i] = new Run(length_per_run);
        }
        List<BufferedReader> rList = new ArrayList<>();

        for (int i = 0; i < ways; i++) {
            BufferedReader bufr = new BufferedReader(new FileReader(files.get(i)));
            rList.add(i, bufr);
            int j = 0;
            while ((runs[i].buffer[j] = bufr.readLine()) != null) {
                ++j;
                if (j == length_per_run)
                    break;
            }
            runs[i].length = j;
            runs[i].index = 0;
        }

        int[] ls = new int[ways];
        createLoserTree(ls, runs, ways);
        BufferedWriter bufw = new BufferedWriter(new FileWriter(outputFile));
        int liveRuns = ways;
        while (liveRuns > 0) {
            bufw.write(runs[ls[0]].buffer[runs[ls[0]].index++]);
            bufw.newLine();
            if (runs[ls[0]].index == runs[ls[0]].length) {
                //reload
                int j = 0;
                while ((runs[ls[0]].buffer[j] = rList.get(ls[0]).readLine()) != null) {
                    j++;
                    if (j == length_per_run) {
                        break;
                    }
                }
                runs[ls[0]].length = j;
                runs[ls[0]].index = 0;
            }
            if (runs[ls[0]].length == 0) {
                liveRuns--;
                String maxString = new String(maxKey);
                maxString += "\n";
                runs[ls[0]].buffer[runs[ls[0]].index] = maxString;
            }
            adjust(ls, runs, ways, ls[0]);
        }
        bufw.flush();
        bufw.close();
        for (BufferedReader bufr : rList
        ) {
            bufr.close();
        }

    }

    public static void sort(String str[], int low, int high){
        if(low<high){
            int mid = low+(high-low)/2;
            sort(str,low,mid);
            sort(str,mid+1,high);
            merge(str,low,mid,high);
        }

    }

    public static void merge(String[] str, int low, int mid, int high) {
        String[] copyStr =new String[str.length];
        for(int i=0;i<=high;i++){
            copyStr[i] = str[i];
        }
        int left = low;
        int right = mid+1;
        int current = low;
        while(left<=mid && right<=high){

            if(copyStr[left].compareTo(copyStr[right]) < 0){
                str[current++]=copyStr[left];
                left++;
            }else{
                str[current++]=copyStr[right];
                right++;
            }
        }
        int remaining = mid-left;
        for(int i=0;i<=remaining;i++){
            str[current+i]=copyStr[left+i];
        }
    }

    private static void createLoserTree(int[] ls, Run[] runs, int n) {

        for (int i = 0; i < n; i++) {
            ls[i] = -1;
        }
        for (int i = n - 1; i >= 0; i--) {
            adjust(ls, runs, n, i);
        }
    }

    private static void adjust(int[] ls, Run[] runs, int n, int s) {
        int t = (s + n) / 2;
        int temp = 0;
        while (t != 0) {
            if (s == -1)
                break;

            if (ls[t] == -1 || (runs[s].buffer[runs[s].index]).compareTo(runs[ls[t]].buffer[runs[ls[t]].index]) > 0) {
                temp = s;
                s = ls[t];
                ls[t] = temp;
            }
            t /= 2;
        }
        ls[0] = s;
    }



    static class Run {
        String[] buffer;
        int length;
        int index;

        Run(int length) {
            this.length = length;
            buffer = new String[length];
        }
    }
}
