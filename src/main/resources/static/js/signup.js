const idInput = document.getElementById("email");
const pwInput = document.getElementById("pw");
const pwConfirmInput = document.getElementById("pw-confirm");
const nameInput = document.getElementById("name");
const nicknameInput = document.getElementById("nickname");
const signupButton = document.querySelector(".signupBTN");
const passwordToggles = document.querySelectorAll(".show-hide");

function signup() {
  const isValidNickname = nicknameInput.value.trim().length <= 10;
  const isValidPassword =
    pwInput.value.trim().length >= 6 && pwInput.value.trim().length <= 16;

  if (
    idInput.value.trim() !== "" &&
    pwInput.value.trim() === pwConfirmInput.value &&
    nameInput.value.trim() !== "" &&
    isValidNickname &&
    isValidPassword
  ) {
    alert("회원가입 성공!");

    const storedData = {
      id: idInput.value.trim(),
      pw: pwInput.value.trim(),
      name: nameInput.value.trim(),
      nickname: nicknameInput.value.trim(),
    };
    localStorage.setItem("userData", JSON.stringify(storedData));

    window.location.href = "login.html";
  } else {
    if (!isValidNickname) {
      alert("닉네임을 10자 이하로 입력하세요.");
    } else if (!isValidPassword) {
      alert("비밀번호는 6자 이상, 16자 이하로 입력해주세요.");
    } else if (pwInput.value !== pwConfirmInput.value) {
      alert("비밀번호가 일치하지 않습니다.");
    } else {
      alert("모든 정보를 올바르게 입력해주세요.");
    }
  }
}

function goToMain() {
  window.location.href = "login.html";
}

function handleInput() {
  const isValidNickname = nicknameInput.value.trim().length <= 10;
  const isValidPassword =
    pwInput.value.trim().length >= 6 && pwInput.value.trim().length <= 16;

  if (
    idInput.value.trim() !== "" &&
    pwInput.value.trim() === pwConfirmInput.value &&
    nameInput.value.trim() !== "" &&
    isValidNickname &&
    isValidPassword
  ) {
    signupButton.classList.add("active");
  } else {
    signupButton.classList.remove("active");
  }
}

idInput.addEventListener("input", handleInput);
pwInput.addEventListener("input", handleInput);
pwConfirmInput.addEventListener("input", handleInput);
nameInput.addEventListener("input", handleInput);
nicknameInput.addEventListener("input", handleInput);

passwordToggles.forEach((toggle) => {
  toggle.addEventListener("click", () => {
    const input = toggle.previousElementSibling;
    if (input.type === "password") {
      input.type = "text";
      toggle.innerHTML = '<i class="fa fa-eye-slash"></i>';
    } else {
      input.type = "password";
      toggle.innerHTML = '<i class="fa fa-eye"></i>';
    }
  });
});
