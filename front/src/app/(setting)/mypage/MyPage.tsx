'use client';

import styles from './MyPage.module.css';
import React, {useState} from 'react';

const MyPage = () => {
  const basicImgUrl = '/user.jpg';
  const editImgUrl = '/editImage.svg';
  const myPoint = 9000;
  const NTitle = '신입';
  const userNName = '나는정말로집에가고싶어요';
  const [titleNum , setTitleNum] = useState('');
  interface myJoinListItem {
    groupId: number;
    categories: Object;
    name: string;
    memberId: number;
    description: string;
    status: string;
    groupImage: string;
    strStartDate: string;
    strEndDate: string;
    strStartTime: string;
    strEndTime: string;
    groupMaxNum: number;
    groupJoinNum: number;
    masterYn: string;
    joinYn: string;
  }
  const [myJoinListConts, setMyJoinListConts] = useState<myJoinListItem[]>([]);
  /**
   * @DESC 칭호변경
   */
  const handleNNameTitleChange = () => {
    console.log('e');
  }
  const pointScore = (value: number) => {
    return new Intl.NumberFormat('ko-KR', {
      style: 'decimal',
      currency: 'KRW',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(value);
  }

  return (
    <div className={styles.mypageWrap}>
      <div className={styles.myInfoWrap}>
        <div className={styles.myImgBox}>
          <div className={styles.myImg}>
            <img src={basicImgUrl} alt="사용자 프로필 이미지" />
          </div>
          <button className={styles.myImgEditBtn}>
            <img src={editImgUrl} alt="사용자 프로필 수정 버튼" />
          </button>
        </div>
        <div className={styles.myInfo}>
          <div className={styles.myInfoNName}>
            <div className={`${styles.nNameTitle} ${titleNum}`}>[{NTitle}]</div>
            <div className={styles.nName}>{userNName}</div>
          </div>
          <button className={styles.myInfoNameEdit} type="button" onClick={handleNNameTitleChange}>칭호 변경</button>
          <p className={styles.myPoint}>
            내 포인트
            <span>{pointScore(myPoint)}</span>
            점
          </p>
        </div>
      </div>
      <div className={styles.myListWrap}>
        <div className={`${styles.myList} ${styles.myJoinList}`}>
          <div className={styles.myListTop}>
            <div className={styles.myListTopTit}>참여중인 모임</div>
            <button type="button" className={styles.myListBtn}>전체보기</button>
          </div>
          <ul className={styles.myListArr}>
            {myJoinListConts.map((myJoinListItem) => {
              <li className={styles.myListCont} key={myJoinListItem.id}></li>
            })}
          </ul>
        </div>
        <div className={`${styles.myList} ${styles.mySeeList}`}>
          <div className={styles.myListTop}>
            <div className={styles.myListTopTit}>최근 본 모임</div>
            <button type="button" className={styles.myListBtn}>전체보기</button>
          </div>
        </div>
      </div>
    </div>
  )
};

export default MyPage;
