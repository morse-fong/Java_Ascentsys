package com.ascent.util;

import com.ascent.bean.Product;
import com.ascent.bean.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 这个类连接数据服务器来获得数据
 *
 * @author ascent
 * @version 1.0
 */
// protocolPort
public class UserDataClient implements ProtocolPort {

    /**
     * socket引用
     */
    // 主机socket
    protected Socket hostSocket;

    /**
     * 输出流的引用
     */
    protected ObjectOutputStream outputToServer;

    /**
     * 输入流的引用
     */
    protected ObjectInputStream inputFromServer;

    /**
     * 默认构造方法
     */
    public UserDataClient() throws IOException {
        // 默认构造，传入默认主机名"localhost" 和 端口号 5150
        this(ProtocolPort.DEFAULT_HOST, ProtocolPort.DEFAULT_PORT);
    }

    /**
     * 接受主机名和端口号的构造方法
     */
    public UserDataClient(String hostName, int port) throws IOException {
        // 打印日志
        log("连接数据服务器..." + hostName + ":" + port);

        // 尝试连接
        try {
            // 创建socket，这里默认连本地服务器
            hostSocket = new Socket(hostName, port);
            // 从socket获取输入，输出流对象从而对应创建新概念对象
            outputToServer = new ObjectOutputStream(hostSocket.getOutputStream());
            inputFromServer = new ObjectInputStream(hostSocket.getInputStream());
            // 对应打印信息
            log("连接成功.");
        }
        // 否则捕获异常，并打印
        catch (Exception e) {
            log("连接失败.");
        }
    }

    /**
     * 返回类别集合
     */
    @SuppressWarnings("unchecked")
    // 获取产品信息
    public ArrayList<String> getCategories() throws IOException {
        // 初始为空
        ArrayList<String> categoryList = null;
        try {
            // 发送请求
            log("发送请求: OP_GET_PRODUCT_CATEGORIES");
            // 向服务器发送一个获取所有的产品的信号
            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            outputToServer.flush();            // 刷新
            // 对应接收请求
            log("接收数据...");
            // 列表获取输入流获取产品信息数据对象
            categoryList = (ArrayList<String>) inputFromServer.readObject();
            // 收到多少产品还有类别
            log("收到 " + categoryList.size() + " 类别.");
        }
        // 打印类未找到的信息
        catch (ClassNotFoundException exc) {
            log("=====>>>  异常: " + exc);
            throw new IOException("找不到相关类");
        }

        // 返回类别集合
        return categoryList;
    }

    public ArrayList<User> getUsers(String category) throws IOException {
        ArrayList<User> productList = null;
        try {
            log("发送请求: OP_GET_USERS  类别 = " + category);
            // 发送一个 获取当前分类名称获得分类下的所有商品对象信号
            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            // 在传入对应的类别名称
            outputToServer.writeObject(category);
            outputToServer.flush();
            log("接收数据...");
            // 接收产品数据
            productList = (ArrayList<User>) inputFromServer.readObject();
            log("收到 " + productList.size() + " 产品.");
        }
        // 捕获类无法找到的异常
        catch (ClassNotFoundException exc) {
            log("=====>>>  异常: " + exc);
            throw new IOException("找不到相关类");
        }

        return productList;
    }

    /**
     * 返回用户
     *
     * @return userTable
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, User> getUsers() {

        HashMap<String, User> userTable = null;

        try {
            log("发送请求: OP_GET_USERS  ");

            // 输出流预备
            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            // 服务器端接收到
            outputToServer.flush();

            log("接收数据...");
            // 输入流获取对象
            userTable = (HashMap<String, User>) inputFromServer.readObject();
        }

        // 捕获类未找到的异常1
        // 捕获IO异常
        // 捕获异常
        catch (Exception e) {
            e.printStackTrace();
        }
        // 返回用户
        return userTable;
    }

    /**
     * 修改接口，从前端接收，修改前后的用户对象
     *
     * @param pre 修改前的对象
     * @param now 修改后的用户
     * @return 返回是否修改成功
     */
    public boolean changeUser(User pre, User now) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(pre.getUsername())) {
            try {
                log("发送请求: OP_CHANGE_USERS  ");
                // 给服务器输出一个注册标志Int
                outputToServer.writeInt(ProtocolPort.OP_CHANGE_USERS);
                // 给服务器输出一个用户类对象
                outputToServer.writeObject(pre);
                outputToServer.writeObject(now);
                // 刷新，服务器可以接收
                outputToServer.flush();
                log("接收数据...");
                return true;
            }
            // 捕获IO异常并打印
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 关闭当前SocKet
     */
    public void closeSocKet() {
        try {
            this.hostSocket.close();
        }
        // 只捕获IO异常
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 日志方法.
     *
     * @param msg 打印的日志信息
     */
    protected void log(Object msg) {
        System.out.println("UserDataClient类: " + msg);
    }

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean true:注册成功，false:注册失败
     */
    public boolean addUser(String username, String password) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(username)) {
            return false;
        } else {
            try {
                log("发送请求: OP_ADD_USERS  ");
                // 给服务器输出一个注册标志Int
                outputToServer.writeInt(ProtocolPort.OP_ADD_USERS);
                // 给服务器输出一个用户类对象
                outputToServer.writeObject(new User(username, password, 0));
                // 刷新，服务器可以接收
                outputToServer.flush();
                log("接收数据...");
                return true;
            }
            // 捕获IO异常并打印
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 删除用户
     *
     * @param user 用户对象
     * @return boolean true:删除成功，false:删除失败
     */
    public boolean delUser(User user) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(user.getUsername())) {
            try {
                log("发送请求: OP_DEL_USERS  ");
                // 给服务器输出一个注册标志Int
                outputToServer.writeInt(ProtocolPort.OP_DEL_USERS);
                // 给服务器输出一个用户类对象
                outputToServer.writeObject(user);
                // 刷新，服务器可以接收
                outputToServer.flush();
                log("接收数据...");
                return true;
            }
            // 捕获IO异常并打印
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 获取两种用户，分别为普通用户和注册用户
     * 返回一个二维对象数组，第一个存储普通用户，第二个存储管理者
     *
     * @return 返回列表
     */
    public ArrayList<ArrayList<User>> getTwoKindUsers() {
        // 新建需要返回的答案
        ArrayList<ArrayList<User>> twoUsers = new ArrayList<ArrayList<User>>();
        ArrayList<User> ordinaryUsers = new ArrayList<User>();                // 一般用户
        ArrayList<User> controller = new ArrayList<User>();                    // 管理者
        HashMap<String, User> userHashMap = getUsers();                        // 调用函数获取用户集

        // 遍历获取到的用户对象
        for (User user : userHashMap.values()) {
            // 根据权限判断是普通还是管理者
            if (user.getAuthority() == 0) {
                ordinaryUsers.add(user);
            } else {
                controller.add(user);
            }
        }
        // 对应加就行了
        twoUsers.add(ordinaryUsers);
        twoUsers.add(controller);

        return twoUsers;
    }

}
