# EasyPizy
흡연구역, 금연구역 지도 앱

# Team Aid
```text
팀장
-이승기

팀원
-이경근
-안채은
-이서현
```

## 정보

- 타겟 os: Android

```text
앱 구조
+- data: 로컬/외부 DB 담당
|  +- data_source: 실제 데이터 입출력 실행
|  +- mapper: Dto <-> Entity 변환
|  +- repository: 데이터 관련 연산들
+- presentation: View 담당
|  +- views: View
|  +- viewModel: ViewModel
```
