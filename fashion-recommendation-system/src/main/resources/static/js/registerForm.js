document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("registerForm");
  const password = document.getElementById("password");
  const passwordConfirm = document.getElementById("passwordConfirm");
  const passwordError = document.getElementById("passwordError");

  form.addEventListener("submit", function (event) {
    if (password.value !== passwordConfirm.value) {
      event.preventDefault();
      passwordError.textContent = "비밀번호가 일치하지 않습니다.";
    } else {
      passwordError.textContent = "";
    }
  });
});
