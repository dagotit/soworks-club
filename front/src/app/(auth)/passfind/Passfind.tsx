'use client';

import styles from './PassFind.module.css';
import Link from 'next/link';
import { DateTime, Interval } from 'luxon';
import React, { useEffect, useState } from 'react';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { useRouter } from 'next/navigation';
import { useDialogStore } from '@/store/useDialog';

let intervalId: undefined | NodeJS.Timeout = undefined;
const EXPIREDTIME = '04:59';
const PassFind = () => {
  const router = useRouter();
  const { open, allClose } = useDialogStore();
  const [step, setStep] = useState(1);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [isEmailCk, setIsEmailCk] = useState(false);
  const [code, setCode] = useState('');
  const [time, setTime] = useState(EXPIREDTIME);
  const [codeExpired, setCodeExpired] = useState(false);
  const [password, setPassword] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [isPasswordErrorMsg, setIsPasswordErrorMsg] = useState(false);

  const EMAILREX =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  // !,@,#,$,%,^,&,*
  const PASSWORDREX = /[\{\}\[\]\/?.,;:|\)~`\-_+<>\\\=\(\'\"]/g;

  useEffect(() => {
    return () => {
      clearTime();
    };
  }, []);

  /**
   **************************      **************************************************
   ************************** 1단계 **************************************************
   **************************      **************************************************
   * */
  /**
   * @function
   * 1단계 이름 입력
   */
  function handleChangeName(event: React.ChangeEvent<HTMLInputElement>) {
    const { value } = event.currentTarget;
    setName(value);
  }
  /**
   * @function
   * 1단계 이메일 입력
   */
  function handleChangeEmail(event: React.ChangeEvent<HTMLInputElement>) {
    const { value } = event.currentTarget;
    setEmail(value);
  }
  function handleChangeCode(event: React.ChangeEvent<HTMLInputElement>) {
    const { value } = event.currentTarget;
    setCode(value);
  }
  /**
   * @function
   * 인증시간 카운트
   */
  function handleTimeInterval() {
    if (intervalId !== undefined) {
      return;
    }
    setTime(EXPIREDTIME);
    const end = DateTime.now().plus({ minute: 5 });
    intervalId = setInterval(() => {
      const now = DateTime.now();
      const remaining = end.diff(now);
      const time = remaining.toFormat(`mm:ss`);
      setTime(time);
      if (time === '00:00') {
        clearTime();
        const timeOut = setTimeout(() => {
          if (!isEmailCk) {
            // 인증코드 확인버튼을 누르지 못 했을 경우만 해당된다.
            setCodeExpired(false);
          }
          clearTimeout(timeOut);
        }, 2000);
      }
    }, 1000);
  }
  /**
   * @function
   * 카운트 interval 클리어
   */
  function clearTime() {
    clearInterval(intervalId);
    intervalId = undefined;
  }

  /**
   * @function
   * 1단계 이메일 인증하기 -> 인증코드 받기
   */
  function handleIsEmailValidation() {
    if (email.trim() === '') {
      open('alert', '패스워드 찾기', '이메일을 입력해주세요.');
      return;
    }
    if (!EMAILREX.test(email)) {
      open('alert', '패스워드 찾기', '이메일 형식이 올바르지 않습니다.');
      return;
    }
    handleTimeInterval();
    setCodeExpired(true);
  }
  /**
   *
   * 1단계 이메일 인증코드 맞는지 확인하기
   */
  function handleCodeCheck() {
    if (code.trim() === '') {
      open('alert', '패스워드 찾기', '인증코드를 입력해주세요.');
      return;
    }
    clearTime();
    setCodeExpired(false);
    // 이메일 인증 성공
    setIsEmailCk(true);
  }
  /**
   * @function
   * 1단계 이메일 체크
   */
  function handleEmailValidationCheck() {
    if (email.trim() === '') {
      open('alert', '패스워드 찾기', '이메일을 입력해주세요.');
      return;
    }
    if (name.trim() === '') {
      open('alert', '패스워드 찾기', '이름을 입력해주세요.');
      return;
    }
    if (!EMAILREX.test(email)) {
      open('alert', '패스워드 찾기', '이메일 형식이 올바르지 않습니다.');
      return;
    }
    setStep(2);
  }

  /**
   **************************      **************************************************
   ************************** 2단계 **************************************************
   **************************      **************************************************
   * */

  /**
   * @function
   * 2단계 새로운 비밀번호와 비밀번호 확인 비교
   */
  useDidMountEffect(() => {
    console.log(password, passwordCheck);
    if (password.trim() === '' || passwordCheck.trim() === '') {
      setIsPasswordErrorMsg(false);
      return;
    }
    if (password !== passwordCheck) {
      setIsPasswordErrorMsg(true);
      return;
    }
    setIsPasswordErrorMsg(false);
  }, [password, passwordCheck]);
  /**
   * @function
   * 2단계 새로운 비밀번호 입력
   */
  function handleChangeNewPassword(event: React.ChangeEvent<HTMLInputElement>) {
    const { value } = event.currentTarget;
    setPassword(value);
  }
  /**
   * @function
   * 2단계 비밀번호 확인용 입력
   */
  function handleChangeCheckPassword(
    event: React.ChangeEvent<HTMLInputElement>,
  ) {
    const { value } = event.currentTarget;
    setPasswordCheck(value);
  }
  /**
   * @function
   * 2단계 비밀번호 변경시도하기
   */
  function handleChangePassword() {
    if (password.trim() === '') {
      open('alert', '패스워드 찾기', '패스워드를 입력해주세요.');
      return;
    }
    if (passwordCheck.trim() === '') {
      open('alert', '패스워드 찾기', '패스워드를 확인해주세요.');
      return;
    }
    if (PASSWORDREX.test(password)) {
      open(
        'alert',
        '패스워드 찾기',
        '패스워드는 특수문자는 !,@,#,$,%,^,&,*만 사용 가능합니다.',
      );
      return;
    }
    if (isPasswordErrorMsg) {
      open('alert', '패스워드 찾기', '패스워드 확인이 일치하지 않습니다.');
      return;
    }
    setStep(3);
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
              value={email}
              onChange={handleChangeEmail}
            />
          </label>
          <label className={styles.inputWrap}>
            <span className={styles.inputText}>이름</span>
            <input
              className={styles.input}
              type="text"
              placeholder="실명"
              value={name}
              onChange={handleChangeName}
            />
          </label>
          {!isEmailCk && (
            <button
              className={styles.btn}
              onClick={handleIsEmailValidation}
              type="button"
              disabled={codeExpired}
            >
              이메일 인증하기
            </button>
          )}
          {isEmailCk && (
            <button
              className={styles.btn}
              onClick={handleEmailValidationCheck}
              type="button"
            >
              찾기
            </button>
          )}

          {codeExpired && (
            <div className={styles.codeWrap}>
              <label className={`${styles.inputWrap} ${styles.code}`}>
                <span className={styles.inputText}>
                  인증
                  <br />
                  코드
                </span>
                <input
                  className={styles.input}
                  type="number"
                  placeholder="인증코드 입력하기"
                  value={code}
                  onChange={handleChangeCode}
                />
              </label>
              <button
                className={`${styles.btn} ${styles.code}`}
                type="button"
                onClick={handleCodeCheck}
              >
                확인
              </button>
            </div>
          )}
          {codeExpired && <p className={styles.time}>{time}</p>}
        </div>
      )}
      {step === 2 && (
        <div className={styles.contents}>
          <label className={styles.inputWrap}>
            <span className={styles.inputText}>패스워드</span>
            <input
              className={styles.input}
              type="password"
              placeholder="password를 입력해주세요."
              value={password}
              onChange={handleChangeNewPassword}
            />
          </label>
          <label className={styles.inputWrap}>
            <span className={styles.inputText}>
              패스워드 <br />
              확인
            </span>
            <input
              className={styles.input}
              type="password"
              placeholder="password를 입력해주세요."
              value={passwordCheck}
              onChange={handleChangeCheckPassword}
            />
          </label>
          <button
            className={styles.btn}
            onClick={handleChangePassword}
            type="button"
          >
            변경
          </button>
          {isPasswordErrorMsg && (
            <p className={styles.errorText}>
              새로운 패스워드와 패스워드 확인란이 동일하지 않습니다.
              <br />
              다시한번 확인해주세요.
            </p>
          )}
        </div>
      )}
      {step === 3 && (
        <div className={styles.complete}>
          <p className={styles.completeText}>
            패스워드가 성공적으로 변경되었습니다.
          </p>
          <Link
            className={styles.linkBtn}
            href={{
              pathname: '/login',
            }}
          >
            로그인하러 가기
          </Link>
        </div>
      )}
    </main>
  );
};
export default PassFind;
