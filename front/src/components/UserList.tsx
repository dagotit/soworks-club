'use client';

import styles from '@/app/(admin)/admin/Admin.module.css';
import { useGetMemberList, usePostAlarmSend } from '@/hooks/useAdmin';
import React, { useEffect, useState } from 'react';
import { isEmptyArr, isEmptyObj } from '@/utils/common';
import DeleteBtn from '@/app/(admin)/admin/userDelete/DeleteBtn';
import { useAdminStore } from '@/store/useAdmin';
import { usePathname } from 'next/navigation';
import AlarmBtn from '@/app/(admin)/admin/alarm/AlarmBtn';
import { useAlarmStore } from '@/store/useAlarmList';
import { useGetMyInfo } from '@/hooks/useUser';
import { useDialogStore } from '@/store/useDialog';

const SEARCH_TYPE = [{val: 'email', name: '이메일'}, {val: 'name', name: '이름'}];
export type ALARM_LIST_ITEM_TYPE = { memberId: number; content: string; title: string; receiveId: number };

const UserList = () => {
  const pathname = usePathname();
  const [input, setInput] = useState('');
  const [searchInput, setSearchInput] = useState('');
  const [searchType, setSearchType] = useState(SEARCH_TYPE[1].val);
  const [type, setType] = useState(SEARCH_TYPE[1].val);
  const [myMemberId, setMyMemberId] = useState<undefined | number>(undefined);

  // 알림보내기
  const [isShowAlarmBtn,setIsShowAlarmBtn] = useState(pathname.includes('alarm'));
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
      return;
    }
    // data 리스트에 담아보기
    const requestData = checkedList.reduce((acc: ALARM_LIST_ITEM_TYPE[], cur, idx ) => {
      if (alarmList.has(cur)) {
        const getAlarmData = alarmList.get(cur);
        if (!isEmptyObj(getAlarmData) && getAlarmData.title !== '') {
          acc.push({
            memberId: myMemberId,
            content: getAlarmData.text,
            title: getAlarmData.title,
            receiveId: cur
          })
        }
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
    alarmSend.mutate(list, {
      onSuccess: () => {
        open('alert', '알림', '전송완료')
      },
      onError: () => {
        open('alert', '알림', '전송실패<br />잠시후 다시 시도해주세요.')
      }
    })
  }


  // TODO 엔터..로 검색 가능하도록 수정?
  return<div className={styles.adminUserContents}>
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
        <ul>
          {userList.length === 0 && <li>불러올 리스트가 없습니다.</li>}
          {userList.length !== 0 && userList.map((value) => (
            <li key={value.memberId}>
              {isShowAlarmBtn && <input type="checkbox" value={value.memberId} checked={checkedList.includes(value.memberId)} onChange={checkListHandler} />}
              <p>{value.name}</p>
              {isShowAlarmBtn && <AlarmBtn receiveId={value.memberId} />}
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