# TermProject

## 실행 방법
### server
```shell script
mvn clean install
java -jar target/termproj-0.0.1-SNAPSHOT.jar -Dserver.port=8081
```
### clinet
```shell script
cd front-end
npm install vue-router
npm install axios
npm run serve
```

## 테이블 정의
```
Employee (직원 정보)
    id IDENTITY PRIMARY KEY, (유저 ID)
    employee_id VARCHAR(20) NOT NULL, (직원 ID)
    name VARCHAR(30) NOT NULL, (직원 이름)
    department VARCHAR(50), (부서)
    role VARCHAR(50), (직책)
    join_date DATE (입사일)
```

```
Leave (휴가 신청 정보)
    id IDENTITY PRIMARY KEY, (휴가 ID)
    employee_id VARCHAR(20) NOT NULL, (직원 ID)
    start DATE NOT NULL, (휴가 시작일)
    end DATE NOT NULL, (휴가 종료일)
    days DOUBLE NOT NULL, (휴가 기간)
    leave_type VARCHAR(30) NOT NULL, (휴가 타입, ex)연차 )
    leave_status VARCHAR(50) NOT NULL, (휴가 승인 상태)
    created_at DATETIME (휴가 신청 날짜/시각)
```

## 스펙 추가
- 연도 경계를 포함하는 휴가의 경우 현재 연도에 포함되는 휴가만 차감
- 중복 날짜에는 휴가 신청 허용하지 않음 (취소했던 휴가를 다시 승인하는 경우도 포함)
- 