package com.example.threadLearning.atomic;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference {

    static AtomicReference<UserInfo> userInfoRef = new AtomicReference<>();

    private static class UserInfo {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public UserInfo(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("Mark", 18);
        userInfoRef.set(userInfo);

        UserInfo updateUserInfo = new UserInfo("Bill", 28);
        userInfoRef.compareAndSet(userInfo, updateUserInfo); //期望值、新值, 其实是替换了引用

        UserInfo updateUserInfo2 = new UserInfo("Jack", 38);
        userInfoRef.compareAndSet(updateUserInfo, updateUserInfo2); //期望值、新值

        //没有更新userInfo对象，只更新原子类里的引用
        System.out.println(userInfoRef.get().toString());
        System.out.println(userInfo.toString());
    }
}
