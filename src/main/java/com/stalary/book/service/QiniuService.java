package com.stalary.book.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.javatuples.Pair;
import lombok.extern.slf4j.Slf4j;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * QiniuService
 *
 * @author hawk
 * @since 2018/02/09
 */
@Service
@Slf4j
public class QiniuService {

    // 账号密钥，可在个人中心-密钥管理中查看
    private static String ACCESS_KEY = "TnIOszZVvneKT9xI9ySSiXpbpCsJeBGoFCUu6jTl";
    private static String SECRET_KEY = "1Mf5ksCyGwIzxTJoZ2zSUS65tS034t48G9nMQJV_";
    // 要上传的空间
    private static String BUCKET_NAME = "hawk97";
    // 七牛默认外链域名
    private static String QINIU_IMAGE_DOMAIN = "http://p44lruo4o.bkt.clouddn.com/";

    // 密钥配置
    private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 创建上传对象
    private UploadManager uploadManager = new UploadManager(new Configuration(Zone.huadong()));

    // 获得简单上传的凭证
    private String getUpToken() {
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 上传图书
     *
     * @param book
     * @return
     */
    public Pair<Boolean, String> uploadBook(MultipartFile book) {
        try {
            int dotPos = book.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return new Pair<>(false, "上传图书格式错误");
            }
            String bookExt = book.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!bookExt.equals("pdf")) {
                return new Pair<>(false, "上传图书格式错误");
            }
            String bookName = UUID.randomUUID().toString().replace("-", "") + "." + bookExt;
            Response response = uploadManager.put(book.getBytes(), bookName, getUpToken());
            if (response.isOK() && response.isJson()) {
                return new Pair<>(true, QINIU_IMAGE_DOMAIN + JSONObject.parseObject(response.bodyString()).get("key"));
            }
            log.error("上传图书失败：" + response.bodyString());
            return new Pair<>(false, "上传图书失败");
        } catch (QiniuException e) {
            log.error("七牛异常：" + e.getMessage());
            return new Pair<>(false, "上传图书失败");
        } catch (IOException e) {
            log.error("IO异常：" + e.getMessage());
            return new Pair<>(false, "上传图书失败");
        }
    }

    /**
     * 上传图书封面
     *
     * @param book
     * @return
     */
    public Pair<Boolean, String> uploadCover(MultipartFile book) {
        Document document = new Document();
        // 缩放比例，1表示不缩放，0.5表示缩小到50%
        float zoom = 0.3f;
        // 旋转角度，0表示不旋转
        float rotation = 0f;
        try {
            document.setInputStream(book.getInputStream(), null);
            BufferedImage cover = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, zoom);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(cover, "jpg", out);
            String coverName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            Response response = uploadManager.put(out.toByteArray(), coverName, getUpToken());
            if (response.isOK() && response.isJson()) {
                return new Pair<>(true, QINIU_IMAGE_DOMAIN + JSONObject.parseObject(response.bodyString()).get("key"));
            }
            log.error("上传图书封面失败：" + response.bodyString());
            return new Pair<>(false, "上传图书封面失败");
        } catch (Exception e) {
            log.error("上传图书封面失败：" + e.getMessage());
            return new Pair<>(false, "上传图书封面失败");
        }
    }
}
