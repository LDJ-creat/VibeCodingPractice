package com.chuanzhi.health.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.model.User;

public class MainFrame extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

    private User currentUser;

    public MainFrame(User user) {
        this.currentUser = user;
        logger.info("用户{}登录系统，角色: {}", currentUser.getUsername(), currentUser.getLev());
        
        setTitle("传智健康管理系统 - " + user.getUname());
        setSize(1000, 700);
        setLocationRelativeTo(null); // 居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        var result ="admin".equals(currentUser.getLev().trim());
        logger.debug("用户角色验证结果: {}", result);
        
        // 根据用户角色添加不同的功能模块
        if ("admin".equals(currentUser.getLev())) {
            // 管理员功能
            logger.debug("加载管理员功能模块");
            tabbedPane.addTab("检查项管理", new CheckitemManagementView());
            tabbedPane.addTab("检查组管理", new CheckgroupManagementView());
            // 可以添加其他管理员功能
        } else if ("user".equals(currentUser.getLev())) {
            // 普通用户功能
            logger.debug("加载普通用户功能模块");
            tabbedPane.addTab("用户主页", new UserMainView(currentUser));
            // 可以添加其他用户功能
        } else {
            // 默认或未知角色
            logger.warn("未知用户角色: {}", currentUser.getLev());
            JOptionPane.showMessageDialog(this, "未知用户角色，请联系管理员。", "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        add(tabbedPane, BorderLayout.CENTER);

        // 窗口关闭监听器，确保所有资源释放
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 可以在这里添加退出确认逻辑或资源清理
                logger.info("用户{}退出系统", currentUser.getUsername());
                // System.exit(0); // 如果需要彻底退出应用程序
            }
        });
    }


    public static void main(String[] args) {
        // 仅用于测试MainFrame的显示，实际应由LoginView启动
        SwingUtilities.invokeLater(() -> {
            User testUser = new User();
            testUser.setUname("测试管理员");
            testUser.setLev("admin"); // 或 "user"
            new MainFrame(testUser).setVisible(true);
        });
    }
}