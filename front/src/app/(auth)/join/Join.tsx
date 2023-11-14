'use client';

import React, { useState } from 'react';


const Join = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [telecom, setTelecom] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({phoneNumber, telecom});
  }
  const handleVerification = () => {
    console.log('인증하기 버튼이 눌렸습니다.');
  };

  return (
    <main>
      <h2>회원가입</h2>
      <div className="joinWrap">
        <div className="name-field">
          <div className="tag-name">이름</div>
          <div className="tag-input">
            <input type="text" />
          </div>
        </div>
        <div className="sn-field">
          <div className="tag-name">주민번호</div>
          <div className="tag-input">
            {/* 주민번호 앞자리 */}
            <input type="number" />
            <div className="dash" />
            {/* 주민번호 뒷자리 */}
            <input type="password" />
          </div>
        </div>
        <form onSubmit={handleSubmit} className="phone-field">
          <label className="tag-name">연락처</label>
          <select value={telecom} onChange={(e) => setTelecom(e.target.value)}>
            <option value="Skt">SKT</option>
            <option value="Kt">KT</option>
            <option value="Lgu+">LG U+</option>
            <option value="Altteul">알뜰폰</option>
          </select>
          <input
            type="tel"
            value={phoneNumber}
            className="tag-input"
            onChange={(e) => setPhoneNumber(e.target.value)}
            placeholder="ex)01012345678(O) 010-1234(X)"
          />
          <span className="tag-subtxt">"-" 기호는 생략해주세요.</span>
          <button type="button" onClick={handleVerification}>
            인증하기
          </button>
        </form>
        <div className="company-name-field">
          <div className="tag-name">회사명</div>
          <div className="tag-input">
            <input type="text" />
          </div>
        </div>
        <div className="company-name-field">
          <div className="tag-name">사업자 번호</div>
          <div className="tag-input">
            <input type="text" />
            <button type="button">인증하기</button>
          </div>
        </div>
      </div>
    </main>
  );
};
export default Join;
