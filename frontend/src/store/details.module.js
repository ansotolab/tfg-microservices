import {
    DetailsService
} from "@/common/api.service";
import {
    EDIT_DETAILS
} from "./actions.type";

export const actions = {
    [EDIT_DETAILS](context, {slug, details}) {
        return DetailsService.update(slug, details);
    },
};

export default {
    actions
};