package com.stalary.book.service;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.stalary.book.data.entity.Book;
import com.stalary.book.utils.PasswordUtil;
import com.stalary.book.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * QiniuService
 *
 * @author hawk
 * @since 2018/02/09
 */
@Service
@Slf4j
public class QiniuService {

    @Autowired
    private BookService bookService;

    private static ExecutorService executor = Executors.newCachedThreadPool();


    private static String ACCESS_KEY = "zfg7aGCs98DbKp_zKHzOAwzd6BoPhLjPkO5ohzEG";
    private static String SECRET_KEY = "l-fs00VcPP2nZRBKZmJj7LeSShi2wKxSMN5RL10w";
    private static String BUCKET_NAME = "stalary";
    private static String QINIU_IMAGE_DOMAIN = "http://p037i675p.bkt.clouddn.com/";

    // 密钥配置
    private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 创建配置对象
    Configuration cfg = new Configuration(Zone.huadong());
    // 创建上传对象
    private UploadManager uploadManager = new UploadManager(cfg);
    // 创建空间管理对象
    private BucketManager bucketManager = new BucketManager(auth, cfg);

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
    public String uploadBook(MultipartFile book, Book newBook) {
        final String bookName = SystemUtil.BOOK + SystemUtil.SPLIT +  PasswordUtil.get5UUID() + ".pdf";
        executor.execute(() -> {
            try {
                Response response = uploadManager.put(book.getBytes(), bookName, getUpToken());
                if (response.isOK() && response.isJson()) {
                    newBook.setPdfUrl(QINIU_IMAGE_DOMAIN + bookName);
                    bookService.saveBook(newBook);
                    log.info("上传图书成功：" + bookName);
                } else {
                    log.error("上传图书失败：" + response.bodyString());
                }
            } catch (QiniuException e) {
                log.error("七牛异常：" + e.getMessage());
            } catch (IOException e) {
                log.error("IO异常：" + e.getMessage());
            }
        });
        return QINIU_IMAGE_DOMAIN + bookName;
    }

    /**
     * 上传图书封面
     *
     * @param book
     * @return
     */
    public String uploadCover(MultipartFile book) {
        final String coverName = SystemUtil.COVER + SystemUtil.SPLIT + PasswordUtil.get5UUID() + ".jpg";
        executor.execute(() -> {
            Document document = new Document();
            // 缩放比例，1表示不缩放，0.5表示缩小到50%
            float zoom = 0.5f;
            // 旋转角度，0表示不旋转
            float rotation = 0f;
            try {
                document.setInputStream(book.getInputStream(), null);
                BufferedImage cover = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, zoom);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(cover, "jpg", out);
                Response response = uploadManager.put(out.toByteArray(), coverName, getUpToken());
                if (response.isOK() && response.isJson()) {
                    log.info("上传图书封面成功：" + coverName);
                } else {
                    log.error("上传图书封面失败：" + response.bodyString());
                }
            } catch (Exception e) {
                log.error("上传图书封面失败：" + e.getMessage());
            }
        });
        return QINIU_IMAGE_DOMAIN + coverName;
    }

    /**
     * 删除图书
     *
     * @param book
     */
    public void deleteBook(Book book) {
        String pdfKey = StringUtils.substringAfter(book.getPdfUrl(), QINIU_IMAGE_DOMAIN);
        String coverKey = StringUtils.substringAfter(book.getCoverUrl(), QINIU_IMAGE_DOMAIN);
        executor.execute(() -> {
            try {
                bucketManager.delete(BUCKET_NAME, pdfKey);
                bucketManager.delete(BUCKET_NAME, coverKey);
                log.info("删除图书成功");
            } catch (QiniuException e) {
                log.error("删除图书失败：" + e.getMessage());
            }
        });
    }
}
