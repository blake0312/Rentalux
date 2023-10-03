import { Skeleton } from "@/components/ui/skeleton"


export default function LoadingSingle(){
    return(
        <main className="self-center">
             <div className="bg-white p-8">
              <Skeleton className="rounded-mb w-96 h-80 bg-gray-400 mb-2"></Skeleton>
              <Skeleton className="h-5 w-1/2 font-bold mb-2"></Skeleton>
              <Skeleton className="h-4 w-1/2 font-bold text-xs mb-4"></Skeleton>
            </div>
        </main>
    )

}