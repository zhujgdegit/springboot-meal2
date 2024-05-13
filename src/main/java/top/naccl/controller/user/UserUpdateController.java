package top.naccl.controller.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.naccl.annotation.OnlyUser;
import top.naccl.bean.User;
import top.naccl.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description: Modify user information
 * @Author: Naccl
 * @Date: 2020-05-18
 */

@Controller
@RequestMapping("/user")
@OnlyUser
public class UserUpdateController {
    @Autowired
    UserService userService;

    @GetMapping("/input")
    public String input(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user = userService.getUser(user.getId());
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        splitAddresses(user1);
        model.addAttribute("user", user1);
        return "user/user-input";
    }

    @PostMapping("/input")
    public String post(@Valid User user, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/user-input";
        }

        User u = (User) session.getAttribute("user");
        User user1 = userService.getUserByUsername(user.getUsername());
        if (user1 != null && user1.getId() != u.getId()) {//exist other users with the same name
            bindingResult.rejectValue("username", "nameError", "User already exists");
            return "user/user-input";
        }
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setTelephone(user.getTelephone());
//        String addresses = mergeAddresses(user);
        String addresses = buildAddressString(user);
        u.setAddress(addresses);
        u.setBirthday(user.getBirthday());
        u.setNackname(user.getNackname());
        u.setIntroduction(user.getIntroduction());
        User user2 = userService.updateUser(u.getId(), u);
        u.setPassword(null);
        if (user2 == null) {//failed to save
            redirectAttributes.addFlashAttribute("message", "Modification failed");
        } else {//successfully saved
            redirectAttributes.addFlashAttribute("message", "Modification successful");
        }
        return "redirect:/user/index";
    }


    /**
     * Split addresses
     *
     * @param user
     * @return
     */
    private void splitAddresses(User user) {
        try {
            String[] split = user.getAddress().split(",");
            user.setAddress(split[0]);
            for (int i = 1; i < split.length; i++) {
                setFieldValue(user, "address" + (i), split[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldValue(Object obj, String fieldName, String value) throws Exception {
        Field field1 = obj.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true); // Set accessible, even private fields can be accessed
//        field.set(obj, value); // Set the value of the field
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true); // Set accessible, even private fields can be accessed
                field.set(obj, value); // Set the value of the field
                return;
            }
        }
        throw new NoSuchFieldException(fieldName);
    }


    public static String buildAddressString(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        try {
            Method getAddressMethod = user.getClass().getMethod("getAddress");
            String address = (String) getAddressMethod.invoke(user);

            if (!StringUtils.isEmpty(address)) {
                stringBuilder.append(address);
            } else {
                return null;
            }

            for (int i = 1; i <= 9; i++) {
                Method getAddressNMethod = user.getClass().getMethod("getAddress" + i);
                String addressN = (String) getAddressNMethod.invoke(user);

                if (!StringUtils.isEmpty(addressN)) {
                    stringBuilder.append(",").append(addressN);
                } else {
                    return stringBuilder.toString();
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * Merge addresses
     *
     * @param user
     * @return
     */
    private String mergeAddresses(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String address = user.getAddress();
        if (StringUtils.isEmpty(address)) {
            return null;
        } else {
            stringBuilder.append(address);
        }
        String address1 = user.getAddress1();
        if (StringUtils.isEmpty(address1)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address1);
        }
        String address2 = user.getAddress2();
        if (StringUtils.isEmpty(address2)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address2);


        }
        String address3 = user.getAddress3();
        if (StringUtils.isEmpty(address3)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address3);
        }
        String address4 = user.getAddress4();
        if (StringUtils.isEmpty(address4)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address4);
        }
        String address5 = user.getAddress5();
        if (StringUtils.isEmpty(address5)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address5);
        }
        String address6 = user.getAddress6();
        if (StringUtils.isEmpty(address6)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address6);
        }
        String address7 = user.getAddress7();
        if (StringUtils.isEmpty(address7)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address7);
        }
        String address8 = user.getAddress8();
        if (StringUtils.isEmpty(address8)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address8);
        }
        String address9 = user.getAddress9();
        if (StringUtils.isEmpty(address9)) {
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",").append(address9);
        }

        return stringBuilder.toString();
    }

}
