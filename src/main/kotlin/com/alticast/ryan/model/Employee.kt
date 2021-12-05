package com.alticast.ryan.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Employee (
    @Id var id: String? = null,
    var name: String? = null,
    var salary: Double? = 0.0,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @CreatedDate var birthday: LocalDate? = null
)
