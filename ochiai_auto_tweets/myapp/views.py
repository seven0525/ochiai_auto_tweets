from flask import jsonify
from myapp import app


@app.route("/generate_tweet/<twitter_id>")
def generate_tweet(twitter_id = None):
    from tweet_generator import TweetGenerator
    tweet_text = TweetGenerator.generate_tweet_from_plain_text(twitter_id)
    result = {
        "tweet": tweet_text
    }
    return jsonify(result)
