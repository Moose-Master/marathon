let socket = null;

function start() {
    console.log("Hello, world!");

    const PATH = `/ws`;
    console.log(PATH);
    socket = new WebSocket(PATH);
    socket.addEventListener("message", (event) => {
        let msg = event.data;
        console.log(`Message: ${msg}`);
        document.getElementById("wsout").innerText += msg + "\n";
    });
}
function updateWebsocket() {
    if(socket.readyState == WebSocket.CLOSED) {
        clearInterval(t);
        console.log("Disconnected");
    } else if (socket.readyState == WebSocket.OPEN) {
        socket.send(document.getElementById("msg").value);
    } else {
        console.log(socket.readyState);
    }
}
let t = setInterval(updateWebsocket, 1000);
start()