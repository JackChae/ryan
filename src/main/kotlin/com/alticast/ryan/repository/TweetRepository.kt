package com.alticast.ryan.repository


import com.alticast.ryan.model.Tweet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface TweetRepository : ReactiveMongoRepository<Tweet, String>