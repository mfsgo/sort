package sort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//使用插入排序的多路归并外排序

public class insertExternalSort {
    static final int maxSize = 15000000;//内存每次最多放1500000条记录
    static final char[] maxKey = {255, 255, 255, 255, 255, 255, 255, 255, ','};


    public static void test(File inputFile, File outputFile, File tempFile) throws Exception {

        ArrayList<String> sortarr = new ArrayList<>();
        String line = null;//用来存放每次从缓冲区读入的一条记录

        BufferedReader bufr = new BufferedReader(new FileReader(inputFile));
        List<File> tempFiles = new ArrayList<>();
        int tempSize = 10000;

        while ((line = bufr.readLine()) != null){   //从输入流读取数据同时进行插入排序
            File newTempFile = File.createTempFile("tempFile", ".txt", tempFile);
            tempFiles.add(newTempFile);
            BufferedWriter bufw = new BufferedWriter(new FileWriter(newTempFile));
            int index = 0;

            while (line != null){   //排序
                int j = index - 1;
                while(j >= 0 && sortarr.get(j).compareTo(line) > 0){
                    j--;
                }
                sortarr.add(j + 1,line);
                index++;
                if (index == tempSize){
                    break;
                }
                line = bufr.readLine();
            }

            int z = 0;
            while(sortarr.get(z) != null) {     //将排序完成的数据写入tempFile文件
                bufw.write(sortarr.get(z++));
                bufw.newLine();
                if(z == index){
                    break;
                }
            }
            sortarr.clear();
            bufw.close();
        }
        sortarr = null;

        System.gc();
        System.runFinalization();
        System.gc();

        multiWayMergeSort(tempFiles, outputFile);
        for (File file : tempFiles
        ) {
            file.delete();
        }

    }




    static void multiWayMergeSort(List<File> files, File outputFile) throws IOException {   //外排序多路归并
        int ways = files.size();
        int length_per_run = maxSize / ways;
        Run[] runs = new Run[ways];
        for (int i = 0; i < ways; i++) {
            runs[i] = new Run(length_per_run);
        }
        List<BufferedReader> rList = new ArrayList<>();//将数据读取到buffer

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
        //合并文件并写入输出文件
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

    private static void createLoserTree(int[] ls, Run[] runs, int n) {  //创建败者树

        for (int i = 0; i < n; i++) {
            ls[i] = -1;
        }
        for (int i = n - 1; i >= 0; i--) {
            adjust(ls, runs, n, i);
        }
    }

    private static void adjust(int[] ls, Run[] runs, int n, int s) {    //调整败者树
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


    static class Run {  //用于储存数据的类
        String[] buffer;
        int length;
        int index;

        Run(int length) {
            this.length = length;
            buffer = new String[length];
        }
    }

}
