import { useEffect, useState } from "react";
import  TabsAdmin from "../custom components/AdminSwitch";
import  Menu  from "../custom components/Menu";
import { useOrganizationList, useUser} from '@clerk/nextjs';
import { useRouter } from "next/router";
export default function Admin() {
  const { organizationList, isLoaded } = useOrganizationList();
  const { isSignedIn } = useUser();
  const router = useRouter();
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const checkAdminStatus = async () => {
      if (isLoaded && isSignedIn) {
        // Find the admin organization from the loaded organization list
        const adminOrganization = organizationList.find(
          (org) => org.membership.role === "admin"
        );

        // If the user is not an admin, redirect to the homepage
        if (!adminOrganization || adminOrganization.membership.role !== "admin") {
          router.push("/"); // Replace '/' with the homepage URL
        } else {
          // Set the state to indicate admin status
          setIsAdmin(true);
        }
      }
    };

    checkAdminStatus();
  }, [isLoaded, organizationList, isSignedIn, router]);

  return (
    <>
      {isAdmin && (
        <main className="">
          <nav className="p-6 space-x-6 ">
            <Menu />
          </nav>
          <div className="flex justify-center">
            <TabsAdmin />
          </div>
        </main>
      )}
    </>
  );
}




