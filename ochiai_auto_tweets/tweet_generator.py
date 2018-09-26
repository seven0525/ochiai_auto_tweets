import text_analyzer
import text_maker


class TweetGenerator:

    @staticmethod
    def generate_tweet_from_plain_text():
        tweet_content = text_maker.make_sentence_from_json_dictionary('/Users/kuratadaisuke/ochiai_auto_tweets/myapp/static/markov-blog.json')
        return tweet_content

#    @staticmethod
#    def generate_tweet_from_plain_text(user_id):
#        print(user_id)
#        json_dictionary_name = text_analyzer.analyze_csv_by_markov()
#        return json_dictionary_name
