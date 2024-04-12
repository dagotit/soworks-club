'use client';

import styles from '@/app/(admin)/admin/Admin.module.css';
import { useGetEmailMemberList, useGetMemberList, usePostMemberDelete } from '@/hooks/useAdmin';
import React, { useEffect, useState } from 'react';
import { isEmptyArr, isEmptyObj } from '@/utils/common';
import { useGetMyInfo } from '@/hooks/useUser';

type ListItemType = {
  "email": string;
  "name": string;
  "nickname": string;
  "memberId":number;
  "profileImage": string;
  "companyName": string;
}

const SEARCH_TYPE = [{val: 'email', name: '이메일'}, {val: 'name', name: '이름'}];

const UserDelete = () => {
  const [name, setName] = useState('');
  const [isSearch, setIsSearch] = useState('');
  const [list, setList] = useState<Array<ListItemType>>([]);
  const [myMemberId, setMyMemberId] = useState<undefined | number>(undefined);
  const [searchType, setSearchType] = useState(SEARCH_TYPE[0].val)

  // api
  const memberList = useGetMemberList(name);
  const emailMemberList  = useGetEmailMemberList();
  const deleteMember = usePostMemberDelete();
  const myInfo = useGetMyInfo(list.length !== 0);

  /**
   * @function
   * 멤버 리스트 조회 subscribe
   */
  useEffect(() => {
    if (isEmptyObj(memberList.data)) {
      return;
    }
    setMemberList(memberList.data);
  }, [memberList.data]);

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
      setList([])
      return;
    }
    setList(respBody);
  }

  /**
   * @function
   * 회원 검색 input change event
   */
  function onChangeSearchInput(e: React.ChangeEvent<HTMLInputElement>) {
    setIsSearch(e.currentTarget.value)
  }

  /**
   * @function
   * 회원 검색 [ api ]
   */
  function getMemberList() {
    SEARCH_TYPE.forEach((item) => {
      if (item.val === searchType) {
        setName(isSearch)
      }
    })
  }

  /**
   * @function
   * 회원 삭제하기 [ api ]
   */
  function onClickMemberDelete(id?: number) {
    deleteMember.mutate(id, {
      onSuccess: apiMemberDeleteSuccess,
      onError: (error) => {
        console.log('삭제 api 실패', error)
      }
    })
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
  function test(e: React.ChangeEvent<HTMLSelectElement>) {
    setSearchType(e.currentTarget.value);
  }


  // TODO 엔터..로 검색 가능하도록 수정?
  return<div className={styles.amdinUserUpload}>
    <div>
      <select value={searchType} onChange={test}>
        {SEARCH_TYPE.map((item) => (
          <option key={item.val} value={item.val}>{item.name}</option>
        ))}
      </select>
      <input type="text" value={isSearch} onChange={onChangeSearchInput} />
      <button type="button" onClick={getMemberList}>검색</button>
    </div>
    {!memberList.isLoading &&
      <ul>
        {list.length === 0 && <li>불러올 리스트가 없습니다.</li>}
        {list.length !== 0 && list.map((value) => (
          <li key={value.memberId}>
            <p>{value.name}</p>
            {myMemberId !== value.memberId &&
              <button type="button" onClick={() => onClickMemberDelete(value.memberId)}>삭제</button>
            }
            {myMemberId === value.memberId &&
              <p>본인 삭제 불가</p>
            }
          </li>
        ))}
      </ul>
    }

  </div>
}

export default UserDelete;