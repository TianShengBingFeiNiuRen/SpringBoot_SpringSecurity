package com.nonce.restsecurity.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andon
 * @date 2019/11/26
 * <p>
 * NIO工具类
 */
@Slf4j
public class NIOUtil {

    /**
     * 读文件返回String
     *
     * @param path 文件路径
     * @return 读取内容
     */
    public static String readFile(String path) {
        String string = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(path, "rws");
            FileChannel channel = randomAccessFile.getChannel();

            ByteBuffer allocate = ByteBuffer.allocate(1024 << 4); //16KB缓冲区
            List<Byte> byteList = new ArrayList<>();
            byte[] array;
            int length;
            while ((length = channel.read(allocate)) != -1) {
                array = allocate.array();
                for (int i = 0; i < length; i++) {
                    byteList.add(array[i]);
                }
                allocate.clear();
            }
            channel.close();
            byte[] arr = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                arr[i] = byteList.get(i);
            }
            string = new String(arr, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("readFile failure!! error={} message={}", e, e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    log.error("randomAccessFile.close() failure!! error={} message={}", e, e.getMessage());
                }
            }
        }
        return string;
    }

    /**
     * String写文件
     *
     * @param text 内容
     * @param path 目标文件路径
     */
    public static void writeFile(String text, String path) {
        RandomAccessFile randomAccessFile = null;
        try {
            Files.deleteIfExists(Paths.get(path)); //目标文件存在先删除
            randomAccessFile = new RandomAccessFile(path, "rws");
            FileChannel channel = randomAccessFile.getChannel();
            channel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));
            channel.close();
        } catch (Exception e) {
            log.error("writeFile failure!! error={} message={}", e, e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    log.error("randomAccessFile.close() failure!! error={} message={}", e, e.getMessage());
                }
            }
        }
    }
}
