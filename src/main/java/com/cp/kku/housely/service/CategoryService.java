package com.cp.kku.housely.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cp.kku.housely.model.Category;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CategoryService {

    private final WebClient webClient;

    @Autowired
    public CategoryService(WebClient webClient){
        this.webClient =webClient;
    }

    public Flux<Category> getAllCategories(){
        return webClient.get()
        .uri("/categories")
        .retrieve()
        .bodyToFlux(Category.class);
    }

    public Mono<Category> getCategoryById(Long id){
        return webClient.get()
        .uri("/categories/{id}",id)
        .retrieve()
        .bodyToMono(Category.class);
    }

    public Mono<Category> createCategory(Category category){
        return webClient.post()
        .uri("/categories/add")
        .bodyValue(category)
        .retrieve()
        .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> Mono.error(new RuntimeException("Error creating department"))
                )
        .bodyToMono(Category.class);
    }

    public Mono<Category> updateCategory(Long id,Category category){
        return webClient.put()
        .uri("/categories/update/{id}",id)
        .bodyValue(category)
        .retrieve()
        .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> Mono.error(new RuntimeException("Error creating department"))
                )
        .bodyToMono(Category.class);
    }

    public Mono<Void> deleteCategory(Long id){
        return webClient.delete()
        .uri("/categories/delete/{id}",id)
        .retrieve()
        .bodyToMono(Void.class);
    }
}

