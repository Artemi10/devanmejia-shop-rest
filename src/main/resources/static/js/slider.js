let array = ["../images/slider0.jpg","../images/slider1.jpg", "../images/slider2.jpg"];
let pointer=0;
setInterval(() => {
    let element = document.getElementById("imageSlider");
    pointer++;
    pointer=checkedPointer(pointer);
    element.src = "images/slider"+pointer+".jpg";
}, 4000);
let elementRight = document.getElementById("right");
let elementLeft = document.getElementById("left");
elementRight.onclick = ()=>{
  pointer++;
  let image = document.getElementById("imageSlider");
  pointer = checkedPointer(pointer);
  image.src = array[pointer];
};
elementLeft.onclick =()=>{
  pointer--;
  let image = document.getElementById("imageSlider");
  pointer = checkedPointer(pointer);
  image.src = array[pointer];
};
function checkedPointer(counter) {
    if(counter>=array.length) counter=0;
    if(counter<0) counter=array.length-1;
    return counter;
}