package com.example.caigou;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class AutoGenerateTest {
    @Test
    public void autoGenerate(){
        FastAutoGenerator.create("jdbc:mysql://101.35.160.252:3306/secondhut?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8","secondhut","123456")
                .globalConfig(builder -> {
                    builder.author("Oliver")
                            .fileOverride()
                            .outputDir("~/IdeaProjects/CaiGou");

                })
                .packageConfig(builder -> {
                    builder.parent("com.example")
                            .moduleName("caigou")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,"~/IdeaProjects/caigou/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("views")
                            .entityBuilder()
                            .enableLombok();
//                            .addTablePrefix()
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
