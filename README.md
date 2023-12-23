## アプリの概要
* ファイルごとに業務書類の保存期間(storage_year)、保存場所(location)を管理できるアプリ
* ファイルを作成したらを保存期間、機密分類(privacy_type)、保存場所を登録できる
* ファイルが保存場所から持ち出されている場合、持ち出しているユーザー(borrower_id)を確認できる
* 保存期間を超過したファイルが処分されていない場合、メール通知される
* ファイルを処分したらアプリ上からファイルを削除する

## 作成背景
公官庁や企業では業務書類を紙ベースで保存しているところもあり保存期間を超過した書類は処分しますが、適切に保存と処分がされない問題があり書類が見つからない、新しいファイルの保存場所が無いなど問題がありました。<br>
そこでファイルを分類して保存場所を登録するAPIを作成しようと思いました。

## 主な使用技術
* Java、Spring Boot
* MySQL
* Docker
* JUnit
* CI（GitHub Actions）
* AWS

## アプリケーション概略図
作成中

## アプリケーション機能一覧
| 項目 | 内容 |
| :----: | :----------------------- |
| ユーザーの登録 | ・管理者ユーザーはユーザーを登録できる |
| ユーザーの更新 | ・管理者ユーザーはユーザーを更新できる |
| ユーザーの削除 | ・管理者ユーザーはユーザーを削除できる |
| 権限の変更 | ・管理者ユーザーはユーザーの権限(管理者or一般)を変更できる |
| ログイン機能 | ・ユーザーはログイン、ログアウトできる |
| ファイル情報の登録 | ・ユーザーはファイル情報を登録できる<br> (内容はファイル名、ファイルの内容、保存場所、保存期間、機密分類) |
| ファイル情報の更新 | ・ユーザーはファイル情報を更新できる |
| ファイル情報の削除 | ・ユーザーはファイル情報を削除できる　　|
| 保存期間と機密分類の追加 | ・管理者ユーザーは保存期間と機密分類を追加できる　　|
| 保存期間と機密分類の更新 | ・管理者ユーザーは保存期間と機密分類を更新できる　　|
| 保存期間と機密分類の削除 | ・管理者ユーザーは保存期間と機密分類を削除できる<br> (ファイルの参照元になっている保存期間と機密分類は削除できない)　　|
| 保存場所の追加 | ・管理者ユーザーは保存場所を追加できる　　|
| 保存場所の更新 | ・管理者ユーザーは保存場所を更新できる　　|
| 保存場所の削除 | ・管理者ユーザーは保存場所を削除できる<br> (ファイルが保存されている保存場所は削除できない)　　|
| 検索機能 | ・ユーザーはファイルをファイル名、保存場所、保存期間超過で検索できる |
| 通知機能 | ・保存期間を超過したファイルがある場合、毎月に管理者にメールで通知される |

## 機能のロジック
### ユーザー登録と削除
![ユーザーロジック](https://github.com/chie-hira/files-management-API/assets/148871501/e430083e-4b30-47e1-ba58-af231258a8f9)

### 保存期間の判定方法
![保存期間ロジック](https://github.com/chie-hira/files-management-API/assets/148871501/58e03720-4e11-4319-994f-33c0b2a0976c)

## ER図
![ER](https://github.com/chie-hira/files-management-API/assets/148871501/6229b8c8-afd0-4a35-9a84-a9f233da2c26)
