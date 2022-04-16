package com.imooc.ecommerce;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 数据库表文档生成  使用 Screw-core 以及 Freemarker 生成数据库文档
 *
 * @author zzy
 * @date 2022/4/16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DBDocTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void buildDBDoc() {

        // 通过容器获取对应的数据源
        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);

        // 生成文档配置 注意是 Screw-core 下的
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir("D:\\code\\e-commerce-springcloud-alibaba\\datasource_screw-core")
                // 生成完后是否打开目录
                .openOutputDir(false)
                // 文件类型
                .fileType(EngineFileType.HTML)
                .produceType(EngineTemplateType.freemarker)
                .build();

        // 生成文档配置，包含自定义版本号，描述等等 注意是 Screw-core 下的
        // 名称： 数据库名_description_version.html
        Configuration config = Configuration.builder()
                .version("1.0.0")
                .description("e-commerce-springcloud")
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(getProduceConfig())
                .build();

        // 执行生成  注意是 Screw-core 下的
        new DocumentationExecute(config).execute();
    }

    /**
     * ProcessConfig 来源于 screw；
     * 配置想要生成的数据表和想要忽略的数据表
     *
     * @return
     */
    private ProcessConfig getProduceConfig() {

        // 想要忽略的数据集表
        List<String> ignoreTableName = Collections.singletonList("undo_log");

        // 忽略表前缀，忽略 a、b 开头的数据库表
        List<String> ignorePrefix = Arrays.asList("a", "b");

        // 忽略表后缀，忽略 a、b 开头的数据库表
        List<String> ignoreSuffix = Arrays.asList("_test", "_Test");

        return ProcessConfig.builder()
                // 根据指定名称指定表生成
                .designatedTableName(Collections.emptyList())
                // 根据表前缀去生成
                .designatedTablePrefix(Collections.emptyList())
                // 根据表后缀去生成
                .designatedTableSuffix(Collections.emptyList())
                // 忽略表
                .ignoreTableName(ignoreTableName)
                // 忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                // 忽略表后缀
                .ignoreTableSuffix(ignoreSuffix)
                .build();
    }
}
