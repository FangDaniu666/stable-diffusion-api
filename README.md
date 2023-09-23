# stable-diffusion-api
基于stable diffusion,生成艺术二维码

### 环境搭建

在使用之前，需要进行一些环境配置。下面是配置步骤：

1. 安装[controlnet](https://github.com/Mikubill/sd-webui-controlnet)插件。

2. 配置stable diffusion地址：在`config.json`文件中的`sdurl`属性中配置stable diffusion的地址。将地址设置为你的stable diffusion服务的地址。

3. 下载ChromeDriver：根据你的Chrome浏览器的版本，下载相应版本的ChromeDriver。你可以在[ChromeDriver官网](https://sites.google.com/a/chromium.org/chromedriver/)上找到下载链接。

4. 配置ChromeDriver路径：在`config.json`文件中的`chromedriver`属性中配置ChromeDriver的路径。将路径设置为你下载的ChromeDriver的路径。

5. 在`config.json`文件中的`downloadPath`(必须是绝对路径)属性中配置下载路径。用于临时存放图片。

完成以上配置后，你就可以开始使用了。
### 快速开始

你可以使用`SdUtils`类中的`generateArtQRCode`方法来生成艺术二维码。这个方法需要传入以下参数：

- `prompt`：正面关键词
- `negative_prompt`：负面关键词
- `file`：sd的其它属性，可以根据需要自己设置
- `content`：生成的二维码的内容

下面是一个示例代码：

```java
import com.daniu.SdUtils;
import com.fasterxml.jackson.databind.JsonNode;

public class Example {
    public static void main(String[] args) {
        String prompt = "positive";
        String negative_prompt = "negative";
        String content = "text";

        File file = new File("src/main/resources/qrcode.json");
        JsonNode imagesNode = SdUtils.generateArtQRCode(prompt, negative_prompt, file, content);

        // 处理生成的艺术二维码
        // ...
    }
}
```

你可以根据需要对生成的艺术二维码进行进一步处理。

`SdUtils`类中同时封装了文生图,图生图的api,使用方法可参考[Example](https://github.com/FangDaniu666/stable-diffusion-api/blob/master/src/main/java/com/daniu/Example.java)类

### 效果图

![image text](https://github.com/FangDaniu666/stable-diffusion-api/blob/master/src/main/resources/imgs/img0.png)

![image text](https://github.com/FangDaniu666/stable-diffusion-api/blob/master/src/main/resources/imgs/img1.png)
