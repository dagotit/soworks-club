'use client';

import styles from './Join.module.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddressSearch from '../../../components/popups/AddressSearch';

const inKey = process.env.NEXT_PUBLIC_API_URL;
const deKey = encodeURI(process.env.CONFIRM_COMPANY_KEY);


console.log(inKey); // 확인용 로그
console.log(deKey); // 확인용 로그


const Join = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [telecom, setTelecom] = useState('');
  const [address, setAddress] = useState('');
  const [zipCode, setZipCode] = useState('');
  const [isClickedCefTopBtn, setIsClickedCefTopBtn] = useState(false);
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordsMatch, setPasswordsMatch] = useState(false);
  const [isConfirmPasswordBlurred, setIsConfirmPasswordBlurred] = useState(false);

  /**
   * @function
   * @DESC 연락처 영역
   */
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({phoneNumber, telecom});
  }
  /**
   * @function
   * @DESC 이메일 인증하기 눌렀을때
   */
  const handleClickedCefTopBtn = () => {
    setIsClickedCefTopBtn(true);
  };

  /**
   * @function
   * @DESC 주소 검색하고 주소를 클릭했을때
   */
  const handleAddressSelect = ( selectedAdd ) => {
    setAddress(selectedAdd.selectedAddress);
    setZipCode(selectedAdd.selectedZipCode);
  };

  /**
   * @function
   * @DESC 비밀번호 설정
   */
  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
  };

  const handleConfirmPasswordBlur = () => {
    // 비밀번호 확인 input에서 포커스가 빠져나갈 때 일치 여부 확인
    setIsConfirmPasswordBlurred(true);
    setPasswordsMatch(password === confirmPassword);
  };

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

  const handleOnerName = (e) => {
    let value = e.target.value;
    setOnerNAme(value);
  }
  const handleCompanyName = (e) => {
    let value = e.target.value;
    setCompanyName(value);
  };
  const handleCompanyDate = (e) => {
    let value = e.target.value;
    setCompanyDate(value);
  }
  const checkCorporateRegiNumber = async (corporateRegiNumber: string, companyDate: string, onerName: string, companyName: string) => {
    try {
      // const decodedServiceKey = encodeURIComponent(process.env.CONFIRM_COMPANY_NUM_DE_KEY);
      const key = process.env.CONFIRM_COMPANY_NUM_IN_KEY;
      console.log(key);
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
      console.log('response.data', response.data);
      if (apiData.b_stt_cd === "01") {
        setIsValid(true);
      } else {
        setIsValid(false);
      }
      setIsConfirmRegiNumBlurred(true);
    } catch (error) {
      // 에러 처리
      console.error(error.response ? error.response.data : error.message);
      setIsValid(false);
    }
  };

  const handleInputChange = (e) => {
    let value = e.target.value;
    // 정규식을 사용하여 숫자 이외의 문자 제거
    value = value.replace(/[^0-9]/g, '');

    // 최대 길이를 10으로 제한
    if (value.length > 10) {
      value = value.slice(0, 10);
    }

    setCorporateRegiNumber(value);
  };


  return (
    <main className={styles.main}>
      <h2>회원가입</h2>
      <div className={styles.joinWrap}>
        {/* 이름 영역 */}
        <div className={styles.name_field}>
          <div className={styles.tag_name}>이름</div>
          <div className={styles.tag_input}>
            <input type="text" placeholder="대표자분의 성함을 입력해주세요." value={onerName ? onerName : ''} onChange={handleOnerName} />
          </div>
        </div>
        {/* //이름 영역 */}
        {/* 주민번호 영역 */}
        <div className={styles.sn_field}>
          <div className={styles.tag_name}>주민번호</div>
          <div className={styles.tag_input}>
            {/* 주민번호 앞자리 */}
            <input type="number" />
            <div className={styles.dash} />
            {/* 주민번호 뒷자리 */}
            <input type="password" />
          </div>
        </div>
        {/* //주민번호 영역 */}
        {/* 연락처 영역 */}
        <form onSubmit={handleSubmit} className={styles.phone_field}>
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
        </form>
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
            <input type="num" placeholder="ex) 20230101 " value={companyDate ? companyDate : ''} onChange={handleCompanyDate} />
          </div>
        </div>
        {/* //회사명 영역 */}
        {/* 사업자 등록번호 영역 */}
        <div className={styles.company_num_field}>
          <div className={styles.tag_name}>사업자 번호</div>
          <div className={styles.tag_input}>
            <input
              type="text"
              value={corporateRegiNumber ? corporateRegiNumber : ''}
              placeholder=" - 기호는 생략해주세요."
              onChange={handleInputChange}
            />
            <button type="button" onClick={() => checkCorporateRegiNumber(corporateRegiNumber, companyDate, onerName, companyName)}>인증하기</button>
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
              <input type="text" />
              <button type="button" onClick={handleClickedCefTopBtn}>인증하기</button>
            </div>
          </div>
          {isClickedCefTopBtn && (
            <div className={styles.cef_bottom}>
              <div className={styles.tag_name}>인증번호 입력</div>
              <div className={styles.tag_input}>
                <input type="text" />
                <button type="button">인증</button>
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
              value={address ? address : ''}
              disabled
              className={`${styles.input_blur} ${styles.address_input}`}
            />
            {/* //도로명 주소 */}
            <input
              type="text"
              placeholder="상세 주소"
            />
          </div>
        </div>
        {/* //회사 주소 영역 */}
      </div>
    </main>
  );
};
export default Join;
