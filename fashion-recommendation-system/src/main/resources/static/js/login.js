document.addEventListener('DOMContentLoaded', () => {
  const loginForm = document.getElementById('login-form');
  const emailInput = document.getElementById('email');
  const passwordInput = document.getElementById('password');
  const submitButton = loginForm.querySelector('button[type="submit"]');
  const errorMessage = document.getElementById('error-message');

  // 로그인 버튼 활성화/비활성화 로직
  function updateSubmitButton() {
    const isValid = emailInput.value.trim() !== '' &&
        passwordInput.value.trim() !== '' &&
        emailInput.validity.valid;
    submitButton.disabled = !isValid;
  }

  // 입력 필드 변경 시 버튼 상태 업데이트
  emailInput.addEventListener('input', updateSubmitButton);
  passwordInput.addEventListener('input', updateSubmitButton);

  // 초기 버튼 상태 설정
  updateSubmitButton();

  // 로그인 폼 제출 이벤트 처리
  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault(); // 기본 동작 방지

    // 입력 값 가져오기
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();

    // 오류 메시지 초기화
    errorMessage.textContent = '';

    try {
      // 버튼 비활성화 및 로딩 상태 설정
      submitButton.textContent = '로그인 중...';
      submitButton.disabled = true;

      // 실제 API 요청 (fetch 사용)
      const response = await fetch('/auth/sign-in', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
      });

      // 서버 응답 처리
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || '로그인에 실패했습니다.');
      }

      const responseData = await response.json();
      console.log('로그인 성공:', responseData);

      // 로그인 성공 후 페이지 이동
      window.location.href = '/index'; // 메인 페이지로 리디렉션

    } catch (error) {
      console.error('로그인 실패:', error);
      errorMessage.textContent = error.message || '로그인에 실패했습니다. 다시 시도해주세요.';
    } finally {
      // 버튼 복구
      submitButton.textContent = '로그인';
      submitButton.disabled = false;
    }
  });
});
