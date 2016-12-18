# WeChat Development Kit

[![Build Status](https://travis-ci.org/ixfan/wechat-kit.svg?branch=master)](https://travis-ci.org/ixfan/wechat-kit)

微信公众平台开发组件(WeChat Kit)是 Java 封装的微信开放平台接口功能，方便在用 Java 开发微信公众平台应用时对微信开放平台接口的使用。

## 怎么用

### 关于 access_token

`access_token` 是公众号的全局唯一接口调用凭证，开发者需要进行妥善保存。WeChat Kit 使用 [`WechatAccessTokenContainer`](src/main/java/me/ixfan/wechatkit/token/WechatAccessTokenContainer.java) 和 [`TokenManager`](src/main/java/me/ixfan/wechatkit/token/TokenManager.java) 来管理 `access_token`。[`WechatAccessTokenContainer`](src/main/java/me/ixfan/wechatkit/token/WechatAccessTokenContainer.java) 是一个接口，定义了 `access_token` 缓存容器存取 `access_token` 的方法，使用者需要为 [`WeChatKit`](src/main/java/me/ixfan/wechatkit/WeChatKit.java) 提供自己的 `WechatAccessTokenContainer` 实现类。

### 开始使用

[`WeChatKit`](src/main/java/me/ixfan/wechatkit/WeChatKit.java) 是使用 WeChat Kit 时主要用到的类，所有对微信开放平台接口功能的使用都要通过 `WeChatKit`。

以为公众号创建自定义菜单和获取微信用户信息为例：

```java
// 首先创建 WechatKit 实例
WeChatKit wechatKit = WeChatKit.build("WeChatID"，"APPID", "APPSECRET", yourWechatAccessTokenContainerImpl);

// 为公众号创建自定义菜单
wechatKit.menuManager().createCustomizedMenu(menuItemsList);

// 获取微信用户信息
WeChatFollower user = wechatKit.userManager().getWechatUserInfo("user's openid");
```

## 开发进程

> ✔️ - Done     🛠 - Ongoing      🕖 - Pending

* 基础功能

 ✔️ `access_token` 管理

* 菜单管理

 ✔️ 创建自定义菜单

 ✔️ 删除自定义菜单

* 消息管理

 ✔️ 解析微信服务器推送的消息&事件

 🛠 主动给用户发送消息

 ✔️ 发送模板消息

 ✔️ 被动回复消息

 ✔️ 群发消息

* 用户管理

 ✔️ 获取用户基本信息

 🕖 设置用户备注名

 ✔️ 用户标签管理

 ✔️ 获取用户列表

 🕖 获取用户地理位置

* 客服消息

 🛠 添加客服帐号

 🛠 修改客服帐号

 🛠 删除客服帐号

 🛠 获取所有客服帐号

 🕖 发送客服消息

* 素材管理

 ✔️ 新增临时素材

 ✔️ 新增永久素材

 🕖 获取临时/永久素材

 🕖 删除永久素材

 🕖 修改永久图文素材

 🕖 获取素材总数/列表

## 一起玩


