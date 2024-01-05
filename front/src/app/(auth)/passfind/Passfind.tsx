'use client';

import styles from './PassFind.module.css';
import Link from 'next/link';
import { DateTime, Interval } from 'luxon';
import React, { useEffect, useState } from 'react';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { useRouter } from 'next/navigation';
import { useDialogStore } from '@/store/useDialog';
import { usePostCreditsEmail, usePostChangePassword } from '@/hooks/useAuth';
import BgMoon from '@/components/bgBox/Bg';

let intervalId: undefined | NodeJS.Timeout = undefined;
const CODEEXPIRED = 3;
const EXPIREDTIME = '02:59';
const PassFind = () => {
  const router = useRouter();
  const creditsEmail = usePostCreditsEmail();
  const emailCodeVerify = usePostChangePassword();
  const { open, allClose } = useDialogStore();
  const [step, setStep] = useState(1);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [ckEmail, setCkEmail] = useState('');
  const [code, setCode] = useState('');
  const [time, setTime] = useState(EXPIREDTIME);
  const [codeExpired, setCodeExpired] = useState(false);

  // prettier-ignore
  const EMAILREX = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
  // prettier-ignore !,@,#,$,%,^,&,*
  const PASSWORDREX = /[\{\}\[\]\/?.,;:|\)~`\-_+<>\\\=\(\'\"]/g;

  useEffect(() => {
    return () => {
      allClose();
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
    const end = DateTime.now().plus({ minute: CODEEXPIRED });
    intervalId = setInterval(() => {
      const now = DateTime.now();
      const remaining = end.diff(now);
      const time = remaining.toFormat(`mm:ss`);
      setTime(time);
      if (time === '00:00') {
        clearTime();
        const timeOut = setTimeout(() => {
          if (step === 1) {
            // 인증코드 확인버튼을 누르지 못 했을 경우만 해당된다.
            setCodeExpired(false);
            setCkEmail('');
            setCode('');
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

  function emailValidationCheck() {
    if (email.trim() === '') {
      open('alert', '패스워드 찾기', '이메일을 입력해주세요.');
      return false;
    }
    if (!EMAILREX.test(email)) {
      open('alert', '패스워드 찾기', '이메일 형식이 올바르지 않습니다.');
      return false;
    }

    return true;
  }

  /**
   * @function
   * 1단계 이메일 인증하기 -> 인증코드 받기
   */
  function handleIsEmailValidation() {
    if (!emailValidationCheck()) {
      return;
    }
    if (name.trim() === '') {
      open('alert', '패스워드 찾기', '이름을 입력해주세요.');
      return;
    }
    const reqData = { email, name };
    setCodeExpired(true);
    creditsEmail.mutate(reqData, {
      onSuccess: (resp: any) => {
        if (resp.respCode === '00' || resp.respCode === 'BIZ_011') {
          setCkEmail(email);
          handleTimeInterval();
          // setCodeExpired(true);
          if (resp.respCode === 'BIZ_011') {
            open('alert', '비밀번호 찾기', resp.respMsg);
          }
          return;
        }
        setCodeExpired(false);
        if (resp.respMsg !== '') {
          open('alert', '비밀번호 찾기', resp.respMsg);
          return;
        }
        open('alert', '비밀번호 찾기', '이메일 인증코드 전송 실패');
      },
      onError: (error: any) => {
        setCodeExpired(false);
        if (error.respCode !== '' && error.respMsg !== '') {
          open('alert', '비밀번호 찾기', error.respMsg);
          return;
        }
        open('alert', '비밀번호 찾기', '이메일 인증코드 전송 실패');
      },
    });
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
    if (!emailValidationCheck()) {
      return;
    }
    if (email !== ckEmail) {
      open(
        'alert',
        '패스워드 찾기',
        '인증 이메일이 동일하지 않습니다. <br /> 다시 확인해주세요.',
      );
      return;
    }
    emailCodeVerify.mutate(
      { email, code },
      {
        onSuccess: (data) => {
          if (data?.respCode === '00') {
            clearTime();
            setCodeExpired(false);
            setCkEmail('');
            setCode('');
            // 이메일 인증 성공
            setStep(2);
            return;
          }
          if (data?.respMsg) {
            open('alert', '패스워드 찾기', data?.respMsg);
            return;
          }
          open('alert', '패스워드 찾기', '인증코드가 일치하지 않습니다.');
        },
        onError: (error: any) => {
          console.log(error);
          // clearTime();
          // setCodeExpired(false);
        },
      },
    );
  }

  return (
    <main>
      <BgMoon />
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
          <button
            className={styles.btn}
            onClick={handleIsEmailValidation}
            type="button"
            disabled={codeExpired}
          >
            이메일 인증하기
          </button>
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
                  type="text"
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
        <div className={styles.complete}>
          <p className={styles.completeText}>
            임시비밀번호가 발급되었습니다. <br />
            {email}를 확인하세요.
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
