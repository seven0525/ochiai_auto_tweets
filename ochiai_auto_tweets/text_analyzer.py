# -*- coding: utf-8 -*-
import copy
import json
import re
import string

from janome.tokenizer import Tokenizer


def analyze_csv_by_markov(twitter_id):
    directory_name = "/Users/kuratadaisuke/ochiai_auto_tweets/tmp/"
    with open(directory_name + twitter_id + 'tweet.txt', 'r') as f:
        tweets = f.readlines()

    text = []
    for i in range(0, len(tweets)):
        if not tweets[i].startswith("RT"):
            tweets[i] = re.sub(r"(https?|ftp)(:\/\/[-_\.!~*\'()a-zA-Z0-9;\/?:\@&=\+\$,%#]+)", "", tweets[i])
            tweets[i] = re.sub(r"@[a-zA-Z0-9_\-.]{3,15}", "", tweets[i])
            text.extend(tweets[i].split())

    formatted_text = __remove_non_ascii_letter(tweet_contents=text)

    t = Tokenizer()
    # 文字列をTokenizeする. 分離した文字ごとに文字の種別、開始位置、終了位置などを分析.
    words = t.tokenize("".join(formatted_text))

    dictionary = __make_dictionary(words)
    json.dump(dictionary, open(directory_name + "markov-blog.json", "w", encoding="utf-8"))


def __make_dictionary(words):
    tmp = ["@"]
    # 辞書オブジェクト生成. 辞書オブジェクト->並び順がない要素の集合
    dictionary = {}
    for i in words:
        word = i.surface
        # 空行、改行は無視
        if word == '' or word == '¥n' or word == '¥r¥n':
            continue
        tmp.append(word)
        if len(tmp) < 3:
            continue
        # 要素数が3より大きい場合は, 今格納されている部分のリストを要素の1番目に代入
        if len(tmp) > 3:
            tmp = tmp[1:]
        __set_word3(dictionary, tmp)
        if word == '.' or word == '。':
            tmp = ['@']
            continue
    return dictionary


def __set_word3(dic, s3):
    # s3の配列を抽出
    w1, w2, w3 = s3
    # 辞書にw1の要素がなければw1の辞書項目を追加
    if w1 not in dic:
        dic[w1] = {}
    # 辞書にw1*w2の要素がなければw1の辞書項目を追加
    if w2 not in dic[w1]:
        dic[w1][w2] = {}
    # 辞書にw1*w2*w3の要素がなければw1の辞書項目を追加
    if w3 not in dic[w1][w2]:
        dic[w1][w2][w3] = 0
    # 既存なら重みを追加
    dic[w1][w2][w3] += 1


def __remove_non_ascii_letter(tweet_contents: list):
    tweet_contents_only_ascii_letter = copy.deepcopy(tweet_contents)
    for line in tweet_contents_only_ascii_letter:
        for word in line:
            if word in string.ascii_letters or word in string.digits:
                if line in tweet_contents_only_ascii_letter:
                    tweet_contents_only_ascii_letter.remove(line)
    return tweet_contents_only_ascii_letter
