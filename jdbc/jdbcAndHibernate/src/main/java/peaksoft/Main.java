package peaksoft;

import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;
import peaksoft.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
//        userService.createUsersTable();

        userService.dropUsersTable();
//        userService.saveUser("Omurbek", "Shaiyrbek uulu", (byte) 22);
//        userService.removeUserById(1);
//        System.out.println(userService.getAllUsers());
//        userService.cleanUsersTable();
    }

}
