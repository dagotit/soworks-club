'use client';

import styles from './Login.module.css';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { useDialogStore } from '@/store/useDialog';
import React, { useEffect, useState } from 'react';
import { useGetLogoImg, usePostLogin } from '@/hooks/useAuth';
import { APIResponse } from '@/services/api';
import { useTokenStore } from '@/store/useLogin';

const Login = () => {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [emailValidation, setEmailValidation] = useState(false);
  const [password, setPassword] = useState('');
  const [passwordValidation, setPasswordValidation] = useState(false);
  const { open, allClose } = useDialogStore();
  const { accessToken } = useTokenStore();
  const getLogoImg = useGetLogoImg();
  const postLogin = usePostLogin();
  const EMAILLENGTH = 50;
  // prettier-ignore
  const EMAILREX = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
  // prettier-ignore !,@,#,$,%,^,&,*
  const PASSWORDREX = /[\{\}\[\]\/?.,;:|\)~`\-_+<>\\\=\(\'\"]/g;

  /**
   * @function
   * mount / distory
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
    if (value.length > EMAILLENGTH) {
      return;
    }

    setEmail(event.currentTarget.value);
  };
  /**
   * @function
   * 이메일 정규식 체크
   */
  useEffect((): void => {
    if (email.trim() !== '' && !EMAILREX.test(email)) {
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
    if (password.trim() !== '' && PASSWORDREX.test(password)) {
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
        },
        onSettled: () => {
          // 요청이 성공하든, 에러가 발생되든 실행하고 싶은 경우
          console.log('onSettled');
        },
      },
    );
  };

  const handlerLoginSuccess = (data: APIResponse) => {
    // 요청이 성공한 경우
    router.push('/');
  };

  return (
    <main className={styles.main}>
      <h1 className={styles.title}>로그인</h1>
      <div className={styles.logoimgWrap}>
        <img src={`https://source.unsplash.com/random`} alt="로고이미지" />
      </div>
      <h2 className={styles.name}>다가치</h2>
      <input
        className={styles.input}
        type="email"
        placeholder="회사 e-mail을 입력해주세요."
        value={email}
        onChange={handleEmailChange}
      />
      {emailValidation && (
        <p className={styles.errText}>이메일 형식이 올바르지 않습니다.</p>
      )}
      <input
        className={styles.input}
        type="password"
        placeholder="password를 입력해주세요."
        value={password}
        onChange={handlePasswordChange}
      />
      {passwordValidation && (
        <p className={styles.errText}>
          비밀번호는 특수문자는 !,@,#,$,%,^,&,*만 사용 가능합니다.
        </p>
      )}
      <Link
        className={styles.passwordFind}
        href={{
          pathname: '/passfind',
        }}
      >
        비밀번호 찾기
      </Link>
      <button className={styles.loginBtn} onClick={handlerLoginBtn}>
        로그인
      </button>
      <p className={styles.pText}>대표님! 다가치 가 처음이신가요?</p>
      <Link
        className={styles.signUp}
        href={{
          pathname: '/join',
        }}
      >
        대표자 회원가입
      </Link>
      <p className={`${styles.pText} ${styles.color}`}>
        직원들은 대표님이 초대해주셔야 합니다!
      </p>
    </main>
  );
};
export default Login;
