import {
    ProductsService
} from "@/common/api.service";
import {
    FETCH_PRODUCTS,
    CREATE_PRODUCT,
} from "./actions.type";

export const actions = {
    [FETCH_PRODUCTS]() {
        return ProductsService.get();
      },
    [CREATE_PRODUCT](context, product) {
        return ProductsService.create(product);
    }
};

export default {
    actions
};