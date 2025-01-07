import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSynchronizedTest {

    private static final Logger logger = LogManager.getLogger(PurchaseServiceSynchronizedTest.class);

    @Test
    @DisplayName("재고테스트")
    void purchaseChronizedTest() throws InterruptedException {

        // given
        final int orderQuantity = 8;
        ProductSychronized service = new ProductSychronized();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch counter = new CountDownLatch(12);

        // when
        for (int i = 0; i < 12; i++) {
            executor.execute(() -> {

                service.decreaseStock(orderQuantity);
                logger.info("Thread {} 주문 정보 : 1건 [{}] : 남은 수량 {}", Thread.currentThread().getId(), orderQuantity, service.getStock());
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
        int finalStock = service.getStock();
        int expecedStock = 4;
        assertEquals(expecedStock, finalStock, "최종 값 예상한 값과 같아야 합니다.");
    }
}
