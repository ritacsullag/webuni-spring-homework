package com.csullagrita.school.websocket;

import com.csullagrita.school.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CourseChatGuard {

    public boolean checkCourseId(Authentication auth, int courseId){
        UserInfo principal = (UserInfo) auth.getPrincipal();
        return principal.getCourseIds().contains(courseId);
    }
}
