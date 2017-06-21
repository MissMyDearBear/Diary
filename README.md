## 前言
一直是接着别人的代码来做App的，所以没有完整的开发App体验，总感觉有所缺少。于是，就利用业余时间亲自操刀开始了我的第一次开源项目。本文将讲述开源项目的整个开发过程。
## 项目初衷
开始这个项目的初衷是为了练习当前比较火热的框架。能够涉及到数据库存储，网络请求，图片加载等功能。于是，便以日记为主题，图文阅读为辅设计了如题的小熊日记。
## 项目功能
- 日记
- 趣闻
- 萌图
- 我

![小熊日记.png](http://upload-images.jianshu.io/upload_images/4469838-df71a4551c059f68.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 项目开发
### 开发模式
开发模式选择MVP模式，如果你问我为什么？我不会告诉你的😈。

我就告诉你我使用后的一些感受吧，是用MVP代码量没有减少，但是在写代码前会思考的比较多，不用关注怎么实现。需要全局考虑功能页面有哪些功能，将所有的功能抽象出来。代码十分优雅，页面得功能能一目了然，后期维护也很方便
### 项目框架

![项目框架.png](http://upload-images.jianshu.io/upload_images/4469838-6b9468c3cc438605.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- #### 网络层
volley和retrofit选择了retrofit，都是非常优秀的网络库，但是为了练习rxjava和lambda选择了retrofit虽然，retrofit的库要比volley大。

>retrofit的整合[点击查看](http://www.jianshu.com/p/7e4e4036f981)

-  #### 数据库
数据库选择GreenDao，库比较小，做文本储存绰绰有余。
> GreenDao的整合[点击查看](http://www.jianshu.com/p/b2d7bb53c454)

- #### 图片加载（glide）
> Glide整合 [点击查看](http://www.jianshu.com/p/406776d09467)

### 项目数据源获取
#### 趣闻来源
本着练习的心态，也是没有去找现有的API接口，去拉数据，于是学习了python爬去了糗百的热门段子，Node写了API接口。（注：本人只做个人练习，并不涉及商业使用）爬虫的编写[点击查看](http://www.jianshu.com/p/fa02736ee217)
#### 图片来源
图片接口来自百度图片的接口。
### 运行效果图

![日记列表.jpg](http://upload-images.jianshu.io/upload_images/4469838-eb12fcee07348b12.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![日记保存.jpg](http://upload-images.jianshu.io/upload_images/4469838-02110a2f21b3bae7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![趣闻.jpg](http://upload-images.jianshu.io/upload_images/4469838-4d8cd0fa189b1f54.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![美图.jpg](http://upload-images.jianshu.io/upload_images/4469838-c236adce57178d7c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![关于我.jpg](http://upload-images.jianshu.io/upload_images/4469838-ef8d99d4991d0015.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
