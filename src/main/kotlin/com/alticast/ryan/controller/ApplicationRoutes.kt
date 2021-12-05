package com.alticast.ryan.controller



import com.alticast.ryan.handler.EmployeeHandler
import com.alticast.ryan.handler.TweetHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class ApplicationRoutes(val tweetHandler: TweetHandler, val employeeHandler: EmployeeHandler) {

    @Bean
    fun appRouter() = router() {
        ("/tweet" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/{id}", tweetHandler::findById)
            GET("", tweetHandler::findAll)
            PUT("", tweetHandler::add)
            PATCH("", tweetHandler::update)
            DELETE("/{id}", tweetHandler::delete)
        }
        ("/employee" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/{id}", employeeHandler::findById)
            GET("", employeeHandler::findAll)
            PUT("", employeeHandler::add)
            PATCH("", employeeHandler::update)
            DELETE("/{id}", employeeHandler::delete)
        }
    }
}