import text_analyzer
import text_maker
import sys


text_file_name = sys.argv[1]
text_analyzer.analyze_csv_by_markov(text_file_name)
tweet_content = text_maker.make_sentence_from_json_dictionary('/Users/kuratadaisuke/ochiai_auto_tweets/tmp/markov-blog.json')
return tweet_content
