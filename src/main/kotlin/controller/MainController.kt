package controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController("/main")
class MainController {
    @GetMapping("/main-api")
    @ResponseBody
    fun mainAPI():String{
        return "main route"
    }
}