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

export function RegisterForm({
  className,
  ...props
}: React.ComponentProps<"div">) {

  function handleRegister(){
    
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
          <form method="POST" action="http://localhost:8080/api/register">
            <div className="grid gap-6">
              
              <div className="grid gap-6">
                <div className="grid gap-3">
                  <Label htmlFor="email">Email</Label>
                  <Input
                    id="email"
                    type="email"
                    placeholder="m@example.com"
                    required
                  />
                </div>
                  <div className="grid gap-3">
                  <Label htmlFor="email">User Name</Label>
                  <Input
                    id="userName"
                    type="text"
                    required
                  />
                </div>
                
                <div className="grid gap-3">
                  <Label htmlFor="email">First Name</Label>
                  <Input
                    id="firstName"
                    type="text"
                    required
                  />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="email">Last Name</Label>
                  <Input
                    id="lastName"
                    type="text"
                    required
                  />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="password">Password</Label>
                  <Input id="password" type="password" required />
                </div>
                <div className="grid gap-3">
                  <Label htmlFor="password">Re-enter Password</Label>
                  <Input id="password" type="password" required />
                </div>
                <Button type="submit" className="w-full" onClick={handleRegister}>
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
