import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseServiceSynchronizedTest {

    private static final Logger logger = LogManager.getLogger(PurchaseServiceSynchronizedTest.class);

    @Test
    @DisplayName("주문 량은 8 로 고정, 사과 재고는 100개, 12번의 구매시점에 사과는 4개만 남는다")
    void 테스트() throws InterruptedException {

        // given
        PurchaseServiceSychronized service = new PurchaseServiceSychronized();

        // 스레드 카운트는 100으로 고정
        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch counter = new CountDownLatch(12);

        // when
        for (int i = 0; i < 12; i++) {
            executor.execute(() -> {

                long threadId = Thread.currentThread().getId();

                service.purchase();

                logger.info("Thread {} 주문 정보 : apple: 1건 [{}]", threadId, service.getPurchaseLimit());

                counter.countDown();
            });
        }

        // 모든 작업 완료 대기
        counter.await();

        // 스레드 풀 종료
        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            executor.shutdownNow(); // 강제 종료
        }

        // then
        int finalStock = service.getAppleStock();
        int expectedStock = 4;

        assertEquals(finalStock, expectedStock,"사과의 재고는 예상한 값과 같아야 합니다.");
        //assertEquals(apple, result, "Click count는 예상한 값과 같아야 합니다.");
    }
}
