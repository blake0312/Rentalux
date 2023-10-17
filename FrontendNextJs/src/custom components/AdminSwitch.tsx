import TableAdmin from "./reservations/tableadmin";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/ui/tabs"
import  VehicleForm  from "./VehicleForm"


export default function TabsAdmin() {
  return (
    <Tabs defaultValue="rental" className="w-[400px]">

      <TabsList className="grid grid-cols-2">
        <TabsTrigger value="rental">Rental</TabsTrigger>
        <TabsTrigger value="reservations">Reservations</TabsTrigger>

      </TabsList>

      <div className="flex justify-center">
        <div className="w-[800px]">

        <TabsContent value="rental" className="flex-grow ">

          <h2 className="text-center font-bold">Create Rental</h2>
          <VehicleForm></VehicleForm>
        </TabsContent>

        <TabsContent value="reservations" className=" flex-grow custom-sm:w-full md:w-[850px]">
          <TableAdmin />
        </TabsContent>

        </div>
      </div>

      
    </Tabs>
  )
}
