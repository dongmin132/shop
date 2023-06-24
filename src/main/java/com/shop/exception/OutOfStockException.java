package com.shop.exception;

// OutOfStockException 클래스는 주어진 메시지를 사용하여 예외를 생성하는 생성자를 가지고 있습니다.
// 생성자는 message 매개변수를 받고, 상위 클래스인 RuntimeException의 생성자를 호출하여 예외 메시지를 설정합니다.
// 즉, OutOfStockException 객체를 생성할 때, 메시지를 전달하여 해당 메시지를 예외 객체에 설정할 수 있습니다.
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}
