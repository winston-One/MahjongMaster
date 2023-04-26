package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.pojo.bo.FastDFSFile;
import com.queshen.utils.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 前端控制器
 * @author WinstonYv
 * @since 2022/11/14
 * 上传文件和下载文件
 */
@RestController
@RequestMapping("/file")
public class FileController {
    //测试文件存储
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        try {
            //判断当前文件是否存在
            if(null == file) {
                throw new RuntimeException("文件不存在");
            }
            //获取文件名称
            String originalFileName = file.getOriginalFilename();
            if(StringUtils.isEmpty(originalFileName)) {
                throw new RuntimeException("文件不存在");
            }
            //获取源文件拓展名
            String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            //获取文件内容
            byte[] content = file.getBytes();
            //创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFileName, content, extName);
            //提交
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);

            //封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];

            return Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("文件上传失败");
    }
}
