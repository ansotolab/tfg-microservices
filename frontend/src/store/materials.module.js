import {
    MaterialsService
} from "@/common/api.service";
import {
    FETCH_MATERIALS,
    FETCH_UNITS,
    CREATE_MATERIAL,
} from "./actions.type";

export const actions = {
    [FETCH_MATERIALS]() {
        return MaterialsService.get();
      },
    [FETCH_UNITS]() {
        return MaterialsService.getUnits();
    },
    [CREATE_MATERIAL](context, material) {
        return MaterialsService.create(material);
    }
};

export default {
    actions
};