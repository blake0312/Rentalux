import { SignIn } from "@clerk/nextjs";
 
export default function signin() {
  return (
    <div className="flex items-center justify-center h-screen">
      <SignIn
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