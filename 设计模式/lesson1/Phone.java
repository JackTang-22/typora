interface Phone {
    // 拨号
    void dial(String number);
    // 通话
    void chat(Object o);
    // 挂断
    void hangup();
}

interface ConnectManager {
     // 拨号
     void dial(String number);
     // 通话
     void chat(Object o);

}

interface DataTransfer {
    // 通话
    void chat(Object o);
}

class PhoneImpl implents ConnectManger, DataTransfer {
    // 拨号实现
    public void dial(String number) {
        // logic impl
    }

    //通话
    public void chat(Object o) {
        // logic impl
    }

    // 挂断
    public void hangup() {
        // logic impl
    }

}
