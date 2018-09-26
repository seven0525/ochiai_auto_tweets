from flask import jsonify
from myapp import app


@app.route("/generate_tweet")
def generate_tweet():
    from tweet_generator import TweetGenerator
    tweet_text = TweetGenerator.generate_tweet_from_plain_text()
    result = {
        "tweet": tweet_text
    }
    return jsonify(result)
