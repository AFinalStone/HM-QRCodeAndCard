#### HM-QRCodeAndCard

集成了扫码和个人名片功能

#### 功能介绍

- 扫码页面
- 个人名片页面

#### 安装

在工程根目录的build.gradle文件里添加如下maven地址：

```gradle
allprojects {
    repositories {
        ...
        maven { url 'file:///Users/syl/.m2/repository/' }
        ...
    }
}
```

在项目模块的buile.gradle文件里面加入如下依赖：

```gradle
    compile 'com.heima.iou:hmqrcodelocal:1.0.0'
```

外部引用：

```gradle
    compile 'com.heima.iou:hmscancodelocal:1.0.0'
```

#### 使用说明

支持的路由

| 页面 | 路由url | 备注 |
| ------ | ------ | ------ |
| 扫码和个人名片页面 | hmiou://m.54jietiao.com/qrcode/index?show_type=*| 参数show_type为"show_my_card"，页面默认显示个人名片；参数show_type为"show_scan_code"，默认显示扫码页面|

路由文件

```json
{
  "qrcode": [
    {
      "url": "hmiou://m.54jietiao.com/qrcode/index?show_type=*",
      "iclass": "",
      "aclass": "com.hm.iou.qrcode.business.view.QRCodeActivity"
    }
  ]
  }
```

#### 集成说明

- 集成本模块之前，需要保证一下模块已经初始化：

Logger（日志记录），HM-BaseBiz初始化(基础业务模块)，HM-Network（网络框架），HM-Router（路由模块）

#### Author

syl