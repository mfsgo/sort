package sort;

import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {

//生成20G数据
//        for(int i = 0;i < 20 ;i++){
//            CreateRandomStrings.createrandomstrings();
//        }


        String readpath = "F:/bigdata/sort/src/randomstrings12.txt";     //读入文件路径
        String writepath = "F:/bigdata/sort/src/sortedstrings/test6.txt";

        long startTime = System.currentTimeMillis();
//插入排序

//        try {
//            insertsort.readFromTxt(readpath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        insertsort.writeSortedTxt(writepath);



//归并排序

//        Merge s = new Merge();
//        try {
//            Merge.readFromTxt(readpath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        s.quartion(Merge.arr,0,Merge.arr.size() - 1);
//        Merge.writeSortedTxt(writepath);



        File inputFile=new File("F:/bigdata/sort/src/randomstrings4.txt");
        File outputFile=new File("F:/bigdata/test/output/output1.txt");
        File tempFile=new File("F:/bigdata/test/tempFile");
//插入+多路归并外排序

        if (outputFile.exists())
            outputFile.delete();
        try {
            insertExternalSort.test(inputFile,outputFile,tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


//归并+多路归并外排序


//        if (outputFile.exists())
//            outputFile.delete();
//        try {
//            mergeExternalSort.test(inputFile,outputFile,tempFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        long endTime = System.currentTimeMillis();
        long usedTime = (endTime - startTime);
        System.out.println(usedTime);
    }

}
