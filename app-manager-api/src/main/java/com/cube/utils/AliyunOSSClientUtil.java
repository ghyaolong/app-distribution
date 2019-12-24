package com.cube.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AliyunOSSClientUtil {
    //log日志


    //阿里云API的外网域名
    public static final String ENDPOINT = "http://oss-cn-hongkong.aliyuncs.com";
    //阿里云API的密钥Access Key ID
    public static final String ACCESS_KEY_ID = "LTAI0HPkgFxYzhIC";

    //阿里云API的密钥Access Key Secret
    public static final String ACCESS_KEY_SECRET = "xPXlSbDSxyN4pMDf5wUcByIDVJmt4N";
    //阿里云API的bucket名称
    public static final String BACKET_NAME = "jhfenfa";
    //阿里云API的文件夹名称
    public static final String FOLDER="identity/";

    private static String localFilePath = "D://temp/";

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

    public static OSS client = null;

    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public static void getOSSClient(){
        if(client!=null){
            client.shutdown();
            client = null;
        }
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setIdleConnectionTime(1000);
        client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, conf);
    }



    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public  static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);

            log.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(String bucketName){
        getOSSClient();
        client.deleteBucket(bucketName);
        log.info("删除" + bucketName + "Bucket成功");
    }

//    /**
//     * 创建模拟文件夹
//     * @param ossClient oss连接
//     * @param bucketName 存储空间
//     * @param folder   模拟文件夹名如"qj_nanjing/"
//     * @return  文件夹名
//     */
//    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
//        //文件夹名
//        final String keySuffixWithSlash =folder;
//        //判断文件夹是否存在，不存在则创建
//        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
//            //创建文件夹
//            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
//            log.info("创建文件夹成功");
//            //得到文件夹名
//            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
//            String fileDir=object.getKey();
//            return fileDir;
//        }
//        return keySuffixWithSlash;
//    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param bucketName  存储空间
     * @param folder  模拟文件夹名 如"qj_nanjing/"
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
        ossClient.deleteObject(bucketName, folder + key);
        log.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 上传图片至OSS
     * @param ossClient  oss连接
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * */
//    public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
//        String resultStr = null;
//        try {
//            //以输入流的形式上传文件
//            InputStream is = new FileInputStream(file);
//            //文件名
//            String fileName = file.getName();
//            //文件大小
//            Long fileSize = file.length();
//            //创建上传Object的Metadata
//            ObjectMetadata metadata = new ObjectMetadata();
//            //上传的文件的长度
//            metadata.setContentLength(is.available());
//            //指定该Object被下载时的网页的缓存行为
//            metadata.setCacheControl("no-cache");
//            //指定该Object下设置Header
//            metadata.setHeader("Pragma", "no-cache");
//            //指定该Object被下载时的内容编码格式
//            metadata.setContentEncoding("utf-8");
//            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
//            //如果没有扩展名则填默认值application/octet-stream
//            metadata.setContentType(getContentType(fileName));
//            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
//            //上传文件   (上传文件流的形式)
//            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
//            //解析结果
//            resultStr = putResult.getETag();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
//        }
//        return resultStr;
//    }


    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    private static String getFileSuffix(String fileName){
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return fileExtension;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }

        if(".apk".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.android.package-archive";
        }

        if(".ipa".equalsIgnoreCase(fileExtension)) {
            return "application/iphone";
        }
        //默认返回类型
        return "image/jpeg";
    }
    /**
     * 分片上传
     * @param filePath
     * return 返回文件的可下载地址
     */
    public static String multipartUpload(String filePath){
        getOSSClient();
        try {
            /*
             * Calculate how many parts to be divided
             */
            final long partSize = 5 * 1024 * 1024L;   // 5MB
            //final File sampleFile = createSampleFile();
            final File sampleFile = new File(filePath);

            //文件后缀
            String suffixName = getFileSuffix(filePath);
            //生产上传的文件名
            String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;


            String objectName = "04afe3b17ce560731151e1a055c74433" + "/" + finalFileName;

            String uploadId = claimUploadId(objectName);

            long fileLength = sampleFile.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            if (partCount > 10000) {
                throw new RuntimeException("Total parts count should not exceed 10000");
            } else {
                System.out.println("Total parts count " + partCount + "\n");
            }

            /*
             * Upload multiparts to your bucket
             */
            System.out.println("Begin to upload multiparts to OSS from a file\n");
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                executorService.execute(new PartUploader(sampleFile,objectName, startPos, curPartSize, i + 1, uploadId));
            }

            /*
             * Waiting for all parts finished
             */
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*
             * Verify whether all parts are finished
             */
            if (partETags.size() != partCount) {
                throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
            } else {
                System.out.println("Succeed to complete multiparts into an object named " + ACCESS_KEY_ID + "\n");
            }

            /*
             * View all parts uploaded recently
             */
            listAllParts(uploadId,objectName);

            /*
             * Complete to upload multiparts
             */
            String downLoadUrl = completeMultipartUpload(uploadId, objectName);
            return downLoadUrl;
        }catch (OSSException oe){
            log.error("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.");
            log.error("Error Message: " + oe.getErrorMessage());
            log.error("Error Code:       " + oe.getErrorCode());
            log.error("Request ID:      " + oe.getRequestId());
            log.error("Host ID:           " + oe.getHostId());
            oe.printStackTrace();
        }catch (ClientException ce){
            ce.printStackTrace();
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ce.getMessage());
        }finally {
            if (client != null) {
                client.shutdown();
            }
        }
        return null;
    }

    private static String claimUploadId(String objectName) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(BACKET_NAME, objectName);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    private static void listAllParts(String uploadId,String objectName) {
        System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(BACKET_NAME, objectName, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            System.out.println("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
        System.out.println();
    }

    /**
     * 返回文件的下载地址
     * @param uploadId
     * @param objectName
     * @return
     */
    private static String completeMultipartUpload(String uploadId,String objectName) {
        // Make part numbers in ascending order
        Collections.sort(partETags, Comparator.comparingInt(PartETag::getPartNumber));

        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(BACKET_NAME, objectName, uploadId, partETags);
        CompleteMultipartUploadResult completeMultipartUploadResult = client.completeMultipartUpload(completeMultipartUploadRequest);
        String eTag = completeMultipartUploadResult.getETag();
        String location = completeMultipartUploadResult.getLocation();
        System.out.println(location);
        System.out.println("合并完之后的文件tag："+eTag);
        return location;
    }

    private static class PartUploader implements Runnable {

        private File localFile;
        private long startPos;

        private long partSize;
        private int partNumber;
        private String uploadId;

        private String objectName;

        public PartUploader(File localFile, String objectName,long startPos, long partSize, int partNumber, String uploadId) {
            this.localFile = localFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
            this.objectName = objectName;
        }

        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = new FileInputStream(this.localFile);
                instream.skip(this.startPos);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(BACKET_NAME);
                uploadPartRequest.setKey(objectName);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                System.out.println("Part#" + this.partNumber + " done\n");
                synchronized (partETags) {
                    partETags.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 这里的fileName只的是oss里的OSSObjectSummary对象里的key
     * 如：
     * {
     *   "bucketName":"jhfenfa",
     *   "eTag":"D41D8CD98F00B204E9800998ECF8427E-0",
     *   "key":"04afe3b17ce560731151e1a055c74433/1576654585253573.apk",
     *   "lastModified":1576654584000,
     *   "owner":{
     *     "displayName":"1187633538637363",
     *     "id":"1187633538637363"
     *   },
     *   "size":0,
     *   "storageClass":"Standard"
     * }
     * @param fileName
     */
    public static void deleteByFileName(String fileName){
        getOSSClient();
        client.deleteObject(BACKET_NAME,fileName);
    }

    public static void getAllFiles(){
        getOSSClient();
        ObjectListing objectListing = client.listObjects(BACKET_NAME);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary sum : sums) {
            //hongkong.aliyuncs.com/04afe3b17ce560731151e1a055c74433/1576654585253573.apk
            System.out.println(JSON.toJSONString(sum));
        }
    }


    //测试
    public static void main(String[] args) {
        //初始化OSSClient
//        OSSClient ossClient=AliyunOSSClientUtil.getOSSClient();
//        //上传文件
//        String files="D:\\image\\1.jpg,D:\\image\\2.jpg,D:\\image\\3.jpg";
//        String[] file=files.split(",");
//        for(String filename:file){
//            //System.out.println("filename:"+filename);
//            File filess=new File(filename);
//            String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, filess, BACKET_NAME, FOLDER);
//            log.info("上传后的文件MD5数字唯一签名:" + md5key);
//            //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
//        }

//        String filePath = "E:\\saytokenrn.apk";
//        AliyunOSSClientUtil.multipartUpload(filePath);
        //AliyunOSSClientUtil.deleteByFileName("04afe3b17ce560731151e1a055c74433/157665729114451.apk");

        AliyunOSSClientUtil.getAllFiles();
    }


}
