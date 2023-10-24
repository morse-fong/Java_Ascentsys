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
 * ������������ݷ��������������
 *
 * @author ascent
 * @version 1.0
 */
// protocolPort
public class UserDataClient implements ProtocolPort {

    /**
     * socket����
     */
    // ����socket
    protected Socket hostSocket;

    /**
     * �����������
     */
    protected ObjectOutputStream outputToServer;

    /**
     * ������������
     */
    protected ObjectInputStream inputFromServer;

    /**
     * Ĭ�Ϲ��췽��
     */
    public UserDataClient() throws IOException {
        // Ĭ�Ϲ��죬����Ĭ��������"localhost" �� �˿ں� 5150
        this(ProtocolPort.DEFAULT_HOST, ProtocolPort.DEFAULT_PORT);
    }

    /**
     * �����������Ͷ˿ںŵĹ��췽��
     */
    public UserDataClient(String hostName, int port) throws IOException {
        // ��ӡ��־
        log("�������ݷ�����..." + hostName + ":" + port);

        // ��������
        try {
            // ����socket������Ĭ�������ط�����
            hostSocket = new Socket(hostName, port);
            // ��socket��ȡ���룬���������Ӷ���Ӧ�����¸������
            outputToServer = new ObjectOutputStream(hostSocket.getOutputStream());
            inputFromServer = new ObjectInputStream(hostSocket.getInputStream());
            // ��Ӧ��ӡ��Ϣ
            log("���ӳɹ�.");
        }
        // ���򲶻��쳣������ӡ
        catch (Exception e) {
            log("����ʧ��.");
        }
    }

    /**
     * ������𼯺�
     */
    @SuppressWarnings("unchecked")
    // ��ȡ��Ʒ��Ϣ
    public ArrayList<String> getCategories() throws IOException {
        // ��ʼΪ��
        ArrayList<String> categoryList = null;
        try {
            // ��������
            log("��������: OP_GET_PRODUCT_CATEGORIES");
            // �����������һ����ȡ���еĲ�Ʒ���ź�
            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            outputToServer.flush();            // ˢ��
            // ��Ӧ��������
            log("��������...");
            // �б��ȡ��������ȡ��Ʒ��Ϣ���ݶ���
            categoryList = (ArrayList<String>) inputFromServer.readObject();
            // �յ����ٲ�Ʒ�������
            log("�յ� " + categoryList.size() + " ���.");
        }
        // ��ӡ��δ�ҵ�����Ϣ
        catch (ClassNotFoundException exc) {
            log("=====>>>  �쳣: " + exc);
            throw new IOException("�Ҳ��������");
        }

        // ������𼯺�
        return categoryList;
    }

    public ArrayList<User> getUsers(String category) throws IOException {
        ArrayList<User> productList = null;
        try {
            log("��������: OP_GET_USERS  ��� = " + category);
            // ����һ�� ��ȡ��ǰ�������ƻ�÷����µ�������Ʒ�����ź�
            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            // �ڴ����Ӧ���������
            outputToServer.writeObject(category);
            outputToServer.flush();
            log("��������...");
            // ���ղ�Ʒ����
            productList = (ArrayList<User>) inputFromServer.readObject();
            log("�յ� " + productList.size() + " ��Ʒ.");
        }
        // �������޷��ҵ����쳣
        catch (ClassNotFoundException exc) {
            log("=====>>>  �쳣: " + exc);
            throw new IOException("�Ҳ��������");
        }

        return productList;
    }

    /**
     * �����û�
     *
     * @return userTable
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, User> getUsers() {

        HashMap<String, User> userTable = null;

        try {
            log("��������: OP_GET_USERS  ");

            // �����Ԥ��
            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            // �������˽��յ�
            outputToServer.flush();

            log("��������...");
            // ��������ȡ����
            userTable = (HashMap<String, User>) inputFromServer.readObject();
        }

        // ������δ�ҵ����쳣1
        // ����IO�쳣
        // �����쳣
        catch (Exception e) {
            e.printStackTrace();
        }
        // �����û�
        return userTable;
    }

    /**
     * �޸Ľӿڣ���ǰ�˽��գ��޸�ǰ����û�����
     *
     * @param pre �޸�ǰ�Ķ���
     * @param now �޸ĺ���û�
     * @return �����Ƿ��޸ĳɹ�
     */
    public boolean changeUser(User pre, User now) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(pre.getUsername())) {
            try {
                log("��������: OP_CHANGE_USERS  ");
                // �����������һ��ע���־Int
                outputToServer.writeInt(ProtocolPort.OP_CHANGE_USERS);
                // �����������һ���û������
                outputToServer.writeObject(pre);
                outputToServer.writeObject(now);
                // ˢ�£����������Խ���
                outputToServer.flush();
                log("��������...");
                return true;
            }
            // ����IO�쳣����ӡ
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * �رյ�ǰSocKet
     */
    public void closeSocKet() {
        try {
            this.hostSocket.close();
        }
        // ֻ����IO�쳣
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��־����.
     *
     * @param msg ��ӡ����־��Ϣ
     */
    protected void log(Object msg) {
        System.out.println("UserDataClient��: " + msg);
    }

    /**
     * ע���û�
     *
     * @param username �û���
     * @param password ����
     * @return boolean true:ע��ɹ���false:ע��ʧ��
     */
    public boolean addUser(String username, String password) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(username)) {
            return false;
        } else {
            try {
                log("��������: OP_ADD_USERS  ");
                // �����������һ��ע���־Int
                outputToServer.writeInt(ProtocolPort.OP_ADD_USERS);
                // �����������һ���û������
                outputToServer.writeObject(new User(username, password, 0));
                // ˢ�£����������Խ���
                outputToServer.flush();
                log("��������...");
                return true;
            }
            // ����IO�쳣����ӡ
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * ɾ���û�
     *
     * @param user �û�����
     * @return boolean true:ɾ���ɹ���false:ɾ��ʧ��
     */
    public boolean delUser(User user) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(user.getUsername())) {
            try {
                log("��������: OP_DEL_USERS  ");
                // �����������һ��ע���־Int
                outputToServer.writeInt(ProtocolPort.OP_DEL_USERS);
                // �����������һ���û������
                outputToServer.writeObject(user);
                // ˢ�£����������Խ���
                outputToServer.flush();
                log("��������...");
                return true;
            }
            // ����IO�쳣����ӡ
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * ��ȡ�����û����ֱ�Ϊ��ͨ�û���ע���û�
     * ����һ����ά�������飬��һ���洢��ͨ�û����ڶ����洢������
     *
     * @return �����б�
     */
    public ArrayList<ArrayList<User>> getTwoKindUsers() {
        // �½���Ҫ���صĴ�
        ArrayList<ArrayList<User>> twoUsers = new ArrayList<ArrayList<User>>();
        ArrayList<User> ordinaryUsers = new ArrayList<User>();                // һ���û�
        ArrayList<User> controller = new ArrayList<User>();                    // ������
        HashMap<String, User> userHashMap = getUsers();                        // ���ú�����ȡ�û���

        // ������ȡ�����û�����
        for (User user : userHashMap.values()) {
            // ����Ȩ���ж�����ͨ���ǹ�����
            if (user.getAuthority() == 0) {
                ordinaryUsers.add(user);
            } else {
                controller.add(user);
            }
        }
        // ��Ӧ�Ӿ�����
        twoUsers.add(ordinaryUsers);
        twoUsers.add(controller);

        return twoUsers;
    }

}
