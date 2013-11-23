package com.qsoft.pilotproject.data.rest;

import org.springframework.web.client.RestTemplate;

public interface IRest<T>
{
    void update(T object, Long id);

    void create(T object);

    void delete(Long id);

    void get();

    RestTemplate getRestTemplate();
}