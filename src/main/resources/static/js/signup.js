const idInput = document.getElementById("email");
const pwInput = document.getElementById("pw");
const pwConfirmInput = document.getElementById("pw-confirm");
const nameInput = document.getElementById("name");
const nicknameInput = document.getElementById("nickname");
const signupButton = document.querySelector(".signupBTN");

handleInput();

function signup() {
  if (
    idInput.value.trim() !== "" &&
    pwInput.value.trim().length >= 6 &&
    pwInput.value.trim().length <= 16 &&
    pwConfirmInput.value === pwInput.value &&
    nameInput.value.trim() !== "" &&
    nicknameInput.value.trim().length <= 10
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
    if (nicknameInput.value.trim().length > 10) {
      alert("닉네임을 10자 이하로 입력하세요.");
    } else if (
      pwInput.value.trim().length < 6 ||
      pwInput.value.trim().length > 16
    ) {
      alert("비밀번호는 6자 이상, 16자 이하로 입력해주세요.");
    } else if (pwInput.value !== pwConfirmInput.value) {
      alert("비밀번호가 일치하지 않습니다.");
    } else {
      alert("모든 정보를 올바르게 입력해주세요.");
    }
  }
}

idInput.addEventListener("input", handleInput);
pwInput.addEventListener("input", handleInput);
pwConfirmInput.addEventListener("input", handleInput);
nameInput.addEventListener("input", handleInput);
nicknameInput.addEventListener("input", handleInput);

function handleInput() {
  if (
    idInput.value.trim() !== "" &&
    pwInput.value.trim().length >= 6 &&
    pwInput.value.trim().length <= 16 &&
    pwConfirmInput.value === pwInput.value &&
    nameInput.value.trim() !== "" &&
    nicknameInput.value.trim().length <= 10
  ) {
    signupButton.classList.add("active");
  } else {
    signupButton.classList.remove("active");
  }
}

function goToMain() {
  window.location.href = "login.html";
}

$(document).ready(function () {
  $(".main i").on("click", function () {
    $("input").toggleClass("active");
    if ($("input").hasClass("active")) {
      $(this)
        .attr("class", "fa fa-eye-slash fa-lg")
        .prev("input")
        .attr("type", "text");
    } else {
      $(this)
        .attr("class", "fa fa-eye fa-lg")
        .prev("input")
        .attr("type", "password");
    }
  });
});
