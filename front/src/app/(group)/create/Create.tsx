'use client';

import React, { useEffect, useState } from 'react';
import styles from './Create.module.css';
import { useDialogStore } from "@/store/useDialog";
import { usePostCreateGroup, useGetCategoryList } from '@/hooks/useAuth';
import {APIResponse} from "@/services/api";
import Header from "@/components/Header";
import {apiCreateGroup, apiGetCategoryList} from "@/services/authService";



const CreatePage = () => {
  const groupData = new FormData(); // 이미지 form Data
  const createGroup = usePostCreateGroup(); // 그룹생성 data be로 보내기
  const categoryList = useGetCategoryList(); // be에서 카테고리 가져오기
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]); // 선택된 카테고리들
  const [category, setCategory] = useState<categoryItem[]>([]); // 카테고리 설정
  const upCategoryId = '';
  const [gTitle, setGTitle] = useState(''); // 모임 제목
  const [maxNumUser, setMaxNumUser] = useState(''); // 모임 인원 수
  const [desc, setDesc] = useState(''); // 모임 설명
  const descLength = 500;
  const [startDate, setStartDate] = useState(''); // 시작 날짜
  const [formatStartDate, setFormatStartDate] = useState(''); // 시작 날짜 (전송용)
  const [endDate, setEndDate] = useState(''); // 종료 날짜
  const [formatEndDate, setFormatEndDate] = useState(''); // 종료 날짜 (전송용)
  // img 태그안에 경로 입력할경우 /create 부터 시작하는 문제가 있어 경로를 별도로 지정했음.
  const defaultImg = '/images/contDefaultImg.png';
  const [mainImg, setMainImg] = useState(''); // 대표 이미지
  const [groupImg, setGroupImg] = useState<File | null>(null);
  const [groupImgName, setGroupImgName] = useState(''); // 대표이미지 이름 저장
  let dateError = false;
  const { open, allClose } = useDialogStore();


  /**
   * @function
   * mount / distory
   */
  useEffect(() => {
    return () => {
      allClose();
    };
  }, []);

  interface categoryItem {
    id: string;
    name: string;
  }

  /**
     * @function
     * @DESC 이메일 인증번호 입력 일치 여부 확인
   */


  const getCategoryList = async (): Promise<void> => {
    try {  // 훅을 호출하여 mutation 객체를 얻음
      const data = await apiGetCategoryList();  // mutation.mutateAsync()를 호출하여 비동기 함수를 실행

      if (data?.respCode === '00') {
        if (data.respBody !== undefined) {
          setCategory(data?.respBody);
        }
      } else if (data?.respMsg !== undefined) {
        open('alert', '카테고리 불러오기 오류', data?.respMsg);
      } else {
        open('alert', '카테고리 불러오기 오류', '관리자에게 문의바랍니다.');
      }
    } catch (error) {
      console.log(error);
    }
  };
  useEffect( () => {
    getCategoryList();
  }, []);
  /**
   * 카테고리 설정할 때
   */
  const handleChangeCate = (event: React.ChangeEvent<HTMLInputElement>):void => {
    const value = event.target.value;
    if (selectedCategories.includes(value)) {
      setSelectedCategories(selectedCategories.filter((category) => category !== value));
    } else {
      setSelectedCategories([...selectedCategories, value]);
    }
  }
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
  const handleImageChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const imgFiles = event.target.files;
    if (imgFiles !== null) {
      // 이미지 파일선택 눌렀으나 아무런 동작도 하지 않았을 때.
      if (imgFiles.length === 0) {
        return;
      }
      const imgFile = imgFiles[0];
      // 이미지 파일의 용량이 0바이트인 경우
      if (imgFile.size === 0) {
        open('alert', '그룹 생성하기', '이미지 파일의 용량이 0바이트입니다.');
        return;
      }

      // 이미지 파일의 형식 및 크기가 올바른 경우
      if (imgFile.type === 'image/png' || imgFile.type === 'image/svg+xml' || imgFile.type === 'image/jpg') {
        if (imgFile.size <= 10 * 1024 * 1024) {
          const url = URL.createObjectURL(imgFile);
          setMainImg(url);
          setGroupImgName(imgFile.name);
          setGroupImg(imgFile);
          const submitButton = document.getElementById('hiddenSubmitButton');
          if (submitButton instanceof HTMLButtonElement) {
            submitButton.click();
          }
          // };
        } else {
          open('alert', '그룹 생성하기', '이미지 파일의 크기를 확인해주세요.');
          return;
        }
      } else {
        open('alert', '그룹 생성하기', '올바른 이미지 형식이 아닙니다.');
        return;
      }
    } else {
      open('alert', '그룹 생성하기', '올바른 이미지 형식이 아닙니다.');
      return;
    }
  };

  const handlerCreateGroup = (data: APIResponse) => {
    // 요청이 성공한 경우

    open('alert', '모임 생성', '생성 성공');
    // router.push('/login');
  };
  const handleImagePost = (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
  }

  /**
   * 시작 날짜 편집하기
   */
  const handleStartDate = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const userSetDateStart = new Date(e.target.value); // ex) Sat Jan 06 2024 14:36:00 GMT+0900 (한국 표준시)

    const year = userSetDateStart.getFullYear().toString();
    const month = (userSetDateStart.getMonth() + 1).toString().padStart(2, '0');
    const day = userSetDateStart.getDate().toString().padStart(2, '0');
    const hours = userSetDateStart.getHours().toString().padStart(2, '0');
    const minutes = userSetDateStart.getMinutes().toString().padStart(2, '0');

    const formattedValue = year + month + day + hours + minutes;
    setFormatStartDate(formattedValue);

    // 유효성 검사
    validateDates(e.target.value, endDate);
    if (!dateError) {
      setStartDate(e.target.value);
    }
  }

  /**
   * 시작 날짜 편집하기
   */
  const handleEndDate = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const userSetDateEnd = new Date(e.target.value);

    const year = userSetDateEnd.getFullYear().toString();
    const month = (userSetDateEnd.getMonth() + 1).toString().padStart(2, '0');
    const day = userSetDateEnd.getDate().toString().padStart(2, '0');
    const hours = userSetDateEnd.getHours().toString().padStart(2, '0');
    const minutes = userSetDateEnd.getMinutes().toString().padStart(2, '0');

    const formattedValue = year + month + day + hours + minutes;
    setFormatEndDate(formattedValue);
    // 유효성 검사
    validateDates(startDate, e.target.value);
    if (!dateError) {
      setEndDate(e.target.value);
    }
  }
  /**
   *
   * @param start 시작 날짜,시각
   * @param end 종료 날짜,시각
   */
  const validateDates = (start: string, end: string) => {
    if (start && end) {
      const startDateObj = new Date(start);
      const endDateObj = new Date(end);

      if (startDateObj > endDateObj) {
        dateError = true;
        open('alert', '모임 생성', '종료날짜는 시작날짜보다 늦어야 합니다.');
      } else {
        dateError = false;
        return;
      }
    }
  };
  /**
   *
   * @DESC 인원수 변경
   */
  const handleChangeGNum = (e:React.ChangeEvent<HTMLSelectElement>):void => {
    const num = e.target.value;
    setMaxNumUser(num);
  }


  /**
   * 모임 생성하기 버튼
   */
  const handleCreateGroup = () => {
    if (selectedCategories.length === 0) {
      open('alert', '그룹 생성하기', '카테고리를 입력해주세요.');
      return;
    }
    if (gTitle.trim() === '') {
      open('alert', '그룹 생성하기', '모임 제목을 입력해주세요.');
      return;
    }
    if (startDate.trim() === '') {
      open('alert', '그룹 생성하기', '시작 날짜를 입력해주세요.');
      return;
    }
    if (endDate.trim() === '') {
      open('alert', '그룹 생성하기', '종료 날짜를 입력해주세요.');
      return;
    }
    if (maxNumUser.trim() === '') {
      open('alert', '그룹 생성하기', '모임 인원 수를 입력해주세요.');
      return;
    }
    const groupStrData = {
      category: category,
      name: gTitle,
      description: desc,
      strStartDateTime: formatStartDate,
      strEndDateTime: formatEndDate,
      memberId: '1',
      groupMaxNum: maxNumUser,
    };

    // @ts-ignore
    groupData.append('file', groupImg);

    const blob = new Blob([JSON.stringify(groupStrData)], { type: "application/json" });
    groupData.append('group', blob);

    createGroup.mutate(
      groupData,
      {
        onSuccess: handlerCreateGroup,
        onError: (error: any) => {
          // 요청에 에러가 발생된 경우
          if (error.respCode !== '') {
            open('alert', '모임 생성', error.respMsg);
          }
        },
        onSettled: () => {
          // 요청이 성공하든, 에러가 발생되든 실행하고 싶은 경우
          console.log('onSettled');
        },
      }
    )
  }





  // @ts-ignore
  return (
    <div className={styles.cWrap}>
      <Header />
      <div className={styles.cWrapTitle}>
        <h2 className={styles.cWrapTxt}>
          <span className={styles.ctxtP}>C</span>reate <span className={styles.ctxtB}>G</span>roup
        </h2>
      </div>
      <div className={styles.cWrapContBox}>
        {/* 카테고리 설정 */}
        <div className={`${styles.cWrapCate} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>카테고리 설정*</div>
          <ul className={styles.cCont}>
            {category.map((categoryItem) => (
              <li className={styles.cContList} key={categoryItem.id}>
                <input
                  type="checkbox"
                  value={categoryItem.name}
                  name={categoryItem.name}
                  checked={selectedCategories.includes(categoryItem.name)}
                  onChange={handleChangeCate}
                />
                <label htmlFor={categoryItem.name}>{categoryItem.name}</label>
              </li>
            ))}
          </ul>
        </div>
        {/* // 카테고리 설정 */}
        {/* 모임 제목 */}
        <div className={`${styles.cWrapGName} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>모임 제목*</div>
          <div className={styles.cCont}>
            <input type="text" value={gTitle} required placeholder='모임 제목을 입력해 주세요.' onChange={changeGroupName}/>
          </div>
        </div>
        {/* // 모임 제목 */}
        {/* 모임 인원수 */}
        <div className={`${styles.cWrapGNum} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>모임 인원 수*</div>
          <div className={styles.cCont}>
            <select onChange={handleChangeGNum} title="모임 인원 수 선택" defaultValue="1">
              <option value="1" hidden disabled>인원 수 선택</option>
              <option value="2">2명</option>
              <option value="3">3명</option>
              <option value="4">4명</option>
              <option value="5">5명</option>
              <option value="6">6명</option>
              <option value="7">7명</option>
              <option value="8">8명</option>
              <option value="9">9명</option>
              <option value="10">10명 이상</option>
            </select>
          </div>
        </div>
        {/* // 모임 제목 */}
        {/* 날짜 설정 */}
        <div className={`${styles.cWrapDesc} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>날짜 설정*</div>
          <div className={styles.cCont}>
            <div className={styles.dateCont}>
              <div>시작</div>
              <input  type="datetime-local" value={startDate} required placeholder='시작 날짜' onChange={handleStartDate} />
            </div>
            <div className={styles.dateCont}>
              <div>종료</div>
              <input type="datetime-local" value={endDate} required placeholder='종료 날짜' onChange={handleEndDate} />
            </div>
          </div>
        </div>
        {/* // 날짜 설정 */}
        {/* 대표 이미지 */}
        <div className={`${styles.cWrapImg} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>대표 이미지</div>
          <div className={styles.cCont}>
            <div className={styles.cContMainImgBox}>
              <img src={mainImg || defaultImg } alt="대표 이미지"/>
            </div>
            <div className={styles.cContMainImgCont}>
              <form className={styles.fileBox} encType="multipart/form-data" onSubmit={handleImagePost}>
                <input type="text" className={styles.fileBoxTit} value={groupImgName || '첨부파일'} disabled />
                <label htmlFor="file">파일찾기</label>
                <input id='file' type="file" onChange={handleImageChange} />
                <button id="hiddenSubmitButton" type="submit" style={{ display: 'none' }} />
              </form>
              <span className={styles.warMsg}>이미지 파일의 최대 크기 10MB</span>
            </div>
          </div>
        </div>
        {/* // 날짜 설정 */}
        {/* 모임 설명 */}
        <div className={`${styles.cWrapDesc} ${styles.cWrapCont}`}>
          <div className={styles.cContTit}>모임 설명</div>
          <div className={styles.cCont}>
            <textarea value={desc} onChange={changeGroupDesc} placeholder='모임설명을 작성해주세요. 최대 500자' maxLength={descLength}>모임 설명을 작성해 주세요.</textarea>
          </div>
        </div>
        {/* // 모임 설명 */}
        {/* 모임생성 버튼 */}
        <div className={styles.cWrapContPostBtn}>
          <button type='button' className={styles.cBtn} onClick={handleCreateGroup}>모임 만들기</button>
        </div>
        {/* //모임생성 버튼 */}
      </div>
    </div>
  );
};

export default CreatePage;
