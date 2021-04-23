// <⚠️ DONT DELETE THIS ⚠️>
import "./styles.css";
const colors = ["#1abc9c", "#3498db", "#9b59b6", "#f39c12", "#e74c3c"];
// <⚠️ /DONT DELETE THIS ⚠️>

/*
✅ The text of the title should change when the mouse is on top of it.
✅ The text of the title should change when the mouse is leaves it.
✅ When the window is resized the title should change.
✅ On right click the title should also change.
✅ The colors of the title should come from a color from the colors array.
✅ DO NOT CHANGE .css, or .html files.
✅ ALL function handlers should be INSIDE of "superEventHandler"
*/
const target = document.getElementsByTagName('h2')[0];

const superEventHandler = {

    hover: function () {
        target.innerHTML= "The mouse is here!"
        target.style.color = colors[0];
    }, 

    leave: function () {
        target.innerHTML= "The mouse is gone!"
        target.style.color = colors[1];
        
    },

    resize: function () {
        target.innerHTML= "You just resized!"
        target.style.color = colors[2];
        
    },

    rightClick: function () {
        target.innerHTML= "That was a right click!"
        target.style.color = colors[3];
    }

};

target.addEventListener('mouseenter', superEventHandler.hover);
target.addEventListener('mouseleave', superEventHandler.leave);
window.addEventListener('resize', superEventHandler.resize);
window.addEventListener('contextmenu', superEventHandler.rightClick);
