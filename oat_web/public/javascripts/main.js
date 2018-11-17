window.addEventListener('DOMContentLoaded', function () {
    document.querySelector("#newTweetForm1").addEventListener('submit', function (event) { return newTweet(event, 'api/_tweets'); });
    document.querySelector("#newTweetForm2").addEventListener('submit', function (event) { return newTweet(event, 'api/_tweets'); });
    document.querySelector("#postTweetForm").addEventListener('submit', function (event) { return postTweet(event, 'api/tweets'); });
}, false);
var Tweet = /** @class */ (function () {
    function Tweet() {
    }
    return Tweet;
}());
var newTweet = function (event, url) {
    // loading
    document.getElementById("tweetText").innerText = 'Now Loading...';
    document.querySelector("input[name='tweetText']").setAttribute("value", "");
    event.preventDefault();
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
    fetch("" + url, {
        method: 'POST', body: urlSearchParams, headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
        }
    }).catch(function (e) {
        alert("エラーが発生しました。もう一度試してください。");
        document.getElementById("cancelButton").click();
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        var tweet = json['tweetText'];
        document.getElementById("tweetText").innerText = tweet;
        document.getElementById("tweetButton").disabled = false;
        document.querySelector("input[name='tweetText']").setAttribute("value", tweet);
    });
};
var postTweet = function (event, url) {
    document.getElementById("tweetButton").disabled = true;
    event.preventDefault();
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
    fetch("" + url, {
        method: 'POST', body: urlSearchParams, headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
        }
    }).catch(function (e) {
        alert("エラーが発生しました。もう一度試してください。");
        document.getElementById("tweetButton").disabled = false;
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        document.getElementById("cancelButton").click();
        alert("ツイートが投稿されました。");
    });
};
