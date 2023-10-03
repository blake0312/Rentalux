import  Menu  from "../custom components/Menu";
import DataTableCustomer from "../custom components/reservations/tablecustomer";

export default function reservations(){
    return (
        <main className="">
          <nav className="p-6 space-x-6 ">
            <Menu/>
          </nav>

          <div className="flex justify-center">

          <div className="grid grid-cols-1">
          <DataTableCustomer/>
          </div>

          </div>
        </main>
    )
}