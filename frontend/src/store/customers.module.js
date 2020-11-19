import {
    CustomersService
} from "@/common/api.service";
import {
    FETCH_CUSTOMERS,
    CREATE_CUSTOMER,
    DELETE_CUSTOMER,
    EDIT_CUSTOMER,
    GET_CUSTOMER
} from "./actions.type";

export const actions = {
    [FETCH_CUSTOMERS]() {
        return CustomersService.get();
      },
    [CREATE_CUSTOMER](context, customer) {
        return CustomersService.create(customer);
    },
    [GET_CUSTOMER](context, id) {
        console.log(id)
        return CustomersService.get(id);
    },
    [DELETE_CUSTOMER](context, id) {
        return CustomersService.destroy(id);
    },
    [EDIT_CUSTOMER](context, {id, customer}) {
        return CustomersService.update(id, customer);
    },
};

export default {
    actions
};