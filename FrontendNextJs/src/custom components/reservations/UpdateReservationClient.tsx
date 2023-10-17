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
import { Reservation } from "./tablecustomer"
import { useLoading } from "../LoadingContext"


interface DatePickerFormProps {
  reservation: {
    id: string;
    vehicleId: string;
    customerId: string;
    payed: boolean;
    startData: string;
    endData: string;
  };
  onUpdateSuccess: (updatedReservation: Reservation) => void;
}

const FormSchema = z.object({
  startData: z.date({
    required_error: "A start date is required.",
  }),
  endData: z.date({
    required_error: "A end date is required.",
  }),
})

export default function DatePickerFormUpdateClient({ reservation, onUpdateSuccess}: DatePickerFormProps) {
  const { loading, loadingRowId, setGlobalLoading } = useLoading();
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      startData: reservation?.startData ? new Date(reservation.startData) : undefined!,
      endData: reservation?.endData ? new Date(reservation.endData) : undefined!,
    }
  })


  async function onSubmit(data: z.infer<typeof FormSchema>) {
    setGlobalLoading(true, reservation.id)
    try {
      const requestData = {
        "customerId": reservation.customerId,
        "payed": false,
        "vehicleId": reservation.vehicleId,
        "startData": data.startData,
        "endData": data.endData,
      };

      const url = `/rental/reservation/${reservation.id}`;
      const response = await fetch(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'ngrok-skip-browser-warning': 'true',
        },
        body: JSON.stringify(requestData),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      } else {
        toast({
          title: "Reservation has been updated",

        })
        onUpdateSuccess({ ...reservation, ...requestData, startData: requestData.startData.toISOString(), endData: requestData.endData.toISOString() });
      }
    } catch (error) {
      console.error("There was a problem", error)
    }
    setGlobalLoading(false, "")
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
