"use client"
 
import * as React from "react"
 
import { Progress } from "@/components/ui/progress"
 
export function ProgressTable() {
  const [progress, setProgress] = React.useState(13)
 
  React.useEffect(() => {
    const firstTimer = setTimeout(() => setProgress(66), 500);

    // After 500ms, change to 80%
    const secondTimer = setTimeout(() => setProgress(77), 1000);

    // After an additional 500ms, change to another value (e.g., 90%)
    const thirdTimer = setTimeout(() => setProgress(90), 1500);

    return () => {
      clearTimeout(firstTimer);
      clearTimeout(secondTimer);
      clearTimeout(thirdTimer);
    };
  }, []);

  return <Progress value={progress} className="w-[20%] h-3" />
}