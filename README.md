# ochiai_auto_tweets
落合陽一っぽいツイートを誰でもツイートすることができるツールです


アイデア主、責任者：
渡辺大智(seven0525)

共同開発者：
DaisukeKURATA

# Project構成
## ochiai_auto_tweets(subdirectory)
1ツイート1行で構成されるテキストファイルを分析し、新しい文を生成するPythonプログラム。
今はFlaskによるWebAPIで、generate_tweetを叩くと、jsonで生成されたテキストがレスポンスされるようになっている。

ファイルが入力値になる関係上、WebAPIではなくプレーンなPythonパッケージにすることを検討中

## tweet_instead_of_you(subdirectory)
Twitterアカウントでログインし、ログインしたアカウントで自動生成したテキストをツイートできるWebアプリケーション。
自動生成にはWebAPI ochiai_auto_tweetsを使用。

# 動かし方
## ochiai_auto_tweets(subdirectroy)
・プロジェクト配下で`pip3 install -r requirements.txt`を実行する。
・同ディレクトリで`py run.py`を実行する。

## tweet_instead_of_you(subdirectory)
加筆予定。
