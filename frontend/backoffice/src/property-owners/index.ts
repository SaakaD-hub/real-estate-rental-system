import { ResourceProps } from "react-admin";
import { Person2 } from "@mui/icons-material";
import { CreatePropertyOwner } from "./create";
import { EditPropertyOwner } from "./edit";
import { ListPropertyOwners } from "./list";
import { ShowPropertyOwner } from "./show";

export const productOwners: ResourceProps = {
    name: "propertyOwners",
    options: { label: "Property Owners" },
    icon: Person2,
    list: ListPropertyOwners,
    edit: EditPropertyOwner,
    create: CreatePropertyOwner,
    show: ShowPropertyOwner,
  };