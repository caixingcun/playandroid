## android

### 开发工具
工具: Android Studio

Android SDK版本号  详见 gradle.properties  所有Module
构建版本
BUILD_TOOLS_VERSION=26.0.2
版本名称
VERSION_NAME=1.0.0
版本号
VERSION_CODE=1
编译版本
COMPILE_SDK_VERSION=26
最小支持版本
MINI_SDK_VERSION=19

### Module

imagepicker  图片选择裁剪框架
https://github.com/jeasonlzy/ImagePicker

daomodule   GreenDao数据库简单封装
在Application中进行初始化 DaoManager.init(app);
之后 通过 EntityManager.getEntityManager().getXXXDao() 获取数据库操作类
需要新建数据表 可以在daomodule
entity中创建数据表实体类 - make project - 在 EntityManager 中创建getDao方法
生成特定dao 就可以对数据表进行操作

b站开源播放器
https://github.com/open-android/IjkPlayer



