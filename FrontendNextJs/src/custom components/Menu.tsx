"use client"

import * as React from "react"
import Link from "next/link"
import { ModeToggle } from '../components/ui/toggle-mode'
import { cn } from "@/lib/utils"
import { SignOutButton, useSession, useUser,   SignedIn, SignedOut, SignInButton, UserButton } from "@clerk/nextjs";
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
  navigationMenuTriggerStyle,
} from "@/components/ui/navigation-menu"
import Logo from "./logo"
import { Button } from "@/components/ui/button"
import { toast } from "@/components/ui/use-toast"
import checkUserRole  from '../pages/utils/userUtils';

const components: { title: string; href: string; description: string }[] = [
  {
    title: "Vehicles",
    href: "/vehicles",
    description:
      "Our selection of vehicles",
  },
  {
    title: "Reservations",
    href: "/reservations",
    description:
      "Manage reservations",
  },
  
]

export default function Menu() {
  const { session } = useSession();
 
  const userRole = checkUserRole(session);

  return (
    <div className="flex items-center">
    <ul className="flex items-center px-1">
    <Logo/>
    </ul>
    <ul className="flex items-center flex-grow px-6">
    <NavigationMenu>
      <NavigationMenuList>
        <NavigationMenuItem>
          <NavigationMenuTrigger className="">About</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-6 md:w-[400px] lg:w-[500px] lg:grid-cols-[.75fr_1fr]">
              <li className="row-span-3">
                <NavigationMenuLink asChild>
                  <a
                    className="flex h-full w-full select-none flex-col justify-end rounded-md bg-gradient-to-b from-muted/50 to-muted p-6 no-underline outline-none focus:shadow-md"
                    
                  >
                    {/* //<Icons.logo className="h-6 w-6" /> */}
                    <div className="mb-2 mt-4 text-lg font-medium">
                      Rentalux
                    </div>
                    <p className="text-sm leading-tight text-muted-foreground">
                      Beautifully designed Rental App with ease of use in mind
                    </p>
                  </a>
                </NavigationMenuLink>
              </li>
              <ListItem  title="Introduction">
                Rentalux aims to simplify the process of renting vehicles.
              </ListItem>
              <ListItem  title="Construction">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris elementum vitae augue ac imperdiet.
              </ListItem>
              <ListItem  title="Construction">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla quis quam ultricies, ultrices lectus at, volutpat tellus.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger className="">Browse</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid w-[400px] gap-3 p-4 md:w-[500px] md:grid-cols-2 lg:w-[600px] ">
              {components.map((component) => (
                <ListItem
                  key={component.title}
                  title={component.title}
                  href={component.href}
                >
                  {component.description}
                </ListItem>
              ))}
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <SignedIn>
        {userRole === 'admin' && 
        (
        <NavigationMenuItem>
          <Link href="/admin" legacyBehavior passHref>
            <NavigationMenuLink className={navigationMenuTriggerStyle()  }>
              Admin
            </NavigationMenuLink>
          </Link>
        </NavigationMenuItem>
        )
        }
        </SignedIn>
      </NavigationMenuList>
    </NavigationMenu>
    </ul>
    <ul className="flex items-center">
        <ModeToggle/>
    </ul>
    <SignedOut>
    <ul className="flex items-center">
          <Button className="ml-2 h-9">
            <Link href="/sign-in">Sign in</Link>
          </Button>
        </ul>
    </SignedOut>
    <SignedIn>
    <ul className="flex items-center">
          <SignOutButton>
            <Button className="ml-2 h-9" onClick={() => signout()}>
              Sign out
            </Button>
          </SignOutButton>
    </ul>
    </SignedIn>
  </div>
  )
}

function signout(){
  toast({
    title: "Signed out!",
  })
}
const ListItem = React.forwardRef<
  React.ElementRef<"a">,
  React.ComponentPropsWithoutRef<"a">
>(({ className, title, children, ...props }, ref) => {
  return (
    <li>
      <NavigationMenuLink asChild>
        <a
          ref={ref}
          className={cn(
            "block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground",
            className
          )}
          {...props}
        >
          <div className="text-sm font-medium leading-none">{title}</div>
          <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
            {children}
          </p>
        </a>
      </NavigationMenuLink>
    </li>
  )
})
ListItem.displayName = "ListItem"
