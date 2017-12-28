package com.mazaiting.freemarkertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class MainActivity1 extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) this.findViewById(R.id.webView);

        try {
            prepareTemplate();
            genHTML();

            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    String templateDir = getFilesDir().getAbsolutePath();
                    String url = "file://" + templateDir + "main.html";
                    mWebView.loadUrl(url);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成网页
     * @throws IOException
     * @throws TemplateException
     */
    private void genHTML() throws IOException, TemplateException {
        String destPath = getFilesDir().getAbsolutePath();
        FileWriter out = null;
        // 数据源
        Map<String, String> root = new HashMap<>();
        // 传入字符串
        root.put("user", "mazaiting");
        Configuration cfg = new Configuration(new Version(2, 3, 25));
        // 设置编码字符
        cfg.setDefaultEncoding("UTF-8");
        // 设置报错提示
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 设置报错提示
        cfg.setLogTemplateExceptions(true);
        out = new FileWriter(new File(destPath + "main.html"));
        // 设置.ftl模板文件路径
        cfg.setDirectoryForTemplateLoading(new File(destPath));
        // 设置template加载的.ftl模板文件名称
        Template temp = cfg.getTemplate("main.ftl");
        // 将数据源和模板生成.html文件
        temp.process(root, out);
        out.flush();
        out.close();
    }

    /**
     * 准备模板
     * @throws IOException
     */
    private void prepareTemplate() throws IOException {
        // 获取app目录 data/data/package/file/
        String destPath = getFilesDir().getAbsolutePath();
        File dir = new File(destPath);

        // 判断文件夹是否存在
        if (!dir.exists()){
            dir.mkdir();
        }

        // 需要生成的.ftl模板文件名及路径
        String tempFile = destPath + "/" + "main.ftl";
        if (!(new File(tempFile).exists())){
            // 获取assets中.tpl模板文件
            InputStream is = getResources().getAssets().open("main.tpl");
            // 生成.ftl模板文件
            FileOutputStream fos = new FileOutputStream(tempFile);
            byte[] buffer = new byte[7168];
            int len;
            while ((len = is.read(buffer)) > 0){
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            is.close();
        }


    }
}
