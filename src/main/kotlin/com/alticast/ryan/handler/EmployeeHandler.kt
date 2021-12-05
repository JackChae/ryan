package com.alticast.ryan.handler




import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import com.alticast.ryan.model.Employee
import com.alticast.ryan.repository.EmployeeRepository

import java.net.URI

@Service
class EmployeeHandler(private val employeeRepository: EmployeeRepository) {

    fun findAll(request: ServerRequest) =
            ServerResponse.ok()
                    .body(employeeRepository.findAll(), Employee::class.java)
                    .switchIfEmpty(notFound().build())

    fun findById(req: ServerRequest) =
            this.employeeRepository.findById(req.pathVariable("id"))
                    .flatMap { post -> ok().body(Mono.just(post), Employee::class.java) }
                    .switchIfEmpty(notFound().build())

    fun add(request: ServerRequest) =
            request.bodyToMono(Employee::class.java)
                    .flatMap { this.employeeRepository.save(it) }
                    .flatMap { (id) -> created(URI.create("/employee/" + id)).build() }

    fun update(request: ServerRequest): Mono<ServerResponse> =
            request.bodyToMono(Employee::class.java).flatMap { newEmployee ->
                newEmployee.id?.let {
                    this.employeeRepository.findById(it)
                        .flatMap { this.employeeRepository.save(newEmployee).then(ok().build()) }
                        .switchIfEmpty(badRequest().build())
                }
            }


    fun delete(request: ServerRequest) =
            this.employeeRepository.findById(request.pathVariable("id"))
                    .flatMap { noContent().build(this.employeeRepository.delete(it)) }
                    .switchIfEmpty(notFound().build());

}