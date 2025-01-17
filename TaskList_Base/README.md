# GitHubからクローンして起動するまでの手順

### GithubからローカルPCへ
1. GitHubで、リポジトリのメインページにアクセスする。 

    URL：[https://github.com/ist-tec-school/23scrum-base-d](https://github.com/ist-tec-school/23scrum-base-d)

1. ファイルの一覧の上に「<>Code」ボタンがあるので、これをクリックし、クローンのコマンドをコピーする。 

   例：```git@github.com:ist-tec-school/23scrum-base-d.git```

2. PCでターミナルを起動する。このときホームディレクトリにいること。（コマンド入力の横に「~ %」と表示されていればOK）
1. ターミナルでクローンのコマンドを入力する。

   ```git clone git@github.com:ist-tec-school/23scrum-base-d.git```

### IntelliJの設定
1. IntelliJを起動する。
1. プロジェクトを選択する画面で、「開く」をクリックする。 
1. GitHubからクローンしたフォルダを選択する。 
1. 「プロジェクト〜〜を信頼して開きますか？」というダイアログが表示されるので、「プロジェクトを信頼」をクリックする。 
1. IntelliJのメニュー（PCの画面左上）で「ファイル」ー「プロジェクト構造」を選択する。 
1. 左側のメニューで「プロジェクト」が選択されていることを確認し、右側の画面のSDKで、今回使用するJDKを選択する。 
1. 「OK」ボタンをクリックする。 
1. IntelliJの右上で「現在のファイル▽」をクリックし、「実行構成の選択」をクリックする。（最初から「実行構成の選択」が表示されていれば、それをクリックする） 
1. 表示されたダイアログの左上の「＋」をクリックし、「アプリケーション」を選択する。 
1. 名前に「Tomcat」を入力する。 
1. ビルドと実行の「メインクラス」の枠をクリックし、「TaskListApplication」を選択して「OK」をクリックする。 
1. 「OK」をクリックする。 
1. IntelliJの右上にある「▷」をクリックする。 
1. IntelliJの下側でいくつかメッセージが表示されるので、「Started TaskListApplication　〜〜」が表示されていれば、起動が成功しています。 

### 動作確認
1. ブラウザを起動して、次のURLにアクセス。

    URL：[http://localhost:8080/list](http://localhost:8080/list)

2. タスク管理アプリケーションにアクセスできたら、適当なタスクを登録、削除してください。
1. タスク管理アプリケーションを終了するときは、IntelliJの右上にある「■（赤色）」をクリックしてください。
