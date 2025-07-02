package com.chuanzhi.health.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 密码处理工具类，用于密码的哈希加密与验证
 */
public class PasswordUtil {

    /**
     * 将明文密码哈希化
     *
     * @param plainTextPassword 明文密码
     * @return 哈希后的密码
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * 验证明文密码与哈希密码是否匹配
     *
     * @param plainTextPassword 明文密码
     * @param hashedPassword    哈希后的密码
     * @return 如果匹配则返回 true，否则返回 false
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            return false;
        }
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}