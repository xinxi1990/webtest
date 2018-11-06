package com.space.selenium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.space.requests.Requests;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;
import java.util.HashMap;

public class GetPer {

    private WebDriver driver;

    /***
     * 测试网页性能
     */
    public void run(String web,String report,String CHROMEPATH) throws IOException {
        ChromeOptions chromeOptions  = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        System.setProperty("webdriver.chrome.driver",CHROMEPATH) ;
        driver = new ChromeDriver(chromeOptions);
        driver.get(web);
        Long DnsTime = getDnsTime(driver,web);
        Long WhitePageTime = getWhitePageTime(driver,web);
        Long DomTime = getDomTime(driver,web);
        Long JSTime = getJSTime(driver,web);
        Long FirstPageTime = getFirstPageTime(driver,web);
        String ResourcesStr =getResources(driver,web);
        driver.close();
        HashMap timeMap = new HashMap();
        timeMap.put("web",web);
        timeMap.put("report",report);
        timeMap.put("DnsTime",DnsTime);
        timeMap.put("WhitePageTime",WhitePageTime);
        timeMap.put("DomTime",DomTime);
        timeMap.put("JSTime",JSTime);
        timeMap.put("FirstPageTime",FirstPageTime);
        new Requests().request(timeMap);
    }

    /**
     * 格式化json
     * @param uglyJSONString
     * @return
     */
    public static String jsonFormatter(String uglyJSONString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    /**
     * 获取DNS查询时间
     */
    public Long getDnsTime(WebDriver driver,String web){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        String domainLookupStart= String.valueOf(jse.executeScript("var domainLookupStart = window.performance.timing.domainLookupStart;return domainLookupStart"));
        String domainLookupEnd= String.valueOf(jse.executeScript("var domainLookupEnd = window.performance.timing.domainLookupEnd;return domainLookupEnd"));
        Long DnsTime = Long.valueOf(domainLookupEnd) - Long.valueOf(domainLookupStart);
        System.out.println(web + "的DNS查询时间是:" + String.valueOf(DnsTime) + "ms");
        return DnsTime;
    }



    /**
     * 获取白屏幕时间
     */
    public Long getWhitePageTime(WebDriver driver,String web){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        String navigationStart= String.valueOf(jse.executeScript("var navigationStart = window.performance.timing.navigationStart;return navigationStart"));
        String responseStart= String.valueOf(jse.executeScript("var responseStart = window.performance.timing.responseStart;return responseStart"));
        Long whitePageTime = Long.valueOf(responseStart) - Long.valueOf(navigationStart);
        System.out.println(web + "的白屏时间是:" + String.valueOf(whitePageTime) + "ms");
        return whitePageTime;
    }


    /**
     * 获取首屏时间
     */
    public Long getFirstPageTime(WebDriver driver,String web){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        String navigationStart= String.valueOf(jse.executeScript("var navigationStart = window.performance.timing.navigationStart;return navigationStart"));
        String loadEventEnd= String.valueOf(jse.executeScript("var loadEventEnd = window.performance.timing.loadEventEnd;return loadEventEnd"));
        Long firstPageTime = Long.valueOf(loadEventEnd) - Long.valueOf(navigationStart);
        System.out.println(web + "的首屏时间是:" + String.valueOf(firstPageTime) + "ms");
        return firstPageTime;
    }

    /**
     * 获取DOM渲染时间
     */
    public Long getDomTime(WebDriver driver,String web){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        String domLoading= String.valueOf(jse.executeScript("var domLoading = window.performance.timing.domLoading;return domLoading"));
        String domComplete= String.valueOf(jse.executeScript("var domComplete = window.performance.timing.domComplete;return domComplete"));
        Long DomTime = Long.valueOf(domComplete) - Long.valueOf(domLoading);
        System.out.println(web + "的DOM渲染时间是:" + String.valueOf(DomTime) + "ms");
        return DomTime;
    }

    /**
     * 获取js加载时间
     */
    public Long getJSTime(WebDriver driver, String web){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        String loadEventStart= String.valueOf(jse.executeScript("var loadEventStart = window.performance.timing.loadEventStart;return loadEventStart"));
        String loadEventEnd= String.valueOf(jse.executeScript("var loadEventEnd = window.performance.timing.loadEventEnd;return loadEventEnd"));
        Long JSTime = Long.valueOf(loadEventEnd) - Long.valueOf(loadEventStart);
        System.out.println(web + "的js加载时间是:" + String.valueOf(JSTime) + "ms");
        return JSTime;

    }



    /**
     * 获取所有资源
     */
    public String getResources(WebDriver driver,String web){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        String Resources= String.valueOf(jse.executeScript("var getEntries = JSON.stringify(window.performance.getEntries());return getEntries"));
        String ResourcesStr = jsonFormatter(Resources);
        //System.out.println(ResourcesStr);
        //JSONArray jsonArray = JSONArray.fromObject(Resources);
        //System.out.println(jsonArray);
        //List obj = JsonPath.read(jsonArray.toString(), "$.[*].name");
        //System.out.println(obj);
        return ResourcesStr;
    }







}
