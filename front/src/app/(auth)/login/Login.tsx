'use client';

import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { useDialogStore } from '@/store/useDialog';
import React, { useEffect, useState } from 'react';
import { usePostLogin } from '@/hooks/useAuth';

const key = process.env.NEXT_PUBLIC_API_URL;
console.log('1', key);
const Login = () => {
  // const { id } = useLoginStore();
  const router = useRouter();
  const [email, setEmail] = useState('test@test.com');
  const [emailValidation, setEmailValidation] = useState(false);
  const [password, setPassword] = useState('1234');
  const [passwordValidation, setPasswordValidation] = useState(false);
  const { open, allClose } = useDialogStore();
  const { mutate, isError, error, isSuccess } = usePostLogin();
  const EMAILLENGTH = 50;
  const EMAILREX =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  // !,@,#,$,%,^,&,*
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
    console.log('2', key);
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
    mutate({ email, password });
    const success = isSuccess;
    console.log('isSuccess:: ', isSuccess);
    // router.push('/');
  };

  return (
    <main>
      로그인
      <input
        type="email"
        placeholder="example@company.com"
        value={email}
        onChange={handleEmailChange}
      />
      {emailValidation && <p>이메일 형식이 올바르지 않습니다.</p>}
      <input type="password" value={password} onChange={handlePasswordChange} />
      {passwordValidation && (
        <p>비밀번호는 특수문자는 !,@,#,$,%,^,&,*만 사용 가능합니다.</p>
      )}
      <Link
        href={{
          pathname: '/passfind',
        }}
      >
        비밀번호 찾기
      </Link>
      <button onClick={handlerLoginBtn}>로그인</button>
      <Link
        href={{
          pathname: '/registration',
        }}
      >
        회원가입
      </Link>
    </main>
  );
};
export default Login;
