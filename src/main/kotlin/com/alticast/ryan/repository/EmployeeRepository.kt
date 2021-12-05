package com.alticast.ryan.repository


import com.alticast.ryan.model.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : ReactiveMongoRepository<Employee, String>