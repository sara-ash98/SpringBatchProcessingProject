package com.sara.Customer_batch_processing.Config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.FileSystemResource;


public class MultiFileItemReader<T> implements ItemReader<T> {

    private final Iterator<FlatFileItemReader<T>> readerIterator;
    private FlatFileItemReader<T> currentReader;
    private final List<String> fileNames;
    private int currentFileIndex=-1;
    private final String backupDirectory="src/main/resources/BackUpDir/";

    public MultiFileItemReader(List<String> fileNames, LineMapper<T> lineMapper) {
    	this.fileNames=fileNames;
        readerIterator = fileNames.stream()
            .map(fileName -> createReader(fileName, lineMapper))
            .iterator();
    }

    private FlatFileItemReader<T> createReader(String fileName, LineMapper<T> lineMapper) {
        FlatFileItemReader<T> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(fileName));
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Override
    public T read() throws Exception {
        while (true) {
            if (currentReader == null || currentReader.read() == null) {
                if (readerIterator.hasNext()) {
                    if (currentReader != null) {
                        currentReader.close(); 
                        moveFileToBackup(fileNames.get(currentFileIndex));
                    }
                    currentFileIndex++;
                    currentReader = readerIterator.next(); 
                    currentReader.open(new ExecutionContext()); 
                } else {
                    return null; 
                }
            }

            T item = currentReader.read(); 
            if (item != null) {
                return item; 
            }
        }
    }
    private void moveFileToBackup(String fileName) {
        try {
            Path sourcePath = new File(fileName).toPath();
            Path backupPath = new File(backupDirectory + new File(fileName).getName()).toPath();
            
            Files.createDirectories(backupPath.getParent());
            Files.move(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
}