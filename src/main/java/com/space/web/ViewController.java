package com.space.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;



/**
 *
 * @author xinxi
 * @describe 视图函数
 *
 */
@Controller
public class ViewController {
    @RequestMapping(value = "/report",method = RequestMethod.GET)

    public String index(String URL,Long DnsTime,Long WhitePageTime,
                        Long DomTime,Long JSTime,Long FirstPageTime,
                        ModelMap map) {
        map.addAttribute("URL", URL);
        map.addAttribute("DnsTime", DnsTime);
        map.addAttribute("WhitePageTime", WhitePageTime);
        map.addAttribute("DomTime", DomTime);
        map.addAttribute("JSTime",JSTime);
        map.addAttribute("FirstPageTime", FirstPageTime);
        return "index";
    }


    /**
     * json转换
     * @param request
     * @return
     */
    public JSONObject getJSONParam(HttpServletRequest request){
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
            // 直接将json信息打印出来
            System.out.println(jsonParam.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }



}