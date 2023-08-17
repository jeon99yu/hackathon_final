const usernameInput = document.getElementById("id");
const passwordInput = document.getElementById("password");
const showHideToggle = document.querySelector(".show-hide");
const loginButton = document.querySelector(".login_button");

showHideToggle.addEventListener("click", () => {
  if (passwordInput.type === "password") {
    passwordInput.type = "text";
    showHideToggle.innerHTML = '<i class="fa fa-eye-slash"></i>';
  } else {
    passwordInput.type = "password";
    showHideToggle.innerHTML = '<i class="fa fa-eye"></i>';
  }
});

function isValidPassword(password) {
  return password.length >= 6 && password.length <= 16;
}

function updateLoginButtonState() {
  const username = usernameInput.value.trim();
  const password = passwordInput.value.trim();

  if (username !== "" && password !== "" && isValidPassword(password)) {
    loginButton.classList.add("active");
  } else {
    loginButton.classList.remove("active");
  }
}

function login() {
  const username = usernameInput.value.trim();
  const password = passwordInput.value.trim();

  if (username !== "" && isValidPassword(password)) {
    window.location.href = "page.html";
  } else {
    if (!isValidPassword(password)) {
      alert("비밀번호는 6자 이상, 16자 이하로 입력해주세요.");
    } else {
      alert("아이디와 비밀번호를 입력해주세요.");
    }
  }
}

function signup() {
  window.location.href = "signup.html";
}

usernameInput.addEventListener("input", updateLoginButtonState);
passwordInput.addEventListener("input", updateLoginButtonState);
