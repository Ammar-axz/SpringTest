"use client";
import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useRouter } from "next/navigation";
import { FormEvent } from "react";

export function RegisterForm({
  className,
  ...props
}: React.ComponentProps<"div">) {

  const router = useRouter()

  async function handleRegister(e:FormEvent<HTMLFormElement>){
    e.preventDefault()
    const formData = new FormData(e.currentTarget)

    const data = Object.fromEntries(formData.entries());

    const res = await fetch("http://localhost:8080/api/auth/register",
      {
        method:"POST",
        headers: {
        "Content-Type":"application/json"
        },
        body:JSON.stringify(data)
      }
    )
    console.log(await res.text());
    
    if(res.ok)
    {
      router.push("/auth/register/verify")
    }
    else{
      alert("registration failed")
    }
  }

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader className="text-center">
          <CardTitle className="text-xl">Sign Up</CardTitle>
          <CardDescription>
            Create a new SpringNext account
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form method="POST" action="http://127.0.0.1:8080/api/auth/register" onSubmit={(e) => handleRegister(e)}>
            <div className="grid gap-6">
              
              <div className="grid gap-6">
                <div className="grid gap-3">
                  <Label htmlFor="email">Email</Label>
                  <Input
                    id="email"
                    name="email"
                    type="email"
                    placeholder="m@example.com"
                    required
                  />
                </div>
                  <div className="grid gap-3">
                  <Label htmlFor="userName">User Name</Label>
                  <Input
                    id="userName"
                    name="userName"
                    type="text"
                    required
                  />
                </div>
                
                <div className="grid gap-3">
                  <Label htmlFor="firstName">First Name</Label>
                  <Input
                    id="firstName"
                    name="firstName"
                    type="text"
                    required
                  />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="lastName">Last Name</Label>
                  <Input
                    id="lastName"
                    name="lastName"
                    type="text"
                    required
                  />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="password">Password</Label>
                  <Input id="password" name="password" type="password" required />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="password2">Re-enter Password</Label>
                  <Input id="password2" type="password" required />
                </div>
                <Button type="submit" className="w-full">
                  SignUp
                </Button>
              </div>
              <div className="text-center text-sm">
                already have an account?{" "}
                <a href="/auth/login" className="underline underline-offset-4">
                  Login
                </a>
              </div>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
