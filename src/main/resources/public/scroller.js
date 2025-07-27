let uname = "PlaceholderPerson"
function start() {
    input_box = document.getElementById("f1box");
    let scrollerContent = document.getElementById('scrollerContent');

    
    let c = document.cookie
    uname = c.substring(c.indexOf('=')+1)
    console.log(uname)
}

function scroll(mesg) {
  let newChild = scrollerContent.lastElementChild.cloneNode(true);
  newChild.innerHTML = (uname + ": " + mesg)
  scrollerContent.appendChild(newChild)
};


function sendEnter(e) {
   if((e && e.keyCode == 13) || e == 0) {
     let value = input_box.value;
     document.forms.form01.reset();
     scroll(value);
       
   }
}
window.onload = start;