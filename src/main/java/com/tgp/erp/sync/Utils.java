package com.tgp.erp.sync;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 工具类
 *
 * @author reph
 *         两个配置信息对象
 *         加解密方法
 */
public class Utils {
    public static Properties config = new Properties();
    public static Properties sqlrule = new Properties();
    public static Properties tableType = new Properties();
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    static {
        try {
            config.load(new FileInputStream("config.properties"));
            sqlrule.load(new FileInputStream("SQLRULE.properties"));
            tableType.load(new FileInputStream("tabletype.properties"));
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

    }

    public static Properties getConfig() {
        return config;
    }

    public static Properties getSqlrule() {
        return sqlrule;
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    /**
     * 文件批量加密方法
     * 在根目录下的password.txt中写入需要加密的信息.
     * 运行之后会产生新的encpassword.txt文件
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File srcfile = new File("password.txt");
        if (!srcfile.exists()) {
            return;
        }
        FileReader in = new FileReader(srcfile);
        BufferedReader reader = new BufferedReader(in);
        String pwd = null;
        Map<String, String> map = new HashMap<String, String>();
        while ((pwd = reader.readLine()) != null) {
            String encpwd = encryptBASE64(pwd.getBytes());
            map.put(pwd, encpwd);
        }
        File file = new File("encpassword.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter out = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(out);
        Set<String> keys = map.keySet();
        String info = null;
        for (String key : keys) {
            info = key + ":" + map.get(key) + "\n";
            writer.write(info);
            writer.flush();
        }
        writer.close();
        out.close();
        reader.close();
        in.close();

    }
}
