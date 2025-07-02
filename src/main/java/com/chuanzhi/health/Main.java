package com.chuanzhi.health;

import com.chuanzhi.health.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 使用 SwingUtilities.invokeLater 确保 GUI 在事件分发线程中创建和更新
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建并显示登录窗口
                new LoginView().setVisible(true);
            }
        });
    }
}