# 基于layuiCMS2.0模板设计的仓库管理系统
## 技术栈
mybatis plus + springboot + shiro + mysql + thymeleaf

## day01
完成基于shiro的登录认证、退出登录功能

## day02
完成首页左侧导航树

## day03
完成登录日志页面查询、删除、批量删除等功能

## day04
完成公告页面、图标页面，左侧导航树其他管理模块全部完成

## day05
完成部门管理页面相关功能

未解决问题：
* 部门更新弹出层中，父级部门无法回显
* 部门数据未设置缓存

## day06
完成菜单管理页面相关功能
完成角色管理页面相关功能

解决问题：
* 部门更新弹出层中，父级部门无法回显的问题
* 角色管理页面中，分配权限功能未完成

## day07
完成角色管理页面分配权限功能

## day08
完成用户管理页面部分功能
待完善功能：
* 数据表显示所属部门和直属领导名称而不是id √
* 添加用户信息页面 √
* 修改用户信息页面 √
* 重置密码 √
* 分配角色 √
* 修改密码 √
* 修改头像 √
* 菜单下拉树关联用户权限 √
* 权限下拉树关联用户权限 √

## day09
完成用户权限控制，采用shiro页面标签形式，根据权限隐藏展示部分增删改按钮标签

## day10
* 修改用户权限控制方案，取消页面显示标签的方式，改用后台方法控制，增加页面观赏性，同时防止直接使用地址越权访问页面
* 完成客户、供应商、商品管理页面的功能
* 完成商品的进货、退货功能

## day11
* 完成销售管理功能
* 修改商品管理页面，当库存低于预警值时，字体变红、加粗
* 完成进货、进货退货、销货、销货退货时，改变对应商品库存值、进货单进货量、销货单销货量的功能

## day12
* 修改pom.xml中shiro的依赖，保证打包jar无错误
* 开启shiro的权限认证以及资源认证的缓存
* 项目已经发布到云服务器，访问地址：www.yzxupup.top:8080 
* 账号system、ls：超级管理员，可以访问所有内容
* 账号ww、zl、sq、lb分别为基础数据管理员、仓库管理员、销售管理员、系统管理员，可以访问拥有权限的内容
* 密码统一为123123
