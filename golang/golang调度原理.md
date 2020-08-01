# golang 调度原理

- 并发(concurrency): 两个或两个以上的任务在同一时间段内运行
- 并行(parallelism):两个或两个以上任务在同一时刻运行

## CSP并发模型(communicating sequential processes)

- **Do not communicate by sharing memory; instead, share memory by communicating.** “不要以共享内存的方式来通信，相反，要通过通信来共享内存”
- golang csp并发模型通过goroutine和channel实现
- groutine是golang中并发执行的单位，轻量级线程
- channel是golang各个并发结构体(goroutine)之间的通信机制，类似Linux中的管道
- 生成goroutine的方式: go func()
- 通信机制channel 传递数据:channel <- data, 取数据: <-channel,传数据和取数据必定成对出现,不管是传还是取,必然阻塞,知道其他的groutine取到数据

## golang并发模型实现原理

- 无论语言层面的线程是何种并发模型，在操作系统的层面，必定是以线程的形态存在的
- 操作系统根据权限将体系架构划分为**用户空间**和**内核空间**，KSE操作系统内核态的线程
- 内核空间:操作CPU资源、I/O资源、内存资源等硬件资源
- 用户空间:上层应用程序的固定活动空间,用户空间无法直接访问资源，需要通过系统调用、库函数、shell脚本来调用内核空间提供的资源

1. 用户级线程模型
   - 用户线程:KSE=M:1
   - 多个用户态线程对应一个内核线程，程序线程的创建、切换、终止和同步等线程工作必须自身来完成
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcb3d4f0deef?imageslim" alt="avatar" style="zoom:50%;" />
2. 内核级线程模型
   - 直接调度操作系统的内核线程，所有线程的创建、切换、终止和同步等线程工作都由内核完成。eg:c++
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcb4e6c9365e?imageslim" alt="avatar" style="zoom:50%;" />
3. 两级线程模型
   - 一个进程中可以对应多个内核级线程，进程中的线程不和内核线程一一对应
   - 这种模型会先创建多个内核级线程，然后通过自身的用户级线程取对应创建的多个内核级线程，自身的用户级线程程序调度，内核级线程操作系统内核取调度
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcb826ac38f4?imageslim" alt="avatar" style="zoom:50%;" />
4. Golang线程实现模型MPG(特殊的两级线程模型)
   - M:Machine，一个M关联一个内核线程
   - P:Processor，表示M所需的上下文环境，用户级代码逻辑的处理器
   - G:Goroutine，轻量级的线程
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcb826bec5fc?imageslim" alt="avatar" style="zoom:25%;" />
   - P的数量被设置为环境变量GOMAXPROCS的值或者runtime.GOMAXPROCS()
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcb9ed3d6a08?imageslim" alt="avatar" style="zoom:50%;" />
   - processor的数量被固定表示任意时刻同时运行的线程数量相同
   - 能否丢弃掉P? 上下文的目的是为了当内核线程出现阻塞的时候能够直接放开其他线程
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcbb7ff4b0e1?imageslim" alt="avatar" style="zoom:50%;" />
   - 如上左边，M0中的G0执行了syscall，然后就会创建一个M1(也可能是本身就存在的一个M1)，然后M0丢弃了P，等待syscall的返回值，M1接受p，继续执行goroutine。系统调用syscall结束之后，M0会偷一个上下文，如果失败M0就会把它的goroutine放到全局的runquene中，自己会放到线程池或者是休眠
   - runquene是各个p在运行完自己的goroutine后用来获取新的goroutine的地方。p也会周期性的检查runquene上的goroutine，防止runquene上的goroutine被饿死。如果全局的runquene中的goroutine没有了，name空闲的p会从其他的p中偷goroutine(一半)
   - <img src="https://user-gold-cdn.xitu.io/2019/3/5/1694bcbd210d309a?imageslim" alt="avatar" style="zoom:50%;" />



