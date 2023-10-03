import { SignIn } from "@clerk/nextjs";
 
export default function signin() {
  return (
    <div className="flex items-center justify-center h-screen">
    <SignIn />
    </div>
    )
}