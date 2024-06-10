'use client';

import styles from './Login.module.css';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { useDialogStore } from '@/store/useDialog';
import React, { useEffect, useState } from 'react';
import { usePostLogin } from '@/hooks/useAuth';
import { Inter } from 'next/font/google';
import {EMAIL_LENGTH, EMAIL_REX, PASSWORD_REX} from '@/utils/constants'

const inter = Inter({ subsets: ['latin'] });

const Login = () => {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [emailValidation, setEmailValidation] = useState(false);
  const [password, setPassword] = useState('');
  const [passwordValidation, setPasswordValidation] = useState(false);
  const { open, allClose } = useDialogStore();
  const postLogin = usePostLogin();


  /**
   * @function
   * mount / destroyed
   */
  useEffect(() => {
    return () => {
      allClose();
    };
  }, []);

  /**************************************************************************************************************/
  /*************************************************** method ***************************************************/
  /**************************************************************************************************************/
  /**
   * @function
   * 이메일 입력시
   */
  const handleEmailChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ): void => {
    const { value } = event.currentTarget;
    if (value.length > EMAIL_LENGTH) {
      return;
    }

    setEmail(event.currentTarget.value);
  };
  /**
   * @function
   * 이메일 정규식 체크
   */
  useEffect((): void => {
    if (email.trim() !== '' && !EMAIL_REX.test(email)) {
      // 이메일 데이터가 있고 정규식에 맞지 않을 경우
      setEmailValidation(true);
      return;
    }
    setEmailValidation(false);
  }, [email]);

  /**
   * @function
   * 비밀번호 onChange
   */
  const handlePasswordChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ): void => {
    const { value } = event.currentTarget;
    setPassword(value);
  };

  /**
   * @function
   * 비밀번호 정규식 체크
   */
  useEffect(() => {
    if (password.trim() !== '' && PASSWORD_REX.test(password)) {
      setPasswordValidation(true);
      return;
    }
    setPasswordValidation(false);
  }, [password]);

  /**
   * @function
   * 로그인하기 버튼
   */
  const handlerLoginBtn = (): void => {
    if (email.trim() === '') {
      open('alert', '로그인', '이메일을 입력해주세요.', () => {
        // alert('액션!');
      });
      return;
    }
    if (password.trim() === '') {
      open('alert', '로그인', '비밀번호를 입력해주세요.');
      return;
    }
    if (emailValidation) {
      open('alert', '로그인', '이메일 형식이 올바르지 않습니다');
      return;
    }
    if (passwordValidation) {
      open(
        'alert',
        '로그인',
        '비밀번호는 특수문자는 !,@,#,$,%,^,&,*만 사용 가능합니다.',
      );
      return;
    }
    // 로그인 완료 홈으로 이동
    postLogin.mutate(
      { email, password },
      {
        onSuccess: handlerLoginSuccess,
        onError: (error: any) => {
          // 요청에 에러가 발생된 경우
          if (error.respCode !== '') {
            open('alert', '로그인', error.respMsg);
          }
          console.log('error.', error);
        },
        onSettled: () => {
          // 요청이 성공하든, 에러가 발생되든 실행하고 싶은 경우
          console.log('onSettled');
        },
      },
    );
  };

  const handlerLoginSuccess = (data: any) => {
    if (data.respCode === '00') {
      // 요청이 성공한 경우
      router.push('/');
    }
  };

  return (
    <main className={styles.logWrap}>
      <div className={styles.logContainer}>
        {/* 회원가입 영역 */}
        <section className={styles.mainJoinWrap}>
          <p className={`${styles.pText} ${inter.className}`}>
            Welcome DAGACHI
          </p>
          <Link
            className={styles.signUp}
            href={{
              pathname: '/join',
            }}
          >
            Oner Sign Up
          </Link>
        </section>
        {/* //회원가입 영역 */}

        {/* 로그인 영역 */}
        <section className={styles.mainlogWrap}>
          <h1 className={styles.titleText}>LOGIN</h1>
          <input
            className={`${styles.input} ${emailValidation ? styles.inputFail : ''}`}
            type="email"
            placeholder="E-MAIL"
            value={email}
            onChange={handleEmailChange}
          />
          {emailValidation && (
            <p className={styles.errText}>이메일 형식이 올바르지 않습니다.</p>
          )}
          <input
            className={`${styles.input} ${
              passwordValidation ? styles.inputFail : ''
            }`}
            type="password"
            placeholder="PASSWORD"
            value={password}
            onChange={handlePasswordChange}
          />
          {passwordValidation && (
            <p className={styles.errText}>
              비밀번호는 특수문자는 !,@,#,$,%,^,&,*만 사용 가능합니다.
            </p>
          )}
          <div className={styles.signInGrp}>
            <Link
              className={styles.passwordFind}
              href={{
                pathname: '/passfind',
              }}
            >
              비밀번호 찾기
            </Link>
            <button
              className={styles.loginBtn}
              onClick={handlerLoginBtn}
            >
              Sign In
            </button>
          </div>
        </section>
        {/* //로그인 영역 */}
      </div>
    </main>
  );
};
export default Login;
