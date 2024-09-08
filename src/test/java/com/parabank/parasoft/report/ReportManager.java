//package com.parabank.parasoft.report;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//
//public class ReportManager {
//    private static String path;
//    private static ExtentReports extent;
//
//    public static ExtentReports getInstance() {
//        if (extent == null)
//            createInstance();
//
//        return extent;
//    }
//
//
//    private static ExtentReports createInstance() {
//        if (extent == null) {
//            String workingDir = System.getProperty("user.dir");
//            String reportName = "Report.html";
//            path = "/build/extendReport/" + reportName;
//            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(workingDir + path);
//            htmlReporter.config().setTheme(Theme.STANDARD);
//            htmlReporter.config().setDocumentTitle("Report");
//            htmlReporter.config().setEncoding("utf-8");
//            htmlReporter.config().setReportName("Automated Tests - Report");
//            extent = new ExtentReports();
//            extent.attachReporter(htmlReporter);
//        }
//        return extent;
//    }
//
//}

package com.parabank.parasoft.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static ExtentReports extent;

    // Returns the single instance of ExtentReports
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    // Creates and configures the ExtentReports instance
    private static ExtentReports createInstance() {
        if (extent == null) {
            String workingDir = System.getProperty("user.dir");
            String reportName = generateReportName();
            String reportPath = workingDir + "/build/extentReports/" + reportName;

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Automated Tests - Detailed Report");
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setTimelineEnabled(true); // Enables the timeline view

            // Adding system information
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("User", "Rafiul");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    // Generates a timestamped report name for uniqueness
    private static String generateReportName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "ExtentReport_" + timeStamp + ".html";
    }
}
