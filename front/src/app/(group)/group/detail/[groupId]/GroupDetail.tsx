'use client';

import styles from './GroupDetail.module.css';
import React, { Fragment, memo, useState } from 'react';
import { useParams } from 'next/navigation';
import { useGetGroupInfo } from '@/hooks/useGroup';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { isEmptyObj } from '@/utils/common';
import Header from '@/components/Header';

// 불필요한 리렌더링 막기
const GroupDetail = memo(() => {
  const pathname: any = useParams();
  const [categoryList, setCategoryList] = useState(['신규', '인기', '전체']);
  const [selectedCategory, setSelectedCategory] = useState('null');
  const apiGroupInfo = useGetGroupInfo(Number(pathname.groupId));
  const [respData, setRespData] = useState({
    id: 0,
    category: '',
    name: '',
    memberId: 0,
    description: '',
    status: '',
    groupImage:
      'https://storage.googleapis.com/dagachi-image-bucket/default/group_default.png',
    strStartDate: '',
    strStartTime: '',
    strEndDate: '',
    strEndTime: '',
    groupMaxNum: null,
    numberPersons: 0,
  });

  useDidMountEffect(() => {
    const data: any = apiGroupInfo.data;
    if (data.respCode === '00') {
      const resp = data.respBody;
      if (!isEmptyObj(resp)) {
        console.log(resp);
        setRespData(resp);
      }
    }
  }, [apiGroupInfo.data]);

  /**
   * @function
   * 카테고리 선택..? 이 아닌데
   */
  function handleSelectChangeCategory(e: React.ChangeEvent<HTMLSelectElement>) {
    setSelectedCategory(e.currentTarget.value);
  }

  return (
    <Fragment>
      <Header />
      <div className={styles.detailWrap}>
        <div className={styles.headerWrap}>
          <h2>{respData.name}</h2>
          <ul className={styles.groupPerson}>
            <li>모임장</li>
            <li>모임원1</li>
            <li>모임원2</li>
          </ul>
        </div>
        <div className={styles.categoryList}>
          <span>{respData.category}</span>
          {/*<select onChange={handleSelectChangeCategory} value={selectedCategory}>
          <option value="null">카테고리 선택</option>
          {categoryList.map((value, index) => (
            <option key={index} value={value}>
              {value}
            </option>
          ))}
        </select>*/}
          {categoryList.map((value, index) => (
            <span key={index}>{value}</span>
          ))}
        </div>
        <div className={styles.imagesWrap}>
          <img src={respData.groupImage} alt="로그인" />
        </div>
        <p>{respData.description}</p>
      </div>
    </Fragment>
  );
});
export default GroupDetail;
