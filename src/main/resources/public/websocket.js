let uname = "PlaceholderPerson"
let socket = null;
let input_box = null;

function start() {
    console.log("Hello, world!");
    input_box = document.getElementById("msg");

    let scrollerContent = document.getElementById('scrollerContent');

    let c = document.cookie

    if(c.substring(0,c.indexOf('=')-1) == "username"){
    uname = c.substring(c.indexOf('=')+1)
    }
    console.log(`User is: ${uname}`)


    const PATH = `/ws`;
    console.log(PATH);
    socket = new WebSocket(PATH);
    socket.addEventListener("message", (event) => {
        let msg = event.data;
        console.log(`Message: ${msg}`);
        scroll(msg)
    });
    socket.addEventListener("close", (event) => {
        console.log("Disconnected");
    })
    socket.addEventListener("error", (event) => {
        console.log(`Error: ${event}`);
    });
}

function sendEnter(e) {
   if((e && e.keyCode == 13) || e == 0) {
     let value = input_box.value;
     document.forms.form01.reset();
     sendMessage();
       
   }
   
   function scroll(mesg) {
  let newChild = scrollerContent.lastElementChild.cloneNode(true);
  newChild.innerHTML = mesg
  scrollerContent.appendChild(newChild)
};
}

function sendMessage() {
    if (socket.readyState == WebSocket.OPEN) {
        let value = (uname + ": " + input_box.value)
        socket.send(value);
        console.log(`Sent message ${value}`);
    } else {
        console.log(socket.readyState);
    }
}

console.log("Sanity check 2");
window.onload = start;