const taskForm = document.querySelector(".js-toDoForm"),
    taskInput = taskForm.querySelector("input"),
  pendingList = document.querySelector(".js-pendingList"),
    finishList = document.querySelector(".js-finishedList")

const PEND_LS = "pending";
const FIN_LS = "finished";

let pending = [];
let finished = [];

var ID = function () {
  return '_' + Math.random().toString(36).substr(2, 9);
}

function pendingSave() {
    localStorage.setItem(PEND_LS, JSON.stringify(pending));
}

function finishSave() {
    localStorage.setItem(FIN_LS, JSON.stringify(finished));
}

function deletePending(event) {
    const btn = event.target;
    const li = btn.parentNode;
    const ul = li.parentNode;
    ul.removeChild(li);

    const cleanList = pending.filter(element => {
        return element.id !== li.id;
    })
    pending = cleanList;
    pendingSave();
}

function paintPending(text) {
  const li = document.createElement("li");
  const delBtn = document.createElement("button");
  const finBtn = document.createElement("button");
  const span = document.createElement("span");
  const newId = ID();


    span.innerText = text;
  delBtn.innerText = '❌';
  finBtn.innerText = '🔚';
  delBtn.addEventListener("click", deletePending);
  finBtn.addEventListener("click", goToFin)
    li.appendChild(span);
  li.appendChild(delBtn);
  li.appendChild(finBtn);
    li.id = newId;
    pendingList.appendChild(li);

    const newPendingObj = {
        text: text,
        id: newId
    }
    pending.push(newPendingObj);
    pendingSave();
}

function goToFin(event) {
  deletePending(event);
  const btn = event.target;
  const li = btn.parentNode;
  const span = li.querySelector("span");
  paintFin(span.innerText);
}

function goToPending(event) {
  deleteFin(event);
  const btn = event.target;
  const li = btn.parentNode;
  const span = li.querySelector("span");
  paintPending(span.innerText); 
}

function deleteFin(event) {
    const btn = event.target;
    const li = btn.parentNode;
    const ul = li.parentNode;
    ul.removeChild(li);

    const cleanList = finished.filter(element => {
        return element.id !== li.id;
    })
    finished = cleanList;
    finishSave();
}

function paintFin(text) {
  const li = document.createElement("li");
  const delBtn = document.createElement("button");
  const pendingBtn = document.createElement("button");
  const span = document.createElement("span");
  const newId = ID();


    span.innerText = text;
  delBtn.innerText = '❌';
  pendingBtn.innerText = '🎒';
  delBtn.addEventListener("click", deleteFin);
  pendingBtn.addEventListener("click", goToPending)
    li.appendChild(span);
  li.appendChild(delBtn);
  li.appendChild(pendingBtn);
    li.id = newId;
    finishList.appendChild(li);

    const newFinishObj = {
        text: text,
        id: newId
    }
    finished.push(newFinishObj);
    finishSave();
}

function handleSubmit(event) {
    event.preventDefault();
    const currentValue = taskInput.value;
    paintPending(currentValue);
    taskInput.value = "";

}

function loadPending() {
    const loadedPending = localStorage.getItem(PEND_LS);
    if (loadedPending !== null) {
      const parsedPending = JSON.parse(loadedPending);
        parsedPending.forEach(element => {
            paintPending(element.text);
        });
    }
}

function loadFin() {
    const loadedFin = localStorage.getItem(FIN_LS);
    if (loadedFin !== null) {
      const parsedFin = JSON.parse(loadedFin);
        parsedFin.forEach(element => {
            paintFin(element.text);
        });
    } 
}

function init() {
  loadPending();
  loadFin();
    taskForm.addEventListener("submit", handleSubmit);

}

init();