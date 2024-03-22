package controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController("/my")
class MyController {
    @GetMapping("/main-api")
    @ResponseBody
    fun myAPI():String{
        return "my route"
    }
}