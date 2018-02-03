package com.line.lintrule;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

/**
 * 把所定义的Issue进行导出,用于提供此jar中所有输出的lint规则,这个类是暴露给lint的一个注册类
 */
public class MyIssueRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        System.out.println("***************************************************");
        System.out.println("**************** lint is starting *****************");
        System.out.println("***************************************************");
        return Arrays.asList(
                CloseDetector.ISSUE,
                LogDetector.ISSUE,
                ToastDetector.ISSUE
        );
    }

}
