'use client';

import styles from './GroupDetail.module.css';
import React, { Fragment, memo, useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import {
  useGetAttendGroup,
  useGetDetailGroup,
  useGetUpdateStatus,
  usePostAttendApply,
  usePostAttendCancel,
  usePostGroupDelete,
} from '@/hooks/useGroup';
import { isEmptyArr, isEmptyObj } from '@/utils/common';
import Header from '@/components/Header';
import Link from 'next/link';
import { AxiosResponse } from 'axios';
import { ResponseSuccess } from '@/services/api';
import { useDialogStore } from '@/store/useDialog';
import { DateTime } from 'luxon';

interface ATTENDTYPE {
  email: string;
  name: string;
  nickname: string;
  memberId: number;
}
interface GROUPDETAILINFOTYPE {
  groupId: number;
  categories: Array<{ id: number; name: string }>;
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
  joinYn: 'Y' | 'N';
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
    categories: [],
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
    joinYn: 'N',
  }); // 모임 정보
  const [groupStatus, setGroupStatus] = useState(respData.status);
  const [attendList, setAttendList] = useState<ATTENDTYPE[] | []>([]); // 모임 참가자 리스트
  const [attendBtnDisabled, setAttendBtnDisabled] = useState(false); // respData.status !== STATUS.WAITING
  const { open, allClose } = useDialogStore();
  const apiGroupInfo = useGetDetailGroup(Number(pathname.groupId)); // 모임 상세 정보 조회 [ 페이지 진입 시점 api list ]
  const apiGetUpdateStatus = useGetUpdateStatus(); // 모임 상태 변경 api
  const apiPostGroupDelete = usePostGroupDelete();
  const apiPostAttendApply = usePostAttendApply();
  const apiPostAttendCancel = usePostAttendCancel();
  const apiAttendList = useGetAttendGroup();
  const [isAttend, setIsAttend] = useState(true);

  useEffect(() => {
    return () => {
      console.log('데이터 비웠는데?');
      reset();
    };
  }, []);

  function reset() {
    setRespData({
      groupId: 0,
      categories: [],
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
      joinYn: 'N',
    });
  }

  /**
   * @function
   * 모임 상세 정보 데이터 setting
   * */
  useEffect(() => {
    reset();
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
    } else {
      // 모임이 없을 경우
      router.push('/');
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
    if (groupStatus === STATUS.DONE) {
      open(
        'confirm',
        '모임 상세조회',
        '모임 종료 후에는 변경하실 수 없습니다.<br /> 변경하시겠습니까?',
        () => {
          apiUpdateStatus();
        },
      );
      return;
    }
    apiUpdateStatus();
  }
  /**
   * @function
   * 모임 상태 변경 api
   */
  function apiUpdateStatus() {
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
      if (!!data && !isEmptyObj(data)) {
        setRespData({
          ...respData,
          status: data.status,
        });
      }
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
    if (respData.status !== STATUS.WAITING) {
      open('alert', '모임 상세', '모임에 참가 하실 수 없습니다.');
      return;
    }
    apiPostAttendApply.mutate(respData.groupId, {
      onSuccess: handleAttendApplySuccess,
    });
  }

  /**
   * @function
   * 모임 참가 신청 성공시
   */
  function handleAttendApplySuccess(resp: any) {
    console.log('모임 참가 신청 성공:', resp);
    if (resp === undefined) {
      return;
    }

    if (resp.respCode !== '00') {
      open('alert', '모임 참가 신청', resp.respBody, () => {
        // 이미 모임 취소가 되어 모임인원이 아닌경우
        if (respData.status !== STATUS.DONE) {
          setAttendBtnDisabled(true);
        }
      });
      return;
    }

    const data = resp.respBody;
    if (!!data && !isEmptyObj(data)) {
      setRespData({
        ...respData,
        groupJoinNum: data.groupJoinNum,
        joinYn: data.joinYn,
      });
    }
    apiAttendList.mutate(Number(pathname.groupId), {
      onSuccess: (resp: any) => {
        if (!!resp && !isEmptyObj(resp) && resp.respCode === '00') {
          // 참여자 리스트 새롭게 불러오기
          setAttendList(resp.respBody);
        }
      },
    });
  }

  /**
   * @function
   * 모임 참가 취소하기
   */
  function handleAttendCancel() {
    if (respData.status === STATUS.DONE) {
      open('alert', '모임 상세', '종료된 모임은 취소하실 수 없습니다.');
      return;
    }
    if (
      respData.status === STATUS.FULL ||
      respData.groupMaxNum === respData.groupJoinNum
    ) {
      open(
        'confirm',
        '모임 상세',
        '모임에 다시 참여하실 수 없을 수도 있습니다. <br /> 그래도 취소하시겠습니까? ',
        () => {
          apiHandleAttendCancel();
        },
      );
      return;
    }
    apiHandleAttendCancel();
  }

  function apiHandleAttendCancel() {
    apiPostAttendCancel.mutate(respData.groupId, {
      onSuccess: (resp: any) => {
        console.log('모임 참가 신청 취소 :', resp);
        if (resp.respCode === '00') {
          setRespData({
            ...respData,
            groupJoinNum: respData.groupJoinNum - 1,
            joinYn: 'N',
            status:
              respData.status !== STATUS.DONE ? STATUS.WAITING : STATUS.DONE,
          });
          apiAttendList.mutate(Number(pathname.groupId), {
            onSuccess: (resp: any) => {
              if (!!resp && !isEmptyObj(resp) && resp.respCode === '00') {
                // 참여자 리스트 새롭게 불러오기
                setAttendList(resp.respBody);
              }
            },
          });
          return;
        }
        // @ts-ignore
        open('alert', '모임 참가 취소', resp.respBody, () => {
          // alert('액션!');
          if (respData.status === STATUS.WAITING) {
            setAttendBtnDisabled(false);
          }
        });
      },
    });
  }

  /**
   * computed
   */
  useEffect(() => {
    // 모임 상태 체크
    const value =
      respData.status === STATUS.WAITING &&
      respData.joinYn === 'N' &&
      respData.groupMaxNum !== respData.groupJoinNum;

    setAttendBtnDisabled(!value); // 모임참여버튼 활성화 여부인데 이건 모임상태 변경과 연결이 안되어있으니.. 확인 필요

    if (
      respData.status === STATUS.DONE ||
      (respData.status === STATUS.FULL &&
        respData.joinYn === 'N' &&
        respData.groupMaxNum === respData.groupJoinNum)
    ) {
      setIsAttend(false);
    }
  }, [respData.status, respData.joinYn, respData.groupJoinNum]);

  if (apiGroupInfo[0].isLoading || apiGroupInfo[0].isFetching) {
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
        {/** 모임 이미지 **/}
        {respData.groupImage !== '' && (
          <div className={styles.imagesWrap}>
            <img src={respData.groupImage} alt="모임 이미지" />
          </div>
        )}
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
        {/** 카테고리 **/}
        <div className={styles.categoryList}>
          {respData.categories.map((value) => (
            <span key={value.id}>{value.name}</span>
          ))}
        </div>

        <div className={styles.groupInfo}>
          <div className={styles.groupInfoTime}>
            <p>날짜</p>
            <span>
              {DateTime.fromFormat(respData.strStartDate, 'yyyyMMdd').toFormat(
                'yyyy-MM-dd',
              )}
            </span>
          </div>
          <div className={styles.groupInfoTime}>
            <p>시간</p>
            <span>
              {DateTime.fromFormat(respData.strStartTime, 'HHmm').toFormat('t')}
            </span>
          </div>
          <div className={styles.groupInfoNum}>
            <div>
              <p>참가인원</p>
              <span>
                {respData.groupJoinNum} / {respData.groupMaxNum} 명
              </span>
            </div>
            {!isAttend && (
              <span>
                {respData.groupMaxNum !== respData.groupJoinNum &&
                respData.status === STATUS.FULL
                  ? '모임장에 의해서 '
                  : ''}
                인원 모집이 마감되었습니다.
              </span>
            )}
          </div>
          {/** 종료된 모임 문구 **/}
          {respData.status === STATUS.DONE && <p>종료된 모임 입니다.</p>}

          {/* TODO  모임장만 할 수 있는 액션 */}
          {respData.masterYn === 'Y' ? (
            <div className={styles.actionWrap}>
              <div className={styles.statusWrap}>
                <p>모임 상태</p>
                <select
                  value={groupStatus}
                  disabled={
                    respData.status === STATUS.DONE ||
                    respData.groupMaxNum === respData.groupJoinNum
                  }
                  onChange={handleChangeStatus}
                >
                  <option value={STATUS.WAITING}>인원 모집중</option>
                  <option value={STATUS.FULL}>인원 모집완료</option>
                  <option value={STATUS.DONE}>모임종료</option>
                </select>
                {/*{respData.status}*/}
                {/** TODO 모임 종료 후에 모임을 삭제하고 수정할 수 있는가??? **/}
                <button
                  type="button"
                  disabled={
                    respData.status === STATUS.DONE ||
                    respData.groupMaxNum === respData.groupJoinNum
                  }
                  onClick={handleUpdateStatus}
                >
                  변경
                </button>
                {respData.groupMaxNum === respData.groupJoinNum && (
                  <p>모임 인원 수를 변경하고 싶을 경우 수정을 해주세요.</p>
                )}
              </div>
              <div className={styles.actionBtnWrap}>
                <button
                  type="button"
                  className={styles.groupDelete}
                  onClick={handleGroupDelete}
                >
                  모임 삭제
                </button>
                <Link
                  className={styles.groupModify}
                  href={{
                    pathname: '/modify',
                  }}
                >
                  모임 수정
                </Link>
              </div>
            </div>
          ) : (
            isAttend && (
              <div className={styles.attendWrap}>
                <button
                  disabled={attendBtnDisabled || apiPostAttendApply.isPending}
                  type="button"
                  onClick={handleAttendApply}
                >
                  모임참가
                </button>
                <button
                  disabled={!attendBtnDisabled || apiPostAttendCancel.isPending}
                  type="button"
                  onClick={handleAttendCancel}
                >
                  모임취소
                </button>
              </div>
            )
          )}
        </div>
        <p className={styles.groupDescription}>{respData.description}</p>
      </div>
    </Fragment>
  );
});
export default GroupDetail;
