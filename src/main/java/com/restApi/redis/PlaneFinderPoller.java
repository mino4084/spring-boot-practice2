package com.restApi.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
class PlaneFinderPoller {

    /**
     * WebClient는 비동기 웹 요청을 생성하는 클라이언트.
     * WebClient는 Reactor 프로젝트의 일부로서, Reactor의 리액티브 프로그래밍 모델을 기반으로 비동기적인 웹 요청을 수행.
     * 이를 통해 블로킹하지 않고 효과적으로 리소스를 활용 가능.
     */
    private WebClient client =
            WebClient.create("http://localhost:7634/aircraft");
            //WebClient.create("http://127.0.0.1:6379/aircraft");

    private final RedisConnectionFactory connectionFactory;
    
    // Repository 사용으로 해당 코드 제거
    //private final RedisOperations<String, Aircraft> redisOperations;
    private final AircraftRepository repository;

    /**
     * RedisConnectionFactory은 redis 의존성 추가로 스프링 부트의 '자동 설정'에 의해 주입.
     * 빈으로 등록한 redisOperations를 사용.
     *
     * 이 두개의 빈을 생성자 주입을 통해 정의된 멤버 변수에 할당.
     */
    PlaneFinderPoller(RedisConnectionFactory connectionFactory,
                      AircraftRepository repository) {
        this.connectionFactory = connectionFactory;
        this.repository = repository;
    }

    // 초당1회
    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        // 이전에 저장된 항공기를 지운다
        connectionFactory.getConnection().serverCommands().flushDb();

        /**
         * WebClient 객체를 생성하고, 하나의 멤버 변수에 할당.
         * 외부 PlaneFinder 서비스에 의해 노출되는 객체의 앤드포인트를 가리킴.
         *
         * Flux는 비동기적으로 발생하는 데이터 스트림을 나타내는데 사용.
         * 여러 데이터를 포함하거나, 에러, 완료 신호를 통해 비동기적인 이벤트 스트림을 처리.
         */
        // 현 위치를 조회하고 저장
        // opsForValue : 문자열 값에 접근하고 조작하기 위한 연산자를 반환
        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)// 응답 객체를 Aircraft 객체의 Flux로 변환
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);

        // 최신 캡쳐의 결과를 보고
        repository.findAll()
                .forEach(System.out::println);
    }
}
