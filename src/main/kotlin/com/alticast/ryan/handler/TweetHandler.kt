package com.alticast.ryan.handler


import com.alticast.ryan.model.Tweet
import com.alticast.ryan.repository.TweetRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.net.URI

@Service
class TweetHandler(private val tweetRepository: TweetRepository) {

    fun findAll(request: ServerRequest) =
            ServerResponse.ok()
                    .body(tweetRepository.findAll(), Tweet::class.java)
                    .switchIfEmpty(notFound().build())

    fun findById(req: ServerRequest) =
            this.tweetRepository.findById(req.pathVariable("id"))
                    .flatMap { post -> ok().body(Mono.just(post), Tweet::class.java) }
                    .switchIfEmpty(notFound().build())

    fun add(request: ServerRequest) =
            request.bodyToMono(Tweet::class.java)
                    .flatMap { this.tweetRepository.save(it) }
                    .flatMap { (id) -> created(URI.create("/tweet/" + id)).build() }

    fun update(request: ServerRequest): Mono<ServerResponse> = request.bodyToMono(Tweet::class.java)
            .flatMap { newTweet ->
                newTweet.id?.let {
                    this.tweetRepository.findById(it)
                        .flatMap { this.tweetRepository.save(newTweet).then(ok().build()) }
                        .switchIfEmpty(badRequest().build())
                }
            }

    fun delete(request: ServerRequest) =
            this.tweetRepository.findById(request.pathVariable("id"))
                    .flatMap { noContent().build(this.tweetRepository.delete(it)) }
                    .switchIfEmpty(notFound().build());

}

