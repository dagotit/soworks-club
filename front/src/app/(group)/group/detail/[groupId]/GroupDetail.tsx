'use client';

import styles from './GroupDetail.module.css';
import React, { Fragment, memo, useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import {
  useGetDetailGroup,
  useGetUpdateStatus,
  usePostAttendApply,
  usePostAttendCancel,
  usePostGroupDelete,
} from '@/hooks/useGroup';
import useDidMountEffect from '@/utils/useDidMountEffect';
import { isEmptyArr, isEmptyObj } from '@/utils/common';
import Header from '@/components/Header';
import Link from 'next/link';
import { AxiosResponse } from 'axios/index';
import { ResponseSuccess } from '@/services/api';
import { useDialogStore } from '@/store/useDialog';

interface ATTENDTYPE {
  email: string;
  name: string;
  nickname: string;
  memberId: number;
}
interface GROUPDETAILINFOTYPE {
  groupId: number;
  category: string;
  name: string;
  memberId: number;
  description: string;
  status: string;
  groupImage: string;
  strStartDate: string;
  strStartTime: string;
  strEndDate: string;
  strEndTime: string;
  groupMaxNum: number | null;
  groupJoinNum: number;
  masterYn: 'Y' | 'N';
}

const STATUS = {
  WAITING: 'WAITING',
  FULL: 'FULL',
  DONE: 'DONE',
};
// 불필요한 리렌더링 막기
const GroupDetail = memo(() => {
  const pathname: any = useParams();
  const router = useRouter();
  const [categoryList, setCategoryList] = useState(['신규', '인기', '전체']); // 카테고리 리스트
  const [selectedCategory, setSelectedCategory] = useState('null'); // TODO 선택한 카테고리 [이건 맞는지 모르겠네..? ]
  const [respData, setRespData] = useState<GROUPDETAILINFOTYPE>({
    groupId: 0,
    category: '',
    name: '',
    memberId: 0,
    description: '',
    status: '',
    groupImage: '',
    strStartDate: '',
    strStartTime: '',
    strEndDate: '',
    strEndTime: '',
    groupMaxNum: null,
    groupJoinNum: 0,
    masterYn: 'N',
  }); // 모임 정보
  const [groupStatus, setGroupStatus] = useState(respData.status);
  const [attendList, setAttendList] = useState<ATTENDTYPE[] | []>([]); // 모임 참가자 리스트
  const { open, allClose } = useDialogStore();
  const apiGroupInfo = useGetDetailGroup(Number(pathname.groupId)); // 모임 상세 정보 조회 [ 페이지 진입 시점 api list ]
  const apiGetUpdateStatus = useGetUpdateStatus(); // 모임 상태 변경 api
  const apiPostGroupDelete = usePostGroupDelete();
  const apiPostAttendApply = usePostAttendApply();
  const apiPostAttendCancel = usePostAttendCancel();

  /**
   * @function
   * 모임 상세 정보 데이터 setting
   * */
  useEffect(() => {
    if (!apiGroupInfo[0].data) {
      return;
    }
    const data: any = apiGroupInfo[0].data;
    if (data.respCode === '00') {
      const resp = data.respBody;
      if (!isEmptyObj(resp)) {
        console.log('resp1', resp);
        setRespData(resp);
        setGroupStatus(resp.status);
      }
    }
  }, [apiGroupInfo[0].data]);

  /**
   * @function
   * 모임 참가자 정보 데이터 setting
   * */
  useEffect(() => {
    if (!apiGroupInfo[1].data) {
      return;
    }
    const attendList: any = apiGroupInfo[1].data;
    if (attendList.respCode === '00') {
      const resp = attendList.respBody;
      if (!isEmptyArr(resp)) {
        console.log('resp2', resp);
        setAttendList(resp);
      }
    }
  }, [apiGroupInfo[1].data]);

  /**
   * @function
   * TODO : 카테고리 선택..? 이 아닌데
   */
  function handleSelectChangeCategory(e: React.ChangeEvent<HTMLSelectElement>) {
    setSelectedCategory(e.currentTarget.value);
  }

  function handleChangeStatus(e: React.ChangeEvent<HTMLSelectElement>) {
    setGroupStatus(e.currentTarget.value);
  }

  /**
   * @function
   * 모임 상태 변경
   */
  function handleUpdateStatus() {
    // TODO 지금 서버에러가..?
    apiGetUpdateStatus.mutate(
      {
        memberId: respData.memberId,
        groupId: respData.groupId,
        status: groupStatus,
      },
      {
        onSuccess: handleUpdateStatusSuccess,
      },
    );
  }
  /**
   * @function
   * 모임 상태 변경 성공시
   */
  function handleUpdateStatusSuccess(
    resp: AxiosResponse<ResponseSuccess, any> | undefined,
  ) {
    // @ts-ignore
    if (resp.respCode === '00') {
      // @ts-ignore
      const data = resp.respBody;
      setRespData({
        ...respData,
        status: data.status,
      });
    }
  }
  /**
   * @function
   * 모임 삭제
   */
  function handleGroupDelete() {
    open('confirm', '모임 삭제', '모임을 삭제하시겠습니까?', () => {
      apiPostGroupDelete.mutate(
        {
          groupId: respData.groupId,
          memberId: respData.memberId,
        },
        {
          onSuccess: (resp) => {
            console.log('모임삭제 성공::', resp);
            router.push('/calendar');
          },
        },
      );
    });
  }

  /**
   * @function
   * 모임 참가 신청하기
   */
  function handleAttendApply() {
    apiPostAttendApply.mutate(respData.groupId, {
      onSuccess: (resp: AxiosResponse<ResponseSuccess, any> | undefined) => {
        console.log('모임 참가 신청 성공:', resp);
        if (resp === undefined) {
          return;
        }
        // @ts-ignore
        if (resp.respCode === '00') {
          return;
        }
        // @ts-ignore
        open('alert', '모임 참가 신청', resp.respBody, () => {
          // alert('액션!');
        });
      },
    });
  }

  /**
   * @function
   * 모임 참가 취소하기
   */
  function handleAttendCancel() {
    apiPostAttendCancel.mutate(respData.groupId, {
      onSuccess: (resp) => {
        console.log('모임 참가 신청 취소 :', resp);
      },
    });
  }

  if (apiGroupInfo[0].isLoading) {
    return (
      <Fragment>
        <Header />
        <div className={styles.loadingText}>
          <div className={styles.loader}></div>

          <div
            className={`${styles.loaderSection} ${styles.sectionLeft}`}
          ></div>
          <div
            className={`${styles.loaderSection} ${styles.sectionRight}`}
          ></div>
        </div>
      </Fragment>
    );
  }

  return (
    <Fragment>
      <Header />
      <div className={styles.detailWrap}>
        <div className={styles.headerWrap}>
          <h2>{respData.name}</h2>
          <ul className={styles.groupPerson}>
            {attendList.map((item, index) => (
              <li key={index}>
                {item.memberId === respData.memberId && <span>모임장</span>}
                {item.nickname}
              </li>
            ))}
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
        {respData.groupImage !== '' && (
          <div className={styles.imagesWrap}>
            <img src={respData.groupImage} alt="로그인" />
          </div>
        )}
        {/* TODO  모임장만 할 수 있는 액션 */}
        {respData.masterYn === 'Y' && (
          <div className={styles.actionWrap}>
            <div className={styles.statusWrap}>
              모임 상태 변경
              <select value={groupStatus} onChange={handleChangeStatus}>
                <option value={STATUS.WAITING}>인원 모집중</option>
                <option value={STATUS.FULL}>인원 모집완료</option>
                <option value={STATUS.DONE}>모임종료</option>
              </select>
              {/*{respData.status}*/}
              <button type="button" onClick={handleUpdateStatus}>
                변경
              </button>
            </div>
            <div>
              <button
                type="button"
                className={styles.groupDelete}
                onClick={handleGroupDelete}
              >
                모임 삭제
              </button>
              <Link
                href={{
                  pathname: '/modify',
                }}
              >
                모임 수정
              </Link>
            </div>
          </div>
        )}
        {respData.masterYn === 'N' && (
          <div className={styles.attendWrap}>
            <button
              disabled={respData.status !== STATUS.WAITING}
              type="button"
              onClick={handleAttendApply}
            >
              모임참가
            </button>
            <button type="button" onClick={handleAttendCancel}>
              모임취소
            </button>
          </div>
        )}
        <p>{respData.description}</p>
      </div>
    </Fragment>
  );
});
export default GroupDetail;
