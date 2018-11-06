package com.space;

import com.space.selenium.GetPer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author xinxi
 * 主函数入口,非阻塞模式启动
 */
@SpringBootApplication
public class Application {

	public static String WEB;
	public static String REPORTPATH;
	public static String CHROMEPATH;
	public static boolean NEEDHELP = false;

	public static void main(String[] args) throws IOException {

		int optSetting = 0;
		for (; optSetting < args.length; optSetting++) {
			if ("-w".equals(args[optSetting])) {
				WEB = args[++optSetting];
			}else if ("-r".equals(args[optSetting])) {
				REPORTPATH = args[++optSetting];
			}else if ("-c".equals(args[optSetting])) {
				CHROMEPATH = args[++optSetting];
			} else if ("-h".equals(args[optSetting])) {
				NEEDHELP = true;
				System.out.println("-w:web页面地址");
				System.out.println("-c:chromedriver地址");
				System.out.println("-r:报告文件夹");
				break;
			}
		}

		killPid("8081");
		Thread thread = new Thread();
		thread.setDaemon(true);
		thread.start();
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
		System.out.println("启动了服务端");
		new GetPer().run(WEB,REPORTPATH,CHROMEPATH);
		thread.interrupt();
		System.out.println("服务线程退出!");
		System.exit(0);

	}







	/**
	 * 启动进程
	 */
	public static void killPid(String port) throws IOException{
		String serachCmd = String.format("lsof -i:%s", port);
		Process p = Runtime.getRuntime().exec(serachCmd);
		InputStream is = p.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line ;
		while ((line = reader.readLine()) != null) {
			if (line.contains(String.valueOf("TCP"))){
				String[] tt=line.split("\\s+");
				String killCmd = String.format("kill -9 %s", tt[1]);
				System.out.println((String.format("kill进程命令:%s", killCmd)));
				Runtime.getRuntime().exec(killCmd);
				break;
			}
		}
		is.close();
		reader.close();
		p.destroy();
	}



}
