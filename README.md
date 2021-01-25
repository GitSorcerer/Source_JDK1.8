## 不瞒你说，我最近跟Java源码杠上了
 
## 为什么要读JDK源码

当然不是为了装，毕竟谁没事找事虐自己 ...

![img](https://img-blog.csdnimg.cn/20210125091208143.gif)

1、**面试跑不掉**。现在只要面试Java相关的岗位，肯定或多或少会会涉及JDK源码相关的问题。

2、**弄懂原理才不慌**。我们作为JDK的使用者，虽然说天天用得很开心，但是有时候遇到问题还是得跟到底层源码去看看，才能帮助我们更好的弄懂原理，

3、**学习优秀的代码、思想和模式**。JDK毕竟是一个优秀的代码库，我们天天用，源码也就在里面，作为一个有志向的程序员，读一读源码也能让我们吸取到更多优秀的思想和模式。

4、**睡前催眠**。额 …… 不过的确有效（滑稽）。

------

## 源码难吗？

废话，当然有难度啦，不然我也不会到现在都还没看完，而且看了也经常忘，哭唧唧...

毕竟像JDK这种源码，和我们平常练手写小例子、写业务代码不一样，人家毕竟是 **类库**，为了**性能**、**稳定性**、**通用性**，**扩展性**等因素考虑，加入了很多**辅助代码**、**泛型**、以及一些**设计模式**上的考量，所以看起来肯定没有那么轻松，**没办法一眼看穿它**。

所以这玩意儿肯定是一个长期的过程，我个人建议（包括我自己也是这样），有时候遇到一些问题，可以针对性地把某些组件或者某个部分的源码，跟到底层去看看，然后做点笔记，写点注释啥的，这样慢慢就能渗透到很多的内容了。

但是我们一定要有足够的信心，我坚信代码人家都写出来了，我就不信我看不懂！

------

## 源码该怎么看

1、**方法一：按需阅读**。如果对某个组件、语法或者特性感兴趣，或者遇到什么问题疑惑，可以有针对性地跟到底层源码按需查看，这也是一种比较高效，能快速树立信心的阅读方式。

2、**方法二：系统化阅读**。具体阅读内容和顺序建议下文详述。

3、**多调试**：如果仅仅靠眼睛看，然后脑补画面调试还是比较吃力的，最好还是借助IDE动手调试起来，走两步就知道了。

4、**别光读，记得读完留下点什么**。我觉得看了多少不重要，重要的是能输出多少，多总结、归纳，写注释，记笔记

> 所以下文准备搭建一个Java源码的阅读和调试环境，建议人手一个，每当**心血来潮时**、**遇到问题时**、**碰到疑惑时**、**闲得无聊**时都可以打开工程看一看源码，做做笔记和注释。

------

## 搭建源码阅读调试环境

我个人觉得看源码这个事情还是应该单独搞一个Java工程，源码放里面，测试代码也放里面，**集中调试**，**集中看代码**，**集中写注释**比较方便一些。

**1、创建源码阅读项目**

选择最普通的Java基础项目即可：

![img](https://img-blog.csdnimg.cn/20210125091208386.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

**2、创建两个目录**

分别为：

- `source`：稍后放置JDK源码进去
- `test`：放置测试代码，里面还可以按需要建立层级子目录

![img](https://img-blog.csdnimg.cn/20210125092124186.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70#pic_center)

**3、导入JDK源码**

有很多小伙伴问**JDK的源码在哪里呢？**

远在天边，仅在眼前，其实在的**JDK安装目录**下就能找到。

JDK安装目录下有一个名为`src.zip`压缩包，这正是JDK源码！

![img](https://img-blog.csdnimg.cn/20210125091208449.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

将其解压后拷贝到上面项目的`source`目录下，这样JDK源码就导入好了。

![img](https://img-blog.csdnimg.cn/20210125091208369.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

有些小伙伴会有疑问，**为什么要将JDK源码导一份放到这个项目里****？**其实主要原因还是方便我们在源码里阅读、调试、以及做笔记和注释。

至于这份JDK源码怎么用上，下文继续阐述。

**4、调试并运行**

我们可以在`test`目录里去随意编写一段测试代码。

比如我这里就以`HashMap`为例，在`test`目录下创建一个子目录`hashmap`，然后在里面创建一个测试主入口文件`Test.java`，随意放上一段测试代码：

```
public static void main( String[] args ) {


    Map<String,Double> hashMap = new HashMap<>();

    hashMap.put( "k1", 0.1 );
    hashMap.put( "k2", 0.2 );
    hashMap.put( "k3", 0.3 );
    hashMap.put( "k4", 0.4 );

    for ( Map.Entry<String,Double> entry : hashMap.entrySet() ) {
        System.out.println( entry.getKey() +"：" + entry.getValue());
    }

}
```

然后启动调试即可。

不过接下来会有几个问题需要一一去解决。

**问题一：启动调试时Build报错，提示系统资源不足**

![img](https://img-blog.csdnimg.cn/20210125091208412.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

**解决方法：** 加大`Build process heap size`。

设置方法：`Preferences --> Build,Execution,Deployment --> Compiler`，将默认`700`的数值加大，比如我这里设置为`1700`：

![img](https://img-blog.csdnimg.cn/20210125091208468.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

**问题二：想从外层代码F7单步调试进入JDK源码内部，结果发现进不去**

这是因为调试时，JDK源码受保护，一般单步调试不让进，但是可以设置。

**解决方法：**：

```
Preferences --> Build,Execution,Deployment --> Debugger --> Stepping
```

![img](https://img-blog.csdnimg.cn/20210125091208361.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

**问题三：如何对JDK源码做注释？**

调试进入JDK源码以后，发现不能进行注释，每个文件上都有一个小锁的图标，这是因为现在关联的源码并不是我们项目里刚拷进去的源码，而是JDK安装目录下的`src.zip`只读压缩包。

**解决办法：** 重新关联JDK源码路径为本项目路径下的这一份JDK源码。

![img](https://img-blog.csdnimg.cn/20210125091208362.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

这样一来，我们就可以愉快地对JDK源码调试以及做注释了。

------

## 源码结构和阅读顺序

JDK源码毕竟太庞大了，所有都看不太现实，我们还是愿意根据日常使用和面试考察的频繁度来挖取重要的内容先看一看。

如果自己没有特别的规划，可以按照如下所示的建议阅读顺序往下进行：

![img](https://img-blog.csdnimg.cn/20210125091208371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0NoaWFuejYzMg==,size_16,color_FFFFFF,t_70)

具体的内容简介如下：

**1、`java.lang`**

这里面其实就是Java的基本语法，比如各种基本包装类型（`Integer`、`Long`、`Double`等）、基本类（`Object`，`Class`，`Enum`，`Exception`，`Thread`）等等...

**2、`java.lang.annotation`**

包含Java注解基本元素相关的源码

**3、`java.lang.reflect`**

包含Java反射基本元素相关的代码

**4、`java.util`**

这里面放的都是Java的基本工具，最典型和常用的就是各种容器和集合（`List`、`Map`、`Set`）

**5、`java.util.concurrent`**

大名鼎鼎的JUC包，里面包含了Java并发和多线程编程相关的代码

**6、`java.util.function` +`java.util.stream`**

包含Java函数式编程的常见接口和代码

**7、`java.io`**

包含Java传统I/O相关的源码，主要是面向字节和流的I/O

**8、`java.nio`**

包含Java非阻塞I/O相关的源码，主要是面向缓冲、通道以及选择器的I/O

**9、`java.time`**

包含Java新日期和期间相关的代码，最典型的当属`LocalDateTime`、`DateTimeFormatter`等

**10、`java.math`**

主要包含一些高精度运算的支持数据类

**11、`java.math`**

主要包含一些高精度运算的支持数据类

**12、`java.net`**

主要包含Java网络通信（典型的如：`Socket`通信）相关的源代码。


该文档来源：b站程序羊


Spring5
MyBatis
常见的设计模式
分布式架构原理
分布式架构策略
分布式中间件
分布式架构实战
微服务框架
Spring Cloud
Docker与虚拟化
微服务架构
性能指标体系
JVM调优
Web调优
DB调优
内存模型
并发模式
线程模型
锁细节

------

