package com.queshen.utils;

import com.queshen.pojo.bo.FastDFSFile;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 操作文件的端口
 * @author WinstonYv
 * @create 2022/11/14
 **/
@Slf4j
public class FastDFSClient {


    /*
     * 初始化加载FastDFS的TrackerServer配置
     */
    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!",e);
        }
    }

    /***
     * 文件上传
     */
    public static String[] upload(FastDFSFile file) {
        //获取文件的作者
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());

        //接收返回数据
        String[] uploadResults = null;
        StorageClient storageClient=null;
        try {
            //创建StorageClient客户端对象
            storageClient = getTrackerClient();

            /*
             * 文件上传
             * 1)文件字节数组
             * 2)文件扩展名
             * 3)文件作者
             */
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (Exception e) {
            log.error("Exception when the file:" + file.getName(), e);
        }

        if (uploadResults == null && storageClient != null) {
            log.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        return uploadResults;
    }

    /***
     * 获取文件信息
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            log.error("Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    /***
     * 文件下载
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            //创建StorageClient
            StorageClient storageClient = getTrackerClient();

            //下载文件
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(fileByte);
        } catch (Exception e) {
            log.error("Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    /***
     * 文件删除
     */
    public static void deleteFile(String groupName, String remoteFileName)
            throws Exception {
        //创建StorageClient
        StorageClient storageClient = getTrackerClient();

        //删除文件
        int i = storageClient.delete_file(groupName, remoteFileName);
    }

    /***
     * 获取Storage组
     */
    public static StorageServer[] getStoreStorages(String groupName)
            throws IOException {
        //创建TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //获取TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage组
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    /***
     * 获取Storage信息,IP和端口
     */
    public static ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /***
     * 获取Tracker服务地址
     */
    public static String getTrackerUrl() throws IOException {
        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ ClientGlobal.getG_tracker_http_port()+"/";
    }

    /***
     * 获取Storage客户端
     */
    private static StorageClient getTrackerClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        return new StorageClient(trackerServer, null);
    }

    /***
     * 获取Tracker
     */
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }
}
