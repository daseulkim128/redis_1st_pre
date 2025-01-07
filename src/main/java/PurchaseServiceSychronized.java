import lombok.Getter;

@Getter
public class PurchaseServiceSychronized {

    private int appleStock;
    private final int purchaseLimit;
    public PurchaseServiceSychronized() {
        this.appleStock = 100; // 초기 재고 100개
        this.purchaseLimit = 8; // 구매 제한 8개
    }
    public synchronized void purchase() {
        if (appleStock >= purchaseLimit) {
            appleStock -= purchaseLimit; // 재고 차감
        } else {
            throw new IllegalStateException("재고가 부족합니다."); // 재고 부족 시 예외 발생
        }
    }
}
