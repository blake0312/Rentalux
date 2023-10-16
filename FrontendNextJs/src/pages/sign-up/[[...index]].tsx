import { SignUp } from "@clerk/nextjs";
 
export default function Page() {
  return (
    <div className="flex items-center justify-center h-screen">
   <SignUp
       appearance={{
           elements: {
               formButtonPrimary:
                   "bg-primary text-primary-foreground hover:bg-primary/90",
           },
       }}
   />
   </div>
  )
}