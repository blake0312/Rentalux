import { Skeleton } from "@/components/ui/skeleton"


export default function Loading(){
    return(
        <main className="m-24 flex justify-center">
        <div className="grid grid-cols-3 gap-12">
            {Array.from({length: 12}, (_, i) => i + 1).map((id) =>(
                <div key={id} className="bg-white p-8">
                    <Skeleton className="rounded-mb w-96 h-80 bg-gray-400 mb-2"></Skeleton>
                    <Skeleton className="h-5 w-1/2 font-bold mb-2"></Skeleton>
                    <Skeleton className="h-4 w-1/2 font-bold text-xs mb-4"></Skeleton>
                </div>
            ))}
            </div>
        </main>
    )

}