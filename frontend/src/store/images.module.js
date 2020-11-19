import {
    ImagesService
} from "@/common/api.service";
import {
    FETCH_IMAGE
} from "./actions.type";

export const actions = {
    [FETCH_IMAGE](context, params) {
        return ImagesService.create(params);
    },
};

export default {
    actions
};