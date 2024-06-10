'use client';

import styles from '@/app/(admin)/admin/Admin.module.css';
import { useGetMemberList, usePostAlarmSend } from '@/hooks/useAdmin';
import React, { useEffect, useRef, useState } from 'react';
import { isEmptyArr, isEmptyObj } from '@/utils/common';
import DeleteBtn from '@/app/(admin)/admin/userDelete/DeleteBtn';
import { useAdminStore } from '@/store/useAdmin';
import { usePathname, useRouter } from 'next/navigation';
import AlarmBtn from '@/app/(admin)/admin/alarm/AlarmBtn';
import { alarmStartData, useAlarmStore } from '@/store/useAlarmList';
import { useGetAlarmList, useGetMyInfo } from '@/hooks/useUser';
import { useDialogStore } from '@/store/useDialog';
import AlarmText from '@/app/(admin)/admin/alarm/AlarmText';

const SEARCH_TYPE = [{val: 'email', name: '이메일'}, {val: 'name', name: '이름'}];
export type ALARM_LIST_ITEM_TYPE = { memberId: number; content: string; title: string; receiveId: number };
type ALARM_TYPE = 'all' | 'custom';

const UserList = () => {
  const pathname = usePathname();
  const router = useRouter();
  const [input, setInput] = useState('');
  const [searchInput, setSearchInput] = useState('');
  const [searchType, setSearchType] = useState(SEARCH_TYPE[1].val);
  const [type, setType] = useState(SEARCH_TYPE[1].val);
  const [myMemberId, setMyMemberId] = useState<undefined | number>(undefined);

  // 알림보내기
  const getAlarmList = useGetAlarmList(false);
  const [isShowAlarmBtn,setIsShowAlarmBtn] = useState(pathname.includes('alarm'));
  const alarmAllEl = useRef(null);
  const alarmCustomEl = useRef(null);
  const [alarmType, setAlarmType] = useState<ALARM_TYPE>('all');
  const [checkedList, setCheckedList] = useState<number[]>([]);
  const { alarmList, resetAlarmList } = useAlarmStore()

  // store
  const { userList, setUserList, resetUserList } = useAdminStore();
  const { open, allClose } = useDialogStore();

  // api
  const memberList = useGetMemberList(input, type);
  const myInfo = useGetMyInfo(userList.length !== 0);
  const alarmSend = usePostAlarmSend();

  useEffect(() => {
    return () => {
      resetSearch();
      resetUserList();
      resetAlarm();
      allClose();
    }
  }, []);

  /**
   * @function
   * 알림 초기화
   */
  function resetAlarm() {
    setCheckedList([]);
    resetAlarmList();
  }

  /**
   * @function
   * 멤버 리스트 조회 subscribe
   */
  useEffect(() => {
    if (isEmptyObj(memberList.data)) {
      return;
    }
    setMemberList(memberList.data);
  }, [ memberList.data ]);

  /**
   * @function
   * 내 정보 조회 subscribe
   */
  useEffect(() => {
    if (myMemberId !== undefined) {
      return;
    }
    if (isEmptyObj(myInfo.data)) {
      return;
    }
    // @ts-ignore
    const { respBody } = myInfo.data;
    if (isEmptyObj(respBody)) {
      return;
    }
    const id = respBody.memberId ? respBody.memberId : undefined;
    setMyMemberId(id);
  }, [ myInfo.data ]);

  /**
   * @function
   * api 로 불러온 회원 리스트 setting
   */
  function setMemberList(data: object | undefined) {
    // @ts-ignore
    const { respBody } = data;
    if (isEmptyArr(respBody)) {
      resetUserList();
      return;
    }
    setUserList(respBody);
  }

  /**
   * @function
   * 회원 검색 input change event
   */
  function onChangeSearchInput(e: React.ChangeEvent<HTMLInputElement>) {
    setSearchInput(e.currentTarget.value)
  }

  /**
   * @function
   * 회원 검색 [ api ]
   */
  function getMemberList() {
    setInput(searchInput);
    setType(searchType);
  }

  /**
   * @function
   * 회원 삭제 api 성공 후 콜백함수
   */
  function apiMemberDeleteSuccess(resp: any) {
    // 삭제 성공 후에.. 회원 리스트 다시 조회
    memberList.refetch({}).then((resp) => {
      if (resp === undefined) {
        // error
        return;
      }
      setMemberList(resp.data)
    })
  }


  /**
   * @function
   * 검색 기준 변경
   */
  function onChangeSelectType(e: React.ChangeEvent<HTMLSelectElement>) {
    setSearchType(e.currentTarget.value);
  }

  /**
   * @function
   * 검색 초기화
   */
  function resetSearch() {
    setSearchInput('');
    setSearchType(SEARCH_TYPE[1].val);
    setInput('');
    setType(SEARCH_TYPE[1].val);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////// 알림 보내기 //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * @function
   * 전체 알림 전송 | 개별 알림 전송 선택
   */
  function onChangeAlarmType(e: React.ChangeEvent<HTMLInputElement>) {
    const value = e.target.value;
    if (value === 'all' || value === 'custom') {
      setAlarmType(value);
    }
    if (value === 'all' && alarmList.size !== 0) {
      // 개별 알림 작성 중 > 전체 알림으로 변경 시
      setAlarmType('custom');
      // @ts-ignore
      alarmCustomEl.current.checked = true;
      // @ts-ignore
      alarmAllEl.current.checked = false;
      open('confirm', '알림', '작성중인 알림을 삭제하시겠습니까?', () => {onClickConFirm('all')})
    }
    if (value === 'custom' && alarmList.has('all')) {
      // 전체 알람 작성 중 > 개별 알림으로 변경
      setAlarmType('all');
      // @ts-ignore
      alarmAllEl.current.checked = true;
      // @ts-ignore
      alarmCustomEl.current.checked = false;
      open('confirm', '알림', '작성중인 알림을 삭제하시겠습니까?', ()=>{onClickConFirm('custom')})
    }

    if (alarmList.size === 0) {
      resetAlarm();
    }
  }

  /**
   * @function
   * 컨펌 > 확인 시 [알림화면 이동]
   */
  function onClickConFirm(type: ALARM_TYPE): void {
    resetAlarm();
    setAlarmType(type);
    // @ts-ignore
    alarmAllEl.current.checked = type !== 'custom';
    // @ts-ignore
    alarmCustomEl.current.checked = type === 'custom';
  }


  /**
   * @function
   * 알림 리스트에 담기
   * 알림 전송할 인원 체크하는 체크박스
   */
  function checkListHandler(e: React.ChangeEvent<HTMLInputElement>) {
    // setIsChecked(!isChecked);
    if (e.target.checked) {
      // 체크했으면 배열에 담기
      setCheckedList((prev) => [...prev, Number(e?.target?.value)]);
      return
    }
    setCheckedList(checkedList.filter((item) => item !== Number(e?.target?.value)));
  }

  /**
   * @function
   * 알림 전송 버튼 클릭시
   */
  function onClickSendAlarm() {
    if (checkedList.length === 0 || myMemberId === undefined) {
      open('alert', '알림 전송', '알림을 전송할 사용자를 선택해주세요.');
      return;
    }

    // data 리스트에 담아보기
    const requestData = checkedList.reduce((acc: ALARM_LIST_ITEM_TYPE[], cur, idx ) => {
      let getAlarmData = alarmStartData;
      if (alarmType === 'all' && alarmList.has('all')) {
        getAlarmData = alarmList.get('all');
      }
      if (alarmType === 'custom' && alarmList.has(cur)) {
        getAlarmData = alarmList.get(cur);
      }
      if (!isEmptyObj(getAlarmData) && getAlarmData.title !== '') {
        acc.push({
          memberId: myMemberId,
          content: getAlarmData.text,
          title: getAlarmData.title,
          receiveId: cur
        })
      }
      return acc;
    }, [])

    console.log('requestData:', requestData)
    apiSendAlarm(requestData);
  }

  /**
   * @function
   * 알림 전송 [ api ]
   */
  function apiSendAlarm(list: ALARM_LIST_ITEM_TYPE[]) {
    if (isEmptyArr(list)) {
      open('alert', '알림', '선택된 회원의 전송할 알람이 없습니다.')
      return;
    }
    alarmSend.mutate(list, {
      onSuccess: () => {
        open('alert', '알림', '전송완료', () => {
          router.push('/admin')
          getAlarmList.refetch()
        })
      },
      onError: () => {
        open('alert', '알림', '전송실패<br />잠시후 다시 시도해주세요.')
      }
    })
  }


  // TODO 엔터..로 검색 가능하도록 수정?
  return <div className={styles.adminUserContents}>
    <div>
      <select value={searchType} onChange={onChangeSelectType}>
        {SEARCH_TYPE.map((item) => (
          <option key={item.val} value={item.val}>{item.name}</option>
        ))}
      </select>
      <input type="text" value={searchInput} onChange={onChangeSearchInput} />
      <button type="button" onClick={getMemberList}>검색</button>
      <button type="button" onClick={resetSearch}>초기화</button>
    </div>
    {!memberList.isLoading &&
      <div>
        {isShowAlarmBtn &&
          <div>
            <input type="radio" id="alarmAll" value="all" ref={alarmAllEl} checked={alarmType === 'all'} name="alarmType" onChange={onChangeAlarmType} />
            <label htmlFor="alarmAll">전체 알림</label>
            <input type="radio" id="alarmCustom" ref={alarmCustomEl} value="custom" checked={alarmType === 'custom'} name="alarmType" onChange={onChangeAlarmType} />
            <label htmlFor="alarmCustom">개별 알림</label>
          </div>
          }
        {isShowAlarmBtn && alarmType === 'all' && <AlarmText />}
        <ul>
          {userList.length === 0 && <li>불러올 리스트가 없습니다.</li>}
          {userList.length !== 0 && userList.map((value) => (
            <li key={value.memberId}>
              {isShowAlarmBtn && <input type="checkbox" value={value.memberId} checked={checkedList.includes(value.memberId)} onChange={checkListHandler} />}
              <p>{value.name}</p>
              {/* 개별알람 */}
              {isShowAlarmBtn && alarmType === 'custom' && <AlarmBtn receiveId={value.memberId} />}
              {pathname.includes('userDelete') && <DeleteBtn memberId={value.memberId} myMemberId={myMemberId} apiMemberDeleteSuccess={apiMemberDeleteSuccess} />}
            </li>
          ))}
        </ul>
        {isShowAlarmBtn && userList.length !== 0 && <button type="button" onClick={onClickSendAlarm}>알림 전송</button>}
      </div>
    }
  </div>
}

export default UserList;