'use client';

import React, { useEffect, useState } from 'react';
import styles from './Create.module.css';

const CreatePage = () => {
  const [gTitle, setGTitle] = useState(''); // 모임 제목
  const [desc, setDesc] = useState(''); // 모임 설명
  const [startDate, setStartDate] = useState(''); // 시작 날짜
  const [endDate, setEndDate] = useState(''); // 종료 날짜
  const [mainImg, setMainImg] = useState(''); // 대표 이미지
  const [allDay, setAllDay] = useState(false); // 모일 종일 여부

  /**
   * 모임 제목 바뀔때
   */
  const changeGroupName = (event: React.ChangeEvent<HTMLInputElement>):void => {
     setGTitle(event.target.value);
  };
  /**
   * 모임 설명 바뀔때
   */
  const changeGroupDesc = (event: React.ChangeEvent<HTMLTextAreaElement>):void => {
    setDesc(event.target.value);
  };

  /**
   * 대표 이미지 변경시
   */
  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>):void => {
    let imgFiles = event.target.files;
    let imgFile = imgFiles[0];
    const url = URL.createObjectURL(imgFile);

    console.log(imgFile);
    console.log(url);
    // if (file.size <= 10 * 1024 * 1024) {
    //   console.log(file.url);
    //   setMainImg(file.url);
    // } else {
    //   open('alert', '그룹 생성하기', '이미지 파일의 크기를 확인해주세요.');
    // }
  };

  return (
    <div className={styles.cWrap}>
      <div className={styles.cWrapTitle}>
        <h2 className={styles.cWrapTxt}>
          <span className={styles.ctxtP}>C</span>reate <span className={styles.ctxtB}>G</span>roup
        </h2>
      </div>
      <div className={styles.cWrapContBox}>
        {/* 모임 제목 */}
        <div className={`${styles.cWrapGName} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>모임 제목</div>
          <div className={styles.cCont}>
            <input type="text" value={gTitle} required placeholder='모임 제목을 입력해 주세요.' onChange={changeGroupName} />
          </div>
        </div>
        {/* // 모임 제목 */}
        {/* 날짜 설정 */}
        <div className={`${styles.cWrapDesc} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>날짜 설정</div>
          <div className={styles.cCont}>
            <input  type="date" value={startDate} required placeholder='시작 날짜' onChange={(e: React.ChangeEvent<HTMLInputElement>) :void => {setStartDate(e.target.value)} } />
            ~
            <input type="date" value={endDate} required placeholder='종료 날짜' onChange={(e: React.ChangeEvent<HTMLInputElement>) :void => {setEndDate(e.target.value)} } />
          </div>
        </div>
        {/* // 날짜 설정 */}
        {/* 대표 이미지 */}
        <div className={`${styles.cWrapImg} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>대표 이미지</div>
          <div className={styles.cCont}>
            <div className={styles.cContMainImgBox}>
              <img src={mainImg || "public/images/contDefaultImg.png"} alt="대표 이미지"/>
            </div>
            <div className={styles.cContMainImgCont}>
              <input type="file" onChange={handleImageChange} />
              <span className={styles.warMsg}>이미지 파일의 최대 크기 10MB</span>
            </div>
          </div>
        </div>
        {/* // 날짜 설정 */}
        {/* 모임 설명 */}
        <div className={`${styles.cWrapDesc} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>모임 설명</div>
          <div className={styles.cCont}>
            <textarea value={desc} onChange={changeGroupDesc} placeholder='모임설명을 작성해주세요.'>모임 설명을 작성해 주세요.</textarea>
          </div>
        </div>
        {/* // 모임 설명 */}
      </div>
    </div>
  );
};

export default CreatePage;
