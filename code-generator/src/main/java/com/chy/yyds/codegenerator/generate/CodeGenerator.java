package com.chy.yyds.codegenerator.generate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public void generateCode(String rootPath, String outputPath, String author, boolean overrideFiles, String idStrategy, String dateStrategy, boolean enableSwagger2, String url, String dbDriver, String username, String password, String tablePrefix, String[] tables, String entityPackage, String mapperPackage, String servicePackage, String serviceImplPackage, String controllerPackage, String xmlOutputFolder) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(rootPath + "/" + outputPath);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setFileOverride(overrideFiles);
        gc.setIdType(IdType.valueOf(idStrategy));
        gc.setDateType(DateType.valueOf(dateStrategy));
        gc.setSwagger2(enableSwagger2);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(dbDriver);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(entityPackage)
                .setMapper(mapperPackage)
                .setService(servicePackage)
                .setServiceImpl(serviceImplPackage)
                .setController(controllerPackage);
        mpg.setPackageInfo(pc);

        // 自定义输出配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        String templatePath = "/templates/mapper.xml.ftl";

        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootPath + "/" + xmlOutputFolder + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(tablePrefix);
        strategy.setInclude(tables);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
