"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import { format } from "date-fns"
import { CalendarIcon } from "lucide-react"
import { useForm } from "react-hook-form"
import * as z from "zod"
import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"
import { toast } from "@/components/ui/use-toast"
import { DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"

interface DatePickerFormProps {
    reservation: {
        id: string;
        vehicleId: string;
        customerId: string;
        payed: boolean;
        startData: string;
        endData: string;
      };
  }

const FormSchema = z.object({
  customerId: z.string(),
  vehicleId: z.string(),
  startData: z.date({
    required_error: "A start date is required.",
  }),
  endData: z.date({
    required_error: "A end date is required.",
  }),
})

export default function DatePickerFormUpdate({reservation}: DatePickerFormProps) {
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      customerId: reservation?.customerId,
      vehicleId: reservation?.vehicleId,
      startData: new Date(reservation?.startData),
      endData: new Date(reservation?.endData),
    }
  })

  
  async function onSubmit(data: z.infer<typeof FormSchema>) {
         try{
          const requestData = {
            "customerId": data.customerId,
            "payed": false,
            "vehicleId": data.vehicleId,
            "startData": data.startData, 
            "endData": data.endData,     
          };
      
        const url = `/rental/reservation/${reservation.id}`; 
        const response = await fetch(url, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(requestData), 
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }else{
            toast({
            title: "Reservation has been updated",
            
          })
        }
     }catch(error){
        console.error("There was a problem", error)
     }

    form.reset();
  }

  return (
    <Form {...form}>
 
      <DialogContent>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
          <DialogHeader>
            <DialogTitle className="!text-primary">
              Update Reservation
            </DialogTitle>
            <DialogDescription>
            </DialogDescription>
          </DialogHeader>
              <FormField
                control={form.control}
                name="customerId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Customer Id</FormLabel>
                    <FormControl>
                      <Input autoComplete="off"{...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="vehicleId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Vehicle Id</FormLabel>
                    <FormControl>
                      <Input autoComplete="off"{...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField 
                control={form.control}
                name="startData"
                render={({ field }) => (
                  <FormItem className="flex flex-col">
                    <FormLabel>Start Date</FormLabel>
                    <Popover>
                      <PopoverTrigger asChild>
                        <FormControl>
                          <Button
                            variant={"outline"}
                            className={cn(
                              "w-[240px] pl-3 text-left font-normal",
                              !field.value && "text-muted-foreground"
                            )}
                          >
                            {field.value ? (
                              format(field.value, "PPP")
                            ) : (
                              <span>Pick a date</span>
                            )}
                            <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                          </Button>
                        </FormControl>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0" align="start">
                        <Calendar
                          mode="single"
                          selected={field.value}
                          onSelect={field.onChange}
                          disabled={(date) =>
                            date < new Date()
                          }
                        />
                      </PopoverContent>
                    </Popover>
                    <FormDescription>
                      Start day to reserve from
                    </FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="endData"
                render={({ field }) => (
                  <FormItem className="flex flex-col">
                    <FormLabel>End Date</FormLabel>
                    <Popover>
                      <PopoverTrigger asChild>
                        <FormControl>
                          <Button
                            variant={"outline"}
                            className={cn(
                              "w-[240px] pl-3 text-left font-normal",
                              !field.value && "text-muted-foreground"
                            )}
                          >
                            {field.value ? (
                              format(field.value, "PPP")
                            ) : (
                              <span>Pick a date</span>
                            )}
                            <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                          </Button>
                        </FormControl>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0" align="start">
                        <Calendar
                          mode="single"
                          selected={field.value}
                          onSelect={field.onChange}
                          disabled={(date) =>
                            date < new Date()
                          }
                        />
                      </PopoverContent>
                    </Popover>
                    <FormDescription>
                      End day to reserve to
                    </FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />
          <DialogFooter>
          <DialogTrigger asChild>
            <Button type="submit">Save Changes</Button>
          </DialogTrigger>
          </DialogFooter>
        </form>
      </DialogContent>
    </Form>
  )
}
