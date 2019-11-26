package com.nonce.restsecurity;

import com.nonce.restsecurity.util.NIOUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Andon
 * @date 2019/11/26
 */
@Slf4j
public class NioTest {

    @Test
    public void readNioTest() {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("D:\\nio.txt", "rw");
            FileChannel channel = randomAccessFile.getChannel();

            ByteBuffer allocate = ByteBuffer.allocate(1024);
//            ByteBuffer allocate = ByteBuffer.allocate(1024 << 4); //16KB缓冲区
            System.out.println("channel >> 限制是：" + allocate.limit() + ",容量是：" + allocate.capacity() + " ,位置是：" + allocate.position());

            int length;
            while ((length = channel.read(allocate)) != -1) {
                byte[] array = allocate.array();
                String s = new String(array, 0, length);
                log.info("s >> {}", s);
                System.out.println("length:" + length + " array.length:" + array.length);
                System.out.println("channel >> 限制是：" + allocate.limit() + ",容量是：" + allocate.capacity() + " ,位置是：" + allocate.position());
                allocate.clear();
            }
            System.out.println("channel >> 限制是：" + allocate.limit() + ",容量是：" + allocate.capacity() + " ,位置是：" + allocate.position());
            channel.close();
        } catch (Exception e) {
            log.error("readNioTest failure!! error={} message={}", e, e.getMessage());
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

    @Test
    public void nioTest() {
        String s = NIOUtil.readFile("D:\\nio.txt");
        System.out.println(s);
        NIOUtil.writeFile(s, "nioWrite.txt");
    }
}
