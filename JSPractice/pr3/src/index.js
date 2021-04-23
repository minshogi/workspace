//import "./styles.css";

const timeContainer = document.querySelector(".TimeUntilChristmas");

// You're gonna need this
function getTime() {
  // Don't delete this.
  const xmasDay = new Date(2021, 11, 24, 0, 0);
  const today = new Date();
  const millisec = xmasDay.getTime() - today.getTime();
  const sec = Math.floor((millisec / 1000)) % 60,
    min = Math.floor((millisec / 1000 / 60)) % 60,
    hour =Math.floor((millisec / 1000 / 60 / 60)) % 24,
    day = Math.floor((millisec / 1000 / 60 / 60 / 24));
  
  timeContainer.innerHTML =
    `${day < 10 ? `0${day}` : day}d ${hour < 10 ? `0${ hour } ` : hour}m ${min < 10 ? `0${min}` : min}m ${sec < 10 ? `0${sec}` : sec}s`;

}


function init() {
  getTime();
  setInterval(getTime, 1000);
}
init();
