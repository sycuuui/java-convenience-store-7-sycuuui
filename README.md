# 과제4 - 편의점
## 구현 기능 목록
<hr>

### 파일 입출력
<br>

1. products.md파일 읽어 상품 객체에 저장

2. promotions.md파일 읽어 프로모션 객체에 저장
  - 추후 프로모션 이름으로 쉽게 찾기 위해 Hashmap을 사용하여 저장 

### 입력
<br>

1. 구매할 상품과 수량 입력
  - [상품명-개수] 표현식으로 되어있지 않을 경우 에러 메세지 출력
  - 상품들 입력 시 쉼표로 구분되어 있지 않을 경우 에러 메세지 출력
  - 상품 개수가 존재하는 개수보다 적을 경우 에러 메세지 출력
  - 빈 값이 있을 경우 에러 메세지 출력
  - 존재하는 상품명이 아닐 경우 에러 메세지 출력

2. 각 여부 입력
  - Y 또는 N으로 입력하지 않을 경우 에러 메세지 출력
  - 추가 구매 여부 입력 시 
    - Y인 경우 구매할 상품과 수량 입력부터 다시 실행
    - N인 경우 프로그램 종료

### 편의점
<br>

1. 상품 구입
  - 입력한 상품만큼 구입할 상품에 저장
   - 저장 시 해당 상품이 프로모션 적용 목록 인지 파악하는 데이터도 저장

2. 프로모션 적용
  - 구입할 상품 목록에 프로모션 상품이 있는 지 확인
  - 프로모션 제고 파악
    - 제고에 맞게 선택한 경우 
      - 프로모션 적용이 가능한 상품에 대해 고객이 수량보다 적게 가져왔는지 판단
        - 가져오지 않은 수량만큼 추가 여부 받기 
    - 제고에 맞지 않게 선택한 경우
      - 프로모션 제고 부족하여 혜택 없이 결제 여부 받기
  - 프로모션 적용 여부와 할인 금액 저장
    - 적용 받았을 시 적용 받은 상품도 저장

3. 멤버십 적용
  - 멤버십 적용 여부 받기
    - 적용 시 
      - 구입한 상품가격에서 프로모션 할인 제외한 가격에서 30% 가격 계산

4. 구입한 상품만큼 재고 목록 업데이트

### 출력
<br>

1. 보유 상품들 출력
 - 저장한 상품들을 플랫폼에 맞게 출력

2. 영수증
 - 구매한 내역 출력
 - 프로모션 적용 시 증정 받은 내역 출력
 - 총 구매액, 행사할인, 멤버십할인, 내실 돈 출력