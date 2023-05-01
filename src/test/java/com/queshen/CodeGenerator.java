package com.queshen;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * @author WinstonYv
 * @since 2022/12/13
 * mapper类和数据库实体类
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/queshen?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("WinstonYv") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.queshen"); // 设置父包名
                            //.moduleName("pojo"); // 设置父包模块名
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath+"src/main/java/com/queshen/service/com.queshen.mapper/xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    builder.addInclude("dm_date_group","dm_date_item","dm_default_mode", "dm_default_task",
//                                    "dm_member", "dm_salary_item","dm_schedule","dm_schedule_item",
//                                    "dm_shop_group","dm_shop_group_item","dm_task_group", "dm_task_group_item", "dm_task_item") // 设置需要生成的表名
                    builder.addInclude("t_comment","t_comment_like","t_chat_msg");
//                    builder.addInclude("dm_member", "dm_task_item", "dm_task_group_item")
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
