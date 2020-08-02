# 单一职责原则(Single Responsibility Principle, SRP)

1. 争议：对职责的定义，什么事类的职责，以及怎么划分类的职责

2. 定义：有且仅有一个原因引起类的变更

3. There should never be more than one reason for a class to change

4. eg：电话通话假设包含拨号、通话、挂断三个过程

   - ```java
       1 public interface Phone {
       2     // 拨号
       3     void dial(String number);
       4     // 通话
       5     void chat(Object o);
       6     // 挂断
       7     void hangup();
       8 }
     ```

   - dail和hangup属于协议管理，chat数据数据传输，因此Phone这个接口不符合单一职责原则

   - ```java
      10 public interface ConnectManager {
      11      // 拨号
      12      void dial(String number);
      13      // 通话
      14      void chat(Object o);
      15
      16 }
      17
      18 public interface DataTransfer {
      19     // 通话
      20     void chat(Object o);
      21 }
     ```

5. 好处：

   - 类的复杂性降低，实现什么职责都有清晰明确的定义
   - 增强可读性
   - 增强可维护性
   - 降低变更引起的风险

6. 单一职责原则提出了一个编写程序的标准，用“职责”或“变化原因”来衡量接口或类设计得是否优良，但是“职责”和“变化原因”都是不可度量的，因项目而异，因环境而异



# 里式替换原则

