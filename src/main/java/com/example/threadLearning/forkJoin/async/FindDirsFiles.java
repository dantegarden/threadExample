package com.example.threadLearning.forkJoin.async;

import com.example.threadLearning.SleepTools;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindDirsFiles extends RecursiveAction {

    private File path; //当前任务需要搜寻的目录

    public FindDirsFiles(File path) {
        this.path = path;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles rootTask = new FindDirsFiles(new File("D://"));
        //异步调用
        pool.execute(rootTask);
        System.out.println("Main Thread : Task is running");
        SleepTools.sleepMs(1);
        int otherwork = 0;
        for (int i = 0; i < 100; i++) {
            otherwork += i;
        }
        System.out.println("Main Thread : finished otherwork: " + otherwork);
        rootTask.join(); //阻塞方法 等待forkjoin完成
        System.out.println("Main Thread : Task end");
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTaskList = Lists.newArrayList();

        File[] files = path.listFiles();
        if(files!=null){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if(file.isDirectory()){
                    subTaskList.add(new FindDirsFiles(file));
                }else{
                    if(file.getAbsolutePath().endsWith(".txt")){
                        System.out.println("发现txt文件: " + file.getAbsolutePath());
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(subTaskList)){
                for (FindDirsFiles subTask: invokeAll(subTaskList)) {
                    subTask.join(); //等待子任务执行完成
                }
            }
        }
    }
}
