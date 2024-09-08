//package com.parabank.parasoft.report;
//
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//import com.parabank.parasoft.testcases.BaseTest;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//public class TestListener implements ITestListener {
//
//    private static String getTestMethodName(ITestResult iTestResult) {
//        return iTestResult.getMethod().getConstructorOrMethod().getName();
//    }
//
//    // Before starting all tests, below method runs.
//    @Override
//    public void onStart(ITestContext iTestContext) {
//        System.out.println("I am in onStart method " + iTestContext.getName());
//        // iTestContext.setAttribute("WebDriver", this.getWebDriver());
//    }
//
//    // After ending all tests, below method runs.
//    @Override
//    public void onFinish(ITestContext iTestContext) {
//        System.out.println("I am in onFinish method " + iTestContext.getName());
//        ReportManager.getInstance().flush();
//    }
//
//    @Override
//    public void onTestStart(ITestResult iTestResult) {
//        System.out.println(iTestResult.getTestName());
//        String description = iTestResult.getMethod().getDescription();
//        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
//        if (iTestResult.getTestName() != null) {
//            ReportTestManager.startTest(iTestResult.getTestName(),
//                    iTestResult.getInstance().getClass().getCanonicalName());
//        } else if (description != null)
//            ReportTestManager.startTest(iTestResult.getMethod().getMethodName() + " " + description + "",
//                    iTestResult.getInstance().getClass().getCanonicalName());
//        else {
//            ReportTestManager.startTest(iTestResult.getMethod().getMethodName(),
//                    iTestResult.getInstance().getClass().getCanonicalName());
//        }
//        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
//        ReportTestManager.getTest().log(Status.INFO, "onTestStart method " + getTestMethodName(iTestResult) + " start");
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult iTestResult) {
//        ReportTestManager.getTest().log(Status.PASS, "Test passed");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult iTestResult) {
//        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
//
//        Object testClass = iTestResult.getInstance();
//        WebDriver webDriver = ((BaseTest) testClass).getWebDriver();
//
//        // Take base64Screenshot screenshot.
//        String base64Screenshot = "data:image/png;base64,"
//                + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
//
//        ReportTestManager.getTest().log(Status.FAIL, iTestResult.getThrowable());
//        ReportTestManager.getTest().fail("details",
//                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult iTestResult) {
//        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
//
//        // Extentreports log operation for skipped tests.
//        ReportTestManager.getTest().log(Status.SKIP, "Test Skipped");
//    }
//
//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
//        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
//    }
//}

package com.parabank.parasoft.report;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.parabank.parasoft.testcases.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("Starting test suite: " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Finishing test suite: " + iTestContext.getName());
        ReportManager.getInstance().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Starting test: " + getTestMethodName(iTestResult));
        String testName = iTestResult.getTestName() != null ? iTestResult.getTestName() : getTestMethodName(iTestResult);
        String description = iTestResult.getMethod().getDescription();
        String fullName = (description != null) ? testName + " - " + description : testName;

        ReportTestManager.startTest(fullName, iTestResult.getInstance().getClass().getCanonicalName());
        ReportTestManager.getTest().log(Status.INFO, "Test started: " + fullName);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test succeeded: " + getTestMethodName(iTestResult));
        ReportTestManager.getTest().log(Status.PASS, "Test passed: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Test failed: " + getTestMethodName(iTestResult));
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).getWebDriver();

        // Capture screenshot as Base64
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

        // Log the failure with the exception and screenshot
        ReportTestManager.getTest().log(Status.FAIL, "Test failed: " + getTestMethodName(iTestResult));
        ReportTestManager.getTest().log(Status.FAIL, iTestResult.getThrowable(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test skipped: " + getTestMethodName(iTestResult));
        ReportTestManager.getTest().log(Status.SKIP, "Test skipped: " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but within success percentage: " + getTestMethodName(iTestResult));
        ReportTestManager.getTest().log(Status.WARNING, "Test failed but within success percentage: " + getTestMethodName(iTestResult));
    }
}
