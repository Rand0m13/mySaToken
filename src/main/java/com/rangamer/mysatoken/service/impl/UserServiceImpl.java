package com.rangamer.mysatoken.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rangamer.mysatoken.constant.RegexCons;
import com.rangamer.mysatoken.mapper.UserMapper;
import com.rangamer.mysatoken.model.User;
import com.rangamer.mysatoken.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author jesse
 * @date 2021年12月16日 17:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 注册用户
     *
     * @param user
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void registerUser(User user) {
        String tmpPass = user.getPassword();
        if (!ReUtil.isMatch(RegexCons.password, tmpPass)) {
            throw new ValidateException("密码格式不正确！");
        }
        String phone = user.getPhone();
        if (StringUtils.isNotBlank(phone) && !ReUtil.isMatch(RegexCons.phone, phone)) {
            throw new ValidateException("手机号格式不正确！");
        }
        String email = user.getEmail();
        if (StringUtils.isNotBlank(email) && !ReUtil.isMatch(RegexCons.mail, email)) {
            throw new ValidateException("邮箱格式不正确！");
        }
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        String pass = aes.encryptHex(tmpPass);
        String keySign = Base64.encode(key);
        user.setPassword(pass);
        user.setKeySign(keySign);
        user.setRegistrationTime(new Date());
        this.baseMapper.insert(user);
    }

//    public static void main(String[] args) {
//
//        //随机生成密钥
//
//        //[35, 63, -6, -110, -67, 117, -93, -49, 33, 63, -46, 1, -71, 108, 33, -16]
//
//        String pass = "数据库查出来的password";
//        String encode = "数据库查出来的keySign";
//        byte[] res = Base64.decode(encode);//解密
//
//
//        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, res);
//        String tmp = aes.encryptHex("新传入的pass");
//        //比较pass与tmp
//
//    }

}
