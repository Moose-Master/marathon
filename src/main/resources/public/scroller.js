function start() {
    input_box = document.getElementById("f1box");
    let scrollerContent = document.getElementById('scrollerContent');
}

function scroll(mesg) {
  let newChild = scrollerContent.lastElementChild.cloneNode(true);
  newChild.innerHTML = mesg;
  scrollerContent.appendChild(newChild);
};


function sendEnter(e) {
   if((e && e.keyCode == 13) || e == 0) {
     let value = input_box.value;
     document.forms.form01.reset();
     scroll(value);
       
   }
}
window.onload = start;