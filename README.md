# NewLintTest

环境 ： As3.0环境

1.新建一个java-library  -> lintrule
作用：生成lint规则定义的jar文件

lintrule的build.gradle配置：

```
apply plugin: 'java-library'

dependencies {
    compileOnly 'com.android.tools.lint:lint-api:26.0.1'
    compileOnly 'com.android.tools.lint:lint-checks:26.0.1'
}

sourceCompatibility = "1.7"
targetCompatibility = "1.7"

jar {
    manifest {
        attributes("Lint-Registry-v2": "com.line.lintrule.MyIssueRegistry")
    }
}
```

#### 注意：

     dependencies中的lint依赖必须用compileOnly；

     attributes中必须用Lint-Registry-v2

2.新建一个android.library -> lintaar
lintaar 的build.gradle配置：
dependencies {
    lintChecks project(':lintrule')
}
作用：这告诉Gradle插件从“lintrule”项目中获取输出，并将其作为“lint.jar”有效内容打包到此库的AAR文件中。 完成后，依赖这个库的任何其他项目将自动使用lint检查。（来自googlesample）https://github.com/googlesamples/android-custom-lint-rules/tree/master/android-studio-3


3.使用
在app的build.gradle中添加lintaar依赖
compile project(':lintaar’)

并添加如下配置，在运行的时候会检查代码

```
android {
    ...

    lintOptions{
        textReport true
        textOutput 'stdout'
        abortOnError true
    }
}

// 配置assemble任务依赖lint，编译时就会执行Lint检查，如果报错则中断构建
android.applicationVariants.all { variant ->
    variant.outputs.each { output ->
        def lintTask = tasks["lint${variant.name.capitalize()}"]
        output.assemble.dependsOn(lintTask)
    }
}

```

4.测试了在module中使用lint
在模块的配置文件中需要添加以下配置,目的是为了能在每次运行或者rebuild的时候执行lint任务

```
afterEvaluate {
        tasks.matching {
            // 以process开头以ReleaseJavaRes或DebugJavaRes结尾的task
            it.name.contains('transform')
        }.each { task ->
            task.dependsOn(lint)  // 任务依赖：执行task之前需要执行dependsOn指定的任务
        }
    }
```


5.发现问题
 测试的时候发现在运行或者rebuild的时候没有打印出所有issue，打印了一个然后就停止了，把abortOnError
 设置为false也不行。不过在模块的build/reports中会列出所有的issue。