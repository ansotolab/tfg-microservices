import {
    UsersService
} from "@/common/api.service";
import {
    FETCH_AUTHORITIES,
    FETCH_USERS,
    GET_USER,
    CREATE_USER,
    EDIT_USER
} from "./actions.type";

export const actions = {
    [FETCH_AUTHORITIES]() {
        return UsersService.getAuthorities();
      },
    [FETCH_USERS]() {
        return UsersService.getUsers();
    },
    [GET_USER](context, id) {
        return UsersService.getUsers(id);
    },
    [CREATE_USER](context, user) {
        return UsersService.registerUser(user);
    },
    [EDIT_USER](context, user) {
        return UsersService.updateUser(user);
    }
};

export default {
    actions
};