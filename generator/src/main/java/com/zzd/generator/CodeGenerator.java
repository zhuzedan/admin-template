package com.zzd.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.*;

public class CodeGenerator {
    public static void main(String[] args) {

        // 生产文件的项目相对位置
        StringBuffer projectPath = new StringBuffer();
        // 获取系统路径
        String systemPath = System.getProperty("user.dir");
        // 将反斜杠全部替换为双斜杠 并拼接项目路径
        projectPath.append(systemPath.replaceAll("\\\\", "//"));
        System.out.println(projectPath);

        //entity表字段属性List，用于生成时间自动填充属性
        List<IFill> iFills = new ArrayList<>();
        iFills.add(new Column("create_time", FieldFill.INSERT));
        iFills.add(new Column("update_time", FieldFill.INSERT_UPDATE));

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/manage_sys?characterEncoding=utf-8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder
                            .author("zzd") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "//server//src//main//java")// 指定输出目录
                            .disableOpenDir();// 生成代码后不打开输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com") // 设置父包名
                            .moduleName("zzd") // 设置父包模块名
                            .entity("domain")
                            .controller("controller") // Controller 包名	默认值:controller
                            .other("other") // 自定义文件包名	输出自定义文件时所用到的包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "//server//src//main//resources//mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(getTables(scanner("请输入表名，多个英文逗号分隔？所有输入 all:"))) // 设置需要生成的表名
                            .addTablePrefix("t_")// 设置过滤表前缀
                            .entityBuilder().addTableFills(iFills)//生成时间自动填充属性
                            .controllerBuilder().enableRestStyle()//开启@RestController风格
                            .serviceBuilder().formatServiceFileName("%sService"); //去掉默认的I前缀
                })
                //使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                //设置自定义模板路径
                .templateConfig(builder -> {
                    builder.controller("/templates/controller.java");
                })
                .execute();

    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append(tip);
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (
                    StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 处理 all 情况
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
