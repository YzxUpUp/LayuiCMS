package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.constant;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysDept;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysDeptService;
import com.yzx.layuicms.service.SysUserService;
import com.yzx.layuicms.util.saltUtil;
import com.yzx.layuicms.vo.userVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    SysUserService userService;

    @Autowired
    SysDeptService deptService;

    /**
     * 获取所有部门层级信息，并返回JSON格式字符串
     *
     * @return
     */
    @RequestMapping("/getUserTree")
    public dataGridView getUserTree() {
        List<SysDept> list = this.deptService.list();
        return new dataGridView(list);
    }

    /**
     * 获取所有用户信息，并返回JSON格式字符串
     * @param userVo
     * @return
     */
    @RequestMapping("/loadAllUser")
    public dataGridView loadAllUser(userVo userVo) {
        //根据页面传输的分页数据进行查询结果分页
        IPage<SysUser> page = new Page<>(userVo.getPage(), userVo.getLimit());

        //设置筛选条件
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(userVo.getName()), "name", userVo.getName());
        wrapper.like(StringUtils.isNotEmpty(userVo.getAddress()), "address", userVo.getAddress());
        wrapper.like(StringUtils.isNotEmpty(userVo.getRemark()), "remark", userVo.getRemark());
        wrapper.eq(userVo.getId() != null, "deptid", userVo.getId());
        wrapper.orderByAsc("ordernum");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.userService.page(page, wrapper);
        //获得所有数据
        List<SysUser> records = page.getRecords();
        //遍历数据
        for (SysUser record : records) {
            //判断当前用户是否有所属部门
            Integer deptid = record.getDeptid();
            if(deptid != null){
                //有，则查询部门名称并设置
                String deptName = this.deptService.getById(deptid).getTitle();
                record.setDeptName(deptName);
            }else{
                record.setDeptName("暂无");
            }
            //判断当前用户是否有直属领导
            Integer mgr = record.getMgr();
            if(mgr != null){
                //有，则查询领导名称并设置
                String mgrName = this.userService.getById(mgr).getName();
                record.setMgrName(mgrName);
            }else{
                record.setMgrName("暂无");
            }
        }

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), records);
    }

    /**
     * 添加用户
     * @param sysUser
     * @param hiredateString
     * @return
     */
    @RequestMapping("/addUser")
    public resultObj addUser(SysUser sysUser,
                             String hiredateString) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("user:create")){
                //存在
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parse = sp.parse(hiredateString);
                //生成随机盐
                String salt = saltUtil.getSalt(8);
                //对默认密码进行加密
                Md5Hash md5Hash = new Md5Hash(constant.USER_DEFAULT_PWD,salt,1024);
                //获取加密后的密码
                String relPassword = md5Hash.toHex();
                //为注册用户添加数据
                sysUser.setSalt(salt);
                sysUser.setPwd(relPassword);
                sysUser.setHiredate(parse);
                sysUser.setType(constant.USER_TYPE_NORMAL);
                this.userService.save(sysUser);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return resultObj.ADD_ERROR;
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteUser")
    public resultObj deleteUser(Integer id) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("user:delete")){
                //存在
                this.userService.removeById(id);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return resultObj.DELETE_ERROR;
        }
    }

    /**
     * 更新用户信息
     * @param sysUser
     * @param hiredateString
     * @return
     */
    @RequestMapping("/updateUser")
    public resultObj updateUser(SysUser sysUser,
                                String hiredateString) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("user:update")){
                //存在
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parse = sp.parse(hiredateString);
                sysUser.setHiredate(parse);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            this.userService.updateById(sysUser);
            return resultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return resultObj.UPDATE_ERROR;
        }
    }

    /**
     * 加载所有领导的名称
     *
     * @return
     */
    @RequestMapping("/loadMgrName")
    public dataGridView loadMgrName() {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.select("id","name");
        List<SysUser> allUserName = this.userService.list(wrapper);
        return new dataGridView(allUserName);
    }

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    @RequestMapping("/restPwd")
    public resultObj restPwd(Integer id){
        try{
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("user:resetPwd")){
                //存在
                //根据id获取需要重置密码的用户
                SysUser user = this.userService.getById(id);
                //生成随机盐
                String salt = saltUtil.getSalt(8);
                //对默认密码进行加密
                Md5Hash md5Hash = new Md5Hash(constant.USER_DEFAULT_PWD,salt,1024);
                //获取加密后的密码
                String relPassword = md5Hash.toHex();
                //为注册用户添加数据
                user.setSalt(salt);
                user.setPwd(relPassword);
                //插入数据
                this.userService.updateById(user);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.RESET_SUCCESS;
        }catch (Exception e){
            return resultObj.RESET_ERROR;
        }
    }

    /**
     * 分配用户角色
     * @param roleId
     * @param userId
     * @return
     */
    @RequestMapping("/saveUserRole")
    public resultObj saveUserRole(Integer roleId,
                                  Integer userId) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("user:allocation")){
                //存在
                //删除指定角色的角色信息
                this.userService.deleteUserRole(userId);
                //重新添加分配的角色信息
                this.userService.saveUserRole(roleId,userId);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            return resultObj.DISPATCH_ERROR;
        }
    }

}
