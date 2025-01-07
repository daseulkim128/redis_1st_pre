import lombok.Getter;

@Getter
public class ProductSychronized {
    /*
    * synchronized 단일 메서드나 코드 블록의 실행을 한 번에 하나의 스레드만 허용
    * 장점  1. 작업 단위의 무결성을 보장 (동시성 해결 보장)
    * 단점  1. 성능저하
    *         - 한 스레드가 동기화된 코드에 진입시, 다른 스레드는 작업 완료시까지 대기해야함. 경쟁이 심한 경우, cpu리소스 낭비와 응답시간 증가의 가능성
    *         - 모든 접근 (읽기, 쓰기)에 대해 락을 사용하여 읽기에도 락이 걸리기에 성능 저하 발생 가능성
    *      2. 정밀한 제어 불가능
    *         - 동기화 범위를 세밀하게 제어하기 어려움 -> 블록이 끝날때까지 락이 유지되기에, 조기에 해제하거나 조건에 따라 동작 변경 불가
    *      3. 교착상태(deadlock)의 위험
    *         - 두 스레드가 서로 다른 자원을 락을 통해 요청하면서 상대 스레드가 해제하기를 기다림
    *      4. 타임아웃 지원 없음
    *         - 스레드는 락을 기다리며 무한대기
    * */
    private final String name;
    private int stock;
    ProductSychronized(){
        this.name = "apple";
        this.stock = 100;
    }
    synchronized void decreaseStock(int quantity){
        if(stock < quantity){
            throw new IllegalStateException("재고가 부족합니다.");
        }
        stock -= quantity;
    }
}
