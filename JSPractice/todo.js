const toDoForm = document.querySelector(".js-toDoForm"),
    toDoInput = toDoForm.querySelector("input"),
    toDoList = document.querySelector(".js-toDoList");

const TODO_LS = "toDos";

let toDos = [];

function saveToDos() {
    localStorage.setItem(TODO_LS, JSON.stringify(toDos));
}

function deleteToDo(event) {
    const btn = event.target;
    const li = btn.parentNode;
    toDoList.removeChild(li);
    const cleanToDos = toDos.filter(element => {
        return element.id !== parseInt(li.id);
    })
    toDos = cleanToDos;
    saveToDos();
}

function paintToDo(text) {
    const li = document.createElement("li");
    const delBtn = document.createElement("button");
    const span = document.createElement("span");
    const newId = toDos.length + 1;

    span.innerText = text;
    delBtn.innerText = '❌';
    delBtn.addEventListener("click", deleteToDo);
    li.appendChild(span);
    li.appendChild(delBtn);
    li.id = newId;
    toDoList.appendChild(li);

    const newToDoObj = {
        text: text,
        id: newId
    }
    toDos.push(newToDoObj);
    saveToDos();
}

function handleSubmit(event) {
    event.preventDefault();
    const currentValue = toDoInput.value;
    paintToDo(currentValue);
    toDoInput.value = "";

}

function loadToDos() {
    const loadedToDos = localStorage.getItem(TODO_LS);
    if (loadedToDos !== null) {
        const parsedToDos = JSON.parse(loadedToDos);
        parsedToDos.forEach(element => {
            paintToDo(element.text);
        });

    }
}

function init() {
    loadToDos();
    toDoForm.addEventListener("submit", handleSubmit);

}

init();