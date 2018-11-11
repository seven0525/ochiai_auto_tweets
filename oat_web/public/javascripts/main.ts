window.addEventListener('DOMContentLoaded', function() {
    document.querySelector("#newTweetForm1").addEventListener('submit', (event: HTMLElementEvent<HTMLFormElement>) => newTweet(event, 'api/_tweets'));
//    document.querySelector("#newTweetForm2").addEventListener('submit', (event: HTMLElementEvent<HTMLFormElement>) => newTweet(event, '/_tweets'));
}, false);


interface HTMLElementEvent<T extends HTMLElement> extends Event {
    target: T;
}

class Tweet {
    tweet: String;
}

const newTweet = (event: HTMLElementEvent<HTMLFormElement>, url: String) => {
    event.preventDefault();
    // TODO modal display

    // TODO loading action

    // fetch
    const form = event.target
    const children = form.children;
    const urlSearchParams = new URLSearchParams();
    for(let i = 0; i < children.length; i++){
        let child = children.item(i)
        if( child.tagName.toLowerCase() === 'input') {
            urlSearchParams.append(child.getAttribute("name"), child.getAttribute("value"))
        }
    }
    const formData = new FormData(event.target)
    let tweet = null

    fetch(`${url}` ,{
        method: 'POST', body: urlSearchParams, headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
    }).catch((e: Error) => {
        // TODO Do something
    }).then((response: Response) =>
        response.json()
    ).then((json: JSON) => {
        tweet = json['tweetText']
        document.getElementById("tweetText").innerText = tweet
    })
}