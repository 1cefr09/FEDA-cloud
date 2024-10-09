package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;
import com.example.exception.*;
import com.example.mapper.CommentMapper;
import com.example.mapper.MessageMapper;
import com.example.mapper.PostMapper;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.InfoIsValidUtil;
import com.example.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Override
    public void UserRegister(UserDTO userDTO){
        User user = new User();

        //检查是否有重复用户名
        if (userMapper.getByUsername(userDTO.getUsername()) != null){
            throw new AlreadyExistException(MessageConstant.USERNAME_EXIST);
        }
        //检查用户名和密码是否合法
        InfoIsValidUtil.isValidUsername(userDTO.getUsername());
        InfoIsValidUtil.isValidPassword(userDTO.getPassword());
        BeanUtils.copyProperties(userDTO,user);
        String originalPassword = userDTO.getPassword();
        user.setPassword(DigestUtils.sha256Hex(originalPassword)); //保存加密后的密码
        userMapper.insert(user);

    }

    @Override
    public void sendActivateEmail(Long id){
        User user = userMapper.getById(id);
        if (user.isActivated()){
            throw new AlreadyExistException(MessageConstant.USER_ACTIVATED);
        }
        //发送激活邮件
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(sendFrom);
            messageHelper.setTo(user.getEmail());
            String subject = "FEDA注册邮箱验证";
            messageHelper.setSubject(subject);
            String code = UUID.randomUUID().toString().substring(0,6);//生成6位验证码
            //String content = "用户:  " + user.getUsername() +"Id:  " + user.getId() + "  邮箱激活验证码如下，30分钟有效:" + code +
            //"<br>或点击以下链接进行激活:<a href=\"http://localhost:8080/api/user/activate?code=" + code + "&Id=" + user.getId() + "\">激活链接</a>";
            String content = "用户:  " + user.getUsername() +"Id:  " + user.getId() + "  邮箱激活验证码如下，30分钟有效:" + code +
            "<br>或点击以下链接进行激活:<a href=\"http://192.144.219.102:80/api/user/activate?code=" + code + "&Id=" + user.getId() + "\">激活链接</a>";
            messageHelper.setText(content, true);
            mailSender.send(message);
            //将验证码存入redis
            redisTemplate.opsForValue().set("emailCode" + user.getId(),code,30, TimeUnit.MINUTES);
        } catch (MessagingException e) {
            throw new MailSendException(MessageConstant.MAIL_SEND_FAILED);
        }
    }

    @Override
    @Transactional
    public void activateUser(Long Id, String code){
        User user = userMapper.getById(Id);
        if (user.isActivated()){
            throw new AlreadyExistException(MessageConstant.USER_ACTIVATED);
        }
        String redisCode = (String) redisTemplate.opsForValue().get("emailCode" + Id);
//        log.info("redisCode:{}",redisCode);
        if (redisCode == null || !redisCode.equals(code)){
            throw new CodeErrorException(MessageConstant.CODE_ERROR);
        }
        redisTemplate.delete("emailCode" + Id);
        userMapper.activateUser(Id);
    }

    @Override
    //完善用户登录方法
    public User UserLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);

        if (user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

//      password = DigestUtils.md5DigestAsHex(password.getBytes());//苍穹外卖使用的MD5加密方法，此处使用的是commons-codec的SHA256加密方法
        password = DigestUtils.sha256Hex(password);
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //此处检测账户是否被ban使用boolean判断，外卖中使用用户状态所处的不同数字来判断
//        if (user.isBanned()){
//            throw new AccountBannedException(MessageConstant.ACCOUNT_BANNED);
//
//        }
        return user;

    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    @Transactional
    /**
     * 用户更改信息时的更新
     */
    public UserVO updateUser(long id, UserDTO userDTO) {
        if (userDTO.getUsername().isEmpty() && userDTO.getPassword().isEmpty() && userDTO.getEmail().isEmpty()){
            throw new MessageInvalidException(MessageConstant.MESSAGE_INVALID);
        }
        User user = userMapper.getById(id);
        String lockKey = null;

        try {
            if (!userDTO.getUsername().isEmpty()){
                //获取锁并且判断锁是否获得成功
                lockKey = "usernameLock" + userDTO.getUsername();
                Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,"locked",5, TimeUnit.SECONDS);
                if (Boolean.TRUE.equals(success)){
                    if (userMapper.getByUsername(userDTO.getUsername()) != null && !user.getUsername().equals(userDTO.getUsername())){
                        throw new AlreadyExistException(MessageConstant.USERNAME_EXIST);
                    }
                    InfoIsValidUtil.isValidUsername(userDTO.getUsername());
                    if (!user.getUsername().equals(userDTO.getUsername())){
                        postMapper.updateUsername(user.getId(),userDTO.getUsername());
                        commentMapper.updateUsername(user.getId(),userDTO.getUsername());
                        messageMapper.updateUsername(user.getId(),userDTO.getUsername());
                    }
                    user.setUsername(userDTO.getUsername());
                }else {
                    throw new CantGetLockException(MessageConstant.CANT_GET_LOCK);
                }

            }
        }finally {
            if (lockKey != null){
                redisTemplate.delete(lockKey);
            }
        }
        //更新其它表和user表中的username
        //更新user表中密码
        if (!userDTO.getPassword().isEmpty()){
            InfoIsValidUtil.isValidPassword(userDTO.getPassword());
            String newPassword = DigestUtils.sha256Hex(userDTO.getPassword());
            user.setPassword(newPassword);
        }
        //更新user表中邮箱
        if (!userDTO.getEmail().isEmpty()){
            user.setEmail(userDTO.getEmail());
        }

        userMapper.update(user);


        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .email(user.getEmail())
                .isBanned(user.isBanned())
                .build();

    }


}
