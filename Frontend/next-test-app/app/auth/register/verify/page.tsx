"use client"
import { useState, useRef } from "react"
import { 
    Card,
    CardHeader,
    CardTitle,
    CardDescription,
    CardContent
 } from "@/components/ui/card"
 import { Input } from "@/components/ui/input"
 import { Button } from "@/components/ui/button"

export default function VerifyPage(){

    const [totalDigits,setTotalDigits] = useState<number>(6);
    const [digits,setDigits] = useState<string[]>(Array(totalDigits).fill(""));
    const inputsRef = useRef<(HTMLInputElement | null)[]>([])
    
    async function onVerify(){
        const verify = await fetch(`http://localhost:8080/api/auth/verify?code=${digits.join("")}`)
    }
    
    function onChangeDigits(e: React.ChangeEvent<HTMLInputElement>, i: number) {
        const value = e.target.value

        if (/^\d$/.test(value)) {
        // only allow single digit 0-9
        const newArray = [...digits]
        newArray[i] = value
        setDigits(newArray)

        // move to next input if exists
        if (i < totalDigits - 1) {
            inputsRef.current[i + 1]?.focus()
        }
        } else if (value === "") {
        // allow clearing
        const newArray = [...digits]
        newArray[i] = ""
        setDigits(newArray)
        }
    }
    function onKeyDown(e: React.KeyboardEvent<HTMLInputElement>, i: number) {
        if (e.key === "Backspace" && !digits[i] && i > 0) {
            // move to previous input if current is empty
            inputsRef.current[i - 1]?.focus()
        }
    }

    return(
        <div className="flex items-center justify-center min-h-screen">
            <Card>
                <CardHeader className="text-center">
                    <CardTitle className="text-xl">Verify</CardTitle>
                    <CardDescription>
                        Enter OTP we sent on your email
                    </CardDescription>
                </CardHeader>
                <CardContent>
                <form  className="flex flex-row space-x-4 justify-center">
                {
                
                digits.map((_ , i)=>
                    {
                        return(
                            <Input
                            value={digits[i]}
                            ref={(el) => {inputsRef.current[i] = el}}
                            type="text" 
                            inputMode="numeric"
                            required 
                            className="aspect-square w-12 sm:w-14 md:w-16 lg:w-20 text-sm sm:text-md md:text-lg text-center" 
                            onChange={(e)=>onChangeDigits(e,i)}
                            onKeyDown={(e) => onKeyDown(e, i)}
                            />
                        )
                    }   
                )
                }
                </form>
                </CardContent>
                <Button onClick={onVerify} className="m-6">Verify</Button>
            </Card>
        </div>
    )
}