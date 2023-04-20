package com.queshen.pojo.bo;

import lombok.Data;

/**
 * FastDFS 所能交互文件类型
 * @author WinstonYv
 * @since 2022/11/14
 * @Description:
 */
@Data
public class FastDFSFile {
    //文件名字
    private String name;

    //文件内容
    private byte[] content;

    //文件扩展名
    private String ext;

    //文件MD5摘要值
    private String md5;

    //文件创建作者
    private String author;


    public FastDFSFile(String name, byte[] content, String ext, String height,
                       String width, String author) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
    }
}
