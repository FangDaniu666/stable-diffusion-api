import com.daniu.base.BaseQRcode;
import com.daniu.utils.ImageBase64Utils;
import org.jetbrains.annotations.TestOnly;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*String base = ImageBase64Utils.image2Base("E:\\图片\\snow miku.jpg");
        System.out.println(base);*/
        String downloadPath = "D:\\SD-Webui-Aki\\SDworkspace\\base";
        BaseQRcode.getBaseQRcode("Daniu",downloadPath);
    }
}
