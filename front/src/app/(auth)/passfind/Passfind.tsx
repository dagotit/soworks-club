'use client';
import styles from './PassFind.module.css';
import { useState } from 'react';

const PassFind = () => {
  const [step, setStep] = useState(1);

  /**
   * @function
   * 1단계 이메일 체크
   */
  function handleEmailValidationCheck() {
    setStep(2);
  }
  return (
    <main>
      <h1 className={styles.title}>패스워드 찾기</h1>
      {step === 1 && (
        <div className={styles.contents}>
          <label className={styles.inputWrap}>
            <span className={styles.inputText}>이메일</span>
            <input
              className={styles.input}
              type="email"
              placeholder="회사 e-mail을 입력해주세요."
            />
          </label>
          <label className={styles.inputWrap}>
            <span className={styles.inputText}>이름</span>
            <input className={styles.input} type="text" placeholder="실명" />
          </label>
          <button
            className={styles.btn}
            onClick={handleEmailValidationCheck}
            type="button"
          >
            찾기
          </button>
        </div>
      )}
      {step === 2 && (
        <div className={styles.contents}>
          <label className={styles.inputWrap}>
            <span className={styles.inputText}>
              new
              <br /> 패스워드
            </span>
            <input
              className={styles.input}
              type="password"
              placeholder="password를 입력해주세요."
            />
          </label>
        </div>
      )}
      {step === 3 && <div>비밀번호 변경완료</div>}
    </main>
  );
};
export default PassFind;
