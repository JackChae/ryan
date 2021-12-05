package com.alticast.ryan.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.util.Date

@Document(collection = "tweets")
data class Tweet (
        @Id var id: String? = null,
        @NotBlank @Size(max = 140) var text: String? = null,
        @NotNull var createdAt: Date = Date()
)