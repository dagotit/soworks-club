'use client';

import styles from './Join.module.css';
import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import AddressSearch from '../../../components/popups/AddressSearch';
import { Jua } from 'next/font/google';
import {useDialogStore} from "@/store/useDialog";
import { usePostSignup, useGetEmailCodeVerfiy, usePostCreditsEmail } from '@/hooks/useAuth';
import {APIResponse} from "@/services/api";
import {useRouter} from "next/navigation";

const jua = Jua({weight: ["400"], subsets: ['latin']});
const Join = () => {
  const router = useRouter();
  const postSignup = usePostSignup(); // 회원가입 시도
  const creditsEmail = usePostCreditsEmail(); // 인증번호 보내기
  const emailCodeVerify = useGetEmailCodeVerfiy(); // 인증번호 가져오기
  const [code, setCode] = useState('');
  // const [phoneNumber, setPhoneNumber] = useState('');
  // const [telecom, setTelecom] = useState('');
  const [roadAddress, setRoadAddress] = useState('');
  const [zipCode, setZipCode] = useState('');
  const [detailAddress, setDetailAddress] = useState('');
  const [isClickedCefTopBtn, setIsClickedCefTopBtn] = useState(false);
  const [mailSendState, setMailSendState] = useState(false);
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordsMatch, setPasswordsMatch] = useState(false);
  const [isConfirmPasswordBlurred, setIsConfirmPasswordBlurred] = useState(false);
  const [email, setEmail] = useState('');
  const { open, allClose } = useDialogStore();
  const emailInput = useRef<HTMLInputElement>();
  // 이메일 유효성 검사
  const emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
  /**
   * @function
   * @DESC 사업자 인증번호 인증 api
   */
  const [onerName, setOnerNAme] = useState('');
  const [companyName, setCompanyName] = useState('');
  const [companyDate, setCompanyDate] = useState('');
  const [corporateRegiNumber, setCorporateRegiNumber] = useState('');
  const [isValid, setIsValid] = useState(false);
  const [isConfirmRegiNumBlurred, setIsConfirmRegiNumBlurred] = useState(false);
  // const [snNum, setSnNum] = useState('');

  // 주민번호 자동 input focus
  // const snNumInput = useRef(null);

  /**
   * @function
   * mount / distory
   */
  useEffect(() => {
    return () => {
      allClose();
    };
  }, []);
  // useEffect(() => {
  //   setAddress(roadAddress + ', ' + detailAddress);
  // }, [roadAddress, detailAddress]);
  /**
   * @function
   * @DESC 연락처 영역
   */
  // const handleSubmit = (e): void => {
  //   e.preventDefault();
  //   console.log({phoneNumber, telecom});
  // }

  // 이메일 유효성 검사
  function isValidEmail(email: string): boolean {
    return emailRegex.test(email);
  }
  /**
   * @function
   * @DESC 이메일 인증하기 눌렀을때
   */
  const handleClickedCefTopBtn = (): void => {
    // emailInput.current 이 null 일 경우 통과 x
    if (emailInput.current) {
      if (!isValidEmail(emailInput.current.value)) {
        // 이메일 주소가 유효하지 않습니다.
        open('alert', '이메일 인증', '올바른 이메일 주소를 입력해주세요.');
        return;
      } else {
        setIsClickedCefTopBtn(true);
        setMailSendState(true);
        // 이메일 주소가 유효합니다.
        creditsEmail.mutate(email, {
          onSuccess: () => {
            open('alert', '이메일 인증하기', '이메일 주소로 인증번호를 발송하였습니다.');
            setMailSendState(false);
          },
          onError: (error: any) => {
            if (error.respCode !== '' && error.respMsg !== '') {
              open('alert', '이메일 인증하기', error.respMsg);
              return;
            }
            open('alert', '이메일 인증하기', '이메일 인증코드 전송 실패');
          },
        });
      }
    }
  };
  function handleChangeCode(event: React.ChangeEvent<HTMLInputElement>) {
    const { value } = event.currentTarget;
    setCode(value);
  }
  /**
   * @function
   * @DESC 이메일 인증번호 입력 일치 여부 확인
   */
  const verifyEmail = (): void => {
    emailCodeVerify.mutate(
      { email, code },
      {
        onSuccess: (data) => {
          if (data?.respCode === '00') {
            // 이메일 인증 성공
            open('alert', '이메일 인증', '이메일 인증이 완료되었습니다. ');
            return;
          }
          if (data?.respMsg) {
            open('alert', '이메일 인증', data?.respMsg);
            return;
          }
          open('alert', '이메일 인증', '인증코드가 일치하지 않습니다.');
        },
        onError: (error: any) => {
          console.log(error);
        },
      },
    );
  };
  /**
   * @function
   * @DESC 주소 검색하고 주소를 클릭했을때
   */
  const handleAddressSelect = ( selectedAdd: Address ) => {
    setRoadAddress(selectedAdd.selectedAddress);
    setZipCode(selectedAdd.selectedZipCode);
  };
  type Address = {
    selectedAddress: string;
    selectedZipCode: string;
  }

  /**
   * @function
   * @DESC 비밀번호 설정
   */
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setConfirmPassword(event.target.value);
  };

  const handleConfirmPasswordBlur = () => {
    // 비밀번호 확인 input에서 포커스가 빠져나갈 때 일치 여부 확인
    setIsConfirmPasswordBlurred(true);
    setPasswordsMatch(password === confirmPassword);
  };


  const handleOnerName = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value;
    setOnerNAme(value);
  }
  const handleCompanyName = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value;
    setCompanyName(value);
  };
  const handleCompanyDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value;
    setCompanyDate(value);
  }
  const checkCorporateRegiNumber = async (corporateRegiNumber: string, companyDate: string, onerName: string, companyName: string) => {
    try {
      // const decodedServiceKey = encodeURIComponent(process.env.CONFIRM_COMPANY_NUM_DE_KEY);
      const key = process.env.CONFIRM_COMPANY_NUM_IN_KEY;
      const response = await axios.post(
        `https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=${key}`,
        { businesses: [
                {
                  b_no: corporateRegiNumber, // 사업자 등록번호
                  start_dt: companyDate, // 개업일자
                  p_nm: onerName, // 대표자 성명
                  p_nm2: "", // 대표자 성명2
                  b_nm: companyName, // 상호
                  corp_no: "", // 법인 등록 번호
                  b_sector: "", // 주 업태명
                  b_type: "", // 주 종목명
                  b_adr: "", // 사업장 주소
                }
              ]
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      // 성공적인 응답 처리
      let apiData = response.data.data[0];
      if (apiData.b_stt_cd === "01") {
        setIsValid(true);
      } else {
        setIsValid(false);
      }
      setIsConfirmRegiNumBlurred(true);
    } catch (error) {
      // 에러 처리
      if (onerName.trim() === '') {
        open('alert', '사업자 번호 인증', '대표자분의 성함을 입력해주세요.');
        return;
      }
      if (corporateRegiNumber.trim() === '') {
        open('alert', '사업자 번호 인증', '사업자 번호를 입력해주세요.');
        return;
      }
      if (companyName.trim() === '') {
        open('alert', '사업자 번호 인증', '회사명을 입력해주세요.');
        return;
      }
      if (companyDate.trim() === '') {
        open('alert', '사업자 번호 인증', '회사 설립날짜를 입력해주세요.');
        return;
      }
      open('alert', '사업자 번호 인증', '등록되지 않은 사업자 번호입니다.<br> 대표자 성함, 회사명, 설립날짜, 사업자 번호를 다시 한 번 확인해주세요.');
      setIsValid(false);
      return;
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;
    // 정규식을 사용하여 숫자 이외의 문자 제거
    value = value.replace(/[^0-9]/g, '');

    // 최대 길이를 10으로 제한
    if (value.length > 10) {
      value = value.slice(0, 10);
    }

    setCorporateRegiNumber(value);
  };

  // 주민번호 포커스 이동
  // const handleSnFeildChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   const value = event.target.value;
  //   if (value.length === 6) {
  //     snNumInput.current?.focus();
  //   }
  // };
  // 회원가입 버튼 클릭
  const handleSignUpClick = (): void => {
    if (onerName.trim() === '') {
      open('alert', '회원가입', '대표자분의 성함을 입력해주세요.');
      return;
    }
    // if (phoneNumber.trim() === '') {
    //   open('alert', '회원가입', '연락처를 입력해주세요.');
    //   return;
    // }
    if (companyName.trim() === '') {
      open('alert', '회원가입', '회사명을 입력해주세요.');
      return;
    }
    if (companyDate.trim() === '') {
      open('alert', '회원가입', '회사 설립날짜를 입력해주세요.');
      return;
    }
    if (corporateRegiNumber.trim() === '') {
      open('alert', '회원가입', '사업자 번호를 입력해주세요.');
      return;
    }
    if (!isValid) {
      open('alert', '회원가입', '올바른 사업자 번호를 입력해주세요.');
      return;
    }
    if (email.trim() === '') {
      open('alert', '회원가입', '회사 이메일을 입력해주세요.');
      return;
    }
    if (password.trim() === '') {
      open('alert', '회원가입', '비밀번호를 입력해주세요.');
      return;
    }
    if (confirmPassword.trim() === '') {
      open('alert', '회원가입', '비밀번호를 다시 한 번 입력해주세요.');
      return;
    }
    if (!passwordsMatch) {
      open('alert', '회원가입', '비밀번호를 다시 확인해주세요.');
      return;
    }
    if (zipCode.trim() === '') {
      open('alert', '회원가입', '우편번호를 입력해주세요.');
      return;
    }
    if (roadAddress.trim() === '') {
      open('alert', '회원가입', '회사 주소를 입력해주세요.');
      return;
    }
    if (detailAddress.trim() === '') {
      open('alert', '회원가입', '회사 상세 주소를 입력해주세요.');
      return;
    }

    let address = roadAddress + ', ' + detailAddress;
    // 가입 완료 후 로그인 페이지로 이동
    postSignup.mutate(
      { email, password, address, corporateRegiNumber, onerName, companyName, companyDate },
      {
        onSuccess: handlerSignupSuccess,
        onError: (error: any) => {
          // 요청에 에러가 발생된 경우
          if (error.respCode !== '') {
            open('alert', '회원가입', error.respMsg);
          }
        },
        onSettled: () => {
          // 요청이 성공하든, 에러가 발생되든 실행하고 싶은 경우
          console.log('onSettled');
        },
      }
    );
  }
  const handlerSignupSuccess = (data: APIResponse) => {
    // 요청이 성공한 경우
    
    open('alert', '회원가입', '가입 성공');
    // router.push('/login');
  };
  return (
    <main className={styles.main}>
      <h2>JOIN DAGACHI</h2>
      <div className={`${styles.joinWrap} ${jua.className}`}>
        {/* 이름 영역 */}
        <div className={styles.name_field}>
          <div className={styles.tag_name}>이름</div>
          <div className={styles.tag_input}>
            <input type="text" placeholder="대표자분의 성함을 입력해주세요." value={onerName ? onerName : ''} onChange={handleOnerName} />
          </div>
        </div>
        {/* //이름 영역 */}
        {/* 주민번호 영역 개인정보 이슈로 인한 막기 */}
        {/* <div className={styles.sn_field}>
         <div className={styles.tag_name}>주민번호</div>
         <div className={styles.tag_input}> */}
            {/* 주민번호 앞자리 */}
            {/* <input type="number"
                  onChange={handleSnFeildChange}/>
            <div className={styles.dash} /> */}
            {/* 주민번호 뒷자리 */}
            {/*<input type="password" ref={snNumInput} />
          </div>
        </div> */}
        {/* //주민번호 영역 */}
        {/* 연락처 영역 */}
        {/* <form className={styles.phone_field}>
          <label className={styles.tag_name}>연락처</label>
          <div className={styles.tag_box}>
            <select value={telecom} onChange={(e) => setTelecom(e.target.value)}>
            <option value="Skt">SKT</option>
            <option value="Kt">KT</option>
            <option value="Lgu+">LG U+</option>
            <option value="Altteul">알뜰폰</option>
          </select>
            <input
              type="tel"
              value={phoneNumber}
              className={styles.tag_input}
              onChange={(e) => setPhoneNumber(e.target.value)}
              placeholder="' - ' 기호는 생략해주세요."
            />
          </div>
        </form> */}
        {/* //연락처 영역 */}
        {/* 회사명 영역 */}
        <div className={styles.company_name_field}>
          <div className={styles.tag_name}>회사명</div>
          <div className={styles.tag_input}>
            <input type="text" value={companyName ? companyName : ''} onChange={handleCompanyName} />
          </div>
        </div>
        {/* //회사명 영역 */}
        {/* 회사명 영역 */}
        <div className={styles.company_date_field}>
          <div className={styles.tag_name}>설립날짜</div>
          <div className={styles.tag_input}>
            <input type="number" placeholder="ex) 20230101 " value={companyDate ? companyDate : ''} onChange={handleCompanyDate} />
          </div>
        </div>
        {/* //회사명 영역 */}
        {/* 사업자 등록번호 영역 */}
        <div className={styles.company_num_field}>
          <div className={styles.tag_name}>사업자 번호</div>
          <div className={styles.tag_input}>
            <input
              type="number"
              value={corporateRegiNumber ? corporateRegiNumber : ''}
              placeholder=" - 기호는 생략해주세요."
              onChange={handleInputChange}
            />
            <button className={jua.className} type="button" onClick={() => checkCorporateRegiNumber(corporateRegiNumber, companyDate, onerName, companyName)}>인증하기</button>
            {isConfirmRegiNumBlurred && (
              <>
                {isValid ? (
                  <p className={styles.valid}>유효한 사업자 번호입니다.</p>
                ) : (
                  <p className={styles.invalid}>올바른 사업자 번호가 아닙니다.</p>
                )}
              </>
            )}
          </div>

        </div>
        {/* //사업자 등록번호 영역 */}
        {/* 회사 이메일 영역 */}
        <div className={styles.company_email_field}>
          <div className={styles.cef_top}>
            <div className={styles.tag_name}>회사 이메일</div>
            <div className={styles.tag_input}>
              <input
                type="text"
                name='email'
                ref={emailInput as React.RefObject<HTMLInputElement>}
                value={email ? email : ''}
                onChange={(e) => setEmail(e.target.value)}
                disabled={isClickedCefTopBtn}
                className={isClickedCefTopBtn ? styles.input_blur : ''}
              />
              <button className={jua.className} type="button" onClick={handleClickedCefTopBtn}>인증하기</button>
            </div>
          </div>
          {isClickedCefTopBtn && (
            <div className={styles.cef_bottom}>
              <div className={styles.tag_name}>인증번호 입력</div>
              <div className={styles.tag_input}>
                <input type="text" value={code} onChange={handleChangeCode} />
                <button className={jua.className} type="button" onClick={verifyEmail}>인증</button>
              </div>
            </div>
          )}
        </div>
        {/* //회사 이메일 영역 */}
        {/* 비밀번호 영역 */}
        <div className={styles.company_password_field}>
          <div className={styles.cpf_top}>
            <div className={styles.tag_name}>비밀번호 설정</div>
            <div className={styles.tag_input}>
              <input type="password" value={password} onChange={handlePasswordChange} />
            </div>
          </div>
          <div className={styles.cpf_bottom}>
            <div className={styles.tag_name}>비밀번호 확인</div>
            <div className={styles.tag_input}>
              <input
                type="password"
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
                onBlur={handleConfirmPasswordBlur}
              />

              {/* onBlur 이벤트 후에만 메시지 표시 */}
              {isConfirmPasswordBlurred && (
                <>
                  {/* 비밀번호 매치 상태에 따른 메세지 */}
                  {passwordsMatch ? (
                    // 일치할경우
                    <span className={styles.cpf_true}>비밀번호가 일치합니다.</span>
                  ) : (
                    // 불일치할경우
                    <span className={styles.cpf_false}>비밀번호가 일치하지 않습니다.</span>
                  )}
                </>
              )}
            </div>
          </div>
        </div>
        {/* //비밀번호 영역 */}
        {/* 회사 주소 영역 */}
        <div className={styles.company_location_field}>
          <div className={styles.tag_name}>회사 주소</div>
          <div className={styles.tag_box}>
            {/* 우편번호 */}
            <input
              type="number"
              placeholder=""
              value={zipCode ? zipCode : ''}
              disabled
              className={`${styles.input_blur} ${styles.num_input}`}
            />
            {/* //우편번호 */}
            <AddressSearch onSelectAddress={handleAddressSelect} />
            {/* 도로명 주소 */}
            <input
              type="text"
              placeholder="주소"
              value={roadAddress ? roadAddress : ''}
              disabled
              className={`${styles.input_blur} ${styles.address_input}`}
            />
            {/* //도로명 주소 */}
            <input
              type="text"
              value={detailAddress ? detailAddress : ''}
              placeholder="상세 주소"
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {setDetailAddress(e.target.value)}}
            />
          </div>
        </div>
        {/* //회사 주소 영역 */}
      </div>
      <button className={`${styles.signUpBtn} ${jua.className}`} type='button' onClick={handleSignUpClick}>가입하기</button>
    </main>
  );
};
export default Join;
