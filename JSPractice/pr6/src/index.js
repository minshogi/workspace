const
  rangeDiv = document.querySelector(".rangeGet"),
  rangeH2 = rangeDiv.querySelector("h2"),
  rangeInput = rangeDiv.querySelector("input"),
  guessForm = document.querySelector(".guessNumber"),
  guessInput = guessForm.querySelector("#guessInput"),
  playBtn = guessForm.querySelector("#guessSubmit"),
  resultDiv = document.querySelector(".result");


function getRandom() {
  const value = parseInt(rangeInput.value);
  return Math.floor(Math.random() * (value+1));
}

function rangePaint(value) {
  const txt = "Generate a number between 0 and " + value;
  rangeH2.innerText = txt;
}  

function rangeInputHandler(event) {
  const changedValue = event.target.value;
  rangePaint(changedValue);
  guessInput.max = changedValue;
}

function playBtnHandler(event) {
  event.preventDefault();
  const inputValue = guessInput.value;
  const answer = getRandom();
  const resultSpan = resultDiv.querySelector("span");
  const resultH2 = resultDiv.querySelector("h2");

  guessInput.value = "";
  resultSpan.innerText = "You choose: " + inputValue + ", the machine choose: " + answer;
  

  if (inputValue === "") {
    resultDiv.hidden = true;
  } else {
    resultDiv.hidden = false;
  }

  if (parseInt(inputValue) === parseInt(answer)) {
    resultH2.innerText = "You Win!";
  } else {
    resultH2.innerText = "You Lost!";
  }
}

function init() {
  rangePaint(rangeInput.value);
  rangeInput.addEventListener("input", rangeInputHandler);
  guessInput.addEventListener("submit", playBtnHandler);
  playBtn.addEventListener("click", playBtnHandler);
}

init();