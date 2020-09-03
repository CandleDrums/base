package com.cds.base.generator.mybatis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cds.base.generator.mybatis.config.DBConnectionConfig;
import com.cds.base.generator.mybatis.config.DbType;

/**
 * XML based config file help class
 * <p>
 * Created by Owen on 6/16/16.
 */
public class ConfigHelper {

    private static final Logger _LOG = LoggerFactory.getLogger(ConfigHelper.class);
    private static final String BASE_DIR = "config";
    private static final String CONFIG_FILE = "/sqlite3.db";

    private static volatile boolean portForwaring = false;

    public static void createEmptyFiles() throws Exception {
        File file = new File(BASE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        File uiConfigFile = new File(BASE_DIR + CONFIG_FILE);
        if (!uiConfigFile.exists()) {
            createEmptyXMLFile(uiConfigFile);
        }
    }

    static void createEmptyXMLFile(File uiConfigFile) throws IOException {
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("sqlite3.db");
            fos = new FileOutputStream(uiConfigFile);
            byte[] buffer = new byte[1024];
            int byteread = 0;
            while ((byteread = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteread);
            }
        } finally {
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        }

    }

    public static List<String> getAllJDBCDriverJarPaths() {
        List<String> jarFilePathList = new ArrayList<>();
        URL url = Thread.currentThread().getContextClassLoader().getResource("logback.xml");
        try {
            File file;
            if (url.getPath().contains(".jar")) {
                file = new File("lib/");
            } else {
                file = new File("src/main/resources/lib");
            }
            _LOG.info("jar lib path: {}", file.getCanonicalPath());
            File[] jarFiles = file.listFiles();
            if (jarFiles != null && jarFiles.length > 0) {
                for (File jarFile : jarFiles) {
                    _LOG.info("jar file: {}", jarFile.getAbsolutePath());
                    if (jarFile.isFile() && jarFile.getAbsolutePath().endsWith(".jar")) {
                        jarFilePathList.add(jarFile.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("找不到驱动文件，请联系开发者");
        }
        return jarFilePathList;
    }

    public static String findConnectorLibPath(String dbType) {
        DbType type = DbType.valueOf(dbType);
        URL resource = Thread.currentThread().getContextClassLoader().getResource("logback.xml");
        _LOG.info("jar resource: {}", resource);
        if (resource != null) {
            try {
                File file = new File(resource.toURI().getRawPath() + "/../lib/" + type.getConnectorJarFile());
                return URLDecoder.decode(file.getCanonicalPath(), Charset.forName("UTF-8").displayName());
            } catch (Exception e) {
                throw new RuntimeException("找不到驱动文件，请联系开发者");
            }
        } else {
            throw new RuntimeException("lib can't find");
        }
    }

    public static String getConnectionUrlWithSchema(DBConnectionConfig dbConfig) throws ClassNotFoundException {
        DbType dbType = DbType.valueOf(dbConfig.getDbType());
        String connectionUrl =
            String.format(dbType.getConnectionUrlPattern(), portForwaring ? "127.0.0.1" : dbConfig.getHost(),
                portForwaring ? dbConfig.getLport() : dbConfig.getPort(), dbConfig.getSchema(), dbConfig.getEncoding());
        return connectionUrl;
    }
}
