// <⚠️ DONT DELETE THIS ⚠️>
//import "./styles.css";
// <⚠️ /DONT DELETE THIS ⚠️>

const colors = ["#FF0400", "#FEFF00", "#00FFE3", "#0100FF", "#FF00EC", "#DC00FF"];

let backGround = document.getElementsByTagName("body")[0];

function widthWatcher() {
    let width = window.visualViewport.width;
    let seloctor = 0;
    console.log(width);
    if (width < 100) {
        seloctor = 1;
    }
    else if (width < 200) {
        seloctor = 2;
    } else if (width < 300) {
        seloctor = 3;
    } else if (width < 400) {
        seloctor = 4;
    } else {
        seloctor = 5;
    }
    backGround.style.backgroundColor = colors[seloctor];
}

window.addEventListener("resize", widthWatcher);
