"use client";
import { useEffect } from "react";
import { useRouter, useSearchParams } from "next/navigation";

export default function CallbackPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const token = searchParams.get("token");

  useEffect(() => {
    if (token) {
        // Store token in localStorage or cookie
        localStorage.setItem("jwt", token);
        console.log("jwt : ");
        console.log(token);
        
        //router.push("/dashboard");
    }
  }, [token, router]);

  return <p>Processing login...</p>;
}