function login() {
  const username = document.getElementById("id").value;
  const password = document.getElementById("password").value;

  if (
    username.trim() !== "" &&
    password.trim().length >= 6 &&
    password.trim().length <= 16
  ) {
    window.location.href = "page.html";
  } else {
    if (password.trim().length < 6 || password.trim().length > 16) {
      alert("비밀번호는 6자 이상, 16자 이하로 입력해주세요.");
    } else {
      alert("아이디와 비밀번호를 입력해주세요.");
    }
  }
}

function signup() {
  window.location.href = "signup.html";
}

const usernameInput = document.getElementById("id");
const passwordInput = document.getElementById("password");

const loginButton = document.querySelector(".login_button");

function handleInput() {
  if (usernameInput.value.trim() !== "" && passwordInput.value.trim() !== "") {
    loginButton.classList.add("active");
  } else {
    loginButton.classList.remove("active");
  }
}

usernameInput.addEventListener("input", handleInput);
passwordInput.addEventListener("input", handleInput);
