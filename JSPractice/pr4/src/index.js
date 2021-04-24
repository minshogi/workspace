const selector = document.querySelector(".js-select");

const USER_NT = "currentNation"

function saveNation(text) {
  localStorage.setItem(USER_NT, text);
 }

 function handleSelect(event) {
   event.preventDefault();
   const currentValue = selector.value;
   saveNation(currentValue);
 }

function selectWaiter() {
  selector.addEventListener('change', handleSelect);
 }

function loadNation() {
  const currentNation = localStorage.getItem(USER_NT);
  if (currentNation !== null) {
    selector.value = currentNation;
  }
}

function init() {
  selectWaiter();
  loadNation();
}

init();