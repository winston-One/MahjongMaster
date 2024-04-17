package com.queshen;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WinstonYv
 * @since 2022/12/13
 * mapper类和数据库实体类
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String moduleName = "";
        String modulePath;
        String moduleEntity;
        if (StringUtils.hasLength(moduleName)) {
            modulePath = "/" + moduleName;
            moduleEntity = "." + moduleName;
        } else {
            modulePath = "";
            moduleEntity = "";
        }
        System.out.println(projectPath);
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/queshen?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("WinstonYv") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    Map<OutputFile, String> map = new HashMap<>();
                    map.put(OutputFile.entity, projectPath + "/src/main/java/com/queshen/pojo.po" + modulePath);//设置entity生成路径
                    map.put(OutputFile.service, projectPath + "/src/main/java/com/queshen/service" + modulePath);//设置service生成路径
                    map.put(OutputFile.serviceImpl, projectPath + "/src/main/java/com/queshen/service" + modulePath + "/impl");//设置serviceImpl生成路径
                    map.put(OutputFile.mapper, projectPath + "/src/main/java/com/queshen/mapper" + modulePath);//设置mapper生成路径或者设置mapperXml生成路径
//                    map.put(OutputFile.xml, projectPath + "/src/main/java/com/queshen/mapper" + modulePath + "/xml");//设置mapperXml生成路径
                    builder.parent("com.queshen") //设置父包名
                            .entity("pojo.po" + moduleEntity)
                            .service("service" + moduleEntity)
                            .serviceImpl("service" + moduleEntity + ".impl")
                            .mapper("mapper" + moduleEntity)
                            .xml("mapper" + moduleEntity + ".xml")
                            .pathInfo(map); //设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_comment","t_comment_like","t_chat_msg");
                    builder.addTablePrefix("t_") // 设置过滤表前缀
                            .entityBuilder()
                            .enableLombok()
                            .versionColumnName("version")
                            .logicDeleteColumnName("is_deleted")
                            .logicDeletePropertyName("isDeleted")
                            .idType(IdType.ASSIGN_ID)
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .controllerBuilder()
                            .enableRestStyle();
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
