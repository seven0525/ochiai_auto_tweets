# -*- coding: utf-8 -*-
# 辞書の読み込み
import json
import random


def make_sentence_from_json_dictionary(json_dictionary_file_name):
    dic = open(json_dictionary_file_name, "r")  # if you run by cron, must be use absolute path.
    dic = json.load(dic)

    # 文章を生成
    tweet_content = __make_sentence(dic)
    return tweet_content


def __choice_word(sel):
    keys = sel.keys()
    ran = random.choice(list(keys))
    return ran


def __make_sentence(dic):
    ret = []
    if "@" not in dic:
        return "no dic"
    top = dic["@"]
    w1 = __choice_word(top)
    w2 = __choice_word(top[w1])
    ret.append(w1)
    ret.append(w2)
    while True:
        if not dic[w1][w2] or len(dic[w1][w2]) == 0:
            break
        w3 = __choice_word(dic[w1][w2])
        if len("".join(ret)) + len(w3) >= 140:
            break
        ret.append(w3)
        if w3 == "．" or w3 == "。" or w3 == ".":
            break
        w1, w2 = w2, w3
    return "".join(ret)
