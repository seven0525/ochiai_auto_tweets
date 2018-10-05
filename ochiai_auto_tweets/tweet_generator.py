import text_analyzer
import text_maker


class TweetGenerator:

    @staticmethod
    def generate_tweet_from_plain_text(twitter_id):
        text_analyzer.analyze_csv_by_markov(twitter_id)
        tweet_content = text_maker.make_sentence_from_json_dictionary('/Users/kuratadaisuke/ochiai_auto_tweets/tmp/markov-blog.json')
        return tweet_content
