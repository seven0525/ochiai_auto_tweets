window.addEventListener('DOMContentLoaded', function() {
    document.querySelector("#newTweetForm1").addEventListener('submit', (event: HTMLElementEvent<HTMLFormElement>) => newTweet(event, 'api/_tweets'));
    document.querySelector("#newTweetForm2").addEventListener('submit', (event: HTMLElementEvent<HTMLFormElement>) => newTweet(event, 'api/_tweets'));
    document.querySelector("#postTweetForm").addEventListener('submit', (event: HTMLElementEvent<HTMLFormElement>) => postTweet(event, 'api/tweets'));
}, false);


interface HTMLElementEvent<T extends HTMLElement> extends Event {
    target: T;
}

class Tweet {
    tweet: String;
}

const newTweet = (event: HTMLElementEvent<HTMLFormElement>, url: String) => {
    // loading
    document.getElementById("tweetText").innerText = 'Now Loading...';
    document.querySelector("input[name='tweetText']").setAttribute("value", "");

    event.preventDefault();

    // fetch
    const form = event.target;
    const children = form.children;
    const urlSearchParams = new URLSearchParams();
    for(let i = 0; i < children.length; i++){
        let child = children.item(i);
        if( child.tagName.toLowerCase() === 'input') {
            urlSearchParams.append(child.getAttribute("name"), child.getAttribute("value"))
        }
    }

    fetch(`${url}` ,{
        method: 'POST', body: urlSearchParams, headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
    }).catch((e: Error) => {
        alert("エラーが発生しました。もう一度試してください。");
        document.getElementById("cancelButton").click()
    }).then((response: Response) =>
        response.json()
    ).then((json: JSON) => {
        const tweet = json['tweetText'];
        document.getElementById("tweetText").innerText = tweet;
        (<HTMLButtonElement>document.getElementById("tweetButton")).disabled = false;
        document.querySelector("input[name='tweetText']").setAttribute("value", tweet)
    })
};

const postTweet = (event: HTMLElementEvent<HTMLFormElement>, url: String) => {

    (<HTMLButtonElement>document.getElementById("tweetButton")).disabled = true;
    event.preventDefault();
    // TODO loading action

    // fetch
    const form = event.target;
    const children = form.children;
    const urlSearchParams = new URLSearchParams();
    for(let i = 0; i < children.length; i++){
        let child = children.item(i);
        if( child.tagName.toLowerCase() === 'input') {
            urlSearchParams.append(child.getAttribute("name"), child.getAttribute("value"))
        }
    }
    const formData = new FormData(event.target);

    fetch(`${url}` ,{
        method: 'POST', body: urlSearchParams, headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
    }).catch((e: Error) => {
        alert("エラーが発生しました。もう一度試してください。");
        (<HTMLButtonElement>document.getElementById("tweetButton")).disabled = false;
    }).then((response: Response) =>
        response.json()
    ).then((json: JSON) => {
        document.getElementById("cancelButton").click()
        alert("ツイートが投稿されました。")
    })
};