window.addEventListener('DOMContentLoaded', function () {
    document.querySelector("#newTweetForm1").addEventListener('submit', function (event) { return newTweet(event, 'api/_tweets'); });
    //    document.querySelector("#newTweetForm2").addEventListener('submit', (event: HTMLElementEvent<HTMLFormElement>) => newTweet(event, '/_tweets'));
}, false);
var Tweet = /** @class */ (function () {
    function Tweet() {
    }
    return Tweet;
}());
var newTweet = function (event, url) {
    event.preventDefault();
    // TODO modal display
    // TODO loading action
    // fetch
    var form = event.target;
    var children = form.children;
    var urlSearchParams = new URLSearchParams();
    for (var i = 0; i < children.length; i++) {
        var child = children.item(i);
        if (child.tagName.toLowerCase() === 'input') {
            urlSearchParams.append(child.getAttribute("name"), child.getAttribute("value"));
        }
    }
    var formData = new FormData(event.target);
    var tweet = null;
    fetch("" + url, {
        method: 'POST', body: urlSearchParams, headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
        }
    }).catch(function (e) {
        // TODO Do something
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        tweet = json['tweetText'];
        document.getElementById("tweetText").innerText = tweet;
    });
};
